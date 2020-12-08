<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
header,footer { display: none;}
#content { min-width: 1000px; }

</style>
</head>
<body>
<div class="contents">

	<form name="form1" id="form1" method="post" action="${path }/lend/update.do" style="width: 100%;"> 
		<table class="t_style01" style="width: 95%; margin: 0 auto;" >
			<colgroup>
				<col width="30%;">
				<col width="20%;">
				<col width="20%;">
				<col width="20%;">
			</colgroup>
			<thead>
				<tr>
					<th>상품이미지</th>
					<th>상품명</th>
					<th> 찜</th>
					<th>대여가격</th>
				</tr>	
			</thead>
			<tbody>
				<c:forEach items="${page}" var="vo">
				<tr>
					<td><img src='${vo.md_photo_url}' style='width: 100px; height: 100px; border-radius: 10px;'></td>
					<td>${vo.md_name}</td>
					<td>${vo.md_fav_count}</td>
					<td>${vo.md_deposit}
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</form>


</div>
</body>
</html>