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
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <link href="/assets/css/chat.css?_=${_ts }" rel="stylesheet">
    <script src="/assets/js/chat.js?_=${_ts }"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-5">
            <div class="panel panel-success">
                <div class="panel-heading">
                    <span class="glyphicon glyphicon-comment"></span> 
                    To ${username }
                    <form class="form-inline">
                    	<div class="btn-group pull-right"></div>
                    </form>
                </div>
                <div class="panel-body" id="conversations-body">
                    <ul class="chat" id="conversations">
                    </ul>
                </div>
                <div class="panel-footer">
                    <div class="input-group">
                    	<form class="form-inline">
                        	<span class="input-group-btn">
                        		<input id="last_id" type="hidden" />
								<input id="text" type="text" class="form-control form-control-inline input-sm" placeholder="" autocomplete="off" />
                            	<button class="btn btn-success btn-sm" id="send" >보내기</button>
                        	</span>
                    	</form>
                    </div>
                </div>
            </div>
        </div>
    </div>
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
    var who = '<c:out value="${who }" />'; 			  // 오퍼레이터의 이름을 넘겨줘야 한다.
    var username = '<c:out value="${username }" />';  // 대화상대의 값을 넘겨줘야 한다.
    var destination = "advice";

	setUsername(username);
	setWho(who);
	setDestination(destination);

	$.get("/hello/"+username+"/list/0",function(messages) {
		refreshMessages(messages, setTimeout);
	})
	connect();
});
</script>