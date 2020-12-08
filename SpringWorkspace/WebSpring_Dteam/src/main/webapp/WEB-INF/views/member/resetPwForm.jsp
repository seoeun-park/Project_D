<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
header, footer {
	display: none;
}

#content {
	padding: 0
}

#main_wrap {
	background-color: #3e4ba9;
	color: #ffffff;
	overflow: hidden;
}

#main {
	width: 400px;
	margin: 15px auto;
	overflow: hidden;
}

#main>img, #main>h4 {
	float: left;
}

#main>h4 {
	padding: 30px 0 0 20px;
}

#alert_wrap {
	background-color: #f2f2f2;
}

#alert {
	padding: 40px 0;
}

#alert>i {
	font-size: 40px;
	margin-right: 20px;
}

#form_wrap {
	width: 40%;
	padding: 40px 0 0 0;
	margin: 0 auto;
}

#form_wrap>input {
	height: 45px;
	width: 100%;
	margin-bottom: 20px;
	box-sizing: border-box;
}

#pk_invalid, #pk_ck_invalid {
	font-size: 13px;
	color: red;
	margin-bottom: 10px;
	display: none;
	text-align: left;
}

#form_wrap>a {
	width: 100%;
	background-color: #3e4ba9;
	height: 50px;
	display: block;
	color: #ffffff;
	line-height: 50px;
	color: #ffffff;
	text-decoration: none;
	font-weight: 900;
}
</style>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script type="text/javascript">
	function go_reset() {
		var reg = /[^a-z0-9#?!@$%^&*-]/g;
		var special = /[#?!@$%^&*-]/g, lower = /[a-z]/g, digit = /[0-9]/g;
		var pw = $("#pw").val();

		if (pw == "") {
			//alert("비밀번호를 입력하세요.");
			$("#pk_invalid").text("비밀번호를 입력하세요").css("display", "block");
			$("#pw").focus();
			return;
		} else if ($("#pw_ck").val() == "") {
			$("#pk_ck_invalid").text("비밀번호 확인란을 입력하세요.")
					.css("display", "block");
			$("#pw_ck").focus();
			return;
		} else if (pw != $("#pw_ck").val()) {
			error_pw("비밀번호가 일치하지 않습니다.", "#pk_ck_invalid");
			return;
		} else if (pw.length < 8) {
			error_pw("비밀번호는 8자 이상 입력해주세요.", "#pk_invalid");
			return;
		} else if (reg.test(pw)) {
			error_pw("비밀번호는 소문자, 숫자, 특수문자를 포함해주세요.", "#pk_invalid");
			return;
		} else if (!lower.test(pw) || !digit.test(pw) || !special.test(pw)) {
			error_pw("비밀번호는 소문자, 숫자, 특수문자를 포함해주세요.", "#pk_invalid");
			return;
		}

		function error_pw(data, type) {

			$(type).text(data).css("display", "block");
			$("#pw").val("");
			$("#pw_ck").val("");
		}

		$.ajax({
			url : "resetPw", //Controller
			data : {
				member_pw : $("#pw").val(),
				member_token : "${member_token}"
			},
			success : function(data) {
				if (data) {
					alert("비밀번호 재설정 되었습니다.");
					//history.go(-1);
					location.href = "/dteam";

				} else {
					alert("비밀번호 재설정에 실패했습니다. 관리자에게 문의하세요");
					location.href = "/dteam";
				}
			},
			error : function(req, text) {
				alert(text + ":" + req.status);
			}
		});
	} //go_reset()
</script>
</head>
<body>
	<div id="main_wrap">
		<div id="main">
			<img src="img/w_logo.png" />
			<h4>대여 안대여 비밀번호 재설정</h4>
		</div>
	</div>
	<div id="alert_wrap">
		<div id="alert">
			<i class="fas fa-unlock-alt" style="color: #3e4ba9;"></i> <span>
				<b>8</b>자리 이상 알파벳 <b>소문자, 숫자 및 특수 문자</b>를 조합하여 입력해주세요.
			</span>
		</div>
	</div>
	<div id="form_wrap">
		<input id="pw" type="password" name="pw"
			placeholder="새로운 비밀번호 입력(최소 8자리 이상)" /><br />
		<div id="pk_invalid">비밀번호는</div>
		<input id="pw_ck" type="password" name="pw_ck"
			placeholder="새로운 비밀번호 확인" />
		<div id="pk_ck_invalid">비밀번호는</div>
		<a onclick="javascript:go_reset();">비밀번호 재설정 완료</a>
	</div>
</body>
</html>