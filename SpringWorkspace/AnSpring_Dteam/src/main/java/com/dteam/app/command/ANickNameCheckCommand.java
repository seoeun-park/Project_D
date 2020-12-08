package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.ANDao;
import com.dteam.app.dao.SEDao;
import com.dteam.app.dto.MemberDto;

public class ANickNameCheckCommand implements ACommand{

	@Override
	public void execute(Model model) {
		String member_nickname = (String)model.asMap().get("member_nickname");
		
		SEDao dao = new SEDao();
		MemberDto adto = dao.anNickNameCheck(member_nickname);
		
		model.addAttribute("anNickNameCheck", adto);
	}

}
