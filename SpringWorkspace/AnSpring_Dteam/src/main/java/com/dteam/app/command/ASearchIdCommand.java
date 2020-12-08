package com.dteam.app.command;

import org.springframework.ui.Model;

import com.dteam.app.dao.ANDao;
import com.dteam.app.dao.SEDao;
import com.dteam.app.dto.MemberDto;

public class ASearchIdCommand implements ACommand{

   @Override
   public void execute(Model model) {
      String member_tel = (String)model.asMap().get("member_tel");
      
      SEDao dao = new SEDao();
      String id = dao.anSearchId(member_tel);
      
      model.addAttribute("anSearchId", id);
   }
}
