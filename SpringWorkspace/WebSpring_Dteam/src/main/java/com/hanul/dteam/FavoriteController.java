package com.hanul.dteam;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import favorite.FavoriteServiceImpl;
import favorite.FavoriteVO;
import member.MemberVO;

@Controller
public class FavoriteController {
	@Autowired private FavoriteServiceImpl service;
	
	//찜화면
	@RequestMapping("/list.vo")
	public String list(Model model, HttpSession session) {
		//DB에서 고객목록을 조회해와 목록화면에 출력할 수 있도록 한다.
		MemberVO member = (MemberVO) session.getAttribute("login_info");
		String id = member.getMember_id();
//		  List<FavoriteVO> list = service.favorite_list(id);
		  model.addAttribute("page", service.favorite_list(id));

		return "favorite/list";
	}
	

}
