<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="_ts" value="<%= System.currentTimeMillis() %>" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>iCGS</title>
    <link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
</head>
<body>
	<div class="row">
		<div class="col-md-6 col-lg-6">
			<div class="alert alert-block alert-warning">
				<h4 class="alert-heading">i-on Chatting Gateway Service</h4>
			</div>
		</div>
	</div>
	<div class="input-group">
		<div class="form-group has-success">
			<form action="/chat/with.do" class="form-inline" method="post">
				<span class="input-group-btn">
					<input name="username" type="text" class="form-control input-sm" placeholder="이름을 입력하세요" autocomplete="off" />
					<button class="btn btn-primary btn-sm" id="send" >문의하기</button>
				</span>
			</form>
		</div>
	</div>
	<div>
		<a href="/chat/with.do">채팅(GET 방식)</a>
	</div>
</body>
</html>

<script language="javascript"> 

   function do1(){ $.post("/chat/with.do", { username: "홍두깨" } ); }
   function do2(){ $.post("/chat/with.do", { username: "홍당무" } ); }

</script>