package com.dteam.app.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dteam.app.command.ACategorySelectCommand;
import com.dteam.app.command.ACommand;
import com.dteam.app.command.ADarunSelectCommand;
import com.dteam.app.command.ADetailCommand;
import com.dteam.app.command.ADetailPhotoSelectCommand;
import com.dteam.app.command.AMainSelectCommand;
import com.dteam.app.command.anFavDeleteCommand;
import com.dteam.app.command.anFavInsertCommand;
import com.dteam.app.command.anFavSelectCommand;
import com.dteam.app.command.anFavSelectListCommand;
import com.dteam.app.command.anFavUpdateCommand;
import com.dteam.app.command.anFavUpdateMinusCommand;

@Controller
public class ChController {
	ACommand command;
	
	@RequestMapping(value="/anCategorySelect", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anSelectMulti(HttpServletRequest req, Model model){
		ACommand command;
		System.out.println("anCategorySelect()");
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 	
		
		String category = (String)req.getParameter("category");		
		System.out.println("컨트롤러: " + category);
		
		model.addAttribute("category", category);
		
		command = new ACategorySelectCommand(category);
		command.execute(model);
		
		return "anCategorySelect";
	}
	

}//class
