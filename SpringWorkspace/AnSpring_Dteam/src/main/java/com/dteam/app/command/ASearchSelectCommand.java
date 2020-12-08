package com.dteam.app.command;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import com.dteam.app.dao.ANDao;
import com.dteam.app.dto.MdDto;

public class ASearchSelectCommand implements ACommand {
	
	String searchKeyword;
	
	
	public ASearchSelectCommand(String searchKeyword) {
		this.searchKeyword = searchKeyword;
		
		
	}
	
	@Override
	public void execute(Model model) {
		ANDao adao = new ANDao();	
		
		 
		
		ArrayList<MdDto> mdDtos = adao.anSearchSelect(searchKeyword);
		
		
		model.addAttribute("anSearchSelect", mdDtos);
		
		
	}

}
