<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.tab li {
    float: left;
    width: 33.3%;
    border-left: 1px solid #ddd;
    text-align: center;
    box-sizing: border-box;
    color: #333333
}

.tab li.on {
    background-color: #eee;
    color: #333333;
    font-weight: bold;
    font-size: 1.1em;
    border-bottom: 2px solid #3E4BA9;
}

.tab_con div { background:#fff; line-height:100px; text-align:center; }  

.jetab_con { clear:both; margin-top:5px; border:1px solid #ddd; padding: 15px 0;}
.jetab_con div { display:block; background:#fff; line-height:30px; text-align:center; padding: 0 15px;}

table {
	width: 100%; 
	margin: 0 auto;
	border: 1px solid;
	border-collapse: collapse;  
}

table th, table td{
	border: 1px solid #ddd;
	padding: 5px 10px;
}

</style>
</head>
<body>
<!-- 공통으로 넣기 -->
<section id="content_area">
	<div class="container">
		<div class="content_wrap">
			<div class="content_top">
				<h3>1:1 문의하기</h3>
				<ul class="breadcrumb">
					<li><img src="img/home.png" alt="홈"> &gt;</li>
					<li>1:1 문의하기</li>
				</ul>
			</div>
			<div class="contents">
<!------------------------------------------------------------------------------------------------->
				<!-- 탭메뉴 -->
				<ul class="tab" id="tab">
					<li onclick="location.href='list.no'">공지사항</li>
					<li onclick="location.href='list.fa'">자주묻는 질문</li>
					<li>1:1 문의하기</li>
				</ul>
		
				<div class="jetab_con" id="tab_con">
					<div class="qna">
					<!------------------------------------------------------------------------------------------------->
					<h3>1:1문의글 상세보기</h3>
					
					<table class="left">
						<tr><th class="w-px160">제목</th>
							<td colspan="5">${vo.title }</td>
						</tr>
						<tr><th>작성자</th>
							 <td>${vo.writer }</td>
							 <th class="w-px120">작성일자</th>
							 <td class="w-px120" >${vo.writedate }</td>
						</tr>
						<tr><th>내용</th>
							<td colspan="5">${fn:replace(vo.content, crlf, '<br>') }</td>
						</tr>
						<tr><th>첨부파일</th>
							<td colspan="5">${vo.filename}
							<c:if test="${!empty vo.filename }">
								<a href="download.qn?id=${vo.id }"><i class="fas fa-download"></i></a>
							</c:if>
							</td>
						</tr>
					</table>
					
					<div class="btnSet">
						<!-- <a class="btn-fill" href="list.qn">목록으로</a> -->
						<a class="btn-fill" href="javascript:$('form').submit()">목록으로</a>
						
						<!-- 관리자로 로그인한 경우만 수정/삭제가능 -->
						<%-- <c:if test="${login_info.member_id eq 'admin'}">
							<a class="btn-fill" href="modify.qn?id=${vo.id }">수정</a>
							<a class="btn-fill" onclick="if( confirm('정말 삭제하시겠습니까?') ){ href='delete.qn?id=${vo.id }' }">삭제</a>
						</c:if> --%>
						
						<%-- <a class="btn-fill" <c:if test="${login_info.member_id eq 'admin' || ( login_info.member_id ne 'admin' && login_info.member_id eq vo.writer) }"> 
						href='modify.qn?id=${vo.id}'</c:if> > 수정</a> --%>
						<!--  -->
						<c:if test="${login_info.member_id eq vo.writer }"> <a class="btn-fill"  
						href='modify.qn?id=${vo.id}'>수정하기</a></c:if> 
					
						<c:if test="${login_info.member_id eq 'admin' || ( login_info.member_id ne 'admin' && login_info.member_id eq vo.writer) }">	
							<a class="btn-fill"  
							href='detail.qn?id=${vo.id}'   
	     					onclick="if( confirm('정말 삭제하시겠습니까?') ){ href='delete.qn?id=${vo.id }' }">삭제 </a>
	     				</c:if>
						
						<!-- 관리자로 로그인한 경우만 답변 작성 가능-->
						<c:if test="${login_info.member_id eq 'admin'}">
						   <a class="btn-fill" href="reply.qn?id=${vo.id }">답변하기</a>
						</c:if>
						
					</div>
					
					<form action="list.qn" method="post">
					<input type="hidden" name="curPage" value="${page.curPage }"/> 
					<input type="hidden" name="search" value="${page.search }"/> 
					<input type="hidden" name="keyword" value="${page.keyword }"/> 
					</form>
					
					<!------------------------------------------------------------------------------------------------->
					<!-- 탭메뉴 닫는태그 -->
					</div>
				</div>

<!------------------------------------------------------------------------------------------------->
			</div>
		</div>
	</div>
</section>			
<script type="text/javascript">
/* 공지사항/자주묻는질문/1:1문의 탭 */
$(function () {	
	tab('#tab',2);	
});

function tab(e, num){
    var num = num || 2;
    var menu = $(e).children();
    var con = $('jetab_con').children();
    var select = $(menu).eq(num);
    var i = num;

    select.addClass('on');
    con.eq(num).show();

    menu.click(function(){
        if(select!==null){
            select.removeClass("on");
            con.eq(i).hide();
        }

        select = $(this);	
        i = $(this).index();

        select.addClass('on');
        con.eq(i).show();
	});
}    

</script>

</body>
</html>