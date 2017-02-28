var retryCnt = 0;

function setConnected(connected) { 
	if(connected) { 
        console.log('Connected');
		retryCnt = 0; 
		return;
	}
	retryCnt++;
    console.log('Disconnected [retry : '+retryCnt+']');
}

function connect() {
	var stompClient = null;
    var socket = new SockJS('/endpoint');
    stompClient = Stomp.over(socket);
    stompClient.heartbeat.outgoing  = 5000; // client will send heartbeat every ms
    stompClient.heartbeat.incomming = 5000; // client will receive heartbeat every ms
	var headers = {};
    stompClient.connect(headers, function (frame) {
        setConnected(true);
        stompClient.subscribe('/queue/waiting', function (callback) {
            drawEach(JSON.parse(callback.body));
        });
    });
    socket.onclose = function() {
        setConnected(false);
    	console.log("retry : "+retryCnt);
    	setTimeout("connect()",5000); // 재시도
    }
}

function formChat(o) {
	return "<form action='/chat/advice.do' class='form-inline' method='post' target='_new'>"
			+"	<input name='who' type='hidden' value='운영자'/>"
			+"  <input name='username' type='hidden' value='"+o.username+"' />"
			+"  <button class='btn btn-danger btn-sm'>"+o.username+"</button>"
			+"</form>";
}

function add(o) {
	let username = o.username;
	let sessionId = o.sessionId;
	$("#waiting").prepend("<div id='"+sessionId+"'>"+formChat(o)+"</div>");
}

function del(o) {
	let sessionId = o.sessionId;
	$('#'+sessionId).remove();
}

function drawEach(o) {
	console.log(o);
	let operator = o.operator;
	let username = o.username;
	if (username == '운영자')
		return;
	if( "+" == operator) add(o);
	else if ("-" == operator) del(o);
}

function drawInit(messages) {
    $("#waiting").html("");
    $.each(messages, function(i, o) {
    	drawEach(o);
    });
}