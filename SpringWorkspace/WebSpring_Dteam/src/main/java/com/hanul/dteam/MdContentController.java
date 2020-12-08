package com.hanul.dteam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import main.MainServiceImpl;

@Controller
public class MdContentController {
	@Autowired private MainServiceImpl service;
	
	@RequestMapping("/mdcontent.ma")
	public String mdcontent() {
		return "main/mdcontent";
	}
}
