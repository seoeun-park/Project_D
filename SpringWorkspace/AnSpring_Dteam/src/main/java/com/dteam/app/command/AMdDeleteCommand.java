package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.ANDao;

public class AMdDeleteCommand implements ACommand {

	@Override
	public void execute(Model model) {
		ANDao anDao = new ANDao();
		String md_serial_number = (String) model.asMap().get("md_serial_number");
		anDao.anMdDelete(md_serial_number);
	}
	
	
	
}
