package com.dteam.app.command;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.dteam.app.dao.SEDao;

public class ASendEmailCommand implements ACommand {

	@Override
	public void execute(Model model) {
		String member_id = (String)model.asMap().get("member_id");
		String member_name = (String)model.asMap().get("member_name");
		
		SEDao dao = new SEDao();
		int state = dao.sendEmail(member_id, member_name);
		
		model.addAttribute("anSendEmail", String.valueOf(state)); 
	}
}
