/**  
 * @Title: MyTestJava.java
 * @Package com.test
 * @Description: TODO
 * @author gexp
 * @dateTime 2018年5月22日 上午11:30:12
 */
package com.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.web.context.ContextLoader;

/**
 * ClassName: MyTestJava 
 * @Description: TODO
 * @author gexp
 * @dateTime 2018年5月22日 上午11:30:12
 */
public class MyTestJava {
	private  static Logger log = LoggerFactory.getLogger(MyTestJava.class);
	/**
	 * @author gexp
	 * @dateTime 2018年5月22日 上午11:30:12
	 * @param args   
	*/
	public static void main(String[] args) {
		Marker m=MarkerFactory.getMarker("myMarker");
		log.info(m,"MyTestJava info:{}","你好hello!");
		log.error("MyTestJava eror:{}","你好hello!");
		log.debug("MyTestJava debug:{}","你好hello!");
	}

}
