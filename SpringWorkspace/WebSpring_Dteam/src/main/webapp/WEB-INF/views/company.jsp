<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>대여안대여</title> 
<style type="text/css">
 .vision_container { width: 100%; max-width: 900px; height: 425px; margin: 0 auto; background: url('img/company_vision.png') no-repeat;
 					background-size: 100%; background-position: center center;  }
#brand_mission{ background-color: #f8f8f8;}
#brand_mission .brand_m, #contact_us .brand_m { text-align:center; margin: 0; height: 15vh; line-height:15vh; font-size: 16px; letter-spacing: 5px; text-transform: uppercase; color:#3f4baa; }
.brand_v { text-align:center; margin: 0; height: 13vh; line-height:6vh; font-size: 16px; letter-spacing: 5px; text-transform: uppercase; color:#3f4baa; }

.contents_container { width: 100%; max-width: 900px; min-height: 40vh; margin: 0 auto; padding-bottom: 20px; }
.mission_title { font-size: 28px; line-height: 1.5; text-align: center; font-weight:400;}   
ul.mission_column { padding: 0; text-align: center;}  
ul.mission_column li{margin: 20px 40px; line-height: 1.5;}
ul.mission_column li h4 { font-size: 24pt; margin: 15px 0 0 ;}
.mission_underline {background: #3f4baa; height: 5px; width: 140px; margin: 0 auto;}  
.mission_circle { width: 3px; height: 3px; border-radius: 50%; border: 2px solid #3f4baa; margin: 30px auto 30px;}
.location_container { width: 94%; padding: 3%; max-width: 800px; margin-top: 10px;}
.head_office { float:left; width:120px; font-size: 36px; font-weight: 400; line-height: 1.2; margin-bottom: 20px;}
table.address { float:right; border:none; border-collapse:collapse; max-width: 520px; }
table.address td:first-child { width: 96px; vertical-align: top;}
table.address td { font-size: 15px; line-height: 1.3; padding: 4px 0;}  
table.address td:nth-child(2) {font-weight: 400; color: #888; padding-left: 20px; padding-right: 20px;}
#sub_visual_area{ margin-top:30px;  min-height:185px; background:#f1f1d5 url(img/sub_banner01.jpg) center no-repeat}

/* map 관련 css */
#map {
	margin: 0 auto;
	width: 80%;
	height: 450px;
}

</style>
</head>
<body>
<!--visual_area-->
<section id="sub_visual_area">		
</section>
<!--visual_area end-->
	<section id="content_area">
		<div class="container">
			<div class="content_wrap">
				<div class="content_top">
					<h3>회사소개</h3>
					<ul class="breadcrumb">
						<li><img src="img/home.png" alt="홈"> &gt;</li>
						<li>회사소개</li>
					</ul>
				</div>
				<div class="contents">
					<!--  vision -->
					<div class="vision_container">
						<h3 class="brand_v">VISION</h3>
						<br />
					</div>
				</div>
			</div>
		</div>
	</section>
	<!--  mission -->
	<div id="brand_mission">
		<h3 class="brand_m">
			Brand Mission
			</h3>
			<div class="contents_container">
				<h3 class="mission_title">필요한 물건 대여하고, 안쓰는 물건 셰어하자!</h3>
				<ul class="mission_column">
					<li>
						<h4>누구나</h4>
						<div class="mission_underline">&nbsp;</div>
						<div class="mission_circle">&nbsp;</div> 모든 상품 카테고리 다양한 가격대모든 지역 /
						내국인 &amp; 외국인
					</li>
					<li>
						<h4>마음 편히</h4>
						<div class="mission_underline">&nbsp;</div>
						<div class="mission_circle">&nbsp;</div> 편하고, 안정적이고, 정확한 서비스깨끗하고,
						신뢰할 수 있는 곳
					</li>
					<li>
						<h4>대여 할 수 있게</h4>
						<div class="mission_underline">&nbsp;</div>
						<div class="mission_circle">&nbsp;</div> 대여하고 싶어지는 다양한 서비스
					</li>
				</ul>
			</div>
		</div>
			<!--  오시는 길 -->
			<div id="contact_us">
				<h3 class="brand_m">contact us</h3>
				<div id="map"></div>
			<div class="contents_container">
				<div class="location_container">
					<div class="head_office">대여 안대여 본사</div>
					<table class="address">
						<tbody>
							<tr>
								<td>주소</td>
								<td>광주광역시 서구 경열로 3 한울직업전문학교 201호</td>
							</tr>
							<tr>
								<td>찾아오시는 길</td>
								<td>농성역1번 출구, (도보 5분여 소요)</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>대표전화</td>
								<td>062)362-7797</td>
							</tr>
							<tr>
								<td>팩스</td>
								<td>062)362-7798</td>
							</tr>
							<tr>
								<td>이메일</td>
								<td>contact@hanul.com</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	<!-- google map을 위한 js -->
	<script type="text/javascript" 
			src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCsrerDHJrp9Wu09Ij7MUELxCTPiYfxfBI" ></script>

	<script type="text/javascript">
		var nav = null;
		var map;
		var marker;

		google.maps.event.addDomListener(window, 'load', initialize(35.15364860194203, 126.887975975145));
			
		// Google Map으로 위도와 경도 초기화
		//initialize(35.15364860194203, 126.887975975145);

		function initialize(latitude, longitude) {
			// 현재 위치의 위도와 경도 정보를 currentLocatioon 에 초기화 
			var currentLocation = new google.maps.LatLng(latitude, longitude);
			var mapOptions = {
				center : currentLocation, 	//지도에 보여질 위치			
				zoom : 15, 		//지도 줌 (0축소 ~ 18확대) 	
				mapTypeId : google.maps.MapTypeId.ROADMAP
			};
			
			// DIV에 지도 달아주기
			map = new google.maps.Map(document.getElementById("map"), mapOptions);
			
			// 지도 위에 마커 달아주기
			marker = new google.maps.Marker({
				position : currentLocation,
				map : map
			});
			
			google.maps.event.addListener(marker, 'click', toggleBounce(marker));

		}

		function toggleBounce(marker) {
			if (marker.getAnimation() != null) {
				marker.setAnimation(null);
			} else {
				marker.setAnimation(google.maps.Animation.BOUNCE);
			}
		}
		
	</script>
</body>
</html>