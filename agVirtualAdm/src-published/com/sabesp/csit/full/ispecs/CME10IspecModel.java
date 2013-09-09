package com.sabesp.csit.full.ispecs;

import java.beans.PropertyVetoException;

import com.unisys.jellybeans.ApplicationState;
import com.unisys.jellybeans.IspecModel;
import com.unisys.jellybeans.LINCFieldException;
import com.unisys.jellybeans.LINCNumericField;
import com.unisys.jellybeans.LINCStringField;

public class CME10IspecModel extends IspecModel {

  public static final String _TOP_LINE___TAG = "_TOP_LINE_";
  private LINCStringField _TOP_LINE_;

  public static final String ACTMTH__TAG = "ACTMTH";
  private LINCNumericField ACTMTH;

  public static final String DSPROXTEL__TAG = "DSPROXTEL";
  private LINCStringField DSPROXTEL;

  public static final String CBBMENU01__TAG = "CBBMENU01";
  private LINCStringField CBBMENU01;

  public static final String CBBMENU02__TAG = "CBBMENU02";
  private LINCStringField CBBMENU02;

  public static final String CBBATALHO__TAG = "CBBATALHO";
  private LINCStringField CBBATALHO;

  public static final String AT_SIGN__TAG = "AT_SIGN";
  private LINCStringField AT_SIGN;

  public static final String DSAMBIENTE__TAG = "DSAMBIENTE";
  private LINCStringField DSAMBIENTE;

  public static final String NMMUNAB1__TAG = "NMMUNAB1";
  private LINCStringField NMMUNAB1;

  public static final String INDIMPTEMP__TAG = "INDIMPTEMP";
  private LINCStringField INDIMPTEMP;


  public CME10IspecModel(ApplicationState appState) {
    super("CME10");
    setAppState(appState);
    _TOP_LINE_ = makeLINCStringField(_TOP_LINE___TAG, 19, 1, 'A', true);
    setLockSign(_TOP_LINE___TAG, true);
    ACTMTH = makeLINCNumericField(ACTMTH__TAG, 4, 2, 'N', true, 0, LINCNumericField.NUL, 0);
    setLockSign(ACTMTH__TAG, true);
    setNotEntered(ACTMTH__TAG, true);
    DSPROXTEL = makeLINCStringField(DSPROXTEL__TAG, 5, 3, 'A', false);
    setDataDisplay(DSPROXTEL__TAG, "Proxima Tela");
    CBBMENU01 = makeLINCStringField(CBBMENU01__TAG, 5, 4, 'A', false);
    CBBMENU02 = makeLINCStringField(CBBMENU02__TAG, 5, 5, 'A', false);
    CBBATALHO = makeLINCStringField(CBBATALHO__TAG, 40, 6, 'A', false);
    AT_SIGN = makeLINCStringField(AT_SIGN__TAG, 1, 7, 'A', false);
    DSAMBIENTE = makeLINCStringField(DSAMBIENTE__TAG, 11, 8, 'A', true);
    setDataDisplay(DSAMBIENTE__TAG, "DS AMBIENTE");
    NMMUNAB1 = makeLINCStringField(NMMUNAB1__TAG, 20, 9, 'A', true);
    setDataDisplay(NMMUNAB1__TAG, "Nome Munic Abrev");
    INDIMPTEMP = makeLINCStringField(INDIMPTEMP__TAG, 1, 10, 'A', true);
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
  public String getCBBMENU01() {
    return CBBMENU01.getValue();
  }
  public void setCBBMENU01(String newValue) throws LINCFieldException, PropertyVetoException {
    CBBMENU01.setValue(newValue);
  }
  public String getCBBMENU02() {
    return CBBMENU02.getValue();
  }
  public void setCBBMENU02(String newValue) throws LINCFieldException, PropertyVetoException {
    CBBMENU02.setValue(newValue);
  }
  public String getCBBATALHO() {
    return CBBATALHO.getValue();
  }
  public void setCBBATALHO(String newValue) throws LINCFieldException, PropertyVetoException {
    CBBATALHO.setValue(newValue);
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
  public String getINDIMPTEMP() {
    return INDIMPTEMP.getValue();
  }
  public String getGenSourceFile() {
    return "";
  }
  public String getGenVersion() {
    return "3.3.3140";
  }
  public String getGenDate() {
    return "Qui, Mar 11 03:51:05 GMT-03:00 2010";
  }
  public String getGUID() {
    return "F78A7F3D17CE34A279A65006C1AB4701";
  }

}
