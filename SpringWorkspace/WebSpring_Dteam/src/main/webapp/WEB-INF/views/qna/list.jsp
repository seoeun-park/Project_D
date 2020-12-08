<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

#list-top{overflow: hidden;}

#btn_qna { float: right;}

.btn-fill { width: 150px;}

/*1:1문의하기(QnA) table style*/
.qna_basic_table { width:100%; border-collapse:collapse; color: #424242; margin: 0 auto; }
.qna_basic_table:after { display:block; visibility:hidden; clear:both; content:"" }
.qna_basic_table tr:hover { background-color: #F7F7F7; }
.qna_basic_table thead tr th,.qna_basic_table thead tr td {padding:15px 5px;}
.qna_basic_table tbody tr th,.qna_basic_table tbody tr td {padding:15px 5px;}

.qna_basic_table th { font-size:1em;  background-color:#f5f5f5; }
.qna_basic_table td { font-size:0.95em;  line-height:150%;}
.qna_basic_table td a{ text-decoration:none; color:#454545}  
.qna_basic_table .td_center { text-align:center }

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

table a { font-weight: bold;}
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
						<form method="post" action="list.qn">
						<input type="hidden" name="curPage" value="1" />
						<div id="list-top">
							<!-- 로그인 회원만 글쓰기 가능 -->
							<a class="btn-fill" id="btn_qna" <c:if test='${login_info.member_id ne null }'> href="new.qn" </c:if> 
							<c:if test="${login_info.member_id eq null }">onclick="alert('로그인이 필요한 기능입니다!'); window.location ='/dteam/login_view';"</c:if> >문의하기</a>
						</div>
						</form>
						
						<table class="qna_basic_table">
							<tr><th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일자</th>
							</tr>
							<c:forEach items="${page.list }" var="vo">
							<tr>
								<td>${vo.no} </td>
								<td class='left'>
									<c:forEach var="i" begin="1" end="${vo.indent }">
									&nbsp;&nbsp;
										<c:if test="${i eq vo.indent }">
											<img src="img/re.gif"><b style="font-size: 0.8em"> [답변완료] </b>
												<%-- ${ vo.indent gt 0 ? '<img src="img/re.gif" />' : '' } --%>
										</c:if>
									</c:forEach>
									
									<!-- !!!!!작성자 또는 관리자만 상세페이지로 들어갈 수 있게 변경해야 함 !!!!!-->
									<c:set var="qna" value="${login_info.member_id eq vo.writer or login_info.member_id eq vo.q_writer}" /> <!-- 작성자나 질문자인지 --> 
									<a <c:if test="${login_info.member_id eq 'admin' || ( login_info.member_id ne 'admin' && qna ) }"> href='detail.qn?id=${vo.id}' </c:if>  
									   <c:if test="${login_info.member_id ne 'admin' && ( login_info.member_id ne 'admin' && !qna) }"> onclick="alert('읽기권한이 없습니다!')"</c:if>
										 ${1 eq vo.indent ? 'style="color: #3043b0;"': ''} >${vo.title}</a>
									
								</td>
								<td>
									<c:if test="${vo.q_writer eq 'admin' }">
										<p style="color: #3043b0; font-weight: bold;">고객만족팀</p>	
									</c:if>
									<c:if test="${vo.q_writer ne 'admin' }">
										<p style="font-weight: bold;">${vo.name}</p>			
									</c:if>
								</td>
								<td>${vo.writedate} </td>
							</tr>	
							</c:forEach>
						</table>
					
						<div class="btnSet">
							<jsp:include page="/WEB-INF/views/include/page.jsp" />
						</div>
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