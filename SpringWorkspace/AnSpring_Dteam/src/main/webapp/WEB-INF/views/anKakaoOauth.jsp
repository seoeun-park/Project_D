<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String access_Token = (String)request.getAttribute("access_Token");
String userId = (String)request.getAttribute("userId");
String userNickname = (String)request.getAttribute("userNickname");
String userProfile = (String)request.getAttribute("userProfile");
out.println(access_Token);
out.println(userId);
out.println(userNickname);
out.println(userProfile);
%>

