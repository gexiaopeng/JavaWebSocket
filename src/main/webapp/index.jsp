<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">  
    <title>Java API for WebSocket (JSR-356)</title>
    <script type="text/javascript" src="js/jquery-1.12.3.min.js"></script>  
    <script type="text/javascript" src="js/sockjs/sockjs.js"></script> 
</head>
<body>
    WebSocket Sockjs demo<br/><input id="text" type="text"/>
    <button onclick="send()" id="echo" disabled="disabled">发送消息</button>
    <hr/>
    <button onclick="closeWebSocket()"  id="disconnect" disabled="disabled">关闭WebSocket连接</button>
    <button onclick="connect()"  id="connect" disabled="disabled">开启WebSocket连接</button>
    <hr/>
    <div id="message"></div>
    <table id="tb" class="altrowstable">
		<th align="center"  colspan="9">实时信息监控</th>
	</table>
</body>

<script type="text/javascript">
    var websocket = null;
    $(function(){
     	setMessageInnerHTML("WebSocket:"+('WebSocket' in window));
     	connectWebSocket();
    	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    	window.onbeforeunload = function () {
        	closeWebSocket();
    	}
    });
    function connectWebSocket(){
    	//判断当前浏览器是否支持WebSocket
    	if ('WebSocket' in window) {  
        	//Websocket的连接  
       		 websocket = new WebSocket("ws://localhost:8080/ws/websocket/socketServer.ws");//WebSocket对应的地址  
    	}else {  
       	 	//SockJS的连接  
        	websocket = new SockJS("http://localhost:8080/ws/sockjs/socketServer.ws");    //SockJS对应的地址  
   		} 
    	//连接发生错误的回调方法
   		websocket.onerror = function () {
        	setMessageInnerHTML("WebSocket连接发生错误");
   	 	};
   		//连接成功建立的回调方法
   		websocket.onopen = function () {
        	setConnected(true);
        	setMessageInnerHTML("WebSocket连接成功");
    	}
  		 //接收到消息的回调方法
    	websocket.onmessage = function (event) {
        	setMessageInnerHTML(event.data);
    	}
   		//连接关闭的回调方法
    	websocket.onclose = function () {
        	setConnected(false);
        	setMessageInnerHTML("WebSocket连接关闭");
    	}
  	}
    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
    	var msg=innerHTML.split(" - ")
		
		var table=document.getElementById("tb");

		 var row;
		 row=table.insertRow(1);
		for(var i=0;i<msg.length;i++){
			 var cell = row.insertCell(i);
		 	 cell.appendChild(document.createTextNode(msg[i]));
		}
		if(table.rows.length>50){
			table.deleteRow(table.rows.length-1);
		}
      //  document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //关闭WebSocket连接
  function closeWebSocket() {
        if(websocket!=null){
           websocket.close();
           websocket=null;
        }
    }
  function connect(){
      websocket = null;
      connectWebSocket();
  }
    //发送消息
  function send() {
        var message = document.getElementById('text').value;
        websocket.send(message);
   }
  function setConnected(connected) {  
            document.getElementById('connect').disabled = connected;  
            document.getElementById('disconnect').disabled = !connected;  
            document.getElementById('echo').disabled = !connected;  
  } 
</script>
</html>