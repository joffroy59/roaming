package com.offroy.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	private static final int LOOP_COUNT = 3;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("test.xml");

		Exemple1Service obj = (Exemple1Service) context.getBean(Exemple1Service.class);
		
		final long startTime = System.currentTimeMillis();
		for (int i = 0; i < LOOP_COUNT; i++) {
			System.out.println("["+i+"]name=" + obj.getName());
		}
		final long endTime = System.currentTimeMillis();

		final long startTime2 = System.currentTimeMillis();
		for (int i = 0; i < LOOP_COUNT; i++) {
			System.out.println("["+i+"]name=" + obj.getNameCached());
		}
		final long endTime2 = System.currentTimeMillis();

		System.out.println("Total execution time: " + (endTime - startTime));		
		System.out.println("Total execution time(cached): " + (endTime2 - startTime2));		
	}

}
