package faq;

import java.util.List;

import org.springframework.stereotype.Component;

import common.PageVO;


@Component
public class FaqPage extends PageVO {
	private List<FaqVO> list;

	
	public List<FaqVO> getList() {
		return list;
	}

	public void setList(List<FaqVO> list) {
		this.list = list;
	}
}
