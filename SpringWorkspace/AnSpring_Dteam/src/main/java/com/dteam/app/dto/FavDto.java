package com.dteam.app.dto;

public class FavDto {
	String member_id;
	String md_serial_number;
	String md_fav_status;
	
	
	public FavDto() {}


	public FavDto(String member_id, String md_serial_number, String md_fav_status) {
		super();
		this.member_id = member_id;
		this.md_serial_number = md_serial_number;
		this.md_fav_status = md_fav_status;
	}


	public String getMember_id() {
		return member_id;
	}


	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}


	public String getMd_serial_number() {
		return md_serial_number;
	}


	public void setMd_serial_number(String md_serial_number) {
		this.md_serial_number = md_serial_number;
	}


	public String getMd_fav_status() {
		return md_fav_status;
	}


	public void setMd_fav_status(String md_fav_status) {
		this.md_fav_status = md_fav_status;
	}
	

}//class
