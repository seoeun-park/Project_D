package detailcomment;

public class DetailcommentVO {
	private int id;
	private String md_serial_number, member_id, content;
	private String writedate;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMd_serial_number() {
		return md_serial_number;
	}
	public void setMd_serial_number(String md_serial_number) {
		this.md_serial_number = md_serial_number;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWritedate() {
		return writedate;
	}
	public void setWritedate(String writedate) {
		this.writedate = writedate;
	}
	
	
}
