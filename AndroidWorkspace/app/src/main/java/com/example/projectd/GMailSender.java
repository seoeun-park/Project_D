package com.example.projectd;

import android.os.AsyncTask;
import android.util.Log;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;

public class GMailSender extends javax.mail.Authenticator {
    private static final String LOG = "GMailSender";

    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;
    private String emailCode;

    static {
        Security.addProvider(new com.example.projectd.JSSEProvider());
    }

    public GMailSender(String user, String password) {
        this.user = user;
        this.password = password;
        emailCode = createEmailCode();

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        //구글에서 지원하는 smtp 정보를 받아와 MimeMessage 객체에 전달해준다.
        session = Session.getDefaultInstance(props, this);
    }


    public String getEmailCode() {
        return emailCode;
    } //생성된 이메일 인증코드 반환

    private String createEmailCode() { //이메일 인증코드 생성
        String[] str = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String newCode = new String();

        for (int x = 0; x < 8; x++) {
            int random = (int) (Math.random() * str.length);
            newCode += str[random];
        }

        return newCode;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        //해당 메서드에서 사용자의 계정(id & password)을 받아 인증받으며 인증 실패시 기본값으로 반환됨.
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(String subject, String body, String sender, String recipients) throws Exception {
        try {
            MimeMessage message = new MimeMessage(session);
            DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
            message.setSender(new InternetAddress(sender)); //본인 이메일 설정
            message.setSubject(subject);    //해당 이메일의 본문 설정
            message.setDataHandler(handler);
            if (recipients.indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            else
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
            Transport.send(message);    //메시지 전달
        } catch (Exception e) {

        }
    }

    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }


    private class EmailSenderTask extends AsyncTask<Void, Void, Void> {
        public final int REPLY_TO_ADDRESSES_AMOUNT = 1;

        private String subject;
        private String recipients;
        private InternetAddress from;
        private InternetAddress[] replyToAddresses;
        private MimeMessage message;
        private DataHandler handler;

        public EmailSenderTask(final String from, String subject, String body, String sender,
                               String recipients) {
            this.subject = subject;
            this.recipients = recipients;

            try {
                this.from = new InternetAddress(from.contains("@") ? from : sender);
            } catch (AddressException e) {
                Log.e(LOG, e.getMessage(), e);
            }

            this.replyToAddresses = new InternetAddress[REPLY_TO_ADDRESSES_AMOUNT];
            this.replyToAddresses[0] = this.from;

            this.message = new MimeMessage(session);
            this.handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
        }

        @Override
        protected void onPreExecute() {
            try {
                // TODO: gmail seems to override this... need to find out how to set the sender
                // COMMENT: Added reply to address so that the user would be able to reply directly to the sender.
                this.message.setSender(this.from);
                this.message.setFrom(this.from);
                this.message.setReplyTo(this.replyToAddresses);
                this.message.setSubject(this.subject);
                this.message.setDataHandler(this.handler);

                if (this.recipients.indexOf(',') > 0) {
                    this.message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(this.recipients));
                } else {
                    this.message.setRecipient(Message.RecipientType.TO, new InternetAddress(
                            this.recipients));
                }
            } catch (Exception e) {
                Log.e(LOG, e.getMessage(), e);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Transport.send(this.message);
            } catch (Exception e) {
                Log.e(LOG, e.getMessage(), e);
            }

            return null;
        }
    } //EmailSenderTask()
}

/*
Gmail smtp 사용할 경우 보안 수준이 낮은 앱 설정을 사용해야 한다.
아니면 구글에서 접근을 차단하기 때문

지메일 로그인 후 https://www.google.com/settings/security/lesssecureapps 접속해서 사용으로 선택한다
 */

