package stcom.test;

import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.quartz.Trigger;

import refer.data.DoTiersRef;
import salto.batch.appender.JdbcErrorAppender;
import salto.batch.lanceur.builder.DBBatchStartParametersComputer;
import salto.batch.lanceur.builder.MultipleBatchLauncher;
import salto.batch.lanceur.builder.TraitementConfiguration;
import salto.batch.lanceur.init.DaoSqlBatchStart;
import salto.batch.lanceur.thread.Traitement;
import salto.batch.linker.AndLinker;
import salto.batch.trigger.CronTrigger;
import stcom.caisse.IBatchCaisse;
import stcom.caisse.ISqlQueries;
import stcom_tools.communprojetstores.bo.BoBatch;
import stcom_tools.communprojetstores.data.DoWScheduler;
import stcom_tools.util.bo.BoStores;
import util.DateUtil;
import wg4.bean.ancestor.TechniqueException;
import caisse.bo.BoTransactionCaisse;
import caisse.data.DoTransactionCaisseCa;

import com.decathlon.fwkstores.feature.nbobo.DefaultNBOBoFactory;
import com.decathlon.fwkstores.feature.nbobo.Historique;
import com.decathlon.fwkstores.feature.nbobo.NBOBoFactory;
import com.decathlon.fwkstores.feature.nbobo.NBOLogger;
import communprojetstores.constante.IConstanteCommunProjet;

public class TraitementBatchSandBox extends Traitement {

	// private static Logger logger =
	// Logger.getLogger(TraitementCAComplete.class);

	private static final String BATCH_GROUP = "STORES.TEST";
	private static final String BATCH_NAME = "TraitementBatchSandBox";

	private static final String TRIGGER_BATCH_GROUP = "STORES.TRIGGERS_TEST";
	private static final String TRIGGER_BATCH_NAME = "trigger" + BATCH_NAME;

	protected Logger logger = NBOLogger.getLoggerForBatch(BATCH_NAME, Historique.VERY_HIGHT);
	protected NBOBoFactory boFactory;

	/**
	 * @see salto.batch.lanceur.thread.Traitement
	 */
	public static void registerAsBatch() {

		// String request =
		// ISqlQueries.getTiersCandidatForCAComplete(BATCH_NAME);
		StringBuffer strBuffer = new StringBuffer();

		// strBuffer.append("select /*+ stcom.caisse.ISqlQueries.getTiersCandidatForCAComplete leading (w) */ ");
		// strBuffer.append("TTI_NUM_TYPE_TIERS,TIR_NUM_TIERS,TIR_SOUS_NUM_TIERS,TCA_DATE_HEURE_TRANS, ");
		// strBuffer.append("TCA_NUM_HOTESSE,TCA_NUM_CAISSE,TCA_NUM_TRANSACTION,TCA_NUM_LIGNE_TRANSACTION,");
		// strBuffer.append("TCA_DATE_ECRITURE,TCA_MODE_SAISIE,TCA_MOTIF_COMMERCIAL_KS,TCA_MOTIF_COMMERCIAL, ");
		// strBuffer.append("TCA_CODE_ARTICLE,TCA_QUANTITE,TCA_MONTANT_KS,TCA_MONTANT,TCA_DEVISE_MONTANT,");
		// strBuffer.append("TCA_CODE_TVA,TCA_TAUX_TVA,TCA_TYPE_TRANSACTION,TCA_ZONE_LIBRE,TCA_PROMO, ");
		// strBuffer.append("NMV_CODE_NATURE_MOUVEMENT,TCA_TOP_TMT_CPT_INFO,TCA_TOP_TMT_DECOMPTE,TCA_TOP_TMT_CA,");
		// strBuffer.append("TCA_TOP_TMT_CLIENT,TCA_TOP_TMT_STOCK,TCA_TOP_TMT_TICKET_CLIENT, TCA_MONTANT_HT_KS, TCA_MONTANT_HT, ");
		// strBuffer.append("TCA_MONTANT_INIT_KS, TCA_ZONE_LIBRE_2 ");
		// strBuffer.append("from TRANSACTION_CAISSE_CA tca ");
		// strBuffer.append("inner join w_scheduler w ");
		// strBuffer.append("on tca.tti_num_type_tiers = w.tti_num_type_tiers_tir ");
		// strBuffer.append("and tca.tir_num_tiers = w.tir_num_tiers_tir ");
		// strBuffer.append("and tca.tir_sous_num_tiers = w.tir_sous_num_tiers_tir ");
		// strBuffer.append("where TCA_TOP_TMT_CPT_INFO=0 ");
		// strBuffer.append("and w.sch_nom_proc = '" + BATCH_NAME + "' ");

		strBuffer.append("select distinct tca.* from STCOM.TTRANSACTION_CAISSE_CA tca ");
		strBuffer.append("inner join w_scheduler w ");
		strBuffer.append("on tca.tti_num_type_tiers = w.tti_num_type_tiers_tir ");
		strBuffer.append("and tca.tir_num_tiers = w.tir_num_tiers_tir ");
		strBuffer.append("and tca.tir_sous_num_tiers = w.tir_sous_num_tiers_tir ");
		strBuffer.append("where ");
		strBuffer.append("tca.tca_num_transaction=8430 ");
		strBuffer.append("and TCA_TOP_TMT_CPT_INFO=0 ");
		strBuffer.append("and sch_nom_proc='" + BATCH_NAME + "' ");

		// strBuffer.append("select * from w_scheduler where sch_nom_proc = '" +
		// BATCH_NAME + "' ");

		String request = strBuffer.toString();

		try {
			TraitementConfiguration lanceur = new TraitementConfiguration();
			lanceur.setGroup(BATCH_GROUP);
			lanceur.setName(BATCH_NAME);
			lanceur.setBatchAppender(new JdbcErrorAppender());
			lanceur.setTrtClassName(TraitementBatchSandBox.class.getName());
			DaoSqlBatchStart starter = new DaoSqlBatchStart("jdbc/stcom", request, 500, false,
					new String[] { DoTransactionCaisseCa.class.getName() });
			lanceur.setMaxThreads(3);
			lanceur.setMinJob(9);
			lanceur.setMaxJob(18);

			// Lit les param�tres de lancement du batch start
			DBBatchStartParametersComputer batchStartParamsComputer = new DBBatchStartParametersComputer(BATCH_GROUP, BATCH_NAME);

			MultipleBatchLauncher metaBatchStart = lanceur.newMultipleBatchLauncher(starter, batchStartParamsComputer);
			lanceur.setMultipleBatchStart(metaBatchStart);

			// CompleterCA est execute apres l'execution de CompleterMDP
			// AndLinker linker = new AndLinker();
			// // linker.addNecessaryBatch(BATCH_GROUP,
			// IBatchCaisse.C_TRAITEMENT_MDP_COMPLETE);
			// linker.addNecessaryBatch(BATCH_GROUP,
			// TraitementNewCAComplete.BATCH_NAME);
			// lanceur.setLinkers(new AndLinker[] { linker });

			CronTrigger trigger = new CronTrigger();
			trigger.setGroup(TRIGGER_BATCH_GROUP);
			trigger.setName(TRIGGER_BATCH_NAME);
			trigger.setJobGroup(BATCH_GROUP);
			trigger.setJobName(BATCH_NAME);

			// Fire every 2mn
			trigger.setCronExpression("0 1/30 * * * ?");
			trigger.setStartTime(new Date());
			trigger.setEndTime(DateUtil.infinite());
			lanceur.setTriggers(new Trigger[] { trigger });

			lanceur.register();
			lanceur.disable();
			lanceur.save();
		} catch (Exception e) {
			throw new IllegalStateException("Error registering " + BATCH_NAME + " , message=" + e.getMessage(), e);
		}
	}

	/**
	 * @see salto.batch.lanceur.thread.Traitement#exec(Object[])
	 * @param data
	 *            Dataobject to work on
	 */
	public void exec(Object[] data) throws Exception {

		logger.info("in exec");
		
		DoTransactionCaisseCa[] theData = (DoTransactionCaisseCa[]) data[0];
		
		for (DoTransactionCaisseCa doTransactionCaisseCa : theData) {
			logger.info("data MC:"+doTransactionCaisseCa.getTcaMotifCommercial());
			logger.info("data DT:"+doTransactionCaisseCa.getTcaDateHeureTrans());
			logger.info("data DE:"+doTransactionCaisseCa.getTcaDateEcriture());
		}

		logger.info("in exec[END]");
	}

	/**
	 * @see salto.batch.lanceur.thread.Traitement#initialize()
	 */
	public void initialize() throws Exception {
		boFactory = new DefaultNBOBoFactory(logger);
		logger.setLevel(Level.ALL);// TODO
		if (logger.isDebugEnabled())
			logger.debug("Initialize Batch : " + BATCH_NAME);
		else
			logger.info("Init");

		// Initialiser les mag sur lesquels le batch peut tourner
		BoStores boStores = boFactory.getBO(BoStores.class);
		DoTiersRef stores = boStores.getTiersStores();

		String connStcom = getBatchExecutionContext().getBatchStartDataSource();

		BoBatch boBatch = boFactory.getBO(BoBatch.class);
		DoWScheduler[] tScheduler = boBatch.getTiersCandidatsBatch(connStcom, stores,
				IConstanteCommunProjet.C_CODE_APPLICATION_ACTIVATION_BATCH, BATCH_NAME);

		if (tScheduler != null) {
			if (logger.isDebugEnabled()) {
				StringBuffer buf = new StringBuffer(36 + (tScheduler.length * 22));
				buf.append(tScheduler.length).append(" thirds ready to perform batch...");
				for (DoWScheduler scheduler : tScheduler)
					buf.append("\n\t-").append(scheduler.getTtiNumTypeTiersTir()).append(" ").append(scheduler.getTirNumTiersTir())
							.append(" ").append(scheduler.getTirSousNumTiersTir());
				logger.debug(buf.toString());
			}

			boBatch.setTiersCandidats(connStcom, tScheduler, BATCH_NAME);
		}

	}

	/**
	 * @see salto.batch.lanceur.thread.Traitement#finalizeBatch()
	 */
	public void finalizeBatch() {
		logger.debug("Finalize");
	}
}
