package com.offroy.core;

public class Exemple1Service {
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
