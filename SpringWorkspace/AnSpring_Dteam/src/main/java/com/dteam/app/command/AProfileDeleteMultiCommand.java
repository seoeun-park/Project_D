package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.ANDao;
import com.dteam.app.dao.JKDao;


public class AProfileDeleteMultiCommand implements ACommand{	
	
	@Override
	public void execute(Model model) {
		String id = (String)model.asMap().get("id");
		String image = (String)model.asMap().get("image");
		
		JKDao jkdao = new JKDao();
		jkdao.anProfileDeleteMulti(id, image);		
			
	}	 

}
