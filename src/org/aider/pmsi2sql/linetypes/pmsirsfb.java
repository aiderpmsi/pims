package org.aider.pmsi2sql.linetypes;

import org.aider.pmsi2sql.dbtypes.PmsiStandardDbTypeEnum;
import org.aider.pmsi2sql.dbtypes.pmsidbinternaldbtype;
import org.aider.pmsi2sql.dbtypes.pmsifiledbtype;
import org.aider.pmsi2sql.dbtypes.pmsifkdbtype;
import org.aider.pmsi2sql.dbtypes.pmsiindexdbtype;

public class pmsirsfb extends pmsilinetype {

	/**
	 * Constructeur
	 */
	public pmsirsfb() {
		super("RSFB");

		pmsidbinternaldbtype MyIdMain =new pmsidbinternaldbtype("rsfbid", PmsiStandardDbTypeEnum.BIGSERIAL, 0);
		MyIdMain.setValue("nextval('rsfb_rsfbid_seq')");
		addChamp(MyIdMain);
		pmsiindexdbtype MyIdmainIndex = new pmsiindexdbtype("RSFB_rsfbid_pidx", pmsiindexdbtype.INDEX_PK);
		MyIdmainIndex.addIndex("rsfbid");
		addChamp(MyIdmainIndex);
		
		pmsidbinternaldbtype MyIdHeader = new pmsidbinternaldbtype("rsfheaderid", PmsiStandardDbTypeEnum.BIGINT, 0);
		MyIdHeader.setValue("currval('rsfheader_rsfheaderid_seq')");
		addChamp(MyIdHeader);
		pmsifkdbtype MyIdHeaderFK = new pmsifkdbtype("RSFB_rsfheaderid_NumFacture_fk", "RSFA", "DEFERRABLE INITIALLY DEFERRED");
		MyIdHeaderFK.addForeignChamp("rsfheaderid", "rsfheaderid");
		MyIdHeaderFK.addForeignChamp("NumFacture", "NumFacture");
		addChamp(MyIdHeaderFK);

		pmsidbinternaldbtype MyLineCounter = new pmsidbinternaldbtype("Line", PmsiStandardDbTypeEnum.INT, 0);
		MyLineCounter.setValue("currval('line_counter')");
		addChamp(MyLineCounter);

		addChamp(new pmsifiledbtype("TypeEnregistrement", PmsiStandardDbTypeEnum.CHAR, 1, "(B)"));
		
		addChamp(new pmsifiledbtype("FINESS", PmsiStandardDbTypeEnum.NUMERIC, 9, "(\\d{9})"));
		pmsiindexdbtype MyFinessIndex = new pmsiindexdbtype("RSFB_FINESS_idx", pmsiindexdbtype.INDEX_SIMPLE);
		MyFinessIndex.addIndex("FINESS");
		addChamp(MyFinessIndex);

		addChamp(new pmsifiledbtype("NumRSS", PmsiStandardDbTypeEnum.NUMERIC, 20, "(.{20})"));
		pmsiindexdbtype MyNumRSSIndex = new pmsiindexdbtype("RSFB_NumRSS_idx", pmsiindexdbtype.INDEX_SIMPLE);
		MyNumRSSIndex.addIndex("NumRSS");
		addChamp(MyNumRSSIndex);
		
		addChamp(new pmsifiledbtype("CodeSS", PmsiStandardDbTypeEnum.VARCHAR, 13, "(.{13})"));
		addChamp(new pmsifiledbtype("CleCodeSS", PmsiStandardDbTypeEnum.VARCHAR, 2, "(.{2})"));
		addChamp(new pmsifiledbtype("RangBeneficiaire", PmsiStandardDbTypeEnum.VARCHAR, 3, "(.{3})"));

		addChamp(new pmsifiledbtype("NumFacture", PmsiStandardDbTypeEnum.NUMERIC, 9, "(.{9})"));
		pmsiindexdbtype MyNumFactureIndex = new pmsiindexdbtype("RSFB_NumFacture_idx", pmsiindexdbtype.INDEX_SIMPLE);
		MyNumFactureIndex.addIndex("NumFacture");
		addChamp(MyNumFactureIndex);
		
		addChamp(new pmsifiledbtype("ModeTraitement", PmsiStandardDbTypeEnum.NUMERIC, 2, "(.{2})"));
		addChamp(new pmsifiledbtype("DisciplinePrestation", PmsiStandardDbTypeEnum.NUMERIC, 3, "(.{3})"));

		addChamp(new pmsifiledbtype("DateDebutSejour", PmsiStandardDbTypeEnum.DATE, 0, "(.{8})"));
		pmsiindexdbtype MyDateDebutSejourIndex = new pmsiindexdbtype("RSFB_DateDebutSejour_idx", pmsiindexdbtype.INDEX_SIMPLE);
		MyDateDebutSejourIndex.addIndex("DateDebutSejour");
		addChamp(MyDateDebutSejourIndex);
		
		addChamp(new pmsifiledbtype("DateFinSejour", PmsiStandardDbTypeEnum.DATE, 0, "(.{8})"));
		pmsiindexdbtype MyDateFinSejourIndex = new pmsiindexdbtype("RSFB_DateFinSejour_idx", pmsiindexdbtype.INDEX_SIMPLE);
		MyDateFinSejourIndex.addIndex("DateFinSejour");
		addChamp(MyDateFinSejourIndex);

		addChamp(new pmsifiledbtype("CodeActe", PmsiStandardDbTypeEnum.VARCHAR, 5, "(.{5})"));
		pmsiindexdbtype MyCodeActeIndex = new pmsiindexdbtype("RSFB_CodeActe_idx", pmsiindexdbtype.INDEX_SIMPLE);
		MyCodeActeIndex.addIndex("CodeActe");
		addChamp(MyCodeActeIndex);
		
		addChamp(new pmsifiledbtype("Quantite", PmsiStandardDbTypeEnum.NUMERIC, 3, "(.{3})"));
		addChamp(new pmsifiledbtype("JustifExonerationTM", PmsiStandardDbTypeEnum.CHAR, 1, "(.{1})"));
		addChamp(new pmsifiledbtype("Coefficient", PmsiStandardDbTypeEnum.NUMERIC, 5, "(.{5})"));
		addChamp(new pmsifiledbtype("CodePEC", PmsiStandardDbTypeEnum.CHAR, 1, "(.{1})"));
		addChamp(new pmsifiledbtype("CoefficientMCO", PmsiStandardDbTypeEnum.NUMERIC, 5, "(.{5})"));
		addChamp(new pmsifiledbtype("PrixUnitaire", PmsiStandardDbTypeEnum.NUMERIC, 7, "(.{7})"));
		addChamp(new pmsifiledbtype("MontantBaseRemboursementPH", PmsiStandardDbTypeEnum.NUMERIC, 8, "(.{8})"));
		addChamp(new pmsifiledbtype("TauxPrestation", PmsiStandardDbTypeEnum.NUMERIC, 3, "(.{3})"));
		addChamp(new pmsifiledbtype("MontantRemboursableAMOPH", PmsiStandardDbTypeEnum.NUMERIC, 8, "(.{8})"));
		addChamp(new pmsifiledbtype("MontantTotalDepense", PmsiStandardDbTypeEnum.NUMERIC, 8, "(.{8})"));
		addChamp(new pmsifiledbtype("MontantRemboursableOCPH", PmsiStandardDbTypeEnum.NUMERIC, 7, "(.{7})"));
		addChamp(new pmsifiledbtype("NumGHS", PmsiStandardDbTypeEnum.VARCHAR, 4, "(.{4})"));
		addChamp(new pmsifiledbtype("MontantNOEMIE", PmsiStandardDbTypeEnum.NUMERIC, 8, "(.{8})"));
		addChamp(new pmsifiledbtype("OperationNOEMIE", PmsiStandardDbTypeEnum.CHAR, 3, "(.{3})"));
	}

}
