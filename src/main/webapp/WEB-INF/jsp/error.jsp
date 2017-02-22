<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error Page</title>
</head>
<body>
<h1>Error Page</h1>
<p>Application has encountered an error.</p>
<!-- 
Failed URL: ${url} 
Exception: ${exception.message} 
	<c:forEach items="${exception.stackTrace}" var="ste"> 
		${ste} 
	</c:forEach> 
-->
</body>
</html>