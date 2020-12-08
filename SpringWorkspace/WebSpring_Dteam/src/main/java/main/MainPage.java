package main;

import java.util.List;

import org.springframework.stereotype.Component;

import common.PageVO;

@Component
public class MainPage extends PageVO{
	private List<MainVO> list;

	public List<MainVO> getList() {
		return list;
	}

	public void setList(List<MainVO> list) {
		this.list = list;
	}
	
}
