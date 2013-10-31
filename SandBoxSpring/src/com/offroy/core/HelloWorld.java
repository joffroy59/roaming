package com.offroy.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HelloWorld {
	
	@Value("test name default")
	private String name;
	 
	public void setName(String name) {
		this.name = name;
	}
 
	public void printHello() {
		System.out.println("Spring 3 : Hello ! " + name);
	}
}
