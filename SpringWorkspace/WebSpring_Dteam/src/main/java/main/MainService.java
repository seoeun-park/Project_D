package main;

import java.util.List;

import member.MemberVO;


public interface MainService {
	
	MainPage main_list(MainPage page); //상품목록조회
	MainVO main_detail(String md_serial_number); //상품상세 조회
	MemberVO member_info(String md_serial_number);		//상품 글을 등록한 사용자의 정보 조회
}
