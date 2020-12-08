package merchandise;

import java.util.List;

public interface MdService {
	//CRUD(Create, Read, Update, Delete)
	List<MdVO> md_list(String id);		//제품목록 조회
	MdVO md_detail(String md_serial_number);	//제품 상세정보 조회
	void md_update(MdVO vo);	//제품정보 업데이트
	void md_delete(String md_serial_number);	//제품정보 삭제
}
