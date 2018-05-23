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


/**
 * ClassName: MyTestJava 
 * @Description: TODO
 * @author gexp
 * @dateTime 2018年5月22日 上午11:30:12
 */
public class MyLogTestJava {
	private  static Logger log = LoggerFactory.getLogger(MyLogTestJava.class);
	private  static Logger logp = LoggerFactory.getLogger(MyLogTestJava.class.getName()+"@p");
	private static Marker myMarker=MarkerFactory.getMarker("myMarker");
	private static Marker gxpMarker=MarkerFactory.getMarker("gxpMarker");
	/**
	 * @author gexp
	 * @dateTime 2018年5月22日 上午11:30:12
	 * @param args   
	*/
	public static void main(String[] args) {
		log.info(null);
		log.info(myMarker,"MyTestJava billing info:{},marker:{}","billing你好hello!",myMarker.getName());
		log.info(gxpMarker,"MyTestJava info:{},marker:{}","gxp这里你好hello!billing",gxpMarker.getName());
		log.info("MyTestJava info:{},marker is null","gxp杭州hangzhou你好statement 2");
		
		log.error("MyTestJava eror:{}","你好hello!");
		log.debug("MyTestJava debug:{}","你好hello!");
		
		logp.info(myMarker,"MyTestJava Person info:{}"," p你好hello!");
		
	}

}
