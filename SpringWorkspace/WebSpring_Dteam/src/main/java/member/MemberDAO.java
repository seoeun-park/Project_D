package member;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO implements MemberService{
	@Autowired private SqlSession sql;

	@Override
	public boolean member_insert(MemberVO vo) {
		return sql.insert("member.mapper.join", vo) > 0 ? true : false;
	}

	@Override
	public MemberVO member_login(HashMap<String, String> map) {
		
		return sql.selectOne("member.mapper.login", map);
	}

	@Override
	public void member_update(MemberVO vo) {
		sql.update("member.mapper.update",vo);
	}

	@Override
	public boolean member_delete(String userid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean member_id_check(String userid) {
		
		return (Integer) sql.selectOne("member.mapper.id_check", userid) > 0 ? false : true;
	}
	
	@Override
	public boolean member_nickname_check(String nickname) {
		return (Integer) sql.selectOne("member.mapper.nickname_check", nickname) > 0 ? false : true;
	}

	@Override
	public MemberVO social_login(String member_id) {
		return sql.selectOne("member.mapper.socialLogin", member_id);
	}

	@Override
	public boolean naver_insert(MemberVO vo) {
		return sql.insert("member.mapper.naver_join", vo) > 0 ? true : false;
	}

	@Override
	public int update_token(MemberVO vo) {
		return sql.update("member.mapper.update_token", vo);
	}

	@Override
	public boolean kakao_insert(MemberVO vo) {
		return sql.insert("member.mapper.kakao_join", vo) > 0 ? true : false;
	}

	@Override
	public String member_searchId(HashMap<String, String> map) {
		return sql.selectOne("member.mapper.search_id", map);
	}

	@Override
	public String member_searchPw(HashMap<String, String> map) {
		return sql.selectOne("member.mapper.search_pw", map);
	}

	@Override
	public boolean member_resetPw(HashMap<String, String> map) {
		return sql.update("member.mapper.reset_pw", map) > 0 ? true: false;
	}

	@Override
	public MemberVO member_detail(String member_id) {
		return sql.selectOne("member.mapper.detail", member_id);
	}

}
