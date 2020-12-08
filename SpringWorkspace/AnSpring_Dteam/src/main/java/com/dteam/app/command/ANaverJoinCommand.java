package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.SEDao;

public class ANaverJoinCommand implements ACommand {

	@Override
	public void execute(Model model) {
		String member_id = (String)model.asMap().get("member_id");
		String member_nickname = (String)model.asMap().get("member_nickname");
		String member_name = (String)model.asMap().get("member_name");
		String member_loginType = (String)model.asMap().get("member_loginType");
		String member_token = (String)model.asMap().get("member_token");
		
		SEDao dao = new SEDao();
		int state  = dao.anNaverJoin(member_id, member_nickname, member_name, member_loginType, member_token);
		
		model.addAttribute("anNaverJoin", String.valueOf(state)); 
	}

}
