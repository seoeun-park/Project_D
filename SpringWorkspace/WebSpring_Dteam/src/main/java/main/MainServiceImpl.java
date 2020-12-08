
package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import member.MemberVO;

@Service
public class MainServiceImpl implements MainService{
	@Autowired private MainDAO dao;

	@Override
	public MainPage main_list(MainPage page) {
		return dao.main_list(page);
	}

	@Override
	public MainVO main_detail(String md_serial_number) {
		return dao.main_detail(md_serial_number);
	}

	@Override
	public MemberVO member_info(String md_serial_number) {
		return dao.member_info(md_serial_number);
	}
	
}
