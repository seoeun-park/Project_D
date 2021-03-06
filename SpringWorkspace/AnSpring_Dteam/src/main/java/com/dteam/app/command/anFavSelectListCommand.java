package com.dteam.app.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.dteam.app.dao.JEDao;
import com.dteam.app.dto.MdDto;

public class anFavSelectListCommand implements ACommand {

	@Override
	public void execute(Model model) {
		JEDao jeDao = new JEDao();
		String member_id = (String) model.asMap().get("member_id");
		System.out.println("comm member_id : " + member_id);
		ArrayList<MdDto> mdDtos = jeDao.anFavSelectList(member_id);
		model.addAttribute("anFavSelectList", mdDtos);

	}

}
