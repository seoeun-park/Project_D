package com.dteam.app.dto;

public class ReviewDto {
	String member_id;
	String review_scope;
	String review_content;
	String review_num;
	String member_nickname;
	String md_member_id;
	String md_serial_number;
	String member_profile;
	
	
	public ReviewDto() {}
	
	public ReviewDto(String member_id, String review_scope, String review_content, String review_num
			, String member_nickname, String md_member_id, String md_serial_number, String member_profile) {
		super();
		this.member_id = member_id;
		this.review_scope = review_scope;
		this.review_content = review_content;
		this.review_num = review_num;
		this.member_nickname = member_nickname;
		this.md_member_id = md_member_id;
		this.md_serial_number = md_serial_number;
		this.member_profile = member_profile;
		
	}
	
	
	public String getReview_num() {
		return review_num;
	}

	public void setReview_num(String review_num) {
		this.review_num = review_num;
	}

	public String getMember_profile() {
		return member_profile;
	}

	public void setMember_profile(String member_profile) {
		this.member_profile = member_profile;
	}

	public String getMd_serial_number() {
		return md_serial_number;
	}

	public void setMd_serial_number(String md_serial_number) {
		this.md_serial_number = md_serial_number;
	}

	public String getMd_member_id() {
		return md_member_id;
	}

	public void setMd_member_id(String md_member_id) {
		this.md_member_id = md_member_id;
	}

	public String getMember_nickname() {
		return member_nickname;
	}

	public void setMember_nickname(String member_nickname) {
		this.member_nickname = member_nickname;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getReview_scope() {
		return review_scope;
	}

	public void setReview_scope(String review_scope) {
		this.review_scope = review_scope;
	}

	public String getReview_content() {
		return review_content;
	}

	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}
	
	
	
	
}
