package com.hanul.dteam;

import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import main.MainPage;
import main.MainServiceImpl;
import main.MainVO;
import member.MemberVO;

@Controller
public class MainController {
	@Autowired private MainServiceImpl service;
	@Autowired private MainPage page;
	
	//메인 목록화면 요청
	@RequestMapping("/list.ma")
	public String list(Model model, HttpSession session
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

	//메인 목록화면 요청
	@RequestMapping("/search.ma")
	public String search(Model model, HttpSession session
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
		return "main/search";
	}
	
	@RequestMapping("/detail.ma")
	public String detail(Model model, String md_serial_number){
		model.addAttribute("vo", service.main_detail(md_serial_number));
		
		// 상세 페이지에서 해당 상품 글을 등록한 사용자의 정보(MemberVO)를 가져와서 model 객체에 담음
		MemberVO vo = service.member_info(md_serial_number);
		String member_addr = vo.getMember_addr();
		String member_addr_re;
		
		if(member_addr != null) {
			String[] split = member_addr.split(" ");
			member_addr_re = "";
			
	        for (int i = 0; i < split.length; i++) {
	            if(Pattern.matches("[가-힣]+(시|도|군|구|동|면)", split[i])) {
	                member_addr_re += split[i] + " ";
	            }
	        }
	        vo.setMember_addr(member_addr_re);
		}
        
		
		model.addAttribute("info", vo);
		
		return "main/detail";
	}
	
	
	// 회사 소개 화면 요청
	@RequestMapping("/company")
	public String company() {
		return "company";
	}
	
	// 이용약관 화면 요청
		@RequestMapping("/service")
		public String service() {
			return "terms/service";
		}
		
	// 개인정보처리방침 화면 요청
			@RequestMapping("/privacy")
			public String privacy() {
				return "terms/privacy";
	}
}
