package merchandise;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MdDAO implements MdService{

	@Autowired private SqlSession sql;
	
	

	  @Override 
	 public List<MdVO> md_list(String id) { 
		  return sql.selectList("merchandise.mapper.list",id); 
	}

	@Override
	public MdVO md_detail(String md_serial_number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void md_update(MdVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void md_delete(String md_serial_number) {
		// TODO Auto-generated method stub
		
	}



}
