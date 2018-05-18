package com.uptop.websocket.javax;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能说明：用来存储业务定义的sessionId和连接的对应关系
 * 利用业务逻辑中组装的sessionId获取有效连接后进行后续操作
 * 作者：葛晓鹏(2018-5-15 13:32)
 */
public class SessionUtils {

	public static Map<String, Session> clients = new ConcurrentHashMap<String, Session>();

	public static void put(int hashCode, Session session){
		clients.put(getKey(hashCode), session);
	}

	public static Session get(int hashCode){
		return clients.get(hashCode+"");
	}
	public static void remove(int hashCode){
		clients.remove(getKey(hashCode));
	}
	

	/**
	 * 判断是否有连接
	 * @param relationId
	 * @param userCode
	 * @return
	 */
	public static boolean hasConnection(int hashCode) {
		return clients.containsKey(getKey(hashCode));
	}

	/**
	 * 组装唯一识别的key
	 * @param relationId
	 * @param userCode
	 * @return
	 */
	public static String getKey(int hashCode) {
		return String.valueOf(hashCode);
	}
	public static List<Session> getSessionList(){
		List<Session> list=null;
		if(clients!=null){
			list=new ArrayList<Session>(clients.values());
		}
		return list;
	}
	

}