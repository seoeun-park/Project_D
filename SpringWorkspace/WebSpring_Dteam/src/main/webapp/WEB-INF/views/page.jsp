<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style type="text/css">
.page_on, .page_off, .page_first, .page_prev, .page_next, .page_last {
	display: inline-block; 
	line-height: 30px; 
	margin: 0; 
	min-width: 22px; 
	padding: 0 5px 2px;
}
.page_on { border: 1px solid gray; 
		   background-color: gray; 
		   color: #fff; 
		   font-weight: bold; }

.page_first, .page_prev, .page_next, .page_last {
	width: 30px; 
	border: 1px solid #d0d0d0; 
	text-indent: -999999999px;
}

.page_first { background: url('img/page_first.jpg') center no-repeat; }
.page_prev { background: url('img/page_prev.jpg') center no-repeat; }
.page_next { background: url('img/page_next.jpg') center no-repeat; }
.page_last { background: url('img/page_last.jpg') center no-repeat; }

</style>

<div class="page_list">

<!-- 처음/이전 블럭 -->
<c:if test="${page.curBlock gt 1 }">
<a class="page_first" href="javascript:go_page(1)">처음</a>
<a class="page_prev" href="javascript:go_page(${page.beginPage-page.blockPage })">이전</a>
</c:if>
<!-- 현재블럭 -->
<c:forEach var="no" begin="${page.beginPage }" end="${page.endPage }">
	<c:if test="${no eq page.curPage }">
		<span class="page_on">${no }</span>
	</c:if>
	<c:if test="${no ne page.curPage}">
		<a class="page_off" href="javascript:go_page(${no})">${no }</a>
	</c:if>
</c:forEach>


<!-- 다음/마지막 블럭 -->
<c:if test="${page.curBlock lt page.totalBlock }">
<a class="page_next" href="javascript:go_page(${page.endPage+1 })">다음</a>
<a class="page_last" href="javascript:go_page(${page.totalPage})">마지막</a>
</c:if>

</div>
<script type="text/javascript">
function go_page(no){
	$('[name=curPage]').val(no);
	$('[name=keyword]').val('${page.keyword}');
	$('form').submit();

	
}



</script>
