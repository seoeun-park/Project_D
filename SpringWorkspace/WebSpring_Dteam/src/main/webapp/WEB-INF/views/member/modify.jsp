<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#frmJoin {
		width: 40%;
		margin: 0 auto 30px;
	}
	
	#frmJoin > div {
		border: 1px solid #858585;
		height: 55px;
		margin-bottom: 20px;
	}
	
	#frmJoin > div > span {
		height: 53px;
		display: block;
		float: left;
		padding: 14px 0 0 0;
		border-right: 1px solid #858585;
	}
	
	.valid, .invalid {
 		font-size: 13px;
 		height: 10px;
 		line-height: 25px;
 		text-align: left;
	}
	
	
	#frmJoin > div > span > svg {
		width: 50px;
		font-size: 25px;
		color: #3e4ba9;
	}
	
	#frmJoin input {
		width: 369px;
		height: 52px;
		box-sizing: border-box;
		border: none;
	}
	
	.common { display: none; }
	.valid { color: #3e4ba9; display: block; }
	.invalid { color: red; display: block;}
	
	.btn-check {
		color: #ffffff;
		background-color: #3e4ba9;
		padding: 6px 5px 8px 5px;
		border-radius: 5px;
		cursor: pointer;
		font-size: 13px;
		margin-right: 3px;
	}
	
/* 	#map { 
		position:absolute; 
		width:700px; height:500px;
		left:50%; top:65%;	
		transform:translate(-50%, -50%);
		border:2px solid #666;		
		display:none;      
	 }
	 */
	 .btnSet {
	 	width: 40%;
	 	height: 55px;
	 	margin: 0 auto;
	 	overflow: hidden;
	 }
	 
	 #btn-submit, #btn-reset {
	 	width: 48.5%;
		display: block; 
		line-height: 50px;
		font-weight: 70;
		cursor: pointer;
		float: left;
	 }
	 
	#btn-submit { background-color: #3e4ba9; color: #ffffff; font-weight: 900; margin-left: 3px; height: 50px; }
	
	#btn-reset { 
		background-color: #ffffff; 
		color: #666666;
		height: 50px; 
		font-weight: 600;
		/* border: 1px solid #000000; */
		box-shadow: 2px 2px 5px #666666;
	}

	
</style>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet">
</head>
<body>
<section id="content_area">
	<div class="container">
		<div class="content_wrap">
			<div class="content_top">
				<h3>[${vo.member_nickname}] &nbsp;회원정보수정</h3>  
				<ul class="breadcrumb">
					<li><img src="img/home.png" alt="홈"> &gt;</li>
					<li>회원정보수정</li>
				</ul>
			</div>
			<div class="contents">
					<form action="update" method="post" id='frmJoin'>
					<input type="hidden" name="member_id" value="${vo.member_id }"/>
							<div>
								<span><i class="fas fa-unlock-alt"></i></span>
								<input type="password" name="member_pw" class="chk" placeholder="비밀번호" />
								<div class="common">비밀번호를 입력하세요(영문대.소문자, 숫자를 모두 포함)</div>
							</div>	
							<div>
								<span><i class="fas fa-unlock"></i></span>
								<input type="password" name="pw_ck" class="chk" placeholder="비밀번호 확인" />
								<div class="common">비밀번호를 다시 입력하세요</div>
							</div>	
							<div>
								<span><i class="far fa-user-circle"></i></span>
								<input type="text" name="member_nickname" class="chk" placeholder="닉네임" style="width: 293px;" />
								<a class="btn-check" id="btn-nickname">중복 확인</a>
								<div class="common">닉네임을 입력하세요</div>
							</div>
						 
						 
				</form>
				<div class="btnSet">
						<a id="btn-submit" onclick="$('form').submit();">저장</a>
						<a id="btn-reset" onclick="history.go(-1)">취소</a>
					</div>
				<div id="popup-background" onclick="$('#popup-background, #map').css('display', 'none');"></div>
				<div id="map"></div>
			</div>
		</div> <!-- .content_wrap -->
	</div> <!-- .container -->
</section>
	
	<script type="text/javascript" src="js/join_check.js"></script>
	
	<!-- datepicker 사용하기 위해 : stylesheet도 설정해야 함(위에 link 태그 참조)  -->
	 <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	 
	<!-- 다음 우편번호 api -->
	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	
	<!-- google map을 위한 js -->
	<script type="text/javascript" 
			src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCsrerDHJrp9Wu09Ij7MUELxCTPiYfxfBI" ></script>
	
	<script type="text/javascript">
		function go_join() {
			//아이디
			//중복확인을 한 경우
			if( $("[name=member_id]").hasClass("checked") ) {
				//이미 사용중인 아이디인 경우 회원가입 불가
				if( $("[name=member_id]").siblings("div").hasClass("invalid") ) {
					alert("회원가입 불가!\n" + join.userid.unUsable.desc );
					$("[name=member_id]").focus();
					return;
				}
			} else {
				//중복확인 하지 않은 경우
				if( !item_check( $("[name=member_id]") ) ) 	return;	
				else {
					alert("회원가입 불가!\n" + join.userid.valid.desc );
					$("[name=member_id]").focus();
					return;
				}
			}
			
			if( $("[name=member_name]").val() == "" ) {
				alert("성명을 입력하세요");
				$("[name=member_name]").focus();
				return;
			}

			//invalid인 경우 회원가입되지 않도록 한다.
			
			
			if( !item_check( $("[name=member_pw]") ) ) 		return;		//비밀번호
			if( !item_check( $("[name=pw_ck]") ) ) 		return;		//비밀번호 확인

			//닉네임
			//중복확인을 한 경우
			if( $("[name=member_nickname]").hasClass("checked") ) {
				//이미 사용중인 아이디인 경우 회원가입 불가
				if( $("[name=member_nickname]").siblings("div").hasClass("invalid") ) {
					alert("회원가입 불가!\n" + join.usernickname.unUsable.desc );
					$("[name=member_nickname]").focus();
					return;
				}
			} else {
				//중복확인 하지 않은 경우
				if( !item_check( $("[name=member_nickname]") ) ) 	return;	
				else {
					alert("회원가입 불가!\n" + join.usernickname.valid.desc );
					$("[name=member_nickname]").focus();
					return;
				}
			}

			if( !item_check( $("[name=member_tel]") ) ) 	return;		//핸드폰 번호			
			
			$("form").submit();
		} //go_join()

		function item_check( item ) {
			var data = join.tag_status( item );
			if( data != undefined ){
				if( data.code == "invalid" ) {
					alert( "회원가입 불가\n" + data.desc );
					item.focus();
					return false;
				} 
				else return true;
			}
		} //item_check()
	
		$("#btn-id").on("click", function() {
			userid_check();
		});

		$("#btn-nickname").on("click", function() {
			usernickname_check();
		});

		$("#test").on("click", function() {
			var reg = /(01[016789])([1-9]{1}[0-9]{2,3})([0-9]{4})$/
			var test1 = "010-1234-5678";
			var test2 = "01012345678";

			console.log("- 들어간 전화번호 : " + reg.test(test1));	
			console.log("- 안들어간 전화번호 : " + reg.test(test2));	

		});

		// 아이디 중복확인
		function userid_check() {			
			//올바른 아이디인 경우만 중복확인 필요
			var $userid = $("[name=member_id]");
			if( $userid.hasClass("checked") ) return;	//이미 중복확인을 한 경우, 함수를 끝냄
			var data = join.tag_status( $userid );
			if( data.code != "valid" ) { 
				alert("아이디 중복확인 불필요\n" + data.desc);
				$userid.focus();
				return;
			}

			$.ajax({
				url: "id_check",
				data: { id: $userid.val() },
				success: function( data ) {
					data = join.userid_usable( data );
					display_status( $userid.siblings("div"), data);	
					$userid.addClass("checked");	//이미 중복확인을 한 경우
				},
				error: function(req, text) {
					alert(text + ":" + req.status);
				}			
			});	
		} //userid_check()
		
		// 닉네임 중복확인
		function usernickname_check() {
			//올바른 닉네임 경우만 중복확인 필요
			var $usernickname = $("[name=member_nickname]");
			if( $usernickname.hasClass("checked") ) return;	//이미 중복확인을 한 경우, 함수를 끝냄
			var data = join.tag_status( $usernickname );
			if( data.code != "valid" ) { 
				alert(data.desc);
				$usernickname.focus();
				return;
			}

			$.ajax({
				url: "nickname_check",
				data: { nickname: $usernickname.val() },
				success: function( data ) {
					data = join.usernickname_usable( data );
					display_status( $usernickname.siblings("div"), data);	
					$usernickname.addClass("checked");	//이미 중복확인을 한 경우
				},
				error: function(req, text) {
					alert(text + ":" + req.status);
				}			
			});

		} //usernickname_check()
		
		$(".chk").on("keyup", function() {
			//아이디에 대해 입력 데이터를 변경시 다시 중복확인할 수 있도록 한다
			if( $(this).attr("name") == "member_id" ) {
				if( event.keyCode == 13 ) userid_check();
				else {
					$(this).removeClass("checked");
					validate( $(this) );
				}	
			} else if( $(this).attr("name") == "member_nickname") {
				if( event.keyCode == 13 ) usernickname_check();
				else {
					$(this).removeClass("checked");
					validate( $(this) );
				}
			} else	validate( $(this) );
		});

		function validate( tag ) {
			var data = join.tag_status( tag );
			display_status( tag.siblings("div"), data );
		}

		function display_status(div, data) {
			div.text( data.desc );
			div.removeClass();
			div.addClass( data.code );
		}
		
		function search_location() {
			$('#popup-background, #map').css('display', 'block');
			
			var nav = null;
			var map;
			var marker;
			/* 현재 위치(위도/경도)를 받아오기 위한 부분 */
			$(function() {
				if (nav == null) {
					nav = window.navigator;
				}
				if (nav != null) {
					var geoloc = nav.geolocation;
					if (geoloc != null) {
						/* Callback 성공 시, successCallback() 호출 */
						geoloc.getCurrentPosition(successCallback);
					} else {
						alert("geolocation not supported");
					}
				} else {
					alert("Navigator not found");
				}
			});

			function successCallback(position) {
				/* 위도 */ var latitude = position.coords.latitude;
				/* 경도 */ var longitude = position.coords.longitude; 
				
				/* Google Map으로 위도와 경도 초기화 */
				initialize(latitude, longitude);
			}

			function initialize(latitude, longitude) {
				/* 현재 위치의 위도와 경도 정보를 currentLocatioon 에 초기화 */
				var currentLocation = new google.maps.LatLng(latitude, longitude);
				var mapOptions = {
					center : currentLocation, /* 지도에 보여질 위치 */ 				
					zoom : 15, /* 지도 줌 (0축소 ~ 18확대),  */ 	
					mapTypeId : google.maps.MapTypeId.ROADMAP
				};
				/* DIV에 지도 달아주기 */
				map = new google.maps.Map(document.getElementById("map"),
						mapOptions);
				/* 지도 위에 마커 달아주기 */
				marker = new google.maps.Marker({
					position : currentLocation,
					map : map
				});
				google.maps.event.addListener(marker, 'click', toggleBounce(marker));

				/* 지도에서 마우스 클릭시 마커 생성 */
				google.maps.event.addListener(map, 'click', function(event) {
					addMarker(event.latLng);
				});
			}

			// Add a marker to the map and push to the array.
			/*
			 * 이 소스는 마커를 하나만 추가할 수 있도록 구현해놓습니다.
			 * 개발자분들 재량에 따라 코드를 응용하도록 하세요.  
			 */
			function addMarker(location) {
				/* 기존에 있던 마커 삭제 후 */
				/*새 마커 추가하기. */ 
				marker.setMap(null);
				marker = new google.maps.Marker({
					position : location,
					map : map
				});
				/* 마커 토글바운스 이벤트 걸어주기(마커가 통통 튀도록 애니메이션을 걸어줌) */
				google.maps.event.addListener(marker, 'click', toggleBounce(marker));
			}

			function toggleBounce(marker) {
				if (marker.getAnimation() != null) {
					marker.setAnimation(null);
				} else {
					marker.setAnimation(google.maps.Animation.BOUNCE);
				}
			}
			google.maps.event.addDomListener(window, 'load', initialize);
			/*
			function geo_success(position) { 
				var latitude = position.coords.latitude;
				var longitude =  position.coords.longitude;
				alert("현재 위치는 : " + latitude + ", " + longitude);
			}
			
			function geo_error() { 
				alert("위치 정보를 사용할 수 없습니다."); 
			}

			var geo_options = {	
				enableHighAccuracy: true, // 불리언 
				maximumAge : 30000, // 천분의 1초 단위 
				timeout : 27000 // 천분의 1초 단위 
			};

			navigator.geolocation.getCurrentPosition(geo_success, geo_error, geo_options);
			*/
		} //search_location()

	</script>
</body>
</html>