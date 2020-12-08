package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.ANDao;

public class ARentStatusCommand implements ACommand {

	@Override
	public void execute(Model model) {
		String md_rent_status = (String) model.asMap().get("md_rent_status");
		String md_serial_number = (String) model.asMap().get("md_serial_number");
		
		ANDao dao = new ANDao();
		dao.anMdRentStatus(md_rent_status , md_serial_number);
	}

}
