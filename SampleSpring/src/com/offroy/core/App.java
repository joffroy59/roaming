package com.offroy.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");

		Exemple1Service obj = (Exemple1Service) context.getBean("exemple1Service");

		final long startTime = System.currentTimeMillis();

		for (int i = 0; i < 1; i++) {
			System.out.println("name=" + obj.getName());
		}

		final long endTime = System.currentTimeMillis();

		System.out.println("Total execution time: " + (endTime - startTime));

	}

}
