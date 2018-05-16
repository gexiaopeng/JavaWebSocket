/**  
 * @Title: WebSocketHandshakeInterceptor.java
 * @Package com.uptop.websocket
 * @Description: TODO
 * @author gexp
 * @dateTime 2018年5月16日 下午3:49:13
 */
package com.uptop.websocket;

import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
import org.springframework.http.server.ServerHttpRequest;  
import org.springframework.http.server.ServerHttpResponse;  
import org.springframework.http.server.ServletServerHttpRequest;  
import org.springframework.web.socket.WebSocketHandler;  
import org.springframework.web.socket.server.HandshakeInterceptor;  
  
import javax.servlet.http.HttpSession;  
import java.util.Map;  
  
/** 
 * WebSocket握手拦截器 
 */  
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {  
    private final static Logger LOGGER = LoggerFactory.getLogger(WebSocketHandshakeInterceptor.class);  
  
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {  
    	LOGGER.info("Before Handshake");  
    	if (request instanceof ServletServerHttpRequest) {  
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;  
            HttpSession session = servletRequest.getServletRequest().getSession(false);  
            if (session != null) {  
                //使用userName区分WebSocketHandler，以便定向发送消息  
            	//LOGGER.info("Before Handshake sessionid:{},sessioncode:{}",session.getId(),session.hashCode());
                String userName = (String) session.getAttribute("SESSION_USERNAME");  
                if (userName == null) {  
                    userName = "system-" + session.hashCode();  
                }  
                attributes.put("WEBSOCKET_USERNAME", userName);  
            }  
        }  
        return true;  
    }  
  
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {  
    	LOGGER.info("After Handshake");  
    }  
}  