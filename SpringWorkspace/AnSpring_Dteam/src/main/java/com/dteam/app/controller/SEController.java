package com.dteam.app.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dteam.app.command.ACommand;
import com.dteam.app.command.AIdCheckCommand;
import com.dteam.app.command.AJoinCommand;
import com.dteam.app.command.ALoginCommand;
import com.dteam.app.command.ANaverLoginCommand;
import com.dteam.app.command.ANaverJoinCommand;
import com.dteam.app.command.ANickNameCheckCommand;
import com.dteam.app.command.AResetPwCommand;
import com.dteam.app.command.ASearchIdCommand;
import com.dteam.app.command.ASendEmailCommand;
import com.dteam.app.command.AUpdateLocationCommand;
import com.dteam.app.command.KakaoService;
import com.dteam.app.dao.SEDao;

@Controller
public class SEController {
	@Autowired private KakaoService service;

	ACommand command;

	// ************************************************************
	// 일반 회원 로그인
	@RequestMapping(value = "/anLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public String anLogin(HttpServletRequest request, Model model) {
		System.out.println("anLogin()");

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String member_id = (String) request.getParameter("member_id");
		String member_pw = (String) request.getParameter("member_pw");

		System.out.println(member_id);
		System.out.println(member_pw);

		model.addAttribute("member_id", member_id);
		model.addAttribute("member_pw", member_pw);

		command = new ALoginCommand();
		command.execute(model);

		return "anLogin";
	} // anLogin()

	// 네이버 로그인
	@RequestMapping(value = "/anNaverLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public String anNaverLogin(HttpServletRequest req, Model model) {
		System.out.println("anNaverLogin()");
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String member_id = (String) req.getParameter("member_id");
		String member_loginType = (String) req.getParameter("member_loginType");

		System.out.println(member_id);
		System.out.println(member_loginType);

		model.addAttribute("member_id", member_id);
		model.addAttribute("member_loginType", member_loginType);

		command = new ANaverLoginCommand();
		command.execute(model);

		return "anNaverLogin";
	}

	// 카카오 로그인
	@RequestMapping(value = "/anKakaoLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public String anKakaoLogin(HttpServletRequest req, Model model) {
		System.out.println("anNaverLogin()");
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String member_id = (String) req.getParameter("member_id");
		String member_loginType = (String) req.getParameter("member_loginType");

		System.out.println(member_id);
		System.out.println(member_loginType);

		model.addAttribute("member_id", member_id);
		model.addAttribute("member_loginType", member_loginType);

		command = new ANaverLoginCommand();
		command.execute(model);

		return "anKakaoLogin";
	}

	// ************************************************************
	// 일반 회원가입
	@RequestMapping(value = "/anJoin", method = { RequestMethod.GET, RequestMethod.POST })
	public String anJoin(HttpServletRequest request, Model model) {
		System.out.println("anJoin()");

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String member_id = (String) request.getParameter("member_id");
		String member_pw = (String) request.getParameter("member_pw");
		String member_nickname = (String) request.getParameter("member_nickname");
		String member_tel = (String) request.getParameter("member_tel");
		String member_addr = (String) request.getParameter("member_addr");
		String member_latitude = (String) request.getParameter("member_latitude");
		String member_longitude = (String) request.getParameter("member_longitude");
		String member_name = (String) request.getParameter("member_name");
		String member_token = (String) request.getParameter("member_token");

		System.out.println(member_id);
		System.out.println(member_pw);
		System.out.println(member_nickname);
		System.out.println(member_tel);
		System.out.println(member_addr);
		System.out.println(member_latitude);
		System.out.println(member_longitude);
		System.out.println(member_name);
		System.out.println(member_token);

		model.addAttribute("member_id", member_id);
		model.addAttribute("member_pw", member_pw);
		model.addAttribute("member_nickname", member_nickname);
		model.addAttribute("member_tel", member_tel);
		model.addAttribute("member_addr", member_addr);
		model.addAttribute("member_latitude", member_latitude);
		model.addAttribute("member_longitude", member_longitude);
		model.addAttribute("member_name", member_name);
		model.addAttribute("member_token", member_token);

		command = new AJoinCommand();
		command.execute(model);

		return "anJoin";
	} // anJoin()

	// 네이버 회원가입
	@RequestMapping(value = "/anNaverJoin", method = { RequestMethod.GET, RequestMethod.POST })
	public String anNaverJoin(HttpServletRequest req, Model model) {
		System.out.println("anNaverJoin()");

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String member_id = (String) req.getParameter("member_id");
		String member_nickname = (String) req.getParameter("member_nickname");
		String member_name = (String) req.getParameter("member_name");
		String member_loginType = (String) req.getParameter("member_loginType");
		String member_token = (String) req.getParameter("member_token");

		System.out.println(member_id + ", " + member_nickname + ", " + member_name + ", " + member_loginType + ", "
				+ member_token);

		model.addAttribute("member_id", member_id);
		model.addAttribute("member_nickname", member_nickname);
		model.addAttribute("member_name", member_name);
		model.addAttribute("member_loginType", member_loginType);
		model.addAttribute("member_token", member_token);

		command = new ANaverJoinCommand();
		command.execute(model);

		return "anNaverJoin";
	}

	// 카카오 회원가입
	@RequestMapping(value = "/anKakaoJoin", method = { RequestMethod.GET, RequestMethod.POST })
	public String anKakaoJoin(HttpServletRequest req, Model model) {
		System.out.println("anNaverJoin()");

		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String member_id = (String) req.getParameter("member_id");
		String member_nickname = (String) req.getParameter("member_nickname");
		String member_name = (String) req.getParameter("member_name");
		String member_loginType = (String) req.getParameter("member_loginType");
		String member_token = (String) req.getParameter("member_token");

		System.out.println(member_id + ", " + member_nickname + ", " + member_name + ", " + member_loginType + ", "
				+ member_token);

		model.addAttribute("member_id", member_id);
		model.addAttribute("member_nickname", member_nickname);
		model.addAttribute("member_name", member_name);
		model.addAttribute("member_loginType", member_loginType);
		model.addAttribute("member_token", member_token);

		command = new ANaverJoinCommand();
		command.execute(model);

		return "anKakaoJoin";
	}

	// ************************************************************
	// 소셜 로그인할 경우 위치 지정
	@RequestMapping(value = "/anUpdateLocation", method = { RequestMethod.GET, RequestMethod.POST })
	public String anUpdateLocation(HttpServletRequest request, Model model) {
		System.out.println("anUpdateLocation()");
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String member_id = (String) request.getParameter("member_id");
		String member_loginType = (String) request.getParameter("member_loginType");
		String member_addr = (String) request.getParameter("member_addr");
		String member_latitude = (String) request.getParameter("member_latitude");
		String member_longitude = (String) request.getParameter("member_longitude");

		System.out.println(member_id +  ", " +  member_loginType +  ", " +  member_addr +  ", " + member_latitude  +  ", " + member_longitude);

		model.addAttribute("member_id", member_id);
		model.addAttribute("member_loginType", member_loginType);
		model.addAttribute("member_addr", member_addr);
		model.addAttribute("member_latitude", member_latitude);
		model.addAttribute("member_longitude", member_longitude);

		command = new AUpdateLocationCommand();
		command.execute(model);

		return "anUpdateLocation";
	}
	
    @RequestMapping(value="/anKakaoOauth")
    public String anKakaoOauth(Model model) {
    	String code = "8yjsj-0C6dwcxkFSu3m1KTikc9di36--WshSOuzWJlbFFi9f6VEdu80H-OtogiDa0VU3sQo9cpcAAAF09s80DAs";
        System.out.println("code : " + code);

        String access_Token = service.getAccessToken();
        System.out.println("access_Token : " + access_Token);
        
        HashMap<String, Object> userInfo = service.getUserInfo(access_Token);
        
        model.addAttribute("access_Token", access_Token);
        
        if(userInfo.get("email") != null) {
        	model.addAttribute("userId", userInfo.get("email"));
        	model.addAttribute("userNickname", userInfo.get("nickname"));
        	model.addAttribute("userProfile", userInfo.get("profile_image"));
        }
        
        return "anKakaoOauth";
    }

	// ************************************************************
	// 아이디 중복 확인
	@RequestMapping(value = "/anIdCheck", method = { RequestMethod.GET, RequestMethod.POST })
	public String anIdCheck(HttpServletRequest request, Model model) {
		System.out.println("anIdCheck()");

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String member_id = (String) request.getParameter("member_id");

		System.out.println(member_id);

		model.addAttribute("member_id", member_id);

		command = new AIdCheckCommand();
		command.execute(model);

		return "anIdCheck";
	} // anIdCheck()

	// 닉네임 중복확인
	@RequestMapping(value = "/anNickNameCheck", method = { RequestMethod.GET, RequestMethod.POST })
	public String anNickNameCheck(HttpServletRequest request, Model model) {
		System.out.println("anNickNameCheck()");

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String member_nickname = (String) request.getParameter("member_nickname");

		System.out.println(member_nickname);

		model.addAttribute("member_nickname", member_nickname);

		command = new ANickNameCheckCommand();
		command.execute(model);

		return "anNickNameCheck";
	} // anNickNameCheck()

	// 핸드폰 번호로 아이디 찾기
	@RequestMapping(value = "/anSearchId", method = { RequestMethod.GET, RequestMethod.POST })
	public String anSearchId(HttpServletRequest request, Model model) {
		System.out.println("anSearchId()");

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String member_tel = (String) request.getParameter("member_tel");

		System.out.println(member_tel);

		model.addAttribute("member_tel", member_tel);

		command = new ASearchIdCommand();
		command.execute(model);

		return "anSearchId";
	} // anSearchId()

	// 이메일 보내기
	@RequestMapping(value = "/anSendEmail", produces = "text/html; charset=utf-8")
	public String anSendEmail(HttpServletRequest request, HttpSession session, Model model) {
		// 화면에서 입력한 회원정보를 DB에 저장한다.
		String member_id = (String) request.getParameter("member_id");
		String member_name = (String) request.getParameter("member_name");
		// String msg = "<script type='text/javascript'>";

		model.addAttribute("member_id", member_id);
		model.addAttribute("member_name", member_name);

		command = new ASendEmailCommand();
		command.execute(model);

		// msg += "location='" + request.getContextPath() + "'";
		// msg += "</script>";

		return "anSendEmail";
	} // anSendEmail()

	// 비밀번호 재설정 화면 호출
	@RequestMapping(value = "/anResetPwView", method = { RequestMethod.GET, RequestMethod.POST })
	public String anResetPwView(HttpServletRequest request, Model model) {
		System.out.println("anResetPwView()");

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String member_token = (String) request.getParameter("member_token");

		System.out.println(member_token);

		model.addAttribute("member_token", member_token);

		command = new AResetPwCommand();
		command.execute(model);

		return "anResetPwView";
	} // anResetPwView()

	// 비밀번호 재설정한 후 DB에 저장
	@ResponseBody
	@RequestMapping(value = "/anResetPw"/*, produces = "text/html; charset=utf-8"*/)
	public Boolean anResetPw(HttpServletRequest request, String member_pw) {
		System.out.println("anResetPw()");
		//String msg = "<script type='text/javascript'>";
		boolean state;

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String member_token = (String) request.getParameter("member_token");
		//String member_pw = (String) request.getParameter("member_pw");

		System.out.println(member_token);
		System.out.println(member_pw);
		//System.out.println(pw);

		SEDao dao = new SEDao();
		// int succ = adao.anResetPw(member_id, member_pw); //일치하는 아이디 반환
		int succ = dao.anResetPw(member_token, member_pw); // 일치하는 아이디 반환

		if (succ > 0) {
			//msg += "alert('비밀번호가 재설정되었습니다.');";
			state = true;
		} else {
			//msg += "alert('비밀번호 재설정 실패'); history.go(-1)";
			state = false;
		}

		//msg += "</script>";

		return state;
	} // anResetPw()
	
}
