<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="lcon" type="image/x-icon" href="img/logo.jsp">
<link rel="stylesheet" type="text/css" 
     href="css/common.css?v=<%=new java.util.Date().getTime()%>">
     
<link href="css/basic.css" rel="stylesheet" type="text/css">
<link href="css/default.css" rel="stylesheet" type="text/css">
<link href="css/owl.carousel.css" rel="stylesheet" type="text/css">
<link href="css/sub.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>

<tiles:insertAttribute name="header"/>
<div id="content">
<tiles:insertAttribute name="content"/>
</div>
<tiles:insertAttribute name="footer"/>

</body>
</html>