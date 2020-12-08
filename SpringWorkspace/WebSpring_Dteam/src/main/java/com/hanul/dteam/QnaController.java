package com.hanul.dteam;

import java.io.File;
import java.util.HashMap;

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
import member.MemberServiceImpl;
import member.MemberVO;
import qna.QnaPage;
import qna.QnaServiceImpl;
import qna.QnaVO;

@Controller
public class QnaController {
	@Autowired private QnaServiceImpl service;
	@Autowired private CommonService common;
	
	
	//공지글에 대한 답글쓰기저장처리 요청
	@RequestMapping("/reply_insert.qn")
	public String reply_insert(QnaVO vo, HttpSession session) {
		//화면에서 입력한 정보를 DB에 답글로 저장한후
		//목록화면으로 연결
		vo.setWriter( ((MemberVO)session.getAttribute("login_info")).getMember_id() );
		service.qna_reply_insert(vo);
		return "redirect:list.qn";
	}
	
	
	
	//공지글에 대한 답글쓰기화면 요청
	@RequestMapping("/reply.qn")
	public String reply(int id, Model model) {
		model.addAttribute("vo", service.qna_detail(id));
		return "qna/reply";
	}//reply()
	
	
	
	//문의글 삭제처리 요청
	@RequestMapping("/delete.qn")
	public String delete(int id, HttpSession session) {
		//선택한 문의글을 DB에서 삭제한 후 화면으로 연결
		//첨부파일이 있는 경우 물리적영역에서 파일삭제
		QnaVO vo = service.qna_detail(id);
		if ( vo.getFilename() != null ) {
			File f =  new File( session.getServletContext().getRealPath("resources") + vo.getFilepath() );
			if ( f.exists() ) f.delete();
		}
		
		service.qna_delete(id);
		return "redirect:list.qn";
	}//delete()
	
	
	
	//문의글 수정처리 요청
	@RequestMapping("/update.qn")
	public String update(QnaVO vo, String attach,  MultipartFile file, HttpSession session) {
		//DB에 저장된 파일정보를 조회해온다
		QnaVO qna = service.qna_detail(vo.getId());
		String uuid = session.getServletContext().getRealPath("resources") + qna.getFilepath();

		
		if ( !file.isEmpty()) {
			//파일을 첨부한 경우
			//- 원래 없었는데 변경시 첨부 / 원래 있었는데 변경시 바꿔서 첨부
			vo.setFilename( file.getOriginalFilename());
			vo.setFilepath( common.upload("qna", file, session) );
			
			//원래 있던 파일은 삭제
			if ( qna.getFilename() != null) {
				File f = new File(uuid);
				if (f.exists()) f.delete();
			}
		}else {
			//파일을 첨부하지 않은 경우
			//- 1.원래 첨부된 파일을 삭제 / 2.원래부터 첨부된 파일이 없었던 경우
			//- 3. 원래 첨부된 파일이 있고 그대로 삭제
			if (attach.isEmpty()) {
				//원래 첨부되어 업로드된 파일을 삭제
				if ( qna.getFilename() != null) {
					File f = new File(uuid);
					if (f.exists()) f.delete();
				}
			}else {
				vo.setFilename( qna.getFilename() );
				vo.setFilepath( qna.getFilepath() );
			}
		}
		
		//화면에서 변경한 정보를 DB에 저장한 후 
		service.qna_update(vo);
		
		//상세화면으로 연결
		return "redirect:detail.qn?id=" + vo.getId();
	}//update()

	
	
	
	//문의글 수정화면 요청
	@RequestMapping("/modify.qn")
	public String modify(Model model, int id) {
		//선택한 문의글 정보를 DB에서 조해해와 수정화면에 출력
		model.addAttribute("vo",  service.qna_detail(id) );
		return "qna/modify";
	}//modify()
	
	
	
	//첨부파일 다운로드 처리
	@ResponseBody @RequestMapping("/download.qn")
	public void download(int id, HttpSession session, HttpServletResponse response) {
		//해당 글에 첨부된 파일정보를 통해 서버의 물리적위치에서 다운로드한다.
		QnaVO vo = service.qna_detail(id);
		common.download(vo.getFilename(), vo.getFilepath(), session, response);
	}//download()
	
	
	
	//신규문의글 저장처리 요청
	@RequestMapping("/insert.qn")
	public String insert(QnaVO vo, MultipartFile file, 
										HttpSession session) {
		//화면에서 입력한 문의글 정보를 DB에 저장한 후
		MemberVO member = (MemberVO)session.getAttribute("login_info");
		vo.setWriter( member.getMember_id() );
		
		
		//첨부파일이 있는 경우 파일정보를 테이터객체에 담는다
		if( file!=null ) {
			vo.setFilename(file.getOriginalFilename());
			vo.setFilepath( common.upload("qna", file, session)); 	//파일 경로
		}
		
		service.qna_insert(vo);
		
		//목록화면으로 연결
		return "redirect:list.qn";
	}//insert()
	
	
	
	
	//신규문의글 화면 요청
	@RequestMapping("/new.qn")
	public String qna() {
		return "qna/new";
	}//qna()
	
	
	
	//문의글 상세화면 요청
	@RequestMapping("/detail.qn")
	public String detail(int id, Model model) {
		
		//선택한 문의글 정보를 DB에서 조회해와 상세화면에 출력한다.
		model.addAttribute("vo", service.qna_detail(id) );
		
		model.addAttribute("crlf", "\r\n");	 //본문 띄어쓰기 적용시키기
		model.addAttribute("lf", "\n");
		model.addAttribute("page", page);
		
		return "qna/detail";
	}//detail()
	
	
	
	@Autowired private MemberServiceImpl member;
	
	@Autowired private QnaPage page;

	
	
	//Q&A(1:1문의하기) 목록화면 요청
	@RequestMapping("/list.qn")
	public String list(Model model, @RequestParam(defaultValue = "1") int curPage, HttpSession session
			, String search, String keyword) {
		//임시테스트용 관리자 로그인
		/*
		 * HashMap<String, String> map = new HashMap<String, String>(); map.put("id",
		 * "admin"); map.put("pw", "0000"); session.setAttribute("login_info",
		 * member.member_login(map));
		 */
		session.setAttribute("category", "qn");

		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		
		//DB에서 문의글 목록을 조회해와 목록화면에 출력한다.
		//model.addAttribute("list", service.qna_list());
		model.addAttribute("page", service.qna_list(page));
		
		return "qna/list";
		
	}//list()
	
	
}//class
