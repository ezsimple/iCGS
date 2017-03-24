<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="_ts" value='<%= System.currentTimeMillis() %>' />
<c:set var="username" value='<%= request.getParameter("username") %>' />
<c:set var="who" value='<%= request.getParameter("who") %>' />
<!DOCTYPE html>
<html>
<head>
    <title>admin</title>
    <link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <link href="/assets/css/chat.css?_=${_ts }" rel="stylesheet">
    <script src="/assets/js/chat.js?_=${_ts }"></script>
</head>
<body>
<div class="container">
<form>
    <div class="row">
        <div class="col-md-5">
            <div class="panel panel-danger">
                <div class="panel-heading">
                    <span class="glyphicon glyphicon-comment"></span> 
                    To ${username }
                </div>
                <div class="panel-body" id="conversations-body">
                    <ul class="chat" id="conversations">
                    </ul>
                </div>
                <div class="panel-footer">
                    <div class="input-group">
                    	<input id="last_id" type="hidden" />
                        <input id="text" type="text" class="form-control input-sm" placeholder="yeogie mesijileul iblyeoghasibsio ..." autocomplete="off" autofocus/>
                        <span class="input-group-btn">
                        <button class="btn btn-warning btn-sm" id="btn-chat">보내기</button>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
</div>
</body>
</html>
<script>
<%/*
// --------------------+----------------------
// 사용자              | 운영자
// --------------------+----------------------
// who = 무식이        | who = 운영자
// username = 무식이   | username = 무식이 
// destination = hello | destination = advice 
// --------------------+----------------------
*/%>
$(document).ready(function() { 
    let who = '<c:out value="${who }" />'; 			  // 오퍼레이터의 이름을 넘겨줘야 한다.
    let username = '<c:out value="${username }" />';  // 대화상대의 값을 넘겨줘야 한다.
    let destination = "advice";

	setUsername(username);
	setWho(who);
	setDestination(destination);

	$.post("/hello/"+username+"/list/0",{last_id:""},function(messages) {
		drawList(messages, setTimeout);
	})
	connect();
});
</script>