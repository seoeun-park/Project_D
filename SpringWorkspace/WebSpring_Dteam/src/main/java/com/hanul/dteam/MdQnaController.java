package com.hanul.dteam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import main.MainServiceImpl;

@Controller
public class MdQnaController {
	@Autowired private MainServiceImpl service;
	
	@RequestMapping("/mdqna.ma")
	public String mdcontent(String md_serial_number, Model model) {
		model.addAttribute("md_serial_number", md_serial_number);
		return "main/mdqna";
	}
}
