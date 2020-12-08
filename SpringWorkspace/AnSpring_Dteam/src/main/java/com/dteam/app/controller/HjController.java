package com.dteam.app.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dteam.app.dao.HjDao;
import com.dteam.app.dto.MdDto;
import com.dteam.app.dto.MemberDto;
import com.dteam.app.dto.ReviewDto;

@Controller
public class HjController {

	
	//상품을 카테고리 별로 가져오는 메소드
	@RequestMapping ("/anMdpull")
	public String anMdpull (String category, String member_id, Model model) {
		HjDao hjdao = new HjDao();
		ArrayList<MdDto> list = hjdao.anMdpull(category, member_id);
		model.addAttribute("list", list);
		return "anMdpull";
	}
	
	@RequestMapping("/anMdDetail")
	public String anMdDetail(String num, Model model) {
		HjDao hjdao = new HjDao();
		model.addAttribute("dto", hjdao.anMdDetail(num));
		return "anMdDetail";
	}
	
	//MemberDto에서 값 가져오는 메소드
	@RequestMapping("/anChatpull")
	public String anChatpull (String member_id, Model model) {
		HjDao hjDao = new HjDao();
		ArrayList<MemberDto> list = hjDao.anChatpull(member_id);
		model.addAttribute("list", list);
		
		return "anChatpull";
	}
	
	//리사이클러뷰 아이템 클릭 시 조회수 증가
	@RequestMapping("/anReadcnt")
	public String anReadcnt (String member_id, Model model) {
		HjDao hjdao = new HjDao();
		ArrayList<MdDto> list = hjdao.anReadcnt(member_id);
		model.addAttribute("list", list);
		
		return "anReadcnt";
	}
	
	//리뷰 어뎁터 
	@RequestMapping ("/anReviewPull")
	public String anReviewPull (String member_id, Model model) {
		HjDao hjdao = new HjDao();
		ArrayList<ReviewDto> list = hjdao.anReviewPull(member_id);
		model.addAttribute("list", list);
		return "anReviewPull";
	}
	
	
	
}
