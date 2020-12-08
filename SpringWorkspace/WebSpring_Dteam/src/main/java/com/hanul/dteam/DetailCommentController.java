package com.hanul.dteam;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.CommonService;
import detailcomment.DetailcommentPage;
import detailcomment.DetailcommentServiceImpl;
import detailcomment.DetailcommentVO;
import member.MemberVO;

@Controller
public class DetailCommentController {
	@Autowired private DetailcommentServiceImpl service;
	@Autowired private DetailcommentPage page;
	@Autowired private CommonService common;
	
	//상품문의 댓글 저장처리요청
	@ResponseBody @RequestMapping ("/detail.ma/comment/regist")
//	public int comment_regist(String content, int bid, HttpSession session) {
	public int comment_regist(@RequestBody DetailcommentVO vo, HttpSession session) {
		//화면에서 입력한 정보를 DB에 저장한 후 ajax쪽으로 돌아간다
		MemberVO member = (MemberVO) session.getAttribute("login_info");
		vo.setMember_id(member.getMember_id());
		return service.detail_comment_regist(vo);
	}
	
	//방명록 댓글 변경저장처리 요청
	@ResponseBody @RequestMapping (value="/detail.ma/comment/update"
				, produces="application/text; charset=utf-8")
		public String Comment_update(@RequestBody DetailcommentVO vo ) {
			return service.detail_comment_update(vo) > 0 ? "성공" : "실패";
		}
	
	
	//댓글 삭제처리 요청
		@ResponseBody @RequestMapping("/detail.ma/comment/delete/{id}")
		public void comment_delete(@PathVariable int id) {
			service.detail_comment_delete(id);
		}
		
	
	//댓글 목록화면 조회
		@RequestMapping("/detail.ma/comment/{md_serial_number}")
		public String comment_list(@PathVariable String md_serial_number , Model model) {
			model.addAttribute("list", service.detail_comment_list(md_serial_number));
			model.addAttribute("crlf", "\r\n");
			model.addAttribute("lf", "\n");
			return"detailcomment/comment_list";
		}
	
	
}
