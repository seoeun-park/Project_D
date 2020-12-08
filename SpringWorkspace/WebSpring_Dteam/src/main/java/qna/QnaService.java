package qna;

import java.util.List;

public interface QnaService {
	
	//CRUD
		void qna_insert(QnaVO vo);					//문의글 저장
		void qna_reply_insert(QnaVO vo);			//문의글 답글 저장
		List<QnaVO> qna_list();						//문의글 목록
		QnaPage qna_list(QnaPage page);				//문의글 목록-페이지처리
		QnaVO qna_detail(int id);					//문의글 상세
		void qna_update(QnaVO vo); 					//문의글 수정
		//void notice_read(int id); 				//문의글 조회수증가
		void qna_delete(int id);					//문의글 삭제
		
		//List<QnaVO> qna_list(QnaPage page);
	
	
}
