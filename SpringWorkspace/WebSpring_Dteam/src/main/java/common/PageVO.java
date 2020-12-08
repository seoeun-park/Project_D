package common;

public class PageVO {
	private int pageList = 10;		//페이지당 보여질 목록수
	private int blockPage = 10;		//블럭당 보여질 페이지수
	private int totalList;			//총 목록수
	
	//512건 : 512/10 = 51...2  나머지가 있으면 한페이지 추가
	private int totalPage;		//총 페이지수
	
	//52페이지 : 52/10 = 5 ...2 나머지가 있으면 한블럭 추가
	private int totalBlock;	//총 블럭수
	
	private int curPage; 	//현재페이지
	
	//현재 페이지에 보여질 목록 시작/끝
	private int beginList, endList;
	
	//현재 블럭에 보여질 페이지 시작/끝
	private int curBlock, beginPage, endPage;
	
	private String search, keyword; //검색조건, 검색어
	private String viewType = "list";
	private String category;

	
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPageList() {
		return pageList;
	}

	public void setPageList(int pageList) {
		this.pageList = pageList;
	}

	public int getBlockPage() {
		return blockPage;
	}

	public void setBlockPage(int blockPage) {
		this.blockPage = blockPage;
	}

	public int getTotalList() {
		return totalList;
	}

	public void setTotalList(int totalList) {
		this.totalList = totalList;
		//DB에서 조회해온 총 목록수에 따라 총페이지수가 결정됨
		totalPage = totalList / pageList;
		if ( totalList % pageList > 0 ) ++totalPage;
		
		//총페이지수에 따라 총블럭수가 결정됨
		totalBlock = totalPage / blockPage;
		if ( totalPage % blockPage > 0 ) ++totalBlock;
		
		//현재 페이지에 따라 보여질 목록의 번호가 결정됨
		//512건 : 1페이지 : 503 ~ 512
		//		  2페이지 : 493 ~ 502
		//		  3페이지 : 483 ~ 492
		endList = totalList - (curPage-1) * pageList;
		beginList = endList - (pageList - 1);
		
		//현재 블럭에 따라 보여질 페이지 번호가 결정됨
		curBlock = curPage / blockPage;
		if ( curPage % blockPage > 0 ) ++curBlock;
		//1블럭 : 1 ~ 10 
		//2블럭 : 11 ~ 20 
		//3블럭 : 21 ~ 30
		endPage = blockPage * curBlock;
		beginPage = endPage - (blockPage - 1);
		
		//512건 : 52페이지 : 6 블럭
		//5블럭 : 41 ~ 50
		//6블럭 : 51 ~ 60
		//endPage는 총 페이지수보다 클 수 없다.
		if ( endPage > totalPage ) endPage = totalPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getBeginList() {
		return beginList;
	}

	public void setBeginList(int beginList) {
		this.beginList = beginList;
	}

	public int getEndList() {
		return endList;
	}

	public void setEndList(int endList) {
		this.endList = endList;
	}

	public int getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getTotalBlock() {
		return totalBlock;
	}

	public void setTotalBlock(int totalBlock) {
		this.totalBlock = totalBlock;
	}

	public int getCurBlock() {
		return curBlock;
	}

	public void setCurBlock(int curBlock) {
		this.curBlock = curBlock;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	
}
