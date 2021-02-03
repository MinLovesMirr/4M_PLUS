<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% request.setCharacterEncoding("UTF-8"); 
String id = (String)session.getAttribute("sessionId");%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/ERPcss.css">
</head>
<body>
	<jsp:include page="T_include.jsp" flush="true"/>
	<div class="sidebar"><jsp:include page="l_include.jsp"/></div>
	
	<div class="bodyall">
	<jsp:useBean id="PRlist" class="java.util.ArrayList"/>
	<jsp:useBean id="PRmap" class="java.util.HashMap"/>
	
	<form name="mwhout" method="get" action="MCHWHOUT.do">
	<h1>배송을 할 물품을 확인하십시오.</h1>
	<div class="first">
	<button type="button" onclick="location.href='/mchwh.do'">제품등록</button>
	<button type="button" onclick="location.href='/mchwhsea.do'">제품검색</button>
	</div>
	<br><br>
		<table border="1">
			<tr>
			<!-- 작업번호	제품모델번호	제품명	수량	불량갯수	입/출고 -->
			<th>작업번호</th>
			<th>제품모델번호</th>
			<th>제품명</th>
			<th>생산 수량</th>
			<th>불량 수량</th>
			<th>실 생산수량</th>
			<th>출고</th>
			<th>입력</th>
			</tr>
			<!-- 수정 -->
			<c:set var="mwh" value="${list}"/><!-- 수정할 자료가져오기 -->
			<tr>
				<td><input name="LOT_NO" type="text" size=10 value="${mwh.LOT_NO}" readonly="readonly"/></td>
				<td><input name="PART_NO" type="text" size=10 value="${mwh.PART_NO}" readonly="readonly"/></td>
				<td>
				<c:forEach var="name" items="${partName}">
				<c:if test="${name.PART_NO == mwh.PART_NO}">
					<input name="PART_NAME" type="text" size=20 value="${name.PART_NAME}" readonly="readonly"/>
				</c:if>
				</c:forEach>
				</td>
				<td><input name="QUAN" type="text" size=10 value="${mwh.QUAN}" readonly="readonly"/>EA</td>
				<td><input name="FAIL" type="text" size=10 value="${mwh.FAIL}" readonly="readonly"/>EA</td>
				<td><input name="R_QUAN" type="text" size=10 value="${mwh.R_QUAN}" readonly="readonly"/>EA</td>
				<td><select name="IO" size=1>
					<c:if test="${(mwh.IO == 'X') || (mwh.IO == 'x') }" >
					<option value="X" selected="selected">출고</option>
					</c:if>
					<option value="X">출고</option>
				</td>
				<td>
					<button type="submit">출고</button>
				</td>	
			</tr>
		</table>
	<hr>
	</form>
	</div>
</body>
</html>