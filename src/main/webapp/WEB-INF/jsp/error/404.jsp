<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>404 Page Not Found</title>
</head>
<body>
페이지가 존재하지 않습니다. 
<!-- 
Failed URL: ${url} 
Exception: ${exception.message} 
	<c:forEach items="${exception.stackTrace}" var="ste"> 
		${ste} 
	</c:forEach> 
-->
</body>
</html>