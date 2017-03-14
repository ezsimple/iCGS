var retryCnt = 0;
var stompClient = null;
var username = null;
var who = null;
var destination = null;

function setUsername(who) { username = who; }
function setWho(_who) { who = _who; }
function setDestination(dest) { destination = dest; }
function getAnswerName(who) {
	switch(who) {
	case 'bot': return "Bot&nbsp;&nbsp;&nbsp;&nbsp;";
	case 'oper' : return "Operator";
	}
	return "Unknown";
}

function setConnected(connected) {
    if (connected) {
        $("#conversation").show();
		retryCnt = 0; 
        return;
    }
	$("#conversation").hide();
	retryCnt++;
    console.log('Disconnected [retry : '+retryCnt+']');
}

function connect() {
    var socket = new SockJS('/endpoint');
    stompClient = Stomp.over(socket);
    // stompClient.heartbeat.outgoing = 10000; // client will send heartbeat every ms
    // stompClient.heartbeat.incomming = 0 // client does not want to receive heartbeats
	var headers = {'username': who };
    stompClient.connect(headers, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/'+username, function (callback) {
            drawEach(JSON.parse(callback.body));
            setTimeout('toBottom()',1000);
        });
    });
    socket.onclose = function() {
        setConnected(false);
    	console.log("retry : "+retryCnt);
    	setTimeout("reconnect()",5000); // 재시도
    }
}

function reconnect() {
	let last_id = get_last_id();
	console.log("last_id : "+last_id);
	$.post("/hello/"+username+"/list/0",{'last_id':last_id},function(o) {
		drawList(o, setTimeout);
	});
	connect();
}

function sendMesg() {
	let v = $("#text").val();
    stompClient.send("/chat"+"/"+destination+"/"+username, {}, JSON.stringify({'text': v}));
}

function toBottom() {
	let v = $('#conversations-body').get(0).scrollHeight + 200;
	$('#conversations-body').animate({scrollTop: v}, 200);
	$('#text').val('');
}

function addUserMessage(who, text, createDate) {
	$("#conversations")
		.append("<li class='left clearfix'>")
		.append("<span class='chat-img pull-left'>")
		.append("<img src='http://placehold.it/50/55C1E7/fff&amp;text=ASK' alt='User Avatar' class='img-circle'>")
		.append("</span>")
		.append("<div class='chat-body clearfix'>")
		.append("<div class='header'>")
		.append("<small class='pull-right text-muted'><span class='glyphicon glyphicon-time'></span>"+createDate+"</small>")
		.append("<strong class='primary-font'>"+who+"</strong>") 
		.append("</div>")
		.append("<p>"+text+"</p>")
		.append("</div>")
		.append("</li>");
}

function addResponseMessage(who, text, createDate) {
	$("#conversations")
		.append("<li class='right clearfix'>")
		.append("<span class='chat-img pull-right'>")
		.append("<img src='http://placehold.it/50/2E8B57/fff&amp;text=ANS' alt='User Avatar' class='img-circle pull-right'>")
		.append("</span>")
		.append("<div class='chat-body clearfix'>")
		.append("<div class='header'>")
		.append("<small class=' text-muted'><span class='glyphicon glyphicon-time'></span>"+createDate+"</small>")
		.append("<strong class='pull-right primary-font'>"+getAnswerName(who)+"</strong>")
		.append("</div>")
		.append("<p>"+text+"</p>")
		.append("</div>")
		.append("</li>");
}

function set_last_id(id) {
	$('#last_id').val(id);
	// console.log(get_last_id());
}

function get_last_id() {
	return $('#last_id').val();
}

function drawEach(message) {
	let id = message.id;
	let who = message.who;
	let text = message.text;
	let createDate = message.createDate;

	if (username == who)
		addUserMessage(who, text, createDate);
	else
		addResponseMessage(who, text, createDate);
	
	set_last_id(id);
}

$(function () {
    $("#connect").click(function() { connect(); });
    $("#disconnect").click(function() { disconnect(); });
    $("form").on('submit', function (e) { e.preventDefault(); });
    $("#send").click(function() { sendMesg(); });
});

function drawList(messages, timeout) {
    $.each(messages, function(i, m) {
    	drawEach(m);
    });
    timeout("toBottom()",1000);
}