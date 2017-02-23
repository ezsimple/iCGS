<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error Page</title>
</head>
<body>
<h1>오류 페이지</h1>
<p>죄송합니다. 곧 조치하도록 하겠습니다.</p>
<!-- 
Failed URL: ${url} 
Exception: ${exception.message} 
	<c:forEach items="${exception.stackTrace}" var="ste"> 
		${ste} 
	</c:forEach> 
-->
</body>
</html>