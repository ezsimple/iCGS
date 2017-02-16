<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>demo</title>
    <link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/assets/css/main.css" rel="stylesheet">
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <script src="/assets/js/app.js?_=6"></script>
</head>
<body>
<noscript>
	<h2 style="color: #ff0000">
	Seems your browser doesn't support Javascript! 
	Websocket relies on Javascript being enabled. 
	Please enable Javascript and reload this page!
	</h2>
</noscript>
<div id="main-content" class="container">
    <div class="row">
        <div class="col-md-6">
            <form class="form-inline">
                <div class="form-group">
                    <label for="connect">소캣:</label>
                    <button id="connect" class="btn btn-default" type="submit">연결</button>
                    <button id="disconnect" class="btn btn-default" type="submit" disabled="disabled">끊기</button>
                </div>
            </form>
        </div>
        <div class="col-md-6">
            <form class="form-inline">
                <div class="form-group">
                    <label for="name">하고싶은 말 : </label>
                    <input type="text" id="name" class="form-control" placeholder="말말말...">
                </div>
                <button id="send" class="btn btn-default" type="submit">문의</button>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table id="conversation" class="table table-striped">
                <thead>
                <tr>
                    <th>대화내용</th>
                </tr>
                </thead>
                <tbody id="greetings"></tbody>
            </table>
        </div>
    </div>
    </form>
</div>
</body>
</html>