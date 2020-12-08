<%@page import="com.dteam.app.dto.MemberDto"%>

<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonObject"%>

<%@page import="org.springframework.ui.Model"%>
<%@page import="java.sql.*, java.sql.Date, javax.sql.*, javax.naming.*, 
					java.util.*, java.io.PrintWriter" %>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%> 
<%
Gson gson = new Gson();
String json = gson.toJson((MemberDto)request.getAttribute("anNickNameCheck"));
MemberDto dto = (MemberDto)request.getAttribute("anNickNameCheck");

if(dto != null) {
	//out.println(dto.getMember_nickname() + "은 사용 불가능한 닉네임입니다.");
	out.println(json);
} else {
	out.println("사용 가능한 닉네임입니다.");	
}

%>