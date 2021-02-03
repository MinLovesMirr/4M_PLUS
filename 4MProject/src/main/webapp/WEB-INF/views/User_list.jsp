<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>회원목록</h2>
	<table border="1" width="700px">
		<tr>
			<th>아이디</th>
			<th>패스워드</th>
			<th>권한</th>
		</tr>
		<c:forEach var="row" items="${list}">
		<tr>
			<td>${row.ID}
			<td>${row.PW}
			<td>${row.AUTHOR}
		</tr>
		</c:forEach>
	</table>
</body>
</html>