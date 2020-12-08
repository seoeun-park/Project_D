package com.hanul.dteam;

import java.io.File;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import common.CommonService;
import faq.FaqPage;
import faq.FaqServiceImpl;
import faq.FaqVO;
import member.MemberServiceImpl;
import member.MemberVO;



@Controller
public class FaqController {
	@Autowired private FaqServiceImpl service;
	@Autowired private CommonService common;

	//FAQ글 삭제처리 요청
	@RequestMapping("/delete.fa")
	public String delete(int id, HttpSession session) {
		//선택한 문의글을 DB에서 삭제한 후 화면으로 연결
		//첨부파일이 있는 경우 물리적영역에서 파일삭제
		FaqVO vo = service.faq_detail(id);
		if ( vo.getFilename() != null ) {
			File f =  new File( session.getServletContext().getRealPath("resources") + vo.getFilepath() );
			if ( f.exists() ) f.delete();
		}
		
		service.faq_delete(id);
		return "redirect:list.fa";
	}//delete()
	
	
	//FAQ글 수정처리 요청
	@RequestMapping("/update.fa")
	public String update(FaqVO vo, String attach,  MultipartFile file, HttpSession session) {
		//DB에 저장된 파일정보를 조회해온다
		FaqVO faq = service.faq_detail(vo.getId());
		String uuid = session.getServletContext().getRealPath("resources") + faq.getFilepath();

		
		if ( !file.isEmpty()) {
			//파일을 첨부한 경우
			//- 원래 없었는데 변경시 첨부 / 원래 있었는데 변경시 바꿔서 첨부
			vo.setFilename( file.getOriginalFilename());
			vo.setFilepath( common.upload("faq", file, session) );
			
			//원래 있던 파일은 삭제
			if ( faq.getFilename() != null) {
				File f = new File(uuid);
				if (f.exists()) f.delete();
			}
		}else {
			//파일을 첨부하지 않은 경우
			//- 1.원래 첨부된 파일을 삭제 / 2.원래부터 첨부된 파일이 없었던 경우
			//- 3. 원래 첨부된 파일이 있고 그대로 삭제
			if (attach.isEmpty()) {
				//원래 첨부되어 업로드된 파일을 삭제
				if ( faq.getFilename() != null) {
					File f = new File(uuid);
					if (f.exists()) f.delete();
				}
			}else {
				vo.setFilename( faq.getFilename() );
				vo.setFilepath( faq.getFilepath() );
			}
		}
		
		//화면에서 변경한 정보를 DB에 저장한 후 
		service.faq_update(vo);
		
		//상세화면으로 연결
		return "redirect:list.fa?id=" + vo.getId();
	}//update()

	
	
	
	//FAQ글 수정화면 요청
	@RequestMapping("/modify.fa")
	public String modify(Model model, int id) {
		//선택한 FAQ글 정보를 DB에서 조해해와 수정화면에 출력
		model.addAttribute("vo",  service.faq_detail(id) );
		return "faq/modify";
	}//modify()
	
		
	
	
	//첨부파일 다운로드 처리
	@ResponseBody @RequestMapping("/download.fa")
	public void download(int id, HttpSession session, HttpServletResponse response) {
		//해당 글에 첨부된 파일정보를 통해 서버의 물리적위치에서 다운로드한다.
		FaqVO vo = service.faq_detail(id);
		common.download(vo.getFilename(), vo.getFilepath(), session, response);
	}//download()	
		
	
	
	//신규FAQ글 저장처리 요청
	@RequestMapping("/insert.fa")
	public String insert(FaqVO vo, MultipartFile file, 
										HttpSession session) {
		//화면에서 입력한 문의글 정보를 DB에 저장한 후
		MemberVO member = (MemberVO)session.getAttribute("login_info");
		vo.setWriter( member.getMember_id() );
		
		
		//첨부파일이 있는 경우 파일정보를 테이터객체에 담는다
		if( file!=null ) {
			vo.setFilename(file.getOriginalFilename());
			vo.setFilepath( common.upload("faq", file, session)); 	//파일 경로
		}
		
		service.faq_insert(vo);
		
		//목록화면으로 연결
		return "redirect:list.fa";
	}//insert()
		
	
	
	
	//신규 FAQ글 화면 요청
	@RequestMapping("/new.fa")
	public String faq() {
		return "faq/new";
	}//faq()
	
	
	
	@Autowired private MemberServiceImpl member;
	
	@Autowired private FaqPage page;
	
	 
	//FAQ(자주묻는 질문) 목록화면 요청
	@RequestMapping("/list.fa")
	public String list(Model model, @RequestParam(defaultValue = "1") int curPage, HttpSession session
			, String search, String keyword) {
		/*
		 * //임시테스트용 관리자 로그인 HashMap<String, String> map = new HashMap<String, String>();
		 * map.put("id", "admin"); map.put("pwd", "Manager");
		 * session.setAttribute("login_info", member.member_login(map));
		 */ 
		 session.setAttribute("category", "fa");
		 
		 page.setCurPage(curPage);
		 page.setSearch(search); 
		 page.setKeyword(keyword);
		 
		 
		 //DB에서 FAQ글 목록을 조회해와 목록화면에 출력한다. 
		 //model.addAttribute("list",service.faq_list());
		 model.addAttribute("page", service.faq_list(page));
		
		 model.addAttribute("crlf", "\r\n");  //본문 띄어쓰기 적용시키기 
		 model.addAttribute("lf", "\n"); 
		 model.addAttribute("page", page);
		
		return "faq/list";
	}
	
	
}//class
