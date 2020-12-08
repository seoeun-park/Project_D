<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false" contentType = "text/html; charset=UTF-8" %>
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
.jetab_con { clear:both; margin-top:5px; border:1px solid #ddd; }
.jetab_con div { display:block; background:#fff; line-height:100px; text-align:center; padding: 0 15px;}

.faq {border: 0px solid red; border-bottom:0px solid #ddd; margin:1em 0; }
.faq .faqHeader{position:relative;zoom:1}
.faq .faqHeader .showAll{position:absolute;bottom:0;right:0;border:0;padding:0;overflow:visible;background:none;cursor:pointer}
.faq .faqBody{margin:0;padding:0}
.faq .faqBody .article{list-style:none}
.faq .q {margin:}
.faq .q a:hover, .notice .q a:active, .notice .q a:focus{}
.faq .q a {
    display: block;
    text-align: left;
    background: url(https://happyjung.diskn.com/data/lecture/icon_jquery_faq_20170221_1.png) no-repeat 0 0;
    padding: 0 0 0 35px;
    font-size: 15px;
    color: #2b2b2b;
    font-weight: bold;
    line-height: 27px;
    cursor: pointer;
    margin: 15px 0 5px 10px;
}
.faq .a {
    background: #f8f8f8 url(https://happyjung.diskn.com/data/lecture/icon_jquery_faq_20170221_2.png) no-repeat 40px 10px;
    padding: 10px 75px 10px 75px;
    font-size: 14px;
    color: #444444;
    line-height: 22px;
    border-top: 1px solid #bdbdbd;
    margin: 5px 0 0 0;
    text-align: left;
}
.time { color: #8f8d8d; font-weight: 500; font-size: 14px;}

</style>
<script type="text/javascript" src="https://code.jquery.com/jquery-latest.js"></script>

</head>
<body>
<!-- 공통으로 넣기 -->
<section id="content_area">
	<div class="container">
		<div class="content_wrap">
			<div class="content_top">
				<h3>자주묻는 질문</h3>
				<ul class="breadcrumb">
					<li><img src="img/home.png" alt="홈"> &gt;</li>
					<li>자주묻는 질문</li>
				</ul>
			</div>
			<div class="contents">
<!------------------------------------------------------------------------------------------------->
				<!-- 탭메뉴 -->
				<ul class="tab" id="tab">
					<li onclick="location.href='list.no'">공지사항</li>
					<li>자주묻는 질문</li>
					<li onclick="location.href='list.qn'">1:1 문의하기</li>
				</ul>
		
				<div class="jetab_con" id="tab_con">
					<div class="faq">
					    <div class="faqHeader">
					        <!--button type="button" class="showAll">답변 모두 여닫기</button-->
					    </div>
					    <ul class="faqBody">
					    <!------------------------------------------------------------------------------------------------->
					    	<form method="post" action="list.fa">
							<input type="hidden" name="curPage" value="1" />
							</form>
						    
						    <!-- 관리자만 글쓰기 가능 -->
						    <div>
								<a class="btn-fill" id="btn_faq" <c:if test='${login_info.member_id eq "admin" }'> href="new.fa" </c:if> 
								<c:if test='${login_info.member_id ne "admin" }'> style="display: none;" </c:if> > FAQ 글쓰기(관리자 전용)</a>
						    </div>
					    	
					    	
						    <!----- 리스트  ----->
						    <c:forEach items="${page.list }" var="vo">
						        <li class="article" id="a1">
						            <p class="q">
						                <a href="#a1">${vo.title} 
						                <br><time class="time">${vo.writedate } </time>
						                	<button <c:if test="${login_info.member_id ne 'admin' }"> style="display: none;"</c:if> onclick="location.href='modify.fa?id=${vo.id}'">수정 / 삭제</button>
						            	</a>
						            </p>
						            <p class="a"> ${fn:replace(fn:replace(vo.content, lf, '<br/>'), crlf, '<br/>')} </p>
						        </li>
						        <hr/>
						     </c:forEach>
						     
					    <!------------------------------------------------------------------------------------------------->
					    </ul>
					</div>
				</div>

				<div class="btnSet">
					<jsp:include page="/WEB-INF/views/include/page.jsp" />
				</div>
<!------------------------------------------------------------------------------------------------->
			</div>
		</div>
	</div>
</section>			
<script type="text/javascript">
$(function () {	
	tab('#tab',1);	
});

function tab(e, num){
    var num = num || 1;
    var menu = $(e).children();
    var con = $(e+'_con').children();
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

jQuery(function($){	
	//공지사항 글보기
    var article = $('.faq>.faqBody>.article');
    article.addClass('hide');
    article.find('.a').hide();
    article.eq(0).removeClass('hide');
    article.eq(0).find('.a').show();
    $('.faq>.faqBody>.article>.q>a').click(function(){
        var myArticle = $(this).parents('.article:first');
        if(myArticle.hasClass('hide')){
            article.addClass('hide').removeClass('show');
            article.find('.a').slideUp(300);
            myArticle.removeClass('hide').addClass('show');
            myArticle.find('.a').slideDown(300);
        } else {
            myArticle.removeClass('show').addClass('hide');
            myArticle.find('.a').slideUp(300);
        }
        return false;
    });
    $('.faq>.faqHeader>.showAll').click(function(){
        var hidden = $('.faq>.faqBody>.article.hide').length;
        if(hidden > 0){
            article.removeClass('hide').addClass('show');
            article.find('.a').slideDown(300);
        } else {
            article.removeClass('show').addClass('hide');
            article.find('.a').slideUp(300);
        }
    });
});

</script>
</body>
</html>