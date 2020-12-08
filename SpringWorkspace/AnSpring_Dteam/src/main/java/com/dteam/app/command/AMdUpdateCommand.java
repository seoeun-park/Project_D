package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.ANDao;

public class AMdUpdateCommand implements ACommand {

	@Override
	public void execute(Model model) {
		String md_name = (String) model.asMap().get("md_name");
		String md_category = (String) model.asMap().get("md_category");
		String md_price = (String) model.asMap().get("md_price");
		String md_rental_term = (String) model.asMap().get("md_rental_term");
		String md_deposit = (String) model.asMap().get("md_deposit");
		String md_detail_content = (String) model.asMap().get("md_detail_content");
		String member_id = (String) model.asMap().get("member_id");
		String md_serial_number = (String) model.asMap().get("md_serial_number");
		
		ANDao adao = new ANDao();
		adao.anMdUpdate(md_name, md_category, md_price, md_rental_term, md_deposit
				, md_detail_content, member_id, md_serial_number);		
	}

}
