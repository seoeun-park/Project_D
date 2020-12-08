package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.JEDao;
import com.dteam.app.dto.MdDto;

public class anFavUpdateCommand implements ACommand {

	@Override
	public void execute(Model model) {

		JEDao jeDao = new JEDao();
		String md_serial_number = (String) model.asMap().get("md_serial_number");
		System.out.println("comm md_serial_number : " + md_serial_number);
		jeDao.anFavUpdate(md_serial_number);
	}

}//class
