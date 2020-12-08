package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.SEDao;
import com.dteam.app.dto.MemberDto;

public class ANaverLoginCommand implements ACommand {

	@Override
	public void execute(Model model) {
		String member_id = (String)model.asMap().get("member_id");
		String loginType = (String)model.asMap().get("member_loginType");
		
		SEDao dao = new SEDao();
		MemberDto adto = dao.anNaverLogin(member_id, loginType);
		
		model.addAttribute("anNaverLogin", adto); 
		
	}
}
