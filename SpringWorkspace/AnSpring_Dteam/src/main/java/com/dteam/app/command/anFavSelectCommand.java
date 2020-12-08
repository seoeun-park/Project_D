package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.JEDao;
import com.dteam.app.dto.FavDto;

public class anFavSelectCommand implements ACommand {

	@Override
	public void execute(Model model) {
		
		JEDao jeDao = new JEDao();
		String member_id = (String) model.asMap().get("member_id");
		String md_serial_number = (String) model.asMap().get("md_serial_number");
		
		System.out.println("command : " + member_id + "&" + md_serial_number);
		
		FavDto favDto = jeDao.anFavSelect(member_id, md_serial_number);
	
		model.addAttribute("anFavSelect", favDto);
	}

}
