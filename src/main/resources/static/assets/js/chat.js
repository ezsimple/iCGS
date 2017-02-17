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
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/'+id, function (callback) {
            showGreeting(JSON.parse(callback.body));
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMesg() {
	let v = $("#mesg").val();
	console.log('sendMesg' + v);
    stompClient.send("/chat/hello/"+id, {}, JSON.stringify({'mesg': $("#mesg").val()}));
}

function toBottom() {
	let v = $('#conversations-body').get(0).scrollHeight;
	$('#conversations-body').animate({scrollTop: v}, 300);
	$('#mesg').val('');
}

function addUserMessage(req, reqTime) {
	$("#conversations")
		.append("<li class='left clearfix'>")
		.append("<span class='chat-img pull-left'>")
		.append("<img src='http://placehold.it/50/55C1E7/fff&amp;text=YOU' alt='User Avatar' class='img-circle'>")
		.append("</span>")
		.append("<div class='chat-body clearfix'>")
		.append("<div class='header'>")
		.append("<small class='pull-right text-muted'><span class='glyphicon glyphicon-time'></span>"+reqTime+"</small>")
		.append("<strong class='primary-font'>Question</strong>") 
		.append("</div>")
		.append("<p>"+req+"</p>")
		.append("</div>")
		.append("</li>");
}

function addBotMessage(res, resTime) {
	$("#conversations")
		.append("<li class='right clearfix'>")
		.append("<span class='chat-img pull-right'>")
		.append("<img src='http://placehold.it/50/FA6F57/fff&amp;text=BOT' alt='User Avatar' class='img-circle pull-right'>")
		.append("</span>")
		.append("<div class='chat-body clearfix'>")
		.append("<div class='header'>")
		.append("<small class=' text-muted'><span class='glyphicon glyphicon-time'></span>"+resTime+"</small>")
		.append("<strong class='pull-right primary-font'>Answer</strong>")
		.append("</div>")
		.append("<p>"+res+"</p>")
		.append("</div>")
		.append("</li>");
}

function showGreeting(responseMesg) {
	let req = responseMesg.req;
	let reqTime = responseMesg.reqTime;
	let res = responseMesg.res;
	let resTime = responseMesg.resTime;

	addUserMessage(req, reqTime);
	addBotMessage(res, resTime);
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

function refreshMessages(messages) {
    $("#conversation").html("");
    $.each(messages, function(i, m) {
    	let who = m.who;
    	let text = m.message;
    	let cdate = m.date;
    	if(who == 'bot') {
    		addBotMessage(text, cdate);
    		return true;
    	}
    	addUserMessage(text, cdate);
    });
}

$(document).ready(function() { 
	connect();
	$.get("/hello/"+id+"/list/0",function(messages) {
		refreshMessages(messages);
	})
	setTimeout("toBottom()", 1000);
});