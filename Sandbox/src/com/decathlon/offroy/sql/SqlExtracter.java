package com.decathlon.offroy.sql;

import stcom.caisse.IBatchCaisse;
import stcom.caisse.ISqlQueries;

import com.decathlon.fwkstores.feature.nbobo.NBOBoAncestorBean;


public class SqlExtracter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sqlQuery = ISqlQueries.getTiersCandidatForMDPCompleteDecompte(IBatchCaisse.C_TRAITEMENT_MDP_COMPLETE);
		
		
		System.out.println(sqlQuery);

		
	}

}
