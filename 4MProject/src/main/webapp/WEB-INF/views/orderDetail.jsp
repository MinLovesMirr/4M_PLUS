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
<title>생산계획 등록창</title>
</head>
<body>
<jsp:include page="T_include.jsp" flush="true"/>
   <div class="sidebar"><jsp:include page="l_include.jsp"/></div>
   <div class="bodyall">
	<form name="m_update" method="get" action="${contextPath}/orderToPInsert.do">
	<h1>주문상세페이지</h1>
	<div class="tablediv">
	<table class="table" border="1">
		<tr>
			<th class="LOT">주문번호</th>
			<th class="NAME">제품모델번호</th>
			<th>성별</th>
			<th class="QUAN">S</th>
			<th class="QUAN">M</th>
			<th class="QUAN" >L</th>
			<th class="QUAN">XL</th>
			<th class="QUAN">F</th>
			<th class="QUAN">TOTAL</th>

		</tr>
	<c:forEach var="row" items="${list}">
		<tr>
			<td><input type="text" name="LOT_NO" value="${row.request_code}" style="background-color:transparent;border:0" readonly></td>
			<td>${row.product_code}</td>
			<td>${row.gender}</td>
			<td>${row.s}</td>
			<td>${row.m}</td>
			<td>${row.l}</td>
			<td>${row.xl}</td>
			<td>${row.f}</td>
			<td>${row.total}</td>
		</tr>
	</c:forEach>
	</table>
	</div>
	<div class="bottom_buttons">
	<button type="submit">등록하기</button>
	<button type="button" onclick="location.href='orderListPage.do'">조회화면으로</button>
	</div>
	</form>
	</div>
</div>
</form>
</body>
</html>