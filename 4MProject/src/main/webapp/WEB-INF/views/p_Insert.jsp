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
 <link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<meta charset="UTF-8">
<title>생산계획 등록창</title>
<script>
function nullchecker(){
	if(p_insert.LOT_NO.value==""){
		alert("작업번호를 입력해주세요!");
		return false;
	}
    if(p_insert.PART_NO.value==""){
		alert("제품번호를 입력해주세요!");
		return false;
	}
    if(p_insert.QUAN.value=="" || p_insert.QUAN.value < 0){
		alert("생산수량을 제대로 입력해주세요!");
		return false;
	}
    if(p_insert.S_DATE.value==""){
		alert("날짜를 선택해주세요!");
		return false;
	}
	return true;
}
jQuery(function($){

    $.datepicker.regional['ko'] = {closeText: '닫기',prevText: '이전달',nextText: '다음달',currentText: '오늘',monthNames: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)','7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],dayNames: ['일','월','화','수','목','금','토'],dayNamesShort: ['일','월','화','수','목','금','토'],dayNamesMin: ['일','월','화','수','목','금','토'],weekHeader: 'Wk',dateFormat: 'yy-mm-dd',firstDay: 0,isRTL: false,showMonthAfterYear: true,yearSuffix: ''};
    $.datepicker.setDefaults($.datepicker.regional['ko']);
    $('#S_DATE').datepicker({changeMonth: true,changeYear:true,showButtonPanel:true,yearRange: 'c-0:c+99',constrainInput: true,maxDate:'+1y',beforeShowDay: noBefore});

});
function noWeekendsOrHolidays(date) {
    return noWeekend[0] ? [true] : noWeekend;
}

function noBefore(date){
    var noWeekend = jQuery.datepicker.noWeekends(date);
    if (date < new Date() || !noWeekend[0])
        return [false];
    return [true];
}


</script>
</head>
<body>
	<jsp:include page="T_include.jsp" flush="true"/>
   <div class="sidebar"><jsp:include page="l_include.jsp"/></div>
   <div class="bodyall">
   	<form name="p_insert" method="get" action="${contextPath}/p_insert.do" onsubmit="return nullchecker()" autocomplete="off">
	<h1>생산계획 등록</h1>
	<table class="table" border="1" width="300px">
	<tr>
		<th>작업번호</th>
		<th>모델번호</th>
		<th>수량</th>
		<th>작업날짜</th>
	</tr>
				<tr>
			<c:if test="${list ne null}">
				<c:forEach var="row" items="${list}">
					<td><input type="text" name="LOT_NO" id="LOT_NO" value="${row.request_code}" style="background-color:transparent;border:0" readonly></td>
					<td><input type="text" name="PART_NO" id="PART_NO" value="${row.product_code}" style="background-color:transparent;border:0" readonly></td>
					<td><input type="number" name="QUAN" id="QUAN" value="${row.total}" style="background-color:transparent;border:0" readonly></td>
					<td><input type="text" id="S_DATE" name="S_DATE" ></td>
				</c:forEach>
			</c:if>
			<c:if test="${list eq null}">
					<td><input type="text" name="LOT_NO" id="LOT_NO"></td>
					<td><input type="text" name="PART_NO" id="PART_NO"></td>
					<td><input type="number" name="QUAN" id="QUAN" min="0"></td>
					<td><input type="text" name="S_DATE" id="S_DATE" ></td>
			</c:if>
		</tr>
	</table>
	<div class="bottom_buttons">
	<button type="submit">등록하기</button>
	<button type="button" onclick="location.href='p_view.do'">생산계획보기</button>
	</div>
	</form>
	</div>
</body>
</html>