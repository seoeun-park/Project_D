package member;

public class MemberVO {
	/*
	 * MEMBER_ID VARCHAR2(130 BYTE) No 
	 * MEMBER_PW VARCHAR2(30 BYTE) Yes
	 * MEMBER_NICKNAME VARCHAR2(30 BYTE) Yes 
	 * MEMBER_TEL VARCHAR2(30 BYTE) Yes
	 * MEMBER_ADDR VARCHAR2(100 BYTE) Yes 
	 * MEMBER_LATITUDE VARCHAR2(15 BYTE) Yes
	 * MEMBER_LONGITUDE VARCHAR2(15 BYTE) Yes 
	 * MEMBER_GRADE VARCHAR2(20 BYTE) Yes
	 * MEMBER_NAME VARCHAR2(10 BYTE) Yes 
	 * MEMBER_PROFILE VARCHAR2(1024 BYTE) Yes
	 * MEMBER_LOGINTYPE VARCHAR2(10 BYTE) No 
	 * MEMBER_TOKEN VARCHAR2(100 BYTE) Yes
	 */
	
	private String member_id, member_pw, member_nickname, member_tel, member_addr,
			member_latitude, member_longitude, member_grade, member_name, 
			member_profile, member_loginType, member_token;
	
	public MemberVO() {}

	// 기본 생성자(모든 멤버변수가 다 들어가 있음)
	public MemberVO(String member_id, String member_pw, String member_nickname, String member_tel, String member_addr,
			String member_latitude, String member_longitude, String member_grade, String member_name,
			String member_profile, String member_loginType, String member_token) {
		super();
		this.member_id = member_id;
		this.member_pw = member_pw;
		this.member_nickname = member_nickname;
		this.member_tel = member_tel;
		this.member_addr = member_addr;
		this.member_latitude = member_latitude;
		this.member_longitude = member_longitude;
		this.member_grade = member_grade;
		this.member_name = member_name;
		this.member_profile = member_profile;
		this.member_loginType = member_loginType;
		this.member_token = member_token;
	}
	
	// 네이버 소셜 로그인할 때
	public MemberVO(String member_id, String member_nickname, String member_name, String member_loginType,
			String member_token) {
		super();
		this.member_id = member_id;
		this.member_nickname = member_nickname;
		this.member_name = member_name;
		this.member_loginType = member_loginType;
		this.member_token = member_token;
	}
	
	// 카카오 소셜 로그인할 때
	public MemberVO(String member_id, String member_nickname, String member_loginType, String member_token) {
		super();
		this.member_id = member_id;
		this.member_nickname = member_nickname;
		this.member_loginType = member_loginType;
		this.member_token = member_token;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_pw() {
		return member_pw;
	}

	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}

	public String getMember_nickname() {
		return member_nickname;
	}

	public void setMember_nickname(String member_nickname) {
		this.member_nickname = member_nickname;
	}

	public String getMember_tel() {
		return member_tel;
	}

	public void setMember_tel(String member_tel) {
		this.member_tel = member_tel;
	}

	public String getMember_addr() {
		return member_addr;
	}

	public void setMember_addr(String member_addr) {
		this.member_addr = member_addr;
	}

	public String getMember_latitude() {
		return member_latitude;
	}

	public void setMember_latitude(String member_latitude) {
		this.member_latitude = member_latitude;
	}

	public String getMember_longitude() {
		return member_longitude;
	}

	public void setMember_longitude(String member_longitude) {
		this.member_longitude = member_longitude;
	}

	public String getMember_grade() {
		return member_grade;
	}

	public void setMember_grade(String member_grade) {
		this.member_grade = member_grade;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public String getMember_profile() {
		return member_profile;
	}

	public void setMember_profile(String member_profile) {
		this.member_profile = member_profile;
	}

	public String getMember_loginType() {
		return member_loginType;
	}

	public void setMember_loginType(String member_loginType) {
		this.member_loginType = member_loginType;
	}

	public String getMember_token() {
		return member_token;
	}

	public void setMember_token(String member_token) {
		this.member_token = member_token;
	}
	
}
