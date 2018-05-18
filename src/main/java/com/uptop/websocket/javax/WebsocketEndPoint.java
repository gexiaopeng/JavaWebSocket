package com.uptop.websocket.javax;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * 功能说明：websocket处理类, 使用J2EE7的标准
 * 切忌直接在该连接处理类中加入业务处理代码
 */
//currentPage和pageSize是业务标识参数(至少两级),websocket.ws是连接的路径，可以自行定义
@ServerEndpoint(value ="/javaxwebsocket/websocket.jws/{currentPage}/{pageSize}/{userId}")
public class WebsocketEndPoint {
	private static Logger log = LoggerFactory.getLogger(WebsocketEndPoint.class);
	private int code;
	private String userId;
	public WebsocketEndPoint(){
		code=this.hashCode();
		log.info("===javax WebsocketEndPoint=== code:"+code);
	}

	/**
	 * 打开连接时触发
	 * @param relationId
	 * @param userCode
	 * @param session
	 */
	@OnOpen
	public void onOpen(@PathParam("currentPage") Integer currentPage,@PathParam("pageSize") Integer pageSize,@PathParam("userId") String userId, Session session){
		this.userId=userId;
		log.info("javax Websocket Start Connecting code:"+ SessionUtils.getKey(code)+",currentPage:"+currentPage+",pageSize:"+pageSize+",userId:"+userId+",QueryString:"+session.getQueryString());
		SessionUtils.put(code, session);
		try {
			session.getBasicRemote().sendText(userId + " wellcome(sessionId:"+code+")");
		} catch (IOException e) {
			log.error("onOpen error userId:{}",userId,e);
		}
	}

	/**
	 * 收到客户端消息时触发
	 * @param relationId
	 * @param userCode
	 * @param message
	 * @return
	 */
	@OnMessage
	public String onMessage(@PathParam("currentPage") Integer currentPage,@PathParam("pageSize") Integer pageSize,String message,Session session) {
		log.info("javax Websocket onMessage currentPage:"+currentPage+",pageSize:"+pageSize+",message:"+message+",code:"+code);
		try {
			sendMessageToUsers(userId+":" +message+"("+new Date().toLocaleString()+")");
		} catch (Exception e) {
			log.error("websocket onMessage error message:{}",message,e);
			return "{\"error\":\""+e.getMessage()+"\",\"totalCount\":0}";
		}
		return null;
		//return "text";
	}

	/**
	 * 异常时触发
	 * @param relationId
	 * @param userCode
	 * @param session
	 */
	@OnError
	public void onError(@PathParam("currentPage") Integer currentPage,@PathParam("pageSize") Integer pageSize,Throwable throwable,Session session) {
		log.error("javax Websocket Connection Exception:"+throwable.getMessage()+",key:"+ SessionUtils.getKey(code));
		SessionUtils.remove(code);
	}

	/**
	 * 关闭连接时触发
	 * @param relationId
	 * @param userCode
	 * @param session
	 */
	@OnClose
	public void onClose(@PathParam("currentPage") Integer currentPage,@PathParam("pageSize") Integer pageSize,Session session) {
		log.info("javax Websocket Close Connection code:"+ SessionUtils.getKey(code));
		SessionUtils.remove(code);
	}
	/** 
	 * 给所有在线用户发送消息 
	 * 
	 * @param message 
	 */  
	private void sendMessageToUsers(String message) {  
		List<Session> users= SessionUtils.getSessionList();
		if(users!=null){
			for (Session user : users) {  
				try {  
					if (user.isOpen()) {  
						user.getBasicRemote().sendText(message);
					}  
				} catch (Exception e) {  
					log.error("sendMessageToUsers error,message:{}",message,e);
				}  
			}
		}
	}  
}
