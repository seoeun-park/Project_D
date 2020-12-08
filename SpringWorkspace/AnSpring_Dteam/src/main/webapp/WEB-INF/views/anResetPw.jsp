<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int succ = (Integer)request.getAttribute("anResetPw");

	if(succ > 0) {
		out.println("재설정 성공!");
	} else {
		out.println("재설정 실패!");
	}
%>