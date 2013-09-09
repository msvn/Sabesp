package com.sabesp.csit.full.ispecs;

import java.beans.PropertyVetoException;

import com.unisys.jellybeans.ApplicationState;
import com.unisys.jellybeans.IspecModel;
import com.unisys.jellybeans.LINCFieldException;
import com.unisys.jellybeans.LINCNumericField;
import com.unisys.jellybeans.LINCStringField;

public class AVWEBIspecModel extends IspecModel {

  public static final String _TOP_LINE___TAG = "_TOP_LINE_";
  private LINCStringField _TOP_LINE_;

  public static final String ACTMTH__TAG = "ACTMTH";
  private LINCNumericField ACTMTH;

  public static final String DSPROXTEL__TAG = "DSPROXTEL";
  private LINCStringField DSPROXTEL;

  public static final String NRRGILIG__TAG = "NRRGILIG";
  private LINCNumericField NRRGILIG;

  public static final String CDGRPSERV__TAG = "CDGRPSERV";
  private LINCNumericField CDGRPSERV;

  public static final String CDSERVCOM__TAG = "CDSERVCOM";
  private LINCNumericField CDSERVCOM;

  public static final String DSTELA40__TAG = "DSTELA40";
  private LINCStringField DSTELA40;

  public static final String DSTELA50__TAG = "DSTELA50";
  private LINCStringField DSTELA50;

  public static final String DSTELA60__TAG = "DSTELA60";
  private LINCStringField DSTELA60;

  public static final String CDDDD__TAG = "CDDDD";
  private LINCStringField CDDDD;

  public static final String NRTELEF__TAG = "NRTELEF";
  private LINCStringField NRTELEF;

  public static final String NRRAMAFAX1__TAG = "NRRAMAFAX1";
  private LINCStringField NRRAMAFAX1;

  public static final String DSTELA60A__TAG = "DSTELA60A";
  private LINCStringField DSTELA60A;

  public static final String DSTELA60B__TAG = "DSTELA60B";
  private LINCStringField DSTELA60B;

  public static final String DSTELA30__TAG = "DSTELA30";
  private LINCStringField DSTELA30;

  public static final String DSTELA30A__TAG = "DSTELA30A";
  private LINCStringField DSTELA30A;

  public static final String STOK__TAG = "STOK";
  private LINCStringField STOK;

  public static final String AT_SIGN__TAG = "AT_SIGN";
  private LINCStringField AT_SIGN;

  public static final String DSAMBIENTE__TAG = "DSAMBIENTE";
  private LINCStringField DSAMBIENTE;

  public static final String NMMUNAB1__TAG = "NMMUNAB1";
  private LINCStringField NMMUNAB1;


  public AVWEBIspecModel(ApplicationState appState) {
    super("AVWEB");
    setAppState(appState);
    _TOP_LINE_ = makeLINCStringField(_TOP_LINE___TAG, 19, 1, 'A', true);
    setLockSign(_TOP_LINE___TAG, true);
    ACTMTH = makeLINCNumericField(ACTMTH__TAG, 4, 2, 'N', true, 0, LINCNumericField.NUL, 0);
    setLockSign(ACTMTH__TAG, true);
    setNotEntered(ACTMTH__TAG, true);
    DSPROXTEL = makeLINCStringField(DSPROXTEL__TAG, 5, 3, 'A', false);
    setDataDisplay(DSPROXTEL__TAG, "Proxima Tela");
    NRRGILIG = makeLINCNumericField(NRRGILIG__TAG, 10, 4, 'N', false, 0, LINCNumericField.NUL, 1);
    setDataDisplay(NRRGILIG__TAG, "Numero RGI");
    CDGRPSERV = makeLINCNumericField(CDGRPSERV__TAG, 2, 5, 'N', false, 0, LINCNumericField.NUL, 1);
    setDataDisplay(CDGRPSERV__TAG, "Grupo de Servico");
    CDSERVCOM = makeLINCNumericField(CDSERVCOM__TAG, 4, 6, 'N', false, 0, LINCNumericField.NUL, 1);
    setDataDisplay(CDSERVCOM__TAG, "Codigo Servico");
    DSTELA40 = makeLINCStringField(DSTELA40__TAG, 40, 7, 'A', false);
    setDataDisplay(DSTELA40__TAG, "Descricao");
    DSTELA50 = makeLINCStringField(DSTELA50__TAG, 50, 8, 'A', false);
    setDataDisplay(DSTELA50__TAG, "Descricao");
    DSTELA60 = makeLINCStringField(DSTELA60__TAG, 60, 9, 'A', false);
    setDataDisplay(DSTELA60__TAG, "DS TELA COM 60");
    CDDDD = makeLINCStringField(CDDDD__TAG, 4, 10, 'A', false);
    setDataDisplay(CDDDD__TAG, "DDD");
    NRTELEF = makeLINCStringField(NRTELEF__TAG, 8, 11, 'A', false);
    setDataDisplay(NRTELEF__TAG, "Telefone");
    NRRAMAFAX1 = makeLINCStringField(NRRAMAFAX1__TAG, 5, 12, 'A', false);
    setDataDisplay(NRRAMAFAX1__TAG, "Numero ramal fax");
    DSTELA60A = makeLINCStringField(DSTELA60A__TAG, 60, 13, 'A', true);
    setDataDisplay(DSTELA60A__TAG, "DESCRICAO TELA");
    DSTELA60B = makeLINCStringField(DSTELA60B__TAG, 60, 14, 'A', true);
    setDataDisplay(DSTELA60B__TAG, "DESCRICAO TELA");
    DSTELA30 = makeLINCStringField(DSTELA30__TAG, 30, 15, 'A', true);
    setDataDisplay(DSTELA30__TAG, "Descricao");
    DSTELA30A = makeLINCStringField(DSTELA30A__TAG, 30, 16, 'A', true);
    setDataDisplay(DSTELA30A__TAG, "Descr Tela 30A");
    STOK = makeLINCStringField(STOK__TAG, 1, 17, 'A', false);
    setDataDisplay(STOK__TAG, "StatConfirmacao");
    AT_SIGN = makeLINCStringField(AT_SIGN__TAG, 1, 18, 'A', false);
    DSAMBIENTE = makeLINCStringField(DSAMBIENTE__TAG, 11, 19, 'A', true);
    setDataDisplay(DSAMBIENTE__TAG, "DS AMBIENTE");
    NMMUNAB1 = makeLINCStringField(NMMUNAB1__TAG, 20, 20, 'A', true);
    setDataDisplay(NMMUNAB1__TAG, "Nome Munic Abrev");
  }


  public String get_TOP_LINE_() {
    return _TOP_LINE_.getValue();
  }
  public String getACTMTH() {
    return ACTMTH.getValue();
  }
  public String getDSPROXTEL() {
    return DSPROXTEL.getValue();
  }
  public void setDSPROXTEL(String newValue) throws LINCFieldException, PropertyVetoException {
    DSPROXTEL.setValue(newValue);
  }
  public String getNRRGILIG() {
    return NRRGILIG.getValue();
  }
  public void setNRRGILIG(String newValue) throws LINCFieldException, PropertyVetoException {
    NRRGILIG.setValue(newValue);
  }
  public String getCDGRPSERV() {
    return CDGRPSERV.getValue();
  }
  public void setCDGRPSERV(String newValue) throws LINCFieldException, PropertyVetoException {
    CDGRPSERV.setValue(newValue);
  }
  public String getCDSERVCOM() {
    return CDSERVCOM.getValue();
  }
  public void setCDSERVCOM(String newValue) throws LINCFieldException, PropertyVetoException {
    CDSERVCOM.setValue(newValue);
  }
  public String getDSTELA40() {
    return DSTELA40.getValue();
  }
  public void setDSTELA40(String newValue) throws LINCFieldException, PropertyVetoException {
    DSTELA40.setValue(newValue);
  }
  public String getDSTELA50() {
    return DSTELA50.getValue();
  }
  public void setDSTELA50(String newValue) throws LINCFieldException, PropertyVetoException {
    DSTELA50.setValue(newValue);
  }
  public String getDSTELA60() {
    return DSTELA60.getValue();
  }
  public void setDSTELA60(String newValue) throws LINCFieldException, PropertyVetoException {
    DSTELA60.setValue(newValue);
  }
  public String getCDDDD() {
    return CDDDD.getValue();
  }
  public void setCDDDD(String newValue) throws LINCFieldException, PropertyVetoException {
    CDDDD.setValue(newValue);
  }
  public String getNRTELEF() {
    return NRTELEF.getValue();
  }
  public void setNRTELEF(String newValue) throws LINCFieldException, PropertyVetoException {
    NRTELEF.setValue(newValue);
  }
  public String getNRRAMAFAX1() {
    return NRRAMAFAX1.getValue();
  }
  public void setNRRAMAFAX1(String newValue) throws LINCFieldException, PropertyVetoException {
    NRRAMAFAX1.setValue(newValue);
  }
  public String getDSTELA60A() {
    return DSTELA60A.getValue();
  }
  public String getDSTELA60B() {
    return DSTELA60B.getValue();
  }
  public String getDSTELA30() {
    return DSTELA30.getValue();
  }
  public String getDSTELA30A() {
    return DSTELA30A.getValue();
  }
  public String getSTOK() {
    return STOK.getValue();
  }
  public void setSTOK(String newValue) throws LINCFieldException, PropertyVetoException {
    STOK.setValue(newValue);
  }
  public String getAT_SIGN() {
    return AT_SIGN.getValue();
  }
  public void setAT_SIGN(String newValue) throws LINCFieldException, PropertyVetoException {
    AT_SIGN.setValue(newValue);
  }
  public String getDSAMBIENTE() {
    return DSAMBIENTE.getValue();
  }
  public String getNMMUNAB1() {
    return NMMUNAB1.getValue();
  }
  public String getGenSourceFile() {
    return "";
  }
  public String getGenVersion() {
    return "3.3.3220";
  }
  public String getGenDate() {
    return "Sat, Jun 12 04:12:24 GMT-03:00 2010";
  }
  public String getGUID() {
    return "52F4CF61561753C66E7C4B258892594D";
  }

}
