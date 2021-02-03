<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("UTF-8"); 
String id = (String)session.getAttribute("sessionId");%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="resources/css/ERPcss.css">


<script language="javascript">
function check() {
	var f = document.mwh; 
	if(f.FAIL.value==""){
		alert("불량갯수를 입력하시오.");
		f.FAIL.focus();
		return false;
	}
	return true;
}//check()
</script>

<head>
<meta charset="UTF-8">
<title>상품창고등록</title>
</head>
<body>
	<jsp:include page="T_include.jsp" flush="true"/>
	<div class="sidebar"><jsp:include page="l_include.jsp"/></div>
	
	<div class="bodyall">
	<jsp:useBean id="PRlist" class="java.util.ArrayList"/>
	<jsp:useBean id="PRmap" class="java.util.HashMap"/>
	<div>
	<!-- onsubmit = submit 하기전에 펀션을 먼저 검사해서 통과면 true -->
	<form name="mwh" method="get" onsubmit="return check()">
	<h1>완성된 물품을 작업번호 기준으로 창고에 입고하십시오.</h1>
		<!-- <input name="LOTNO" type="text" placeholder="작업번호를 입력하시오.">
		<button type="submit?lotno">검색</button> -->
		<div class="first">
		<button type="button" onclick="location.href='/mchwhsea.do'">제품조회</button>
		</div>
		<br><br>
		<hr>
		
		<c:if test="${mwhIn != null}">
		<h2>선택한 작업물품의 불량 수량을 입력하십시오.</h2>
		<table class="table" border="1">
			<tr>
			<!-- 작업번호	제품모델번호	제품명	수량	불량갯수	입/출고 -->
			<th>작업번호</th>
			<th>제품모델번호</th>
			<th>제품명</th>
			<th>수량</th>
			<th>불량 수량</th>
			<th>입고</th>
			<th>입력</th>
			</tr>
			<%-- <c:forEach var="mwhIn" items="${mwhIn}"> --%>
			<c:if test="${mwhIn != null}">
			<tr>
			<td><input name="LOT_NO" type="text" size=10 value="${mwhIn.LOT_NO}" readonly="readonly"/></td>
			<td><input name="PART_NO" type="text" size=10 value="${mwhIn.PART_NO}" readonly="readonly"/></td>
			<c:forEach var="name" items="${partName}">
			<c:if test="${name.PART_NO == mwhIn.PART_NO}">
			<td><input name="PART_NAME" type="text" size=20 value="${name.PART_NAME}" readonly="readonly"/></td>
			</c:if>
			</c:forEach>
			<td><input name="QUAN" type="number" size=10 value="${mwhIn.QUAN}" readonly="readonly"/>EA</td>
			<td><input name="FAIL" type="number" min="0" size=10 placeholder="불량품 수량"/>EA</td>
			<td><select name="IO" size=1>
				<option value="O" selected="selected">입고</option>
				<!-- <option value="O">입고</option> -->
				<!-- <option value="X">출고</option> -->
			</td>
			<td>
				<button type="submit" >입력</button>
			</td>
			</tr>
			</c:if>
			<%-- </c:forEach> --%>
		</table>
		<hr>
		</c:if>
		</form>
		</div>
		
		<h2>작업 진행 중인 물품들입니다.</h2>
		<table class="table" border="1">
			<tr>
			<!-- 작업번호	제품모델번호	제품명	수량	불량갯수	입/출고 -->
			<th><a href="MCHWH.do?ORDER=LOT_NO">작업번호</a></th>
			<th><a href="MCHWH.do?ORDER=PART_NO">제품모델번호</a></th>
			<th><a href="MCHWH.do?ORDER=PART_NAME">제품명</a></th>
			<th>수량</th>
			<th>비고</th>
			</tr>
			<c:forEach var="mwh" items="${lotList}">
			<tr>
			<td><input name="LOT_NO" type="text" size=10 value="${mwh.LOT_NO}" readonly="readonly"/></td>
			<td><input name="PART_NO" type="text" size=10 value="${mwh.PART_NO}" readonly="readonly"/></td>
			<c:forEach var="name" items="${partName}">
			<c:if test="${name.PART_NO == mwh.PART_NO}">
			<td><input name="PART_NAME" type="text" size=20 value="${name.PART_NAME}" readonly="readonly"/></td>
			</c:if>
			</c:forEach>
			<td><input name="QUAN" type="number" size=10 value="${mwh.QUAN}" readonly="readonly"/>EA</td>
			<td>
			<button type="button" onclick="location.href='MCHWH.do?LOTNO=${mwh.LOT_NO}'">등록</button>
			</td>
			</tr>
			</c:forEach>
		</table>
		</form>
		<div class="paging">
			<c:forEach begin="1" end="${pageNum}" var="num">
   				<span>
     				<li class="p_li"><a class="p_font" href="mchwh.do?num=${num}">${num}</a></li>
  				</span>
 			</c:forEach>
		</div>
	</div>
</body>
</html>