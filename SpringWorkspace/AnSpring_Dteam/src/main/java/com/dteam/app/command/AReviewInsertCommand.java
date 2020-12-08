package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.ANDao;

public class AReviewInsertCommand implements ACommand {

	@Override
	public void execute(Model model) {
		String member_id = (String) model.asMap().get("member_id");
		String review_scope = (String) model.asMap().get("review_scope");
		String review_content = (String) model.asMap().get("review_content");
		String member_nickname = (String) model.asMap().get("member_nickname");
		String md_member_id = (String) model.asMap().get("md_member_id");
		String md_serial_number = (String) model.asMap().get("md_serial_number");
		String member_profile = (String) model.asMap().get("member_profile");
		String review_num = (String) model.asMap().get("review_num");
		
		ANDao adao = new ANDao();
		adao.anReviewInsert(member_id, review_scope, review_content, member_nickname, md_member_id, md_serial_number, member_profile, review_num);
	}

}
