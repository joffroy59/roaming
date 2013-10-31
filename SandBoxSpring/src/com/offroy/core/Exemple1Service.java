package com.offroy.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class Exemple1Service {
	
	@Value(value="TesterServiceDEfault")
	private String name;
	 
	public void setName(String name) {
		this.name = name;
	}
 
	public String getName() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}

	@Cacheable("service") 
	public String getNameCached() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
}
