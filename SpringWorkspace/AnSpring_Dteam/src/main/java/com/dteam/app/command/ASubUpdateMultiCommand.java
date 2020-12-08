package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.ANDao;
import com.dteam.app.dao.JKDao;

public class ASubUpdateMultiCommand implements ACommand{	
	
	@Override
	public void execute(Model model) {
		String id= (String)model.asMap().get("id");
		String name= (String)model.asMap().get("name");
		String nickname= (String)model.asMap().get("nickname");
		String tel= (String)model.asMap().get("tel");
		String addr= (String)model.asMap().get("addr");
		String latitude= (String)model.asMap().get("latitude");
		String longitude= (String)model.asMap().get("longitude");
		
		
		JKDao jkdao = new JKDao();
		
		jkdao.anSubUpdateMulti(id, name, nickname, tel, addr, latitude, longitude);
		
		
	}	 

}
