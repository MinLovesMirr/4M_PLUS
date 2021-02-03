<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%
	request.setCharacterEncoding("UTF-8");
	String id = (String)session.getAttribute("sessionId");
%>  
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="resources/css/ERPcss.css">
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function nullchecker(){
	if(m_input.M_NO.value==""){
		alert("자재번호를 입력해주세요!");
		return false;
    }
    if(m_input.M_NAME.value==""){
		alert("자재이름을 입력해주세요!");
		return false;
    }
    if(m_input.M_QUAN.value==""){
		alert("자재수량을 입력해주세요!");
		return false;
    }
    return true;
}
</script>
</head>
<body>
	<jsp:include page="T_include.jsp"/>
	<div class="sidebar"><jsp:include page="l_include.jsp"/></div>
	<div class="bodyall">
		<h1>자재추가 페이지</h1>
		<form name="m_input" method="get" action="${contextPath}/m_input.do" onsubmit="return nullchecker()" autocomplete="off">
		<table class="table" border="1">
		<tr>
			<th>자재번호</th>
			<th>자재명</th>
			<th>수량</th>
		</tr>
		<tr>
			<td><input type="text" name="M_NO"></td>
			<td><input type="text" name="M_NAME"></td>
			<td><input type="number" name="M_QUAN" min="0"></td>
		</tr>
		</table>
		<div class="bottom_buttons">
			<input type="submit" value="추가하기">
			<input type="reset" value="다시입력">
			<button type="button" onclick="location.href='m_view.do'">돌아가기</button>
		</div>
		</form>
	</div>
</body>
</html>