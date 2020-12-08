<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String state = (String) request.getAttribute("anSendEmail");
out.println(state);
%>