<%@page import="member.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% MemberVO member; %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>대여안대여</title>

<style type="text/css">
/* banner */
 #container01 { border-top: 8px solid white;  border-bottom: 8px solid white; margin: 10px 0px 0px 0px;  overflow: hidden; min-width: 1080px;}
#slide {width: 500%;}
#slide::after { content: ""; display: block; clear: both; }
#slide > li { float: left; width: 20%; position: relative;height: 500px;overflow: hidden;}
#slide > li > img { display: block; position: absolute; top: 50%; left: 50%;  transform: translate(-50%, -50%);}

/* 카테고리 */  
/* #main_content {height:440px;} */
#main_content .info_cate {position:relative; height:190px}   
#main_content .cate_list li {float:left; margin-left:41px; width:90px; height:174px; margin-top:38px; text-align:center;}
#main_content .cate_list li a {display:block; line-height:140%;padding-top:110px;}

#main_content .new_pro{position:relative; padding-top:20px;} 
#main_content .new_review{position:relative; padding-top:20px;} 

#main_content .info_cate a.more {position:absolute;  top:43px; right:17px; height:25px; margin:0 ! important; background:url(img/cate_more.png) no-repeat; padding-left:25px;}
#main_content .info_cate a.more span {display:block; }  
.m_container{ margin: 0 auto; width: 1200px;}
.r_container { margin: 0 auto;background-color: #eee; height: 250px; margin-top: 50px; margin-bottom: 80px;}
.rolling_txt { padding-top: 80px;} 
.input { background-position:top center; background-repeat:no-repeat; background-color: white; background-size: 100%; width: 90px; height: 170px;
 	    border: none; padding-top: 55px; cursor:pointer;}
input:focus{ outline: none;}
.div{overflow: hidden; margin: 0 auto; margin-top: 30px; margin-bottom: 40px;}
.divsub{ width:90px; height:170px; float: left; margin-left: 44px;}   
.divsub:nth-child(1){ margin-left: 0;}
.a{ font-size: 11px; font-weight: bold;}
.img{ width: 250px; height: 250px; border-radius: 10px; border: 1px solid #d8d8d8; background-color: white;}
.grid li div:nth-child(1) {
	 overflow:hidden;  display:-webkit-box;
	-webkit-line-clamp:3;  /*보이게할 줄 수*/
	-webkit-box-orient:vertical; 
	word-wrap: break-word;  /* 영문인 경우 */
} 

.grid{overflow: hidden;}
.gride_li { display:none; }
#data-list { displau: none;}


.ticker { height: 30px; overflow: hidden; margin: 0; padding: 0; list-style: none; }
.ticker li { height: 30px; padding: 5px; margin: 0px 5px; }


</style>

</head>   
<body>
<section id="visual_area"> 
 <div id="container01">
            <ul id="slide">
                <li><img src="img/banner01.jpg"></li>
                <li><img src="img/banner02.jpg"></li>
                <li><img src="img/banner03.jpg"></li>
            </ul>
        </div>
</section>
<section id="main_content">	
	<h2 class="blind">메인 카테고리</h2>
		<article class="container info_cate">
			<form method='post' action='list.ma'>
				<div class="div">
					<div class="divsub">
						<input type="submit" name="category" value="가전" class="input"
							style="background-image: url('img/cate_img1.jpg');">
					</div>
					<div class="divsub">
						<input type="submit" name="category" value="아동" class="input"
							style="background-image: url('img/cate_img2.jpg');">
					</div>
					<div class="divsub">
						<input type="submit" name="category" value="생활" class="input"
							style="background-image: url('img/cate_img3.jpg');">
					</div>
					<div class="divsub">
						<input type="submit" name="category" value="스포츠" class="input"
							style="background-image: url('img/cate_img4.jpg');">
					</div>
					<div class="divsub">
						<input type="submit" name="category" value="의류" class="input"
							style="background-image: url('img/cate_img5.jpg');">
					</div>
					<div class="divsub">
						<input type="submit" name="category" value="게임" class="input"
							style="background-image: url('img/cate_img6.jpg');">
					</div>
					<div class="divsub">
						<input type="submit" name="category" value="뷰티" class="input"
							style="background-image: url('img/cate_img7.jpg');">
					</div>
					<div class="divsub">
						<input type="submit" name="category" value="동물" class="input"
							style="background-image: url('img/cate_img8.jpg');">
					</div>
					<div class="divsub">
						<input type="submit" name="category" value="기타" class="input"
							style="background-image: url('img/cate_img10.jpg');">
					</div>
					<div class="divsub">
						<input type="submit" name="category" value="나눔" class="input"
							style="background-image: url('img/cate_img9.jpg');">
					</div>
				</div>
			</form>
			<!-- <a href="" class="more">전체보기</a> -->
		</article>

		<article class="m_container new_pro">
		<div style="width: 1200px; margin: 0 auto; margin-bottom: 10px;">
			<h3 style="width: 150px;">최신상품 ＞</h3>
		</div>
		<div id='data-list'
			style="width: 1200px; margin: 0 auto; margin-top: 10px; margin-bottom: 15px;">
			<div class='grid'
				style="width: 1200px; margin: 0 auto; border-radius: 10px;">
				<c:forEach items="${page.list}" var="vo">
					<div class='gride_li'
						style="border: 0px; height: 240px; margin-bottom: 80px; float: left; margin-right: 50px; margin-top: 20px;">
						<a href='detail.ma?md_serial_number=${vo.md_serial_number }'>
							<img src='${vo.md_photo_url}' onerror="this.src='img/white.png'"
							class="img">
						</a>
						<div style="padding: 0px; font-weight: 600; margin-top: 10px;">${vo.md_name}</div>
						<div
							style="padding: 0px; font-weight: 300; margin-top: 10px; margin-bottom: 10px">${vo.md_price}원</div>
					</div>
				</c:forEach>
			</div>
		</div>
	<div style=" border:1px solid #a3a3a3; height: 60px; width: 800px; margin-bottom: 20px; margin-left: 170px; margin-top: 45px;">
			<a href="#" id="load" style="height: 50px; line-height: 60px; font-weight: bold;">더 보기  ▼ </a> 
		</div>
	</section>		
		</article>
		<!-- <div class="visivle"  
			style="width: 50px; hegiht: 50px; padding-top: 50px;"></div> -->

<%-- <section class="r_container new_review">
			<div style="overflow:hidden; width:600px; height:250px; margin-left:550px; ">
			<c:forEach items="${page.list}" var="vo">
			<ul id="list_txt" style="position:relative; padding-top: 100px;">
				<li style="height:150px; text-align:center">
				${vo.review_content }</li>
			</ul>
			</c:forEach>
		</div>
		</article>
	</section> --%>
<script type="text/javascript">
$(function(){
    $(".gride_li").slice(0, 8).show(); // select the first ten
    $("#load").click(function(e){ // click event for load more
        e.preventDefault();
        $(".gride_li:hidden").slice(0, 8).show();
        if($(".gride_li:hidden").length == 0){ // check if any hidden divs still exist
           alert("마지막 리스트입니다."); // alert if there are none left
        }
    });
});
$(function(){
	 var len = $(".gride_li").not(':hidden').length;
	$('#data-list ul').css('height',  ( ( len%5 > 0 ? 1 : 0) + Math.floor(len/5)  )*$('.grid li').outerHeight(true)-20  );	
	
});


//리뷰 배너 
// banner_roll("div태그 id", 배너1개높이, 딜레이, 1칸이동속도, 0);
	function banner_roll(div_id, banner_height, banner_delay, banner_speed, this_height)
	{
		var div_tag = document.getElementById(div_id);
		var a_tag, i;
		
		this_height ++;
		if(this_height < banner_height) {
			div_tag.style.top = -this_height;
			setTimeout("banner_roll('" + div_id + "', " + banner_height + ", " + banner_delay + ", " + banner_speed + ", " + this_height + ");", banner_speed);
		} else {
			a_tag = div_tag.getElementsByTagName("li");
			div_tag.appendChild(a_tag[0]);
			div_tag.style.top = 0;
			setTimeout("banner_roll('" + div_id + "', " + banner_height + ", " + banner_delay + ", " + banner_speed + ", 0);", banner_delay);
		}
		return true;
	}
	// banner_roll("div태그 id", 배너1개높이, 딜레이, 1칸이동속도, 0);
	banner_roll("list_txt", 18, 2000, 40, 0);


</script>

</body>
</html>