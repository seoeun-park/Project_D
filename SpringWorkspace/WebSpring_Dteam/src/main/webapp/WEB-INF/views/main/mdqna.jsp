<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
header,footer { display: none;}
hr { color: #3a3a3a; margin-bottom: 20px;}

</style>
</head>
<body>
<div style="margin: 0 auto; padding-top: 20px; width: 800px;" >
	<div id='comment_regist'>
		<span class="left"><strong>문의작성</strong></span>
		<span class="right"><a onclick="comment_regist()" class="btn-fill-s2">등록</a></span>
		<textarea id="comment" placeholder="내용을 입력하세요" style="width: 99%; height: 50px; border:1px solid #d3d3d3; margin-top:30px; margin-bottom:30px; resize: none; padding: 10px;	"></textarea>	
	</div>
	<div id = "comment_list" class="left">
		
	</div>
</div>

<script type="text/javascript">

comment_list();

function comment_list(){
	$.ajax({
		url:'detail.ma/comment/${md_serial_number}',
		success: function(data){
			
			$('#comment_list').html(data);
		},error: function(req, text) {
			alert(text+':'+req.status);
		}
	})
}


function comment_regist(){
	if( ${empty login_info}){
		alert('댓글을 등록하려면 로그인하세요!')
		return;
	}else if( $('#comment').val() == '' ){
		alert('댓글을 입력하세요')
	}
	
	$.ajax({
		url: 'detail.ma/comment/regist',
		type: 'post',
		data: JSON.stringify( { content:$('#comment').val(), md_serial_number:${md_serial_number} } ),
		contentType: 'application/json',
		success: function(data){
			if(data == 1){
				alert('댓글이 등록되었습니다!')
				$('#comment').val('');
				comment_list();
			}
		},error: function(req, text) {
			alert(text+':'+req.status);
		}
	});
}

</script>

</body>
</html>