<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="_ts" value='<%= System.currentTimeMillis() %>' />
<c:set var="who" value='<%= request.getParameter("username") %>' />
<!DOCTYPE html>
<html>
<head>
    <title>user</title>
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
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <span class="glyphicon glyphicon-comment"></span> 
                    채팅서비스 (${who })
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
                        	<input id="text" type="text" class="form-control input-sm" placeholder="" autocomplete="off" />
                            	<button class="btn btn-warning btn-sm" id="send" >보내기</button>
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
$(document).ready(function() { 
    var who = '<c:out value="${who }" />'; 
    var destination = "hello";

	setWho(who);
	setUsername(who);

	setDestination(destination);

	$.get("/hello/"+who+"/list/0",function(messages) {
		refreshMessages(messages, setTimeout);
	})
	connect();
});
</script>