package com.decathlon.offroy.string;

public class Sandbox {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String strRefFactureCourt = "123";

		String strRefFacture = String.format("%016d", Long.parseLong(strRefFactureCourt ));
		System.out.println(strRefFactureCourt);
		System.out.println(strRefFacture);
	}

}
