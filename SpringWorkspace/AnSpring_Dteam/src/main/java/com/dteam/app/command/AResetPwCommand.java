package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.ANDao;
import com.dteam.app.dao.SEDao;

public class AResetPwCommand implements ACommand {

	@Override
	public void execute(Model model) {
		String member_id = (String)model.asMap().get("member_id");
		String member_pw = (String)model.asMap().get("member_pw");
	      
	    SEDao dao = new SEDao();
	    int succ = dao.anResetPw(member_id, member_pw);	//일치하는 아이디 반환
	      
	    model.addAttribute("anResetPw", succ);
	}
}
