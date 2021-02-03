<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	request.setCharacterEncoding("UTF-8");
	String id = (String)session.getAttribute("sessionId");
%>   

<!DOCTYPE html>
<html>
<head>
<script>
function nullchecker(){
	if(bomist.PART_NO.value==""){
		alert("제품번호를 입력해주세요!");
		return false;
	}
	return true;
}
</script>
<meta charset="UTF-8">
<link rel="stylesheet" href="resources/css/ERPcss.css">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="T_include.jsp" flush="true"/>
   <div class="sidebar"><jsp:include page="l_include.jsp"/></div>
	<div class="bodyall">
	<h1>IMS로부터 주문받은 내역입니다.</h1>
		<form name="p_search" method="get" action="${contextPath}/p_view.do" onsubmit="return nullchecker()">
		<input type="text" name="LOT_NO" placeholder="주문번호를 적어주세요"/>
		<input type="submit" class="btn btn-primary" value="검색" />
		</form>
	<div class="tablediv">
		<table class="table" border="1">
			<tr>
			<th class ="i"></th>
			<th class="LOT"><a href="p_view.do?ORDER=request_code">주문번호</a></th>
			<th class="LOT"><a href="p_view.do?ORDER=product_code">제품번호</a></th>
			<th>성별</a></th>
			<th>단가</a></th>
			<th><a href="p_view.do?ORDER=request_date">업체주문날짜</th>
			<th class="DATE">주문수량</a></th>
			<th></th>
		</tr>
		<tbody>
		<c:forEach var="row" items="${list}">
			<c:set var="i" value="${i+1}"/>
		<tr>
			<td>${i}</td>
			<td>${row.request_code}</td>
			<td>${row.product_code}</td>
			<td>${row.gender}</td>
			<td>${row.unit_price}</td>
			<fmt:formatDate value="${row.request_date}" pattern="yyyy-MM-dd" var="request_date"/>
			<td><c:out value="${request_date}"/></td>
			<td>${row.total}</td>
			<td><div class="button"><button type="button" onclick="location.href='orderDetail.do?request_code=${row.request_code}'">조회</button></div></td>
		</tr>
		</c:forEach>
		</tbody>
		</table>
		</div>
		<div class="paging">
			<c:forEach begin="1" end="${pageNum}" var="num">
   				<span>
     				<li><a href="orderListPage.do?num=${num}">${num}</a></li>
  				</span>
 			</c:forEach>
		</div>
</body>
</html>