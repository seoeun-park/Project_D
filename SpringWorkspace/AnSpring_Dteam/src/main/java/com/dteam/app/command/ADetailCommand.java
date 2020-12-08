package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.JEDao;
import com.dteam.app.dto.MemberDto;

public class ADetailCommand implements ACommand{

	@Override
	public void execute(Model model) {
		
		JEDao jeDao = new JEDao();
		String member_id = (String) model.asMap().get("member_id");
		
		System.out.println("member_id : " + member_id);
		
		MemberDto adto = jeDao.anDetail(member_id);
		model.addAttribute("anDetail", adto);
	}

}
