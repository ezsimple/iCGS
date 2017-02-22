var stompClient = null;
var id = "userid";

function setConnected(connected) {
    // $("#connect").prop("disabled", connected);
    // $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
        return;
    }
	$("#conversation").hide();
    // $("#conversations").html("");
}

function getAll(page) {
    // stompClient.send("/chat/hello/"+id+"/list/"+page,{},"");
}

function connect() {
    var socket = new SockJS('/broker-websocket');
    stompClient = Stomp.over(socket);
    stompClient.heartbeat.outgoing = 3000; // client will send heartbeat every ms
    stompClient.heartbeat.incomming = 0 // client does not want to receive heartbeats
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/'+id, function (callback) {
            showGreeting(JSON.parse(callback.body));
        });
    });
    socket.onclose = function() {
    	disconnect();
    }
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMesg() {
	let v = $("#text").val();
	// console.log('sendMesg : ' + v);
    stompClient.send("/chat/hello/"+id, {}, JSON.stringify({'text': v}));
}

function toBottom() {
	let v = $('#conversations-body').get(0).scrollHeight + 200;
	$('#conversations-body').animate({scrollTop: v}, 200);
	$('#text').val('');
}

function addUserMessage(text, createDate) {
	$("#conversations")
		.append("<li class='left clearfix'>")
		.append("<span class='chat-img pull-left'>")
		.append("<img src='http://placehold.it/50/55C1E7/fff&amp;text=YOU' alt='User Avatar' class='img-circle'>")
		.append("</span>")
		.append("<div class='chat-body clearfix'>")
		.append("<div class='header'>")
		.append("<small class='pull-right text-muted'><span class='glyphicon glyphicon-time'></span>"+createDate+"</small>")
		.append("<strong class='primary-font'>Question</strong>") 
		.append("</div>")
		.append("<p>"+text+"</p>")
		.append("</div>")
		.append("</li>");
}

function addResponseMessage(text, createDate) {
	$("#conversations")
		.append("<li class='right clearfix'>")
		.append("<span class='chat-img pull-right'>")
		.append("<img src='http://placehold.it/50/FA6F57/fff&amp;text=BOT' alt='User Avatar' class='img-circle pull-right'>")
		.append("</span>")
		.append("<div class='chat-body clearfix'>")
		.append("<div class='header'>")
		.append("<small class=' text-muted'><span class='glyphicon glyphicon-time'></span>"+createDate+"</small>")
		.append("<strong class='pull-right primary-font'>Answer</strong>")
		.append("</div>")
		.append("<p>"+text+"</p>")
		.append("</div>")
		.append("</li>");
}

function showGreeting(backMessage) {
	let who = backMessage.who;
	let text = backMessage.text;
	let createDate = backMessage.createDate;

	if (id == who)
		addUserMessage(text, createDate);
	else
		addResponseMessage(text, createDate);

	toBottom();
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMesg(); });
});

function refreshMessages(messages, timeout) {
	let h = 0;
    $("#conversation").html("");
    $.each(messages, function(i, m) {
    	// console.log(m);
    	let who = m.who;
    	let text = m.text;
    	let createDate = m.createDate;
    	if(who == 'bot') {
    		addResponseMessage(text, createDate);
    		return true;
    	}
    	addUserMessage(text, createDate);
    });
    timeout("toBottom()",1000);
}

$(document).ready(function() { 
	connect();
	$.get("/hello/"+id+"/list/0",function(messages) {
		refreshMessages(messages, setTimeout);
	})
});