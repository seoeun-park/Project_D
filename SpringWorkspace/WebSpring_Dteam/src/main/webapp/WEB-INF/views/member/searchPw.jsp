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
		width: 85px; 
		float: left; 
		background-color: #3e4ba9;
		height: 35px;
		line-height: 35px;
		margin-left: 10px;
		border-radius: 5px; 
	}
	
	#pw_form {
		background-color: #f2f2f2;
		height: 350px;
		padding-top: 100px;
	}
	
	#show_pw {
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
		var reg = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		
		var $member_name = $("#member_name");
		var $member_id = $("#member_id");
		
		if( $member_name.val() == "" ) {
			alert("이름을 입력하세요.");
			$member_name.focus();
			return;
		} else if ( $member_id.val() == "" ) {
			alert("아이디(이메일)를 입력하세요.");
			$member_tel.focus();
			return;
		} else if(!reg.test($member_id.val())) {
			alert("이메일 형식으로 입력해주세요.");
			return;
		}

		$.ajax({
			url: "searchPw",		//Controller
			data: { member_name:$member_name.val(), member_id:$member_id.val()  },
			success: function(data) {
				if (data) {
					$("#show_pw").css("display", "block");
				} else {
					alert("이름이나 아이디(이메일)이 회원정보와 일치하지 않습니다.\n다시 입력해주세요.");
					$member_name.val("");
					$member_id.val("");
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
<div class="ml20 mb20 tl"><span class="mr10"><i class="fas fa-check"></i></span><span style="font-weight: 900">소셜 계정 회원</span>은 비밀번호 찾기가 불가능합니다.</div>
<div class="ml20 mb20 tl"><span class="mr10"><i class="fas fa-check"></i></span>입력하신 아이디(이메일) 주소로 메일이 발송됩니다. 비밀번호를 재설정해주세요.</div>
<table class="basic_table">
	<tr>
		<th><span>이름</span></th>
		<td><input type="text" name="member_name" id="member_name" /></td>
	</tr>
	<tr>
		<th>아이디(이메일)</th>
		<td><input type="text" name="member_id" id="member_id" /><a style="color: #ffffff; margin: 0 0 0 10px;" onclick="search_id();">이메일 보내기</a></td>
	</tr>
</table>
<div id="pw_form">
	<h4 id="show_pw" style="padding-left: 350px;">
		이메일이 발송되었습니다.<br/><br/>
		비밀번호를 재설정해주세요.
	</h4>
</div>
</body>
</html>