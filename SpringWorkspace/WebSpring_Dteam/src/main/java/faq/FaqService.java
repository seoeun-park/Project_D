package faq;

import java.util.List;

public interface FaqService {
	//CRUD
	void faq_insert(FaqVO vo);		  			//FAQ글 저장
	//void faq_reply_insert(NoticeVO vo);		//FAQ글 답글 저장
	List<FaqVO> faq_list();						//FAQ글 목록
	FaqPage faq_list(FaqPage page);				//FAQ글 목록-페이지처리
	FaqVO faq_detail(int id);					//FAQ글 상세
	void faq_update(FaqVO vo); 					//FAQ글 수정
	//void notice_read(int id); 				//FAQ글 조회수증가
	void faq_delete(int id);					//FAQ글 삭제
	
}
