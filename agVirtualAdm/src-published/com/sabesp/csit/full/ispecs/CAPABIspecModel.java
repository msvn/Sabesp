package com.sabesp.csit.full.ispecs;

import java.beans.PropertyVetoException;

import com.unisys.jellybeans.ApplicationState;
import com.unisys.jellybeans.IspecModel;
import com.unisys.jellybeans.LINCFieldException;
import com.unisys.jellybeans.LINCNumericField;
import com.unisys.jellybeans.LINCStringField;

public class CAPABIspecModel extends IspecModel {

  public static final String _TOP_LINE___TAG = "_TOP_LINE_";
  private LINCStringField _TOP_LINE_;

  public static final String ACTMTH__TAG = "ACTMTH";
  private LINCNumericField ACTMTH;

  public static final String DSPROXTEL__TAG = "DSPROXTEL";
  private LINCStringField DSPROXTEL;

  public static final String NMGERAL__TAG = "NMGERAL";
  private LINCStringField NMGERAL;

  public static final String CDDDD__TAG = "CDDDD";
  private LINCStringField CDDDD;

  public static final String NRTELEF__TAG = "NRTELEF";
  private LINCStringField NRTELEF;

  public static final String STGERAL__TAG = "STGERAL";
  private LINCStringField STGERAL;

  public static final String STGERAL1__TAG = "STGERAL1";
  private LINCStringField STGERAL1;

  public static final String STGERAL2__TAG = "STGERAL2";
  private LINCStringField STGERAL2;

  public static final String DSTPLOGAB__TAG = "DSTPLOGAB";
  private LINCStringField DSTPLOGAB;

  public static final String SGTITLOG__TAG = "SGTITLOG";
  private LINCStringField SGTITLOG;

  public static final String SGPREPLOG__TAG = "SGPREPLOG";
  private LINCStringField SGPREPLOG;

  public static final String DSENDEREC__TAG = "DSENDEREC";
  private LINCStringField DSENDEREC;

  public static final String NRENDEREC__TAG = "NRENDEREC";
  private LINCNumericField NRENDEREC;

  public static final String DSCOMPLEM__TAG = "DSCOMPLEM";
  private LINCStringField DSCOMPLEM;

  public static final String NMBAIRRO__TAG = "NMBAIRRO";
  private LINCStringField NMBAIRRO;

  public static final String CDCEP__TAG = "CDCEP";
  private LINCNumericField CDCEP;

  public static final String NMMUNICIP__TAG = "NMMUNICIP";
  private LINCStringField NMMUNICIP;

  public static final String SGESTADO__TAG = "SGESTADO";
  private LINCStringField SGESTADO;

  public static final String STPRENAU__TAG = "STPRENAU";
  private LINCStringField STPRENAU;

  public static final String STIMPRESS__TAG = "STIMPRESS";
  private LINCStringField STIMPRESS;

  public static final String AT_SIGN__TAG = "AT_SIGN";
  private LINCStringField AT_SIGN;

  public static final String DSAMBIENTE__TAG = "DSAMBIENTE";
  private LINCStringField DSAMBIENTE;

  public static final String NMMUNAB1__TAG = "NMMUNAB1";
  private LINCStringField NMMUNAB1;

  public static final String NRRGILIG__TAG = "NRRGILIG";
  private LINCNumericField NRRGILIG;


  public CAPABIspecModel(ApplicationState appState) {
    super("CAPAB");
    setAppState(appState);
    _TOP_LINE_ = makeLINCStringField(_TOP_LINE___TAG, 19, 1, 'A', true);
    setLockSign(_TOP_LINE___TAG, true);
    ACTMTH = makeLINCNumericField(ACTMTH__TAG, 4, 2, 'N', true, 0, LINCNumericField.NUL, 0);
    setLockSign(ACTMTH__TAG, true);
    setNotEntered(ACTMTH__TAG, true);
    DSPROXTEL = makeLINCStringField(DSPROXTEL__TAG, 5, 3, 'A', false);
    setDataDisplay(DSPROXTEL__TAG, "Proxima Tela");
    NMGERAL = makeLINCStringField(NMGERAL__TAG, 30, 4, 'A', false);
    setDataDisplay(NMGERAL__TAG, " NOME DO SOLICIT");
    CDDDD = makeLINCStringField(CDDDD__TAG, 4, 5, 'A', false);
    setDataDisplay(CDDDD__TAG, "DDD");
    NRTELEF = makeLINCStringField(NRTELEF__TAG, 8, 6, 'A', false);
    setDataDisplay(NRTELEF__TAG, "Telefone");
    STGERAL = makeLINCStringField(STGERAL__TAG, 1, 7, 'A', false);
    setDataDisplay(STGERAL__TAG, "Status");
    STGERAL1 = makeLINCStringField(STGERAL1__TAG, 1, 8, 'A', false);
    setDataDisplay(STGERAL1__TAG, "Status Geral");
    STGERAL2 = makeLINCStringField(STGERAL2__TAG, 1, 9, 'A', false);
    setDataDisplay(STGERAL2__TAG, "Status");
    DSTPLOGAB = makeLINCStringField(DSTPLOGAB__TAG, 4, 10, 'A', false);
    setDataDisplay(DSTPLOGAB__TAG, "Ds Tp Log Abrev");
    SGTITLOG = makeLINCStringField(SGTITLOG__TAG, 5, 11, 'A', false);
    setDataDisplay(SGTITLOG__TAG, "Titulo");
    SGPREPLOG = makeLINCStringField(SGPREPLOG__TAG, 3, 12, 'A', false);
    setDataDisplay(SGPREPLOG__TAG, "Preposicao");
    DSENDEREC = makeLINCStringField(DSENDEREC__TAG, 30, 13, 'A', false);
    setDataDisplay(DSENDEREC__TAG, "Endereco");
    NRENDEREC = makeLINCNumericField(NRENDEREC__TAG, 5, 14, 'N', false, 0, LINCNumericField.NUL, 1);
    setDataDisplay(NRENDEREC__TAG, "No.");
    DSCOMPLEM = makeLINCStringField(DSCOMPLEM__TAG, 10, 15, 'A', false);
    setDataDisplay(DSCOMPLEM__TAG, "Complemento");
    NMBAIRRO = makeLINCStringField(NMBAIRRO__TAG, 20, 16, 'A', false);
    setDataDisplay(NMBAIRRO__TAG, "Bairro");
    CDCEP = makeLINCNumericField(CDCEP__TAG, 8, 17, 'N', false, 0, LINCNumericField.NUL, 1);
    setDataDisplay(CDCEP__TAG, "CEP");
    NMMUNICIP = makeLINCStringField(NMMUNICIP__TAG, 30, 18, 'A', false);
    setDataDisplay(NMMUNICIP__TAG, "Nome Municipio");
    SGESTADO = makeLINCStringField(SGESTADO__TAG, 2, 19, 'A', false);
    setDataDisplay(SGESTADO__TAG, "Sigla de Estado");
    STPRENAU = makeLINCStringField(STPRENAU__TAG, 1, 20, 'A', false);
    setDataDisplay(STPRENAU__TAG, "Preench autom.");
    STIMPRESS = makeLINCStringField(STIMPRESS__TAG, 1, 21, 'A', false);
    setDataDisplay(STIMPRESS__TAG, "St Impres");
    AT_SIGN = makeLINCStringField(AT_SIGN__TAG, 1, 22, 'A', false);
    DSAMBIENTE = makeLINCStringField(DSAMBIENTE__TAG, 11, 23, 'A', true);
    setDataDisplay(DSAMBIENTE__TAG, "DS AMBIENTE");
    NMMUNAB1 = makeLINCStringField(NMMUNAB1__TAG, 20, 24, 'A', true);
    setDataDisplay(NMMUNAB1__TAG, "Nome Munic Abrev");
    NRRGILIG = makeLINCNumericField(NRRGILIG__TAG, 10, 25, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(NRRGILIG__TAG, "Numero RGI");
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
  public String getNMGERAL() {
    return NMGERAL.getValue();
  }
  public void setNMGERAL(String newValue) throws LINCFieldException, PropertyVetoException {
    NMGERAL.setValue(newValue);
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
  public String getSTGERAL() {
    return STGERAL.getValue();
  }
  public void setSTGERAL(String newValue) throws LINCFieldException, PropertyVetoException {
    STGERAL.setValue(newValue);
  }
  public String getSTGERAL1() {
    return STGERAL1.getValue();
  }
  public void setSTGERAL1(String newValue) throws LINCFieldException, PropertyVetoException {
    STGERAL1.setValue(newValue);
  }
  public String getSTGERAL2() {
    return STGERAL2.getValue();
  }
  public void setSTGERAL2(String newValue) throws LINCFieldException, PropertyVetoException {
    STGERAL2.setValue(newValue);
  }
  public String getDSTPLOGAB() {
    return DSTPLOGAB.getValue();
  }
  public void setDSTPLOGAB(String newValue) throws LINCFieldException, PropertyVetoException {
    DSTPLOGAB.setValue(newValue);
  }
  public String getSGTITLOG() {
    return SGTITLOG.getValue();
  }
  public void setSGTITLOG(String newValue) throws LINCFieldException, PropertyVetoException {
    SGTITLOG.setValue(newValue);
  }
  public String getSGPREPLOG() {
    return SGPREPLOG.getValue();
  }
  public void setSGPREPLOG(String newValue) throws LINCFieldException, PropertyVetoException {
    SGPREPLOG.setValue(newValue);
  }
  public String getDSENDEREC() {
    return DSENDEREC.getValue();
  }
  public void setDSENDEREC(String newValue) throws LINCFieldException, PropertyVetoException {
    DSENDEREC.setValue(newValue);
  }
  public String getNRENDEREC() {
    return NRENDEREC.getValue();
  }
  public void setNRENDEREC(String newValue) throws LINCFieldException, PropertyVetoException {
    NRENDEREC.setValue(newValue);
  }
  public String getDSCOMPLEM() {
    return DSCOMPLEM.getValue();
  }
  public void setDSCOMPLEM(String newValue) throws LINCFieldException, PropertyVetoException {
    DSCOMPLEM.setValue(newValue);
  }
  public String getNMBAIRRO() {
    return NMBAIRRO.getValue();
  }
  public void setNMBAIRRO(String newValue) throws LINCFieldException, PropertyVetoException {
    NMBAIRRO.setValue(newValue);
  }
  public String getCDCEP() {
    return CDCEP.getValue();
  }
  public void setCDCEP(String newValue) throws LINCFieldException, PropertyVetoException {
    CDCEP.setValue(newValue);
  }
  public String getNMMUNICIP() {
    return NMMUNICIP.getValue();
  }
  public void setNMMUNICIP(String newValue) throws LINCFieldException, PropertyVetoException {
    NMMUNICIP.setValue(newValue);
  }
  public String getSGESTADO() {
    return SGESTADO.getValue();
  }
  public void setSGESTADO(String newValue) throws LINCFieldException, PropertyVetoException {
    SGESTADO.setValue(newValue);
  }
  public String getSTPRENAU() {
    return STPRENAU.getValue();
  }
  public void setSTPRENAU(String newValue) throws LINCFieldException, PropertyVetoException {
    STPRENAU.setValue(newValue);
  }
  public String getSTIMPRESS() {
    return STIMPRESS.getValue();
  }
  public void setSTIMPRESS(String newValue) throws LINCFieldException, PropertyVetoException {
    STIMPRESS.setValue(newValue);
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
  public String getNRRGILIG() {
    return NRRGILIG.getValue();
  }
  public String getGenSourceFile() {
    return "";
  }
  public String getGenVersion() {
    return "3.3.3220";
  }
  public String getGenDate() {
    return "Sat, Mar 27 10:09:31 GMT-03:00 2010";
  }
  public String getGUID() {
    return "B15CB324F264349A658A8F1E67AF232A";
  }

}
