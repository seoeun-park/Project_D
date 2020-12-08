package merchandise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MdServiceImpl implements MdService{
	@Autowired private MdDAO dao;


	@Override 
	public List<MdVO> md_list(String id) {
		return dao.md_list(id); 
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
