package member;

import java.util.HashMap;

public interface MemberService {
	boolean member_insert(MemberVO vo);	//회원가입시 회원정보 저장
	MemberVO member_login(HashMap<String, String> map);		//로그인 처리
	// 로그인 정보를 보다 빨리 찾기 위해 사용하는 것이 Map →  HashMap<key의 parameter, data의 parameter>
	void member_update(MemberVO vo);		//마이페이지에서 회원정보 수정
	MemberVO member_detail(String member_id);	//마이페이지
	boolean member_delete(String userid);	//회원탈퇴처리
	boolean member_id_check(String userid);	//아이디 중복확인
	boolean member_nickname_check(String nickname);	//닉네임 중복확인
	MemberVO social_login(String member_id);	
	// → 네이버 계정이 DB에 저장되어 있는지 확인하고 그 회원정보를 가져오는 메소드
	
	boolean naver_insert(MemberVO vo);
	// → 네이버 계정이 DB에 저장이 안되어 있을 경우 DB에 저장하도록 하는 메소드
	
	boolean kakao_insert(MemberVO vo);
	// → 카카오 계정이 DB에 저장이 안되어 있을 경우 DB에 저장하도록 하는 메소드
	
	int update_token(MemberVO vo);	//토큰 변경시 수정
	String member_searchId(HashMap<String, String> map);	//아이디 찾기
	String member_searchPw(HashMap<String, String> map);	//비밀번호 찾기
	boolean member_resetPw(HashMap<String, String> map);	//비밀번호 재설정
}
