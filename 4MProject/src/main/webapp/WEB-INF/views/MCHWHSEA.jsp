<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("UTF-8"); 
String id = (String)session.getAttribute("sessionId");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품창고검색</title>

<link rel="stylesheet" href="resources/css/ERPcss.css">

</head>
<body>
	<jsp:include page="T_include.jsp"/>
	<div class="sidebar"><jsp:include page="l_include.jsp"/></div>
			
	<div class="bodyall">
		<jsp:useBean id="PRlist" class="java.util.ArrayList"/>
		<jsp:useBean id="PRmap" class="java.util.HashMap"/>
		<div>
			<form name="mhsea" method="get" action="MCHWHSCH.do?PART_NO=${PART_NO}">
				<h1>제품 창고 조회</h1>
				<div class="first">
					<button type="button" onclick="location.href='/mchwh.do'">제품등록</button>
				</div>
					<input name="PART_NO" type="text" size=10 placeholder="제품모델번호"/>
				<button type="submit">검색</button>
			</form>
		</div>
		<hr>
		<div class="partNo">
			<h1>창고에 입고된 전체 물품의 종류와 수량입니다.</h1>
			<table class="table" border="1">
				<tr>
				<th>제품모델번호</th>
				<th>제품모델명</th>
				<th>제품 수량</th>
				<th>검색</th>
				</tr>
				<c:forEach var="nameCount" items="${nameCount}">
				<tr>
				<td><input type="text" value="${nameCount.PART_NO}" size=8 readonly="readonly" /></td>
				<c:forEach var="name" items="${partName}">
				<c:if test="${name.PART_NO == nameCount.PART_NO}">
				<td><input name="PART_NAME" type="text" size=20 value="${name.PART_NAME}" readonly="readonly"/></td>
				</c:if>
				</c:forEach>
				<td><input type="text" value="${nameCount.count}" size=5 readonly="readonly" />
				</td>
				<td>
					<button type="button" onclick="location.href='MCHWHSCH.do?PART_NO=${nameCount.PART_NO}'">검색</button>
				</td>
				</tr>
				</c:forEach>
			</table>
			
			<div class="paging">
				<c:forEach begin="1" end="${pageInNum}" var="inNum">
		  			<span>
		    			<li class="p_li"><a class="p_font" href="mchwhsea.do?inNum=${inNum}">${inNum}</a></li>
		 			</span>
				</c:forEach>
			</div>
		</div>
		<div class="LOT_NO">
			<hr>
			<h2>창고에 입고된 물품들입니다.</h2>
			<table class="table" border="1">
				<tr>
					<th><a href="MCHWHSEA.do?ORDER=LOT_NO">작업번호</a></th>
					<th><a href="MCHWHSEA.do?ORDER=PRAT_NO">제품모델번호</a></th>
					<th>제품명</th>
					<th><a href="MCHWHSEA.do?ORDER=QUAN">생산수량</a></th>
					<th><a href="MCHWHSEA.do?ORDER=FIAL">불량수량</a></th>
					<th><a href="MCHWHSEA.do?ORDER=R_QUAN">실 생산수량</a></th>
					<!-- <th>입고</th> -->
					<th>비고</th>
				</tr>
				<c:forEach var="mwh" items="${list}">
				<tr>
					<td><input type="text" name="LOT_NO" value="${mwh.LOT_NO}" size=8 readonly="readonly"></td>
					<td><input type="text" name="PART_NO" value="${mwh.PART_NO}" size=8 readonly="readonly"/></td>
					<c:forEach var="name" items="${partName}">
						<c:if test="${name.PART_NO == mwh.PART_NO}">
							<td><input name="PART_NAME" type="text" size=20 value="${name.PART_NAME}" readonly="readonly"/></td>
						</c:if>
					</c:forEach>
					<td><input type="text" name="QUAN" value="${mwh.QUAN}" size=5 readonly="readonly"/></td>
					<td><input type="text" name="FAIL" value="${mwh.FAIL}" size=5 readonly="readonly"/></td>
					<td><input type="text" name="R_QUAN" value="${mwh.R_QUAN}" size=5 readonly="readonly"/></td>
					<%-- <td>
					<c:if test="${(mwh.IO == 'O') || (mwh.IO == 'o')}" >
						<p>입고</p>
					</c:if>
					<c:if test="${(mwh.IO == 'X') || (mwh.IO == 'x') }" >
						<p>출고</p>
					</c:if></td> --%>
					<td><!-- 비고 -->
					<c:if test="${(mwh.IO == 'O') || (mwh.IO == 'o')}" >
						<button type="button" onclick="location.href='/mchwhout.do?LOT_NO=${mwh.LOT_NO}'">출고</button>
					</c:if>
					</td>
				</tr>
				</c:forEach>
			</table>
		</div>
		<div class="paging">
			<c:forEach begin="1" end="${pageNum}" var="num">
	  			<span>
	    			<li class="p_li"><a class="p_font" href="mchwhsea.do?num=${num}">${num}</a></li>
	 			</span>
			</c:forEach>
		</div>
		<hr>
	</div>
</body>
</html>