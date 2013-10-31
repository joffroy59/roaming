package com.decathlon.offroy.sql;

import stcom.caisse.IBatchCaisse;
import stcom.caisse.ISqlQueries;

public class SqlExtracter {

	private static final boolean IS_DEBUG = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SqlQueryFormater queryFormater1 = new SqlQueryFormater("queryFormater1",
				ISqlQueries.getTiersCandidatForMDPCompleteDecompte(IBatchCaisse.C_TRAITEMENT_MDP_COMPLETE));

		queryFormater1.print();

	}

}
