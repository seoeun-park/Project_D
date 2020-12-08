<%@page import="com.dteam.app.dto.ReviewDto"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.dteam.app.dto.MdDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
ArrayList<ReviewDto> list = (ArrayList<ReviewDto>) request.getAttribute("list");
Gson gson = new Gson();
out.println(gson.toJson(list));
%>