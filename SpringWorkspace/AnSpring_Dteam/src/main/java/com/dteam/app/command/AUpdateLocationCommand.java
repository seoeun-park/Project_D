package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.SEDao;
import com.dteam.app.dto.MemberDto;

public class AUpdateLocationCommand implements ACommand{

	@Override
	public void execute(Model model) {
		String member_id = (String)model.asMap().get("member_id");
		String member_loginType = (String)model.asMap().get("member_loginType");	
		String member_addr = (String)model.asMap().get("member_addr");	
		String member_latitude = (String)model.asMap().get("member_latitude");	
		String member_longitude = (String)model.asMap().get("member_longitude");	
		
		SEDao dao = new SEDao();
		int succ = dao.anUpdateLocation(member_id, member_loginType, member_addr, member_latitude, member_longitude);
		
		model.addAttribute("anUpdateLocation", String.valueOf(succ));
	}

}
