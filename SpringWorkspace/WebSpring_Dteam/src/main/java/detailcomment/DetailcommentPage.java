package detailcomment;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class DetailcommentPage {
	private List<DetailcommentVO> list;

	public List<DetailcommentVO> getList() {
		return list;
	}

	public void setList(List<DetailcommentVO> list) {
		this.list = list;
	}
	
}
