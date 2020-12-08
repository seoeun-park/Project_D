package com.dteam.app.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.dteam.app.dao.ANDao;
import com.dteam.app.dao.CHDao;
import com.dteam.app.dto.MdDto;



public class ACategorySelectCommand implements ACommand{
	String category;
	
	public ACategorySelectCommand(String category) {
		this.category = category;
	}
	
	@Override
	public void execute(Model model) {
		CHDao adao = new CHDao();

		
		
		System.out.println("category" + category);
		
		//ArrayList<MdDto> adtos = adao.anCategorySelect(category);
		ArrayList<MdDto> adtos = adao.anCategorySelect(category);
		
		model.addAttribute("anCategorySelect", adtos); 
		
	}

}
