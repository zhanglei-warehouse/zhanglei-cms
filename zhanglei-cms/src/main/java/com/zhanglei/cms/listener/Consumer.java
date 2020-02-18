package com.zhanglei.cms.listener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/** 
* @ClassName: Consumer 
* @Description: TODO
* @author: lei zhang
* @date: 2020年2月13日 下午3:02:43 
*/
public class Consumer {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring-beans.xml");
		System.out.println("消费者启动成功！");
	}
}
