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
    <script src="/assets/js/oper.js?_=${_ts }"></script>
</head>
<body>
	<div class="row">
		<div class="col-md-4 col-lg-4">
			<div class="alert alert-block alert-warning">
				<h4 class="alert-heading"><b>i-on Chatting Gateway Service</b></h4>
			</div>
		</div>
	</div>
	<div class="input-group">
		<div class="form-group has-success">
			<form action="/chat/with.do" class="form-inline" method="post" target="_blank">
				<span class="input-group-btn">
					<input name="last_id" type="hidden" value="" />
					<input name="username" type="text" class="form-control input-sm" placeholder="이름을 입력하세요" autocomplete="off" />
					<button class="btn btn-primary btn-sm" id="sendQ" >문의하기</button>
				</span>
			</form>
		</div>
	</div>
	<div class="input-group">
		<div class="form-group has-error">
			<form action="/chat/advice.do" class="form-inline" method="post" target="_new">
				<span class="input-group-btn">
					<input name="last_id" type="hidden" value="" />
					<input name="who" type="hidden" value="운영자"/>
					<input name="username" type="text" class="form-control input-sm" placeholder="고객명을 입력하세요" autocomplete="off" />
					<button class="btn btn-danger btn-sm" id="sendA" >응대하기</button>
				</span>
			</form>
		</div>
	</div>
	<div>
		[forbidden]
		<a href="/chat/with.do">채팅(GET 방식)</a>
	</div>
	<div class="row">
		<div class="col-md-4 col-lg-4">
			<div class="alert-danger"><h6>[대기실]</h6></div>
			<div id="waiting"></div>
		</div>
	</div>
</body>
</html>
<script>
$(document).ready(function() { 
	$.get("/queue/waiting",function(o) {
		drawList(o);
	})
	connect();
});
</script>