package common;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import member.MemberVO;

@Service
public class CommonService {
	// 이메일 전송
	/*
	public void sendEmail(String email, String name, HttpSession session) {
		// 1.일반 메일
		// simpleSend(email, name);

		// 2.첨부 파일이 있는 메일
		// attachSend(email, name, session);

		// 3.HTML 형식의 내용을 갖는 메일
		htmlSend(email, name, session);

	}
	*/
	
	// 파일다운로드
	public File download(String filename, String filepath, HttpSession session, HttpServletResponse response) {
		// 다운로드할 파일객체 생성
		File file = new File(session.getServletContext().getRealPath("resources") + filepath);
		String mine = session.getServletContext().getMimeType(filename);
		response.setContentType(mine);

		try {
			filename = URLEncoder.encode(filename, "utf-8");
			response.setHeader("content-disposition", "attachment; filename=" + filename);

			ServletOutputStream out = response.getOutputStream();
			FileCopyUtils.copy(new FileInputStream(file), out);
			out.flush();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return file;
	}

	// 파일업로드
	public String upload(String category, MultipartFile file, HttpSession session) {
		// 업로드해 둘 서버의 물리적 위치
		String resources = session.getServletContext().getRealPath("resources");

		// 업로드해 둘 폴더 지정
		String upload = resources + "/upload";
		// upload/qna/2020/10/12/1234_abc.txt
		// 폴더 만들기
		String folder = upload + "/" + category + "/" + new SimpleDateFormat("yyyy/MM/dd").format(new Date());

		File f = new File(folder);
		if (!f.exists())
			f.mkdirs(); // 폴더가 없으면 만든다.
		String uuid = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

		try {
			file.transferTo(new File(folder, uuid));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// folder:
		// D:\Study_Spring\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\IOT\resources
		return folder.substring(resources.length()) + "/" + uuid;

	}
	
	// 회원가입 인증 메일
	public void emailAuthSend(MemberVO vo, String code) {
		HtmlEmail mail = new HtmlEmail();
		mail.setHostName("smtp.naver.com");
		mail.setCharset("utf-8");
		mail.setDebug(true);

		mail.setAuthentication("hanul0420", "hanul-0420");
		mail.setSSLOnConnect(true);

		try {
			mail.setFrom("hanul0420@naver.com", "한울관리자");
			mail.addTo(vo.getMember_id(), vo.getMember_name());

			mail.setSubject("[대여안대여] 회원가입 이메일 인증메일");
			StringBuffer msg = new StringBuffer();
			msg.append("<html>");
			msg.append("<head>");
			msg.append("<style>");
			msg.append("h1 { color: #3e4ba9; } div { margin-bottom: 20px; }");
			msg.append("a { display: block; background-color: #3e4ba9; color: #ffffff; width: 300px; height: 50px; line-height: 50px; text-align: center; font-weight: bold; }");
			msg.append("</style>");
			msg.append("</head>");
			msg.append("<body>");
			msg.append("<h1>이메일 인증</h1>");
			msg.append("<div>");
			msg.append("안녕하세요 [대여안대여]입니다.<br/> 아래 버튼을 누르고 회원가입을 진행해주세요.");
			msg.append("</div>");
			msg.append("<a href='http://112.164.58.7:8181/dteam/emailAuth?code=" + code + "&member_id=" + vo.getMember_id() + "'>이메일 인증하기</a>");
			msg.append("</body>");
			msg.append("</html>");
			mail.setHtmlMsg(msg.toString());

			mail.send();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	} // emailAuthSend()
	
	// 난수(키)값을 발급해주는 메소드
	public String getRandomCode( int length ){
        char[] charaters = {'a','b','c','d','e','f','g','h','i','j','k','l','m',
        					'n','o','p','q','r','s','t','u','v','w','x','y','z',
        					'0','1','2','3','4','5','6','7','8','9'};
        StringBuffer sb = new StringBuffer();
        Random rn = new Random();

        for( int i = 0 ; i < length ; i++ ){
            sb.append( charaters[ rn.nextInt( charaters.length ) ] );
        }
        
        return sb.toString();
    } //getRandomPassword()
	
	// 비밀번호 재설정하는 이메일 보내기
	public void resetPwSend(String member_id, String member_name, String member_token) {
		HtmlEmail mail = new HtmlEmail();
		mail.setHostName("smtp.naver.com");
		mail.setCharset("utf-8");
		mail.setDebug(true);

		mail.setAuthentication("hanul0420", "hanul-0420");
		mail.setSSLOnConnect(true);

		try {
			mail.setFrom("hanul0420@naver.com", "한울관리자");
			mail.addTo(member_id, member_name);

			mail.setSubject("[대여안대여] 비밀번호 재설정 메일");
			StringBuffer msg = new StringBuffer();
			msg.append("<html>");
			msg.append("<body>");
			msg.append("<h1 style='color: #3e4ba9; margin-bottom: 40px;'>비밀번호 재설정</h1>");
			msg.append("<div style='margin-bottom: 20px; font-size: 15px;'>");
			msg.append("안녕하세요 [대여안대여]입니다.<br/> 아래 버튼을 누르고 비밀번호를 재설정해주세요.");
			msg.append("</div>");
			msg.append("<a href='http://112.164.58.7:8181/dteam/resetPwForm?member_token=" + member_token + "'>비밀번호 재설정하기</a>");
			msg.append("</body>");
			msg.append("</html>");
			mail.setHtmlMsg(msg.toString());

			mail.send();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	} // emailAuthSend()



}// class
