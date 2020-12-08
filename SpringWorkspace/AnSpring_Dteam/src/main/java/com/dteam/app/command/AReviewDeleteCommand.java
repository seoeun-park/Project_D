package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.ANDao;

public class AReviewDeleteCommand implements ACommand{

	@Override
	public void execute(Model model) {
		ANDao anDao = new ANDao();
		String review_num = (String) model.asMap().get("review_num");
		anDao.anReviewDelete(review_num);		
	}

	
}
