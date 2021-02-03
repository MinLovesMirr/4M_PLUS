<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<html>
<head>
<link rel="stylesheet" href="resources/css/ERPcss.css">
<title>4M factory 로그인</title>
<style>
   .home{display: flex;
  justify-content: center;
  flex-direction:column;
  align-items: center;
  min-height: 50vh;
  font-weight:900;
   }
	.bottom_buttons{margin-top : 10px;
	display: flex;
	margin-left:90px;
  }
</style>
</head>
<body>
   <!-- ne = not equals eq = equals -->
   <c:if test="${msg ne null }">
      <Script>
         alert('${msg}');
      </Script>
   </c:if>
   <div class="home">
   <h2>WELCOME TO 4M FACTORY</h2>
   <form method="post" action="/t_include.do">
      ID&nbsp;&nbsp; : <input type="text" name="id"><br>
      PW  : <input type="password" name="pw">
       <input class="bottom_buttons" type="submit" value="LOGIN">
      </form>
      </div>
</body>
</html>