<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">  
    <title>Java API for webSocket (JSR-356)</title>
    <script type="text/javascript" src="js/jquery-1.12.3.min.js"></script>  
    <script type="text/javascript" src="js/sockjs/sockjs.js"></script> 
</head>
<body>
    webSocket Sockjs demo<br/><input id="text" type="text"/>
    <button onclick="send()" id="echo" disabled="disabled">发送消息</button>
    <hr/>
    <button onclick="closewebSocket()"  id="disconnect" disabled="disabled">关闭webSocket连接</button>
    <button onclick="connect()"  id="connect" disabled="disabled">开启webSocket连接</button>
    <hr/>
    <div id="message"></div>
    <table id="tb" class="altrowstable">
		<th align="center"  colspan="9">实时信息监控</th>
	</table>
</body>

<script type="text/javascript">
    var webSocket = null;
    var tryTime = 0;
    $(function(){
     	setMessageInnerHTML("webSocket:"+('webSocket' in window));
     	connectwebSocket();
    	//监听窗口关闭事件，当窗口关闭时，主动去关闭webSocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    	window.onbeforeunload = function () {
        	closewebSocket();
    	}
    });
    function connectwebSocket(){
    	//判断当前浏览器是否支持webSocket
    	if ('webSocket' in window) {  
        	//webSocket的连接  
       		 webSocket = new WebSocket("ws://localhost:8080/ws/websocket/socketServer.ws");//webSocket对应的地址  
    	}else {  
       	 	//SockJS的连接  
        	webSocket = new SockJS("http://localhost:8080/ws/sockjs/socketServer.ws");    //SockJS对应的地址  
   		} 
    	//连接发生错误的回调方法
   		webSocket.onerror = function () {
        	setMessageInnerHTML("webSocket连接发生错误");
   	 	};
   		//连接成功建立的回调方法
   		webSocket.onopen = function () {
        	setConnected(true);
        	setMessageInnerHTML("webSocket连接成功");
    	}
  		 //接收到消息的回调方法
    	webSocket.onmessage = function (event) {
        	setMessageInnerHTML(event.data);
    	}
   		//连接关闭的回调方法
    	webSocket.onclose = function () {
        	setConnected(false);
        	setMessageInnerHTML("webSocket连接关闭");
        	// 重试10次，每次之间间隔1秒
	 		if (tryTime < 10) {
	 			setTimeout(function () {
	 				webSocket = null;
					tryTime++;
					connectwebSocket();
	 			}, 1000);
	 		} else {
	    		tryTime = 0;
			}
    	}
  	}
   
//发送消息
function webSocketSend (message) {
	    waitForConnection(function () {
	    	webSocket.send(message);
	    }, 1000);
}
    //关闭webSocket连接
  function closewebSocket() {
        tryTime=200;
        if(webSocket!=null){
           webSocket.close();
           webSocket=null;
        }
  }
  function waitForConnection(callback, interval) {
	    if (webSocket!=null && webSocket.readyState === 1) {
	        callback();
	    } else {
	        setTimeout(function () {
	            waitForConnection(callback, interval);
	        }, interval);
	    }
 }
  
  
  function connect(){
      webSocket = null;
      connectwebSocket();
  }
    //发送消息
  function send() {
        var message = document.getElementById('text').value;
        webSocketSend(message);
  }
  function setConnected(connected) {  
            document.getElementById('connect').disabled = connected;  
            document.getElementById('disconnect').disabled = !connected;  
            document.getElementById('echo').disabled = !connected;  
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
</script>
</html>