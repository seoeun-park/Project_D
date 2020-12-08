package detailcomment;

import java.util.List;

public interface DetailcommentService {
	
	int detail_comment_regist(DetailcommentVO vo);	//댓글등록
	List<DetailcommentVO> detail_comment_list(String md_serial_number);//목록조회
	int detail_comment_update(DetailcommentVO vo);//댓글 변경
	int detail_comment_delete(int id);	//댓글 삭제
	
	

}
