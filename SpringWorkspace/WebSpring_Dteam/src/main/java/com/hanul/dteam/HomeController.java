package com.hanul.dteam;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import main.MainPage;
import main.MainServiceImpl;

@Controller
public class HomeController {
	@Autowired private MainServiceImpl service;
	@Autowired private MainPage page;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale,
					Model model, HttpSession session
				, String keyword, String category
				, @RequestParam(defaultValue = "list") String viewType 
				, @RequestParam(defaultValue = "100") int pageList
				, @RequestParam(defaultValue = "1") int curPage) {
			
			//DB에서 방명록 정보를 조회해와 목록화면에 출력
			page.setCurPage(curPage);
			page.setKeyword(keyword);
			page.setPageList(pageList);
			page.setViewType(viewType);
			page.setCategory(category);
			model.addAttribute("page", service.main_list(page));
		return "main/list";
	}
}