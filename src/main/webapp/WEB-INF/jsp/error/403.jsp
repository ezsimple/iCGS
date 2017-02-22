<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>403 Access Denied</title>
</head>
<body>
부적절한 접근 입니다.
<!-- 
Failed URL: ${url} 
Exception: ${exception.message} 
	<c:forEach items="${exception.stackTrace}" var="ste"> 
		${ste} 
	</c:forEach> 
-->
</body>
</html>