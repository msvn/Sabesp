package com.prime.app.jpa.test;

public class RunTabelaPrecosServicos extends RunGenericRDMS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub AGV_TAB_SUBSERVICO->CD_SERVICO_CSI(CDGRPSERV)
//		findQBE(" Select B.cdunidco3, B.cdunidco2, B.CDMUNICIP " +
//				" from PCTB2304 A, ctb23 B " +
//				" where A.CDMUNICIP=40 AND A.CDMUNICIP=B.CDMUNICIP AND A.CDATCOM=B.CDATCOM");

//		findQBE(" Select CDUNIDCO3, CDUNIDCO2, CDGRPSERV, CDSERVCOM, CDSERVEXE, CDPRIORID,QTPARCEL1, SNPRECOFIX, STGERAL1 " +
//				" FROM CTB01 WHERE CDUNIDCO3=3 AND CDUNIDCO2=2 AND CDGRPSERV =27 and CDSERVEXE=1");

		findQBE(" Select CDUNIDCO3, CDUNIDCO2, CDGRPSERV, CDSERVCOM, CDSERVEXE, DTVIGENC, QTPARCEL1, VLSERVCOM " +
				" FROM CTB81 WHERE CDUNIDCO2=3 AND CDUNIDCO3=3 AND " +
				" CDGRPSERV = 1 AND CDSERVCOM=10 AND CDSERVEXE=1 ORDER BY CDSERVEXE,DTVIGENC DESC FETCH FIRST 1 ROW ONLY");
	}

}
