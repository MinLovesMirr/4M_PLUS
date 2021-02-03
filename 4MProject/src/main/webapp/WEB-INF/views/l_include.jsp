<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    width: 200px;
    height:800px;
    background-color: #f1f1f1;
}
li a {
    display: block;
    color: #000;
    padding: 8px 16px;
    text-decoration: none;
}
li a:hover:not(li) {
    background-color: #555;
    color: white;
}</style>
</head>
<body>
<ul>
      <li><a href="/orderListPage.do">주문리스트조회</a></li>
      <li><a href="/p_view.do">생산계획조회</a></li>
      <li><a href="/MCHWHSEA.do">제품창고조회</a></li>
      <li><a href="/bom.do">BOM조회</a></li>
      <li><a href="/m_view.do">자재창고조회</a></li>
    </ul>
</body>
</html>