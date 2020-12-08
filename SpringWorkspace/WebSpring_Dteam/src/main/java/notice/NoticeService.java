package notice;

import java.util.List;

public interface NoticeService {
	//CRUD
	void notice_insert(NoticeVO vo);		  		//공지글 저장
	//void notice_reply_insert(NoticeVO vo);		//공지글 답글 저장
	List<NoticeVO> notice_list();					//공지글 목록
	NoticePage notice_list(NoticePage page);		//공지글 목록-페이지처리
	NoticeVO notice_detail(int id);					//공지글 상세
	void notice_update(NoticeVO vo); 				//공지글 수정
	//void notice_read(int id); 					//공지글 조회수증가
	void notice_delete(int id);						//공지글 삭제
	

}
