<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	 iframe { height: 500px;} 
</style>
</head>
<body>
<section id="content_area">
	<div class="container">
		<div class="content_wrap">
			<div class="content_top">
				<h3>계정정보 찾기</h3>
				<ul class="breadcrumb">
					<li><img src="img/home.png" alt="홈"> &gt;</li>
					<li>계정정보 찾기</li>
				</ul>
			</div>
			<div class="contents">
				<!-- 탭메뉴 -->
				<ul class="tab" id="tab">
					    <li><a href="searchId_view" ${searchType eq 'id' ? 'class="active"' : ''} target="id">아이디 찾기</a></li>
    					<li><a href="searchPw_view" ${searchType eq 'pw' ? 'class="active"' : ''} target="pw">비밀번호 찾기</a></li>
				</ul>
				<div class="tab_con" id="tab_con">
    				<div><iframe src="searchId_view" name="id" width="100%" scrolling="no"></iframe></div>	
    				<div><iframe src="searchPw_view" name="pw" width="100%" scrolling="no"></iframe></div>
				</div>
			</div>	<!-- .contents -->
		</div> <!-- .content_wrap -->
	</div> <!-- .container -->
</section>
<script type="text/javascript">
	$(function () {	
		tab('#tab',0);	
	});

	function tab(e, num){
	    var num = num || 0;
	    var menu = $(e).children();		// #tab의 자식요소(li)
	    var con = $(e+'_con').children();	// #tab_con의 자식요소(div)
	    var select = $(menu).eq(num);
	    var i = num;
	
	    select.addClass('on');
	    con.eq(num).show();
	
	    menu.click(function(){
	        if(select != null){
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