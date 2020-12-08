package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.ANDao;
import com.dteam.app.dao.JKDao;

public class AUpdateMultiCommand implements ACommand{	
	

	
	@Override
	public void execute(Model model) {
		String id= (String)model.asMap().get("id");
		String dbImgPath= (String)model.asMap().get("dbImgPath");
		
		
		JKDao jkdao = new JKDao();
		
		jkdao.anUpdateMulti(id, dbImgPath);
		
		
	}	

}
