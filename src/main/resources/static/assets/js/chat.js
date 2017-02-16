var stompClient = null;
var id = "userid";

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#conversations").html("");
}

function connect() {
    var socket = new SockJS('/broker-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        // setConnected(true);
        $("#conversation").show();
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).req,JSON.parse(greeting.body).res);
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

function sendName() {
	let v = $("#name").val();
	console.log('sendName' + v);
    stompClient.send("/chat/hello/"+id, {}, JSON.stringify({'name': $("#name").val()}));
}

function toBottom() {
	let v = $('#conversations-body').get(0).scrollHeight;
	$('#conversations-body').animate({scrollTop: v}, 300);
}

function cleanInput() {
	$('#name').val('');
}

function showGreeting(req, res) {
	$("#conversations").attr('display','inline');
	$("#conversations")
		.append("<li class='left clearfix'>")
		.append("<span class='chat-img pull-left'>")
		.append("<img src='http://placehold.it/50/55C1E7/fff&amp;text=YOU' alt='User Avatar' class='img-circle'>")
		.append("</span>")
		.append("<div class='chat-body clearfix'>")
		.append("<div class='header'>")
		.append("<small class='pull-right text-muted'><span class='glyphicon glyphicon-time'></span>14 mins ago</small>")
		.append("<strong class='primary-font'>Question</strong>") 
		.append("</div>")
		.append("<p>"+req+"</p>")
		.append("</div>")
		.append("</li>");

	$("#conversations")
		.append("<li class='right clearfix'>")
		.append("<span class='chat-img pull-right'>")
		.append("<img src='http://placehold.it/50/FA6F57/fff&amp;text=BOT' alt='User Avatar' class='img-circle'>")
		.append("</span>")
		.append("<div class='chat-body clearfix'>")
		.append("<div class='header'>")
		.append("<small class=' text-muted'><span class='glyphicon glyphicon-time'></span>15 mins ago</small>")
		.append("<strong class='pull-right primary-font'>Answer</strong>")
		.append("</div>")
		.append("<p>"+res+"</p>")
		.append("</div>")
		.append("</li>");
	$("#conversations").attr('display','inline-block');

	toBottom();
	clearInput();
}


$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});
$(document).ready(function() { 
	connect();
});