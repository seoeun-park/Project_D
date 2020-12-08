package detailcomment;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DetailcommentDAO implements DetailcommentService{

	@Autowired private SqlSession sql;
	
	@Override
	public int detail_comment_regist(DetailcommentVO vo) {
		return sql.insert("detailcomment.mapper.comment_regist", vo);
	}

	@Override
	public List<DetailcommentVO> detail_comment_list(String md_serial_number) {
		return sql.selectList("detailcomment.mapper.comment_list", md_serial_number);
	}

	@Override
	public int detail_comment_update(DetailcommentVO vo) {
		return sql.update("detailcomment.mapper.comment_update", vo);
	}

	@Override
	public int detail_comment_delete(int id) {
		return sql.delete("detailcomment.mapper.comment_delete", id);
	}

}
