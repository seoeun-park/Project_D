package detailcomment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailcommentServiceImpl implements DetailcommentService{
	@Autowired private DetailcommentDAO dao;
	
	@Override
	public int detail_comment_regist(DetailcommentVO vo) {
		return dao.detail_comment_regist(vo);
	}

	@Override
	public List<DetailcommentVO> detail_comment_list(String md_serial_number) {
		return dao.detail_comment_list(md_serial_number);	
	}

	@Override
	public int detail_comment_update(DetailcommentVO vo) {
		return dao.detail_comment_update(vo);
	}

	@Override
	public int detail_comment_delete(int id) {
		return dao.detail_comment_delete(id);
	}

}
