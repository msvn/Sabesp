package com.sabesp.csit.full.ispecs;

import java.beans.PropertyVetoException;

import com.unisys.jellybeans.ApplicationState;
import com.unisys.jellybeans.IspecModel;
import com.unisys.jellybeans.LINCFieldException;
import com.unisys.jellybeans.LINCNumericField;
import com.unisys.jellybeans.LINCStringField;

public class W2VINIspecModel extends IspecModel {

  public static final String _TOP_LINE___TAG = "_TOP_LINE_";
  private LINCStringField _TOP_LINE_;

  public static final String ACTMTH__TAG = "ACTMTH";
  private LINCNumericField ACTMTH;

  public static final String DSPROXTEL__TAG = "DSPROXTEL";
  private LINCStringField DSPROXTEL;

  public static final String CDMUNICIP__TAG = "CDMUNICIP";
  private LINCNumericField CDMUNICIP;

  public static final String NRRGILIG__TAG = "NRRGILIG";
  private LINCNumericField NRRGILIG;

  public static final String NMGERAL__TAG = "NMGERAL";
  private LINCStringField NMGERAL;

  public static final String AMJREFER__TAG = "AMJREFER";
  private LINCNumericField AMJREFER;

  public static final String TPLEITURA__TAG = "TPLEITURA";
  private LINCStringField TPLEITURA;

  public static final String AT_SIGN__TAG = "AT_SIGN";
  private LINCStringField AT_SIGN;

  public static final String DSAMBIENTE__TAG = "DSAMBIENTE";
  private LINCStringField DSAMBIENTE;

  public static final String NMMUNAB1__TAG = "NMMUNAB1";
  private LINCStringField NMMUNAB1;


  public W2VINIspecModel(ApplicationState appState) {
    super("W2VIN");
    setAppState(appState);
    _TOP_LINE_ = makeLINCStringField(_TOP_LINE___TAG, 19, 1, 'A', true);
    setLockSign(_TOP_LINE___TAG, true);
    ACTMTH = makeLINCNumericField(ACTMTH__TAG, 4, 2, 'N', true, 0, LINCNumericField.NUL, 0);
    setLockSign(ACTMTH__TAG, true);
    setNotEntered(ACTMTH__TAG, true);
    DSPROXTEL = makeLINCStringField(DSPROXTEL__TAG, 5, 3, 'A', false);
    setDataDisplay(DSPROXTEL__TAG, "Proxima Tela");
    CDMUNICIP = makeLINCNumericField(CDMUNICIP__TAG, 5, 4, 'N', false, 0, LINCNumericField.NUL, 1);
    setDataDisplay(CDMUNICIP__TAG, "Codigo Municipio");
    NRRGILIG = makeLINCNumericField(NRRGILIG__TAG, 10, 5, 'N', false, 0, LINCNumericField.NUL, 1);
    setDataDisplay(NRRGILIG__TAG, "Numero RGI");
    NMGERAL = makeLINCStringField(NMGERAL__TAG, 30, 6, 'A', false);
    setDataDisplay(NMGERAL__TAG, "Nome");
    AMJREFER = makeLINCNumericField(AMJREFER__TAG, 3, 7, 'N', false, 0, LINCNumericField.NUL, 1);
    setDataDisplay(AMJREFER__TAG, "ANO/MES REFERENC");
    TPLEITURA = makeLINCStringField(TPLEITURA__TAG, 1, 8, 'A', false);
    setDataDisplay(TPLEITURA__TAG, "Tipo de Leitura");
    AT_SIGN = makeLINCStringField(AT_SIGN__TAG, 1, 9, 'A', false);
    DSAMBIENTE = makeLINCStringField(DSAMBIENTE__TAG, 11, 10, 'A', true);
    setDataDisplay(DSAMBIENTE__TAG, "DS AMBIENTE");
    NMMUNAB1 = makeLINCStringField(NMMUNAB1__TAG, 20, 11, 'A', true);
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
  public String getCDMUNICIP() {
    return CDMUNICIP.getValue();
  }
  public void setCDMUNICIP(String newValue) throws LINCFieldException, PropertyVetoException {
    CDMUNICIP.setValue(newValue);
  }
  public String getNRRGILIG() {
    return NRRGILIG.getValue();
  }
  public void setNRRGILIG(String newValue) throws LINCFieldException, PropertyVetoException {
    NRRGILIG.setValue(newValue);
  }
  public String getNMGERAL() {
    return NMGERAL.getValue();
  }
  public void setNMGERAL(String newValue) throws LINCFieldException, PropertyVetoException {
    NMGERAL.setValue(newValue);
  }
  public String getAMJREFER() {
    return AMJREFER.getValue();
  }
  public void setAMJREFER(String newValue) throws LINCFieldException, PropertyVetoException {
    AMJREFER.setValue(newValue);
  }
  public String getTPLEITURA() {
    return TPLEITURA.getValue();
  }
  public void setTPLEITURA(String newValue) throws LINCFieldException, PropertyVetoException {
    TPLEITURA.setValue(newValue);
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
    return "Wed, May 26 05:15:41 GMT-03:00 2010";
  }
  public String getGUID() {
    return "F7DA1B1212C81B834098AFF9882A8633";
  }

}
