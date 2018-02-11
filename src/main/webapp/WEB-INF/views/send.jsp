<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Insert title here</title>
</head>
<body>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.bootcss.com/sockjs-client/1.1.1/sockjs.js"></script>
<script type="text/javascript">
    var websocket = null;
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/websocket/socketServer");
    }
    else if ('MozWebSocket' in window) {
        websocket = new MozWebSocket("ws://localhost:8080/websocket/socketServer");
    }
    else {
        websocket = new SockJS("http://localhost:8080/sockjs/socketServer");
    }
    websocket.onopen = onOpen;
    websocket.onmessage = onMessage;
    websocket.onerror = onError;
    websocket.onclose = onClose;

    function onOpen(openEvt) {
        //alert(openEvt.Data);
        setMessageInnerHTML("open");
    }

    function onMessage(event) {
        setMessageInnerHTML(event.data);
    }

    function onError() {
        setMessageInnerHTML("error");
    }

    function onClose() {
        setMessageInnerHTML("close");
    }

    function doSend() {
        if (websocket.readyState == websocket.OPEN) {
            var msg = document.getElementById("inputMsg").value;
            websocket.send(msg);//调用后台handleTextMessage方法
        } else {
            alert("连接失败!");
        }
    }

    // 将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    window.close = function () {
        websocket.onclose();
    }

</script>
请输入：<input type="text" id="inputMsg" name="inputMsg"/>
<button onclick="doSend();">发送</button>
<form action="/websocket/send">
    <input type="submit" value="测试"/>
</form>
<div id="message"></div>
</body>
</html>