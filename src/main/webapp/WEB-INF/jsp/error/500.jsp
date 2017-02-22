<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>500 Internal Server Error</title>
</head>
<body>
서버가 아픈가 봅니다. 잠시만 기다려 주세요.
<!-- 
Failed URL: ${url} 
Exception: ${exception.message} 
	<c:forEach items="${exception.stackTrace}" var="ste"> 
		${ste} 
	</c:forEach> 
-->
</body>
</html>