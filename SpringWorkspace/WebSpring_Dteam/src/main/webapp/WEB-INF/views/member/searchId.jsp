<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	header, footer { display: none; }
	input { float: left; margin-left: 20px; height: 35px; }
	
	a { 
		display: block; 
		width: 70px; 
		float: left; 
		background-color: #3e4ba9;
		height: 35px;
		line-height: 35px;
		margin-left: 10px;
		border-radius: 5px; 
	}
	
	#id_form {
		background-color: #f2f2f2;
		height: 350px;
		padding-top: 100px;
	}
	
	#member_id {
		color: #3e4ba9;
		font-size: 25px;
	}
	
	#show_id {
		text-align: left;
		display: none;
	}
	
	.mr10 {
		color: #3e4ba9;
	}
	
</style>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript">
	function search_id() {
		var reg = /^01(?:0|1|[6-9])(\d{3}|\d{4})(\d{4})$/;
		var $member_name = $("#member_name");
		var $member_tel = $("#member_tel");
		
		if( $member_name.val() == "" ) {
			alert("이름을 입력하세요.");
			$member_name.focus();
			return;
		} else if ( $member_tel.val() == "" ) {
			alert("핸드폰 번호를 입력하세요.");
			$member_tel.focus();
			return;
		} else if(!reg.test($member_tel.val())) {
			alert("-를 제외한 숫자만 입력하세요");
			return;
		}

		$.ajax({
			url: "searchId",		//Controller
			data: { member_name:$("#member_name").val(), member_tel:$("#member_tel").val()  },
			success: function(data) {
				if (data != "") {
					$("#show_id").css("display", "block");
					$("#member_id").text(data);
				} else {
					alert("이름이나 핸드폰 번호가 회원정보와 일치하지 않습니다.\n다시 입력해주세요.");
					$member_name.val("");
					$member_tel.val("");
				}
					
			},
			error: function(req, text) {
				alert(text + ":" + req.status);
			}
		});
	}
</script>
</head>
<body>
<div class="ml20 mb20 tl"><span class="mr10"><i class="fas fa-check"></i></span><b>소셜 계정 회원</b>은 아이디 찾기가 불가능합니다.</div>
<div class="ml20 mb20 tl"><span class="mr10"><i class="fas fa-check"></i></span>핸드폰 번호는 -를 제외한 <b>숫자만</b> 입력하세요</div>
<table class="basic_table">
	<tr>
		<th><span>이름</span></th>
		<td><input type="text" name="member_name" id="member_name" /></td>
	</tr>
	<tr>
		<th>핸드폰 번호</th>
		<td><input type="text" name="member_tel" id="member_tel" /><a style="color: #ffffff; margin: 0 0 0 10px;" onclick="search_id();">인증하기</a></td>
	</tr>
</table>
<div id="id_form">
	<h4 id="show_id" style="padding-left: 350px;">회원님의 아이디는<br/><br/><br/>
		<span id="member_id"></span>
		입니다
	</h4>
</div>
</body>
</html>