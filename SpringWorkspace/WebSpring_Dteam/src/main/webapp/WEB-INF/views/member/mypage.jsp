<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>대여안대여</title> 
<style type="text/css">
 iframe { height: 500px;}   
</style>
<c:if test="${empty login_info}">
<script>
   alert("로그인이 필요한 페이지 입니다.");
   location.href = "login_view";
</script>
</c:if>
</head>  
<body>
<section id="content_area">
	<div class="container">
		<div class="content_wrap">
			<div class="content_top">
				<h3>마이페이지</h3>
				<ul class="breadcrumb">
					<li><img src="img/home.png" alt="홈"> &gt;</li>
					<li>마이페이지</li>
				</ul>
			</div>
				<div class="contents">
					<div class="profile_box">
						<div class="profile_img" style="float: left; width: 200px; height: 200px; margin-right: 20px;">
							 <img src="${vo.member_profile}" style=" width: 180px; height: 180px;"/>
						</div>
						<div class="user_info" style="float: left; width: 830px;">
						<table class="t_style03" >
							<colgroup>
								<col width="50%">
								<col width="50%">
							</colgroup>
							<tbody>
								<tr>
									<th>닉네임</th>
									<td>${vo.member_nickname}</td>
								</tr>

								<tr>
									<th>등급</th>
									<td>${vo.member_grade}</td>
								</tr>
								<tr>
									<th colspan="2">${vo.member_id}&nbsp;&nbsp; <a href="modify?member_id=${vo.member_id}">회원정보수정</a></th>
								</tr>
							</tbody>
						</table>
						</div>
					</div>
					<ul class="tab" id="tab">
    <li><a href="list.bo" ${category eq 'bo' ? 'class="active"' : ''} target="lend">등록한 상품 내역</a></li>
    <li><a href="list.vo" ${category eq 'vo' ? 'class="active"' : ''} target="fav">찜 목록</a></li>
	
</ul>

<div class="tab_con" id="tab_con">
    <div><iframe src="list.bo" name="lend" width="100%";></iframe></div>	
    <div><iframe src="list.vo" name="fav" width="100%";></iframe></div>
 
</div>
				</div>
			</div>
	</div>
</section>
<script type="text/javascript">
$(function () {	
	tab('#tab',0);	
});

function tab(e, num){
    var num = num || 0;
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
</script>
</body>
</html>