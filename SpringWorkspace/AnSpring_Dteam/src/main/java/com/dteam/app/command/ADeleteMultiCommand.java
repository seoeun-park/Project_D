package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.ANDao;
import com.dteam.app.dao.JKDao;


public class ADeleteMultiCommand implements ACommand{	
	
	@Override
	public void execute(Model model) {
		String id = (String)model.asMap().get("id");
		
		JKDao jkdao = new JKDao();
		jkdao.anDeleteMulti(id);		
			
	}	 

}
