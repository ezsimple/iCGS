<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>iCGS</title>
</head>
<body>
	ion Chatting Gateway Service
	<hr>
	<div>
		<a href="javascript:do1();">채팅1</a>
	</div>
	<div>
		<a href="javascript:do2();">채팅2</a>
	</div>
	<div>
		<a href="/chat/with.do">채팅(접속불가)</a>
	</div>
</body>
</html>

<script language="javascript"> 

   function do1(){ $.post("/chat/with.do", { username: "홍두깨" } ); }
   function do2(){ $.post("/chat/with.do", { username: "홍당무" } ); }

</script>