<%@page import="com.google.gson.Gson"%>
<%@page import="com.dteam.app.dto.MemberDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
ArrayList<MemberDto> list = (ArrayList<MemberDto>) request.getAttribute("list");
Gson gson = new Gson();
out.println(gson.toJson(list));
%>