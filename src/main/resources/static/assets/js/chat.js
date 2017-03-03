var stompClient = null;
var username = null;
var who = null;
var destination = null;

function setUsername(who) { username = who; }
function setWho(_who) { who = _who; }
function setDestination(dest) { destination = dest; }
function getAnswerName(who) {
	switch(who) {
	case 'bot': return "Simsimi";
	case 'oper' : return "Operator";
	}
	return "Unknown";
}

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
    // stompClient.send("/chat"+"/"+destination+"/"+user+"/list/"+page,{},"");
}


function connect() {
    var socket = new SockJS('/endpoint');
    stompClient = Stomp.over(socket);
    stompClient.heartbeat.outgoing = 3000; // client will send heartbeat every ms
    stompClient.heartbeat.incomming = 0 // client does not want to receive heartbeats
	var headers = {'username': who };
    stompClient.connect(headers, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/'+username, function (callback) {
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

function setLastId(id) {
	$('#last_id').val(id);
}

function showGreeting(backMessage) {
	let id = backMessage.id;
	let who = backMessage.who;
	let text = backMessage.text;
	let createDate = backMessage.createDate;

	if (username == who)
		addUserMessage(who, text, createDate);
	else
		addResponseMessage(who, text, createDate);
	
	setLastId(id);
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
    	if(username == who) {
			addUserMessage(who, text, createDate);
    		return true;
    	}
    	addResponseMessage(who, text, createDate);
    });
    timeout("toBottom()",1000);
}

// $(document).ready(function() { 
//	connect();
//	$.get("/"+destination+"/"+username+"/list/0",function(messages) {
//		refreshMessages(messages, setTimeout);
//	})
// });