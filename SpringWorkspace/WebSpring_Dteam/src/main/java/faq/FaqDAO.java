package faq;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FaqDAO implements FaqService {
	@Autowired private SqlSession sql;
	

	@Override
	public void faq_insert(FaqVO vo) {
		sql.insert("faq.mapper.insert", vo);
		
	}

	@Override
	public List<FaqVO> faq_list() {
		return sql.selectList("faq.mapper.list");
	}

	@Override
	public FaqPage faq_list(FaqPage page) {
		page.setTotalList((Integer)sql.selectOne("faq.mapper.total", page) ); 
		page.setList(sql.selectList("faq.mapper.list", page) ); 
		return page;
	}

	@Override
	public FaqVO faq_detail(int id) {
		return sql.selectOne("faq.mapper.detail", id);
	}

	@Override
	public void faq_update(FaqVO vo) {
		sql.update("faq.mapper.update", vo);
		
	}

	@Override
	public void faq_delete(int id) {
		sql.delete("faq.mapper.delete", id);
		
	}
	
}
