<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="css/basic.css" rel="stylesheet" type="text/css">


<!--footer_area-->
<footer id="footer">
	<div class="container">
		<div class="bottom_gnb_menu">
			<a href="<c:url value='/'/>"><img src="img/logo.png" alt="홈으로"/></a>		
			<a href="company">회사소개</a>
			<a href="list.no" ${category eq 'no' ? 'class="active"' : ''}>공지사항</a>
			<a href="list.fa" ${category eq 'fa' ? 'class="active"' : ''}>자주묻는 질문</a>
			<a href="list.qn" ${category eq 'qn' ? 'class="active"' : ''}>1:1문의하기</a>
			<a href="service">이용약관</a>
			<a href="privacy">개인정보처리방침</a>
		</div>
	</div>
	<p class="address mt30">[61945] 광주광역시 서구 경열로 3(한울직업전문학교)       대표전화 062)362-7797</p>
	<p class="copyright">Copyright(C) 2020 Daeyeo-Andaeyeo All Rights Reserved.</p>
</footer>

<style type="text/css">

.container {
	margin: 0 auto;
	width: 1300px;
	display: flex;
	-webkit-box-pack: center;
    justify-content: center;
}

.bottom_gnb_menu {
	height: 64px;
    width: 1200px;
    display: flex;
    -webkit-box-pack: justify;
    justify-content: space-between;
    -webkit-box-align: center;
    align-items: center;
}

.bottom_gnb_menu a {
	width: 14.5%;
    text-align: center;
    font-size: 14.5px;
    font-weight: bold;
    color: rgb(88, 88, 88);
    position: relative;
    background-color: transparent;
}

.bottom_gnb_menu a:hover {
	width: 14.5%;
    text-align: center;
    font-size: 15px;
    font-weight: bold;
    /* color: rgb(33, 33, 33); */
    color: #21379c;
    position: relative;
    background-color: transparent;
}

.bottom_gnb_menu a:not(:first-child):not(:last-child)::after {
	content: "";
    position: absolute;
    top: 0px;
    right: 0px;
    width: 2px;
    height: 18px;
    border-right: 1px solid rgb(181, 181, 181);
    box-sizing: border-box;
}

</style>