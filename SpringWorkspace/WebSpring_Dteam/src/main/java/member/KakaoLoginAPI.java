package member;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class KakaoLoginAPI {
	public String getAccessToken (String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";
        
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            // POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            
            // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=8f9058216b087187fa01c4f671353a08");
            sb.append("&redirect_uri=http://www.localhost:8080/dteam/kakao_login");
            sb.append("&code=" + authorize_code);
            sb.append("&client_secret=Lnes9HsZSjq0qjqiCZZ8oxXBgFGLfF2T");
            bw.write(sb.toString());
            bw.flush();
            
            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
 
            // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);
            
            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JSONParser jsonParser = new JSONParser();
            JSONObject responseInfo;
			try {
				responseInfo = (JSONObject) jsonParser.parse(result);
	            access_Token = (String) responseInfo.get("access_token");
	            refresh_Token = (String) responseInfo.get("refresh_token");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);
            
            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        return access_Token;
    } //getAccessToken()
	
	
	public HashMap<String, Object> getUserInfo (String access_Token) {
		String result = "";
        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            // 요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token.trim());
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //conn.setRequestProperty("charset", "utf-8");
            
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            
            if(responseCode == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String line = "";
            //String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            
            System.out.println("response body : " + result);
            
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            
            userInfo.put("nickname", nickname);
            userInfo.put("email", email);
            } else {
            	System.out.println("결과" + result);
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return userInfo;
    } //getUserInfo()


	
	/* 
	public MemberVO getUserInfo (String access_Token) {
		String member_id, member_nickname, member_loginType = "K", member_token;
		MemberVO kakao_vo = null;
		
	    String reqURL = "https://kapi.kakao.com/v2/user/me";
	    try {
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        
	        // 요청에 필요한 Header에 포함될 내용
	        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
	        
	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);
	        
	        	
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}

			System.out.println("response body : " + result);

			try {
				JSONParser parser = new JSONParser();
				JSONObject jsonObject;

				jsonObject = (JSONObject) parser.parse(result);
				JSONObject properties = (JSONObject) jsonObject.get("properties");
				JSONObject kakao_account = (JSONObject) jsonObject.get("kakao_account");

				member_nickname = (String) properties.get("nickname");
				member_id = (String) kakao_account.get("email");
				member_token = access_Token;

				System.out.println("이메일 : " + member_id);
				System.out.println("닉네임 : " + member_nickname);
				System.out.println("토큰 : " + access_Token);

				kakao_vo = new MemberVO(member_id, member_nickname, member_loginType, member_token);

			} catch (ParseException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    
	    return kakao_vo;
	} //getUserInfo()
	*/
}
