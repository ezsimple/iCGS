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
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
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

	<p></p>
	<button class="btn btn-success btn-sm" data-toggle="collapse" data-target="#demo">구현기능</button>
	<div id="demo" class="collapse">
		<div class="col-md-4 col-lg-4">
			<ol>
				<li>채팅 채널 접속 (사용자, 오퍼레이터)</li>
				<li>채팅 메세지 발행 (사용자)</li>
				<li>채팅 메세지 구독 (오퍼레이터)</li>
				<li>심심이 연동 (질의, 응답 파싱)</li>
				<li>서버 사이드 시간 관리</li>
				<li>메세지별 UUID 생성</li>
				<li>대화 내용 저장</li>
				<li>사용자별 이전 대화내역 조회</li>
				<li>하이브리드 채팅 기능 (사용자+챗봇+오퍼레이터)</li>
				<li>자동 재접속 기능</li>
				<li>last_id 기능 - 재접속 이후 메세지만 가져오기</li>
				<li>채팅 세션 관리 (연결, 해제)</li>
				<li>HEARTBEAT 체크 (10초 주기)</li>
				<li>HEARTBEAT을 통한 비정상 접속 해제 감지</li>
				<li>인사말 기능 추가 (사용자명 출력)</li>
				<li>와이즈넛 연동 (질의, 응답 파싱, 세션ID 관리 별도 필요)
					<ul>
						<li>원하는 질문이 아닐 경우, 응답 시간이 매우 오래 걸립니다.</li>
						<li>다른 질문들에도 언제나 똑같은 답변만을 반복 합니다.</li>
					</ul>
				</li>
			</ol>
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