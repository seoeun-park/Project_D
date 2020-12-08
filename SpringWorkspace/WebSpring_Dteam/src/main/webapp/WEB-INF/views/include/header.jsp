<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>대여 안대여</title>
 <script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
<header id="header">
	<h2 class="blind">상단 네비게이션</h2>
	<div class="tnb">
		<div class="container01">		
			<ul class="">
				<li><a href='<c:url value="/"/>'>홈으로</a></li>
				<c:if test="${empty login_info }">
					<li><a href="login_view" class="login">로그인</a></li>
					<li><a href="member">회원가입</a></li>
				</c:if>

				<c:if test="${!empty login_info }">
					<li><a id="mypage"
						href="mypage?member_id=${login_info.member_id }">마이페이지</a></li>
					<%-- <span>${login_info.member_nickname } [${login_info.member_id }]</span> --%>
					<li><a onclick="go_logout()" style="color: #fff;">로그아웃</a></li>

				</c:if>
			</ul>		
		</div>
	</div>
	<div class="m_content">
		<h1 class="logo">
			<a href='<c:url value="/"/>' title="대여안대여"><img
				src="img/logo.png" alt="대여안대여"></a>
		</h1>
		<form method='post' action='search.ma'>
		<input type='hidden' name='curPage' value='1' />
		<input type='hidden' name='id' />
		<div class="wrap">
			<div class="search">
				<input type="text" class="searchTerm" name="keyword" placeholder="검색어를 입력해주세요!">
				<button type="submit" class="searchButton">
					<i class="fas fa-search"></i>
				</button>
			</div>
		</div>
		</form>
	</div>
	
</header>
<script type="text/javascript"
	src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.2.js"
	charset="utf-8"></script>
<script type="text/javascript">
	function go_logout() {
		$.ajax({
			url : "logout",
			success : function() {
				/* alert("로그아웃 되었습니다!"); */
				location.reload();
			},
			error : function() {
				alert(text + ":" + req.status);
			}
		});
	} //go_logout()
</script>
<script>
	$(function() {
	
		var $slide = $("#slide");
		var timerId;

		timerId = window.setInterval(slideBanner, 2000);
		$("#container01").hover(function() {
			window.clearInterval(timerId);
		}, function() {
			
			timerId = window.setInterval(slideBanner, 2000);
		});

		function slideBanner() {
		
			$slide.css({
				"margin-left" : "-100%",
				"transition" : "0.4s"
			});

			window.setTimeout(function() {
				
				$slide.removeAttr("style").children(":first").appendTo($slide);

			}, 400);
		}
	}); // document.onready
</script>


<style type="text/css">
/*header */
#header{}
.container01 { margin: 0 auto; width: 1200px; }
#header > .container01{ padding:13px 0; overflow:hidden }
#header > .m_content{ padding:13px 0; overflow:hidden; position: relative;  }
#header .tnb{width:100%; height:40px; background-color:#3f4baa;}
#header .tnb li{ float:left; font-size:12px}
#header .tnb li a{ display:block; padding:0 10px; line-height:40px; text-decoration: none; color: #fff;}
#header .tnb p {float:left;}
#header .tnb ul {float:right;}

#header .logo{ width:236px; height:105px; margin:0 auto;}
#header .logo a{ display:block }
button:focus{ outline: none;}
.m_content { width: 1200px; margin: 0 auto; min-width: 1200px;}

/* 검색 */
.search {
  width: 100%;
  position: relative;
  display: flex;
}

.searchTerm {
  width: 100%;
  border: 3px solid #3f4baa;
  border-right: none;
  padding: 5px;
  height: 50px;
  border-radius: 5px 0 0 5px;
  outline: none;
  color: #3f4baa;  
}

.searchTerm:focus{
  color: #00B4CC;
}

.searchButton {
  width: 50px;
  height: 50px;
  border: 1px solid #3f4baa;
  background: #3f4baa;
  text-align: center;
  color: #fff;
  border-radius: 0 5px 5px 0;
  cursor: pointer;
  font-size: 20px;
}

/*Resize the wrap to see the search bar change!*/
.wrap{
  width: 40%;
  position: absolute;
  top: 54%;
  left: 55%;      
  transform: translate(-50%, -50%);
}


</style>  

