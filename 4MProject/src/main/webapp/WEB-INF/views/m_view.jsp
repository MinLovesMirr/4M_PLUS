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
function nullcheckers(){
	if(m_search.M_NO.value==""){
	   	 alert("검색어를 입력해주세요!");
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
		<h1>자재창고 조회</h1>
		
		<div class="first">
		<button type="button" onclick="location.href='/transferToM_input.do'">새자재추가</button>
		</div>
		<form name="m_search" method="get" action="${contextPath}/m_view.do" onsubmit="return nullcheckers()">
		<input type="text" name="M_NO" placeholder="M_NO를 적어주세요"/>
		<input type="submit" value="검색" />
		</form>
		<table class ="table" border="1">
		<tr>
			<th class="i"></th>
			<th class="M_NO"><a href="m_view.do?ORDER=M_NO">자재번호</a></th>
			<th class="MNAME"><a href="m_view.do?ORDER=M_NAME">자재명</a></th>
			<th>수량</th>
			<th>추가수량</th>
			<th>등록</th>
		</tr>
			<c:forEach var="row" items="${list}">
			<c:set var="i" value="${i+1}"/>
		<form name="m_update" method="get" action="${contextPath}/m_update.do?M_NO=${row.M_NO}" onsubmit="return nullcheckeri(PLUS_QUAN)">
				<tr>
					<td>${i}</td>
					<td><input type="text" name="M_NO" value="${row.M_NO}" style="background-color:transparent;border:0" readonly/></td>
					<td>${row.M_NAME}</td>
					<td class="QUAN">${row.M_QUAN}EA</td>
					<td><input type="number" name="PLUS_QUAN" style="background-color:transparent;border:0;width:50px;" min="0" placeholder="0"/>EA</td>
					<td><div class="button"><button type="submit">추가</button></div></td>
				</tr>
			</form>
			</c:forEach>
		</table>
		</div>
		<div class="paging">
			<c:forEach begin="1" end="${pageNum}" var="num">
   				<span>
     				<li><a href="m_view.do?num=${num}">${num}</a></li>
  				</span>
 			</c:forEach>
		</div>
</body>
</html>