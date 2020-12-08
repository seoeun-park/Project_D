<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.tab li {
    float: left;
    width: 33.3%;
    border-left: 1px solid #ddd;
    text-align: center;
    box-sizing: border-box;
    color: #333333
}

.tab li.on {
    background-color: #eee;
    color: #333333;
    font-weight: bold;
    font-size: 1.1em;
    border-bottom: 2px solid #3E4BA9;
}

.tab_con div { background:#fff; line-height:100px; text-align:center; }  

.jetab_con { clear:both; margin-top:5px; border:1px solid #ddd; padding: 15px 0;}
.jetab_con div { display:block; background:#fff; line-height:30px; text-align:center; padding: 0 15px;}

table {
	width: 100%; 
	margin: 0 auto;
	border: 1px solid;
	border-collapse: collapse;  
}

table th, table td{
	border: 1px solid #ddd;
	padding: 5px 10px;
}

</style>
</head>
<body>
<!-- 공통으로 넣기 -->
<section id="content_area">
	<div class="container">
		<div class="content_wrap">
			<div class="content_top">
				<h3>1:1 문의하기</h3>
				<ul class="breadcrumb">
					<li><img src="img/home.png" alt="홈"> &gt;</li>
					<li>1:1 문의하기</li>
				</ul>
			</div>
			<div class="contents">
<!------------------------------------------------------------------------------------------------->
				<!-- 탭메뉴 -->
				<ul class="tab" id="tab">
					<li onclick="location.href='list.no'">공지사항</li>
					<li onclick="location.href='list.fa'">자주묻는 질문</li>
					<li>1:1 문의하기</li>
				</ul>
		
				<div class="jetab_con" id="tab_con">
					<div class="qna">
					<!------------------------------------------------------------------------------------------------->
					<h3>1:1문의하기 글쓰기</h3>
					<!-- 
					파일전송시
					1. form의 전송방식은 반드시 post 로 지정한다. 
					2. form enctype 을 지정한다. enctype='multipart/form-data'
					 -->
					<form action="insert.qn" method="post" enctype='multipart/form-data'>
					<table>
						<tr><th>제목</th>
							<td><input type="text" name="title" class="need" title="제목" style="height: 27px;"/></td>
						</tr>
						<tr><th>내용</th>
							<td><textarea name="content" class="need" title="내용" ></textarea></td>
						</tr>
						<tr><th>첨부파일</th>
							<td class="left">
								<label>
									<input type="file" name="file" id="attach-file" />
									<img alt="파일선택" class="file-img" src="img/select.png">
								</label>
								<span id="file-name"></span>
								<span id="delete-file" style="color: red;" ><i class="fas fa-times font-img"></i></span>
							</td>
						</tr>
					</table>
					</form>
					
					<div class="btnSet">
					<!-- <a class="btn-fill" onclick="if( necessary() ){ console.log( $('[name=title]').val() ); $('form').submit() }">저장</a> -->
					<a class="btn-fill" onclick="if( necessary() ){ $('form').submit() }">저장</a>
					<a class="btn-empty" href="list.qn">취소</a>
					</div>
					<!------------------------------------------------------------------------------------------------->
					<!-- 탭메뉴 닫는태그 -->
					</div>
				</div>

<!------------------------------------------------------------------------------------------------->
			</div>
		</div>
	</div>
</section>
<script type="text/javascript" src="js/need_check.js"></script>
<script type="text/javascript" src="js/file_attach.js"></script>
<script type="text/javascript">
/* 공지사항/자주묻는질문/1:1문의 탭 */

$(function () {	
	tab('#tab',2);	
});

function tab(e, num){
    var num = num || 2;
    var menu = $(e).children();
    var con = $('jetab_con').children();
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