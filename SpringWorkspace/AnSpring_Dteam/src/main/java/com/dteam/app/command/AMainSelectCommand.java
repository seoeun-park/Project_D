package com.dteam.app.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.dteam.app.dao.JEDao;
import com.dteam.app.dto.MdDto;

public class AMainSelectCommand implements ACommand{

	@Override
	public void execute(Model model) {
				
		JEDao jeDao = new JEDao();
		
		String member_addr = (String) model.asMap().get("member_addr");
		
		System.out.println("member_addr : " + member_addr);
		
		ArrayList<MdDto> mdDtos = jeDao.anMainSelect(member_addr);
		
		model.addAttribute("anMainSelect", mdDtos);
	}

}
