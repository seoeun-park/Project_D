package com.dteam.app.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.dteam.app.dao.JEDao;
import com.dteam.app.dto.ReviewDto;

public class AReviewSelectCommand implements ACommand {

	@Override
	public void execute(Model model) {
		JEDao jeDao = new JEDao();
		String md_serial_number = (String) model.asMap().get("md_serial_number");
		System.out.println("md_serial_number : " + md_serial_number);
		ArrayList<ReviewDto> reviews = jeDao.anReviewSelect(md_serial_number);
		model.addAttribute("anReviewSelect", reviews);

	}

}
