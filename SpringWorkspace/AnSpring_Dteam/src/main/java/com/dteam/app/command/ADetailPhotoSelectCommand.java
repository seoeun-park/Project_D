package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.JEDao;
import com.dteam.app.dto.MdDto;

public class ADetailPhotoSelectCommand implements ACommand{

	@Override
	public void execute(Model model) {
		
		
		JEDao jeDao = new JEDao();
		
		
		String md_serial_number = (String) model.asMap().get("md_serial_number");
		//String member_id = (String) model.asMap().get("member_id");
		//String md_photo_url = (String) model.asMap().get("md_photo_url");
		
		
		System.out.println("md_serial_number : " + md_serial_number);
		//System.out.println("member_id : " + member_id);
		//System.out.println("md_photo_url : " + md_photo_url);
		
		 
		MdDto mdDto = jeDao.anDetailPhoto(md_serial_number);
		
		model.addAttribute("anDetailPhoto", mdDto);
	}
	
	
	
}
