<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% request.setCharacterEncoding("UTF-8"); 
String id = (String)session.getAttribute("sessionId");%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>제품검색결과</title>
<link rel="stylesheet" href="resources/css/ERPcss.css">
</head>
<body>
	<jsp:include page="T_include.jsp" flush="true"/>
	<div class="sidebar"><jsp:include page="l_include.jsp"/></div>
	
	<div class="bodyall">
	<form name="mwhsch" method="get" ><%-- action="MCHWHCOR.do/${mwh.LOT_NO}"> --%>
		<h1>창고에 입고 되어 있는 검색한 물품의 총 수량입니다.</h1>
		<div class="first">
		<button type="button" onclick="location.href='/mchwh.do'">제품등록</button>
		<button type="button" onclick="location.href='/mchwhsea.do'">제품검색</button>		
		</div>
		<br><br>
		<%-- <c:set var="mwh" value="${list}"/><!-- 수정할 자료가져오기 --> --%>
		<c:set var="schPartNo" value="${schPartNo}"/>
		<table class ="table" border="1">
			<tr>
			<th>제품모델번호</th>
			<th>제품명</th>
			<th>제품 수량</th>
			</tr>
			<c:forEach var="nameCount" items="${nameCount}">
				<tr><td><input type="text" value="${nameCount.PART_NO}" readonly="readonly" /></td>
				<c:forEach var="name" items="${partName}">
					<c:if test="${name.PART_NO == nameCount.PART_NO}">
				<td><input name="PART_NAME" type="text" size=20 value="${name.PART_NAME}" readonly="readonly"/></td>
					</c:if>
				</c:forEach>
				<td><input type="text" value="${nameCount.count}" readonly="readonly" /></td></tr>
			</c:forEach>
		</table>
		<br>
	</form>
	<hr>
	
	<h2>검색한 물품의 입고 상태입니다.</h2>
	
	<table class ="table" border="1">
	<tr>
		<th><a href="MCHWHSCH.do?ORDER=LOT_NO">작업번호</a></th>
		<th><a href="MCHWHSCH.do?ORDER=PRAT_NO">제품모델번호</a></th>
		<th>제품명</th>
		<th><a href="MCHWHSCH.do?ORDER=QUAN">생산수량</a></th>
		<th><a href="MCHWHSCH.do?ORDER=FIAL">불량수량</a></th>
		<th><a href="MCHWHSCH.do?ORDER=R_QUAN">실 생산수량</a></th>
		<th>입고</th>
		<th>출고</th>
		<th>비고</th>
	</tr>
	<c:forEach var="mwh" items="${list}">
	<tr>
		<td><input type="text" name="LOT_NO" value="${mwh.LOT_NO}" readonly="readonly" style="width:100px;"/></td>
		<td><input type="text" name="PART_NO" value="${mwh.PART_NO}" readonly="readonly"/></td>
		<c:forEach var="name" items="${partName}">
			<c:if test="${name.PART_NO == mwh.PART_NO}">
			<td><input name="PART_NAME" type="text" size=20 value="${name.PART_NAME}" readonly="readonly"/></td>
			</c:if>
		</c:forEach>
		<td><input type="number" name="QUAN" value="${mwh.QUAN}" readonly="readonly" style="width:100px;"/></td>
		<td><input type="number" min=0 name="FAIL" value="${mwh.FAIL}" readonly="readonly" style="width:100px;"/></td>
		<td><input type="number" name="R_QUAN" value="${mwh.R_QUAN}" readonly="readonly" style="width:100px;"/></td>
		
		<td>
		<c:if test="${(mwh.IO == 'O') || (mwh.IO == 'o')}" >
		<p>입고</p>
		</c:if>
		<c:if test="${(mwh.IO == 'X') || (mwh.IO == 'x') }" >
		<p>출고</p>
		</c:if>
		</td>
		<td>
		<button type="button" onclick="location.href='/mchwhout.do?LOT_NO=${mwh.LOT_NO}'">출고</button>
		</td>
		<td align = "center">
			<a href="MCHWHCOR.do?LOT_NO=${mwh.LOT_NO}">수정</a>
		<!-- <button type="submit" >수정</button> -->
		</td>		
	</tr>
	</c:forEach>
	
	</table>
		<div class="third">
		<c:forEach begin="1" end="${pageNum}" var="num">
  			<span>
    			<li class="p_li"><a class="p_font" style="color:white" href="MCHWHSCH.do?PART_NO=${schPartNo.PART_NO}&&num=${num}">${num}</a></li>
 			</span>
		</c:forEach>
	</div>
	</div>
</body>
</html>