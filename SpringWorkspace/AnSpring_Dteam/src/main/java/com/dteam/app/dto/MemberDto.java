package com.dteam.app.dto;

import java.sql.Date;

public class MemberDto {
    String member_id;
    String member_pw;
    String member_nickname;
    String member_tel;
    String member_addr;
    String member_latitude;
    String member_longitude;
    String member_grade;
    String member_name;
    String member_profile;
    String member_logintype;
    String member_token;

    public MemberDto() {}
    
    // 일반 회원가입 시 데이터베이스에 멤버 정보를 추가할 때
    // 일반 회원가입때 이 생성자 안쓰는데, 혹시 쓰는 사람 있을까봐 남겨둡니다.
    public MemberDto(String member_id, String member_pw, String member_nickname, String member_tel, String member_addr,
			String member_latitude, String member_longitude, String member_grade, String member_name, String member_profile) {
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
	}
    
    // 일반 회원가입 시 데이터베이스에 멤버 정보를 추가할 때
    /*
    public MemberDto(String member_id, String member_pw, String member_nickname, String member_tel, String member_addr,
			String member_latitude, String member_longitude, String member_grade, String member_name,
			String member_profile, String member_logintype, String member_token) {
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
		this.member_logintype = member_logintype;
		this.member_token = member_token;
	}
	*/

	// 암호 없이 멤버 정보를 가져올 때(소셜로그인)
    // 이메일 중복체크할 때
    public MemberDto(String member_id, String member_nickname,
                     String member_tel, String member_addr, String member_latitude,
                     String member_longitude, String member_grade, String member_name, String member_profile
                     , String member_logintype, String member_token) {
        this.member_id = member_id;
        this.member_nickname = member_nickname;
        this.member_tel = member_tel;
        this.member_addr = member_addr;
        this.member_latitude = member_latitude;
        this.member_longitude = member_longitude;
        this.member_grade = member_grade;
        this.member_name = member_name;
        this.member_logintype = member_logintype;
        this.member_token = member_token;
    }
    
    // 암호 없이 멤버 정보를 가져올 때(프로필)
    public MemberDto(String member_id, String member_nickname,
                     String member_tel, String member_addr, String member_latitude,
                     String member_longitude, String member_grade, String member_name, String member_profile) {
        this.member_id = member_id;
        this.member_nickname = member_nickname;
        this.member_tel = member_tel;
        this.member_addr = member_addr;
        this.member_latitude = member_latitude;
        this.member_longitude = member_longitude;
        this.member_grade = member_grade;
        this.member_name = member_name;
        this.member_profile = member_profile; 
    }
    
    
    // 암호 없이 멤버 정보를 가져올 때
    // 이메일 중복체크할 때
    public MemberDto(String member_id, String member_nickname,
                     String member_tel, String member_addr, String member_latitude,
                     String member_longitude, String member_grade, String member_name) {
        this.member_id = member_id;
        this.member_nickname = member_nickname;
        this.member_tel = member_tel;
        this.member_addr = member_addr;
        this.member_latitude = member_latitude;
        this.member_longitude = member_longitude;
        this.member_grade = member_grade;
        this.member_name = member_name;
    }
    
    // 닉네임 중복체크할 때
	public MemberDto(String member_nickname) {
		super();
		this.member_nickname = member_nickname;
	}

	// 네이버(소셜)로 회원가입 & 로그인할 때
	public MemberDto(String member_id, String member_nickname, String member_name, 
					 String member_logintype, String member_token) {
		super();
		this.member_id = member_id;
		this.member_nickname = member_nickname;
		this.member_name = member_name;
		this.member_logintype = member_logintype;
		this.member_token = member_token;
	}

	public String getMember_profile() {
		return member_profile;
	}

	public void setMember_profile(String member_profile) {
		this.member_profile = member_profile;
	}

	public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
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
}