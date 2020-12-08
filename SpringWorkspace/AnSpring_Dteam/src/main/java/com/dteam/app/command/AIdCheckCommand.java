package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.ANDao;
import com.dteam.app.dao.SEDao;
import com.dteam.app.dto.MemberDto;

public class AIdCheckCommand implements ACommand {

	@Override
	public void execute(Model model) {
		String member_id = (String)model.asMap().get("member_id");
		
		SEDao dao = new SEDao();
		MemberDto adto = dao.anIdCheck(member_id);
		
		model.addAttribute("anIdCheck", adto);
	} //execute()
}
