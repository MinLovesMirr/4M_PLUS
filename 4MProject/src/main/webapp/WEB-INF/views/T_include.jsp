<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*" isELIgnored="false"%>
	<!-- <link rel="stylesheet" href="resources/css/ERPcss.css"> -->
<%
	request.setCharacterEncoding("UTF-8");
	String id = (String)session.getAttribute("sessionId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.t_header {
  height: 55px;
  color: white;
  background: black;
  display: flex;
  justify-content: space-between;
  align-items: center;
	}
.jin {
	color: white;
	text-decoration:none;
	}
</style>
</head>
<body>
<header class="t_header">
	<h1>반갑습니다. <%= id %>님</h1>
	
	<a class="jin" href='/logout.do'>로그아웃</a>
</header>
</body>
</html>