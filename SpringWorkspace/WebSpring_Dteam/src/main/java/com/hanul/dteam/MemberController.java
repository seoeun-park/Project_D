package com.hanul.dteam;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scribejava.core.model.OAuth2AccessToken;

import common.CommonService;
import member.KakaoLoginAPI;
import member.MemberServiceImpl;
import member.MemberVO;
import member.NaverLoginBO;

@Controller
public class MemberController {
	@Autowired
	private MemberServiceImpl service;
	@Autowired
	private KakaoLoginAPI kakao;
	@Autowired
	private CommonService common;

	/* NaverLoginBO */
	private NaverLoginBO naverLoginBO;
	private String apiResult = null;

	@Autowired
	private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
		this.naverLoginBO = naverLoginBO;
	}

	// 마이페이지 화면 요청
	@RequestMapping("/mypage")
	public String mypage(Model model, String member_id, HttpSession session) {
		session.setAttribute("header_menu", "mypage");
		model.addAttribute("vo", service.member_detail(member_id));
		return "member/mypage";
	}

	// 회원정보수정 화면 요청
	@RequestMapping("/modify")
	public String modify(Model model, String member_id) {
		model.addAttribute("vo", service.member_detail(member_id));
		return "member/modify";
	}

	// 회원정보수정
	@RequestMapping("/update")
	public String update(MemberVO vo, HttpSession session) {
		service.member_update(vo);
		session.setAttribute("login_info", vo);
		return "redirect:mypage?member_id=" + vo.getMember_id();
	}

	/****************************************
	 * 로그인
	 ********************************/

	// 로그인 화면 요청
	@RequestMapping("/login_view")
	public String login(HttpSession session, Model model) {

		return "member/login";
	}

	// 로그인 처리
	@ResponseBody
	@RequestMapping("/login")
	public MemberVO login(String userId, String userPw, HttpSession session, Model model) {

		// 화면에서 입력한 아이디, 비번이 일치하는 회원정보를 가져옴
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", userId);
		map.put("pw", userPw);
		MemberVO vo = service.member_login(map);

		/*
		 * if(vo == null) { state = false; } else if(vo.getMember_token() == null) {
		 * state = false; } else { System.out.println("토큰 : " + vo.getMember_token());
		 * state = true; }
		 */

		if (vo != null) { // 아이디와 비밀번호가 일치하거나
			if (vo.getMember_token() != null) { // 토큰값이 DB에 저장이되있어야만(이메일 인증)
				// DB에서 조회해와 세션에 담는다.
				session.setAttribute("login_info", vo);
			}
		}

		return vo;
		// 화면을 넘기는 것이 아니라 ajax로 통신한 쪽(header.jsp)으로 데이터를 가지고 오기만 하면 된다.
		// return "true"으로 작성할 경우 true.jsp라는 화면으로 넘기게 된다.
	} // login()

	@RequestMapping(value = "/naver_login", method = { RequestMethod.GET, RequestMethod.POST })
	public String naver_login(Model model, HttpSession session) {

		// 네이버 아이디로 인증 URL을 생성하기 위하여 naverLoginBO 클래스의 getAuthorizationUrl 메소드 호출
		System.out.println("naver_login session> " + session);
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
		// https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=sE***************&
		// redirect_uri=http%3A%2F%2F211.63.89.90%3A8090%2Flogin_project%2Fcallback&state=e68c269c-5ba9-4c31-85da-54c16c658125
		System.out.println("네이버:" + naverAuthUrl);

		System.out.println("oauth_state > " + session.getAttribute("oauth_state"));
		// 네이버
		// model.addAttribute("naverAuthUrl", naverAuthUrl);

		return "redirect:" + naverAuthUrl;
	} // login()

	// 네이버 로그인 성공시 callback호출 메소드
	@ResponseBody
	@RequestMapping(value = "/naver_callback", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/html; charset=utf-8")
	public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session)
			throws IOException, ParseException {
		String msg = "<script type='text/javascript'>";
		String member_id, member_nickname, member_name, member_loginType = "N", member_token;
		MemberVO naverVO = null;
		Boolean result = false;

		System.out.println("naver_callback oauth_state > " + session.getAttribute("oauth_state"));
		OAuth2AccessToken oauthToken = null;
		oauthToken = naverLoginBO.getAccessToken(session, code, state);
		System.out.println("여기는 callback1");

		// 로그인 사용자 정보를 읽어온다. : oauthToken이 null
		if (oauthToken != null) {
			apiResult = naverLoginBO.getUserProfile(oauthToken);

			System.out.println("여기는 callback3");

			model.addAttribute("result", apiResult);

			// System.out.println(apiResult);

			try {
				System.out.println("여기는 callback4 : try 구문 안");
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(apiResult);
				JSONObject responseInfo = (JSONObject) jsonObject.get("response");

				member_id = member_loginType + (String) responseInfo.get("email");
				System.out.println("네이버 회원 프로필의 아이디 : " + member_id);

				member_nickname = (String) responseInfo.get("nickname");
				member_name = (String) responseInfo.get("name");
				member_token = oauthToken.getAccessToken();
				// System.out.println("access 토큰 = " + oauthToken.getAccessToken());
				// System.out.println("기한 = " + oauthToken.getExpiresIn());
				// System.out.println("refresh 토큰 = " + oauthToken.getRefreshToken());

				// 네이버(소셜 로그인) 계정이 DB에 저장되어 있는 지 확인
				naverVO = service.social_login(member_id);

				if (naverVO == null) { // 네이버 계정이 DB에 저장 x
					naverVO = new MemberVO(member_id, member_nickname, member_name, member_loginType, member_token);
					result = service.naver_insert(naverVO); // DB에 저장 시킴
				} else {
					// 마지막 토큰 :
					// AAAAOs44pMDCKPkYZtEyQ3eMdG-6QWMpNcCtE4_wvZ9dwUp1GuNmEPBRNU6un6AS1a5_9tFqtIonX6IgR3TBEJFJD3k
					if (!member_token.equals(naverVO.getMember_token())) { // 토큰 재발급되었을 때
						System.out.println("access 토큰 = " + member_token);
						System.out.println("db 토큰 = " + naverVO.getMember_token());
						if (service.update_token(naverVO) > 0) {
							System.out.println("토큰 DB 업데이트 o");
						} else {
							System.out.println("토큰 DB 업데이트 x");
						}
					} else {
						System.out.println("토큰 값 그대로");
					}
					result = true;
					System.out.println("vo의 아이디 : " + naverVO.getMember_id());
				}
				// String member_id_c = member_id.substring(member_id.indexOf(member_loginType)
				// + 1, member_id.length());
				// System.out.println(member_id_c);
				// naverVO.setMember_id(member_id_c);
				session.setAttribute("login_info", naverVO);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (result) {
			msg += "location.href = '/dteam'";
		} else {
			msg += "alert('네이버 소셜 로그인에 실패하셨습니다. 관리자에게 문의하세요'); location.href = '/dteam/login_view'";
		}

		msg += "</script>";

		return msg;
	}

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/kakao_login", produces =
	 * "text/html; charset=utf-8") public String kakao_login(@RequestParam(value =
	 * "code", required = false) String code, HttpSession session) throws Exception
	 * { System.out.println(code); String access_token = kakao.getAccessToken(code);
	 * System.out.println("Controller access token : " + access_token);
	 * 
	 * HashMap<String, Object> userInfo = kakao.getUserInfo(access_token);
	 * System.out.println("아이디 : " + userInfo.get("email"));
	 * System.out.println("닉네임 : " + userInfo.get("nickname"));
	 * 
	 * Boolean result = false; String msg = "<script type='text/javascript'>";
	 * String member_id = (String) userInfo.get("email"); String member_nickname =
	 * (String) userInfo.get("nickname"); String member_id_c, member_loginType =
	 * "K"; String member_token = access_token; MemberVO vo = null;
	 * 
	 * if(member_id != null) { // 토큰을 통해 사용자 정보를 가져올 경우 // 카카오(소셜 로그인) 계정이 DB에 저장되어
	 * 있는 지 확인 vo = service.social_login(member_id);
	 * 
	 * if(vo == null) { //네이버 계정이 DB에 저장 x vo = new MemberVO(member_id,
	 * member_nickname, member_loginType, member_token); result =
	 * service.naver_insert(vo); //DB에 저장 시킴 } else {
	 * if(!access_token.equals(vo.getMember_token())) { //토큰 재발급되었을 때
	 * System.out.println("access 토큰 = " + access_token);
	 * System.out.println("db 토큰 = " + vo.getMember_token());
	 * vo.setMember_token(access_token);
	 * 
	 * if(service.update_token(vo) > 0) { System.out.println("토큰 DB 업데이트 o"); } else
	 * { System.out.println("토큰 DB 업데이트 x"); } } else {
	 * System.out.println("토큰 값 그대로"); }
	 * 
	 * result = true; System.out.println("vo의 아이디 : " + vo.getMember_id()); }
	 * 
	 * member_id = vo.getMember_id(); member_loginType = vo.getMember_loginType();
	 * 
	 * member_id_c = member_id.substring(member_id.indexOf(member_loginType) + 1,
	 * member_id.length()); System.out.println(member_id_c);
	 * vo.setMember_id(member_id_c); session.setAttribute("login_info", vo); } else
	 * { msg += "alert('네이버 소셜 로그인에 실패하셨습니다.\n 관리자에게 문의하세요'); history.go(-1)"; }
	 * 
	 * if (result) { msg += "alert('네이버 소셜 로그인 성공'); location.href = '/dteam'"; }
	 * else { msg += "alert('네이버 소셜 로그인에 실패하셨습니다.\n 관리자에게 문의하세요'); history.go(-1)";
	 * }
	 * 
	 * msg += "</script>";
	 * 
	 * return "msg"; }
	 */

	@ResponseBody
	@RequestMapping("/kakao_login")
	public Boolean kakao_login(String member_id, String member_nickname, String member_token, HttpSession session) {
		String member_id_c, member_loginType = "K";
		member_id = member_loginType + member_id;
		Boolean result = false;

		System.out.println("Controller의 member_id : " + member_id);
		System.out.println("Controller의 member_nickname : " + member_nickname);
		System.out.println("Controller의 member_token : " + member_token);

		// 카카오(소셜 로그인) 계정이 DB에 저장되어 있는 지 확인
		MemberVO kakaoVO = service.social_login(member_id);

		if (kakaoVO == null) { // 카카오 계정이 DB에 저장 x
			System.out.println("카카오 계정이 DB에 저장 x");
			kakaoVO = new MemberVO(member_id, member_nickname, member_loginType, member_token);
			result = service.kakao_insert(kakaoVO); // DB에 저장시킴
		} else { // 카카오 계정이 DB에 저장 o
			result = true;
			System.out.println("카카오 계정이 DB에 저장 o");

			if (member_token.equals(kakaoVO.getMember_token())) {
				// 토큰이 재발급되었을 때
				System.out.println("db 토큰 : " + kakaoVO.getMember_token());
				System.out.println("재발급된 토큰 : " + member_token);

				if (service.update_token(kakaoVO) > 0) {
					System.out.println("토큰 DB 업데이트 o");
				} else {
					System.out.println("토큰 DB 업데이트 x");
				}
			} else {
				System.out.println("토큰 값 그대로");
			}
		}

		//member_id_c = member_id.substring(member_id.indexOf(member_loginType) + 1, member_id.length());
		//System.out.println("자른 member_id : " + member_id_c);
		//kakaoVO.setMember_id(member_id_c);
		session.setAttribute("login_info", kakaoVO);

		return result;
	}

	// 로그아웃 처리
	@ResponseBody
	@RequestMapping("/logout")
	public void logout(HttpSession session) {
		// 세션의 로그인정보를 삭제한다.
		session.removeAttribute("login_info");

		// 로그아웃 한 후 가지고갈 데이터가 없기 때문에 반환할 값이 없다.
	} // logout()

	/****************************************
	 * 회원가입
	 ******************************/

	// 회원가입처리 요청
	@ResponseBody
	@RequestMapping(value = "/join", produces = "text/html; charset=utf-8")
	public String join(MemberVO vo, HttpServletRequest request, HttpSession session) {
		// 화면에서 입력한 회원정보를 DB에 저장한다.
		String msg = "<script type='text/javascript'>";
		if (service.member_insert(vo)) {
			String code = common.getRandomCode(8);
			common.emailAuthSend(vo, code);
			// 8자리 난수를 생성하여 이메일을 보냄
			System.out.println("Controller의 join : code = " + code);

			msg += "alert('회원가입을 축하합니다. 이메일을 인증해주세요.'); location='" + request.getContextPath() + "'";

		} else {
			msg += "alert('회원가입에 실패했습니다. 관리자에게 문의하세요.'); history.go(-1)";
		}

		msg += "</script>";

		return msg;
	} // join()

	// 회원가입화면 요청
	@RequestMapping("/member")
	public String member(HttpSession session) {
		session.removeAttribute("category");
		// → 회원가입 화면으로 이동할때는 카테고리에 속하지 않으므로 카테고리 정보 삭제

		return "member/join";
	} // member()

	// 아이디 중복확인 요청
	@ResponseBody
	@RequestMapping("/id_check")
	public boolean id_check(String id) {
		// 화면에서 입력한 아이디가 DB에 존재하는지 여부를 판단한다

		return service.member_id_check(id);
	} // id_check()

	// 닉네임 중복확인 요청
	@ResponseBody
	@RequestMapping("/nickname_check")
	public boolean nickname_check(String nickname) {
		// 화면에서 입력한 아이디가 DB에 존재하는지 여부를 판단한다

		return service.member_nickname_check(nickname);
	} // nickname_check()

	// 이메일 인증 버튼 누를 때
	@ResponseBody
	@RequestMapping(value = "/emailAuth", produces = "text/html; charset=utf-8")
	public String email_auth(@RequestParam String code, @RequestParam String member_id) {
		String msg = "<script type='text/javascript'>";
		MemberVO vo = new MemberVO();
		vo.setMember_id(member_id);
		vo.setMember_token(code);

		if (service.update_token(vo) > 0) {
			msg += "location.href = '/dteam'";
		} else {
			msg += "alert('이메일 인증 실패했습니다. 관리자에게 문의하세요.'); history.go(-1)";
		}

		msg += "</script>";

		return msg;
	} // email_auth()

	// 아이디/비밀번호 찾기 화면 요청
	@RequestMapping("/searchIdPw_view")
	public String searchIdPw_view() {

		return "member/searchIdPw_view";
	} // searchIdPw_view()

	// 아이디 찾기 화면 요청
	@RequestMapping("/searchId_view")
	public String searchId_view(HttpSession session) {
		session.setAttribute("searchType", "id");

		return "member/searchId";
	}

	// 비밀번호 찾기 화면 요청
	@RequestMapping("/searchPw_view")
	public String searchPw_view(HttpSession session) {
		session.setAttribute("searchType", "pw");

		return "member/searchPw";
	}

	// 아이디 찾기
	@ResponseBody
	@RequestMapping("/searchId")
	public String searchId(String member_name, String member_tel) {
		// 화면에서 입력한 이름, 핸드폰 번호가 일치하면 회원 아이디를 가져옴
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("member_name", member_name);
		map.put("member_tel", member_tel);
		String member_id = service.member_searchId(map);

		String[] split = member_id.split("@");
		int split0length = split[0].length(); // 5
		String email = split[0].replace(split[0].substring(split0length - 3, split0length), "***");
		email += "@" + split[1];

		return email;
	}

	// 비밀번호 재설정
	@ResponseBody
	@RequestMapping("/searchPw")
	public boolean searchPw(String member_name, String member_id) {
		boolean state = false;

		// 화면에서 입력한 이름, 핸드폰 번호가 일치하면 회원 아이디를 가져옴
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("member_name", member_name);
		map.put("member_id", member_id);
		String member_token = service.member_searchPw(map);

		if (member_token != null) {
			state = true;
			common.resetPwSend(member_id, member_name, member_token); // 이메일 보내기
		}

		return state;
	}

	// 비밀번호 재설정 화면 요청
	@RequestMapping("/resetPwForm")
	public String resetPwForm(@RequestParam String member_token, HttpSession session) {
		session.setAttribute("member_token", member_token);
		System.out.println("resetPwForm()의 토큰 : " + member_token);

		return "member/resetPwForm";
	}

	// 재설정된 비밀번호를 DB에 저장
	@ResponseBody
	@RequestMapping(value = "resetPw")
	public boolean restPw(String member_pw, String member_token) {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("member_pw", member_pw);
		map.put("member_token", member_token);

		return service.member_resetPw(map);
	}

}
