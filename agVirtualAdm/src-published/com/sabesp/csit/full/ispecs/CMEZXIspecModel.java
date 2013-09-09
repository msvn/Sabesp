package com.sabesp.csit.full.ispecs;

import java.beans.PropertyVetoException;

import com.unisys.jellybeans.IspecModel;
import com.unisys.jellybeans.LINCField;
import com.unisys.jellybeans.LINCStringField;
import com.unisys.jellybeans.LINCNumericField;
import com.unisys.jellybeans.LINCFieldException;
import com.unisys.jellybeans.NoSuchFieldException;
import com.unisys.jellybeans.ApplicationState;

public class CMEZXIspecModel extends IspecModel {

  public static final String _TOP_LINE___TAG = "_TOP_LINE_";
  private LINCStringField _TOP_LINE_;

  public static final String ACTMTH__TAG = "ACTMTH";
  private LINCNumericField ACTMTH;

  public static final String DSPROXTEL__TAG = "DSPROXTEL";
  private LINCStringField DSPROXTEL;

  public static final String STSELEC__AT_00__TAG = "STSELEC__AT_00";

  public static final String DSTELA15__AT_00__TAG = "DSTELA15__AT_00";

  public static final String DSTELA50__AT_00__TAG = "DSTELA50__AT_00";

  public static final String STSELEC__AT_01__TAG = "STSELEC__AT_01";

  public static final String DSTELA15__AT_01__TAG = "DSTELA15__AT_01";

  public static final String DSTELA50__AT_01__TAG = "DSTELA50__AT_01";

  public static final String STSELEC__AT_02__TAG = "STSELEC__AT_02";

  public static final String DSTELA15__AT_02__TAG = "DSTELA15__AT_02";

  public static final String DSTELA50__AT_02__TAG = "DSTELA50__AT_02";

  public static final String STSELEC__AT_03__TAG = "STSELEC__AT_03";

  public static final String DSTELA15__AT_03__TAG = "DSTELA15__AT_03";

  public static final String DSTELA50__AT_03__TAG = "DSTELA50__AT_03";

  public static final String STSELEC__AT_04__TAG = "STSELEC__AT_04";

  public static final String DSTELA15__AT_04__TAG = "DSTELA15__AT_04";

  public static final String DSTELA50__AT_04__TAG = "DSTELA50__AT_04";

  public static final String STSELEC__AT_05__TAG = "STSELEC__AT_05";

  public static final String DSTELA15__AT_05__TAG = "DSTELA15__AT_05";

  public static final String DSTELA50__AT_05__TAG = "DSTELA50__AT_05";

  public static final String STSELEC__AT_06__TAG = "STSELEC__AT_06";

  public static final String DSTELA15__AT_06__TAG = "DSTELA15__AT_06";

  public static final String DSTELA50__AT_06__TAG = "DSTELA50__AT_06";

  public static final String STSELEC__AT_07__TAG = "STSELEC__AT_07";

  public static final String DSTELA15__AT_07__TAG = "DSTELA15__AT_07";

  public static final String DSTELA50__AT_07__TAG = "DSTELA50__AT_07";

  public static final String STSELEC__AT_08__TAG = "STSELEC__AT_08";

  public static final String DSTELA15__AT_08__TAG = "DSTELA15__AT_08";

  public static final String DSTELA50__AT_08__TAG = "DSTELA50__AT_08";

  public static final String STSELEC__AT_09__TAG = "STSELEC__AT_09";

  public static final String DSTELA15__AT_09__TAG = "DSTELA15__AT_09";

  public static final String DSTELA50__AT_09__TAG = "DSTELA50__AT_09";

  public static final String STSELEC__AT_10__TAG = "STSELEC__AT_10";

  public static final String DSTELA15__AT_10__TAG = "DSTELA15__AT_10";

  public static final String DSTELA50__AT_10__TAG = "DSTELA50__AT_10";

  public static final String STSELEC__AT_11__TAG = "STSELEC__AT_11";

  public static final String DSTELA15__AT_11__TAG = "DSTELA15__AT_11";

  public static final String DSTELA50__AT_11__TAG = "DSTELA50__AT_11";

  public static final String STSELEC__AT_12__TAG = "STSELEC__AT_12";

  public static final String DSTELA15__AT_12__TAG = "DSTELA15__AT_12";

  public static final String DSTELA50__AT_12__TAG = "DSTELA50__AT_12";

  public static final String STSELEC__AT_13__TAG = "STSELEC__AT_13";

  public static final String DSTELA15__AT_13__TAG = "DSTELA15__AT_13";

  public static final String DSTELA50__AT_13__TAG = "DSTELA50__AT_13";

  public static final String AT_SIGN__TAG = "AT_SIGN";
  private LINCStringField AT_SIGN;

  public static final String DSAMBIENTE__TAG = "DSAMBIENTE";
  private LINCStringField DSAMBIENTE;

  public static final String NMMUNAB1__TAG = "NMMUNAB1";
  private LINCStringField NMMUNAB1;

  public static final String DSTELA65__TAG = "DSTELA65";
  private LINCStringField DSTELA65;

  public static final String DSTELA65A__TAG = "DSTELA65A";
  private LINCStringField DSTELA65A;


  public CMEZXIspecModel(ApplicationState appState) {
    super("CMEZX", 3, 14);
    setAppState(appState);
    _TOP_LINE_ = makeLINCStringField(_TOP_LINE___TAG, 19, 1, 'A', true);
    setLockSign(_TOP_LINE___TAG, true);
    ACTMTH = makeLINCNumericField(ACTMTH__TAG, 4, 2, 'N', true, 0, LINCNumericField.NUL, 0);
    setLockSign(ACTMTH__TAG, true);
    setNotEntered(ACTMTH__TAG, true);
    DSPROXTEL = makeLINCStringField(DSPROXTEL__TAG, 5, 3, 'A', false);
    setDataDisplay(DSPROXTEL__TAG, "Proxima Tela");
    addToArray(makeLINCStringField(STSELEC__AT_00__TAG, 1, 4, 'A', false), 0, 0);
    setDataDisplay(STSELEC__AT_00__TAG, "Seleciona Opcao");
    addToArray(makeLINCStringField(DSTELA15__AT_00__TAG, 15, 5, 'A', true), 1, 0);
    setDataDisplay(DSTELA15__AT_00__TAG, "DSTELA15");
    addToArray(makeLINCStringField(DSTELA50__AT_00__TAG, 50, 6, 'A', true), 2, 0);
    setDataDisplay(DSTELA50__AT_00__TAG, "Descricao");
    addToArray(makeLINCStringField(STSELEC__AT_01__TAG, 1, 7, 'A', false), 0, 1);
    setDataDisplay(STSELEC__AT_01__TAG, "Seleciona Opcao");
    addToArray(makeLINCStringField(DSTELA15__AT_01__TAG, 15, 8, 'A', true), 1, 1);
    setDataDisplay(DSTELA15__AT_01__TAG, "DSTELA15");
    addToArray(makeLINCStringField(DSTELA50__AT_01__TAG, 50, 9, 'A', true), 2, 1);
    setDataDisplay(DSTELA50__AT_01__TAG, "Descricao");
    addToArray(makeLINCStringField(STSELEC__AT_02__TAG, 1, 10, 'A', false), 0, 2);
    setDataDisplay(STSELEC__AT_02__TAG, "Seleciona Opcao");
    addToArray(makeLINCStringField(DSTELA15__AT_02__TAG, 15, 11, 'A', true), 1, 2);
    setDataDisplay(DSTELA15__AT_02__TAG, "DSTELA15");
    addToArray(makeLINCStringField(DSTELA50__AT_02__TAG, 50, 12, 'A', true), 2, 2);
    setDataDisplay(DSTELA50__AT_02__TAG, "Descricao");
    addToArray(makeLINCStringField(STSELEC__AT_03__TAG, 1, 13, 'A', false), 0, 3);
    setDataDisplay(STSELEC__AT_03__TAG, "Seleciona Opcao");
    addToArray(makeLINCStringField(DSTELA15__AT_03__TAG, 15, 14, 'A', true), 1, 3);
    setDataDisplay(DSTELA15__AT_03__TAG, "DSTELA15");
    addToArray(makeLINCStringField(DSTELA50__AT_03__TAG, 50, 15, 'A', true), 2, 3);
    setDataDisplay(DSTELA50__AT_03__TAG, "Descricao");
    addToArray(makeLINCStringField(STSELEC__AT_04__TAG, 1, 16, 'A', false), 0, 4);
    setDataDisplay(STSELEC__AT_04__TAG, "Seleciona Opcao");
    addToArray(makeLINCStringField(DSTELA15__AT_04__TAG, 15, 17, 'A', true), 1, 4);
    setDataDisplay(DSTELA15__AT_04__TAG, "DSTELA15");
    addToArray(makeLINCStringField(DSTELA50__AT_04__TAG, 50, 18, 'A', true), 2, 4);
    setDataDisplay(DSTELA50__AT_04__TAG, "Descricao");
    addToArray(makeLINCStringField(STSELEC__AT_05__TAG, 1, 19, 'A', false), 0, 5);
    setDataDisplay(STSELEC__AT_05__TAG, "Seleciona Opcao");
    addToArray(makeLINCStringField(DSTELA15__AT_05__TAG, 15, 20, 'A', true), 1, 5);
    setDataDisplay(DSTELA15__AT_05__TAG, "DSTELA15");
    addToArray(makeLINCStringField(DSTELA50__AT_05__TAG, 50, 21, 'A', true), 2, 5);
    setDataDisplay(DSTELA50__AT_05__TAG, "Descricao");
    addToArray(makeLINCStringField(STSELEC__AT_06__TAG, 1, 22, 'A', false), 0, 6);
    setDataDisplay(STSELEC__AT_06__TAG, "Seleciona Opcao");
    addToArray(makeLINCStringField(DSTELA15__AT_06__TAG, 15, 23, 'A', true), 1, 6);
    setDataDisplay(DSTELA15__AT_06__TAG, "DSTELA15");
    addToArray(makeLINCStringField(DSTELA50__AT_06__TAG, 50, 24, 'A', true), 2, 6);
    setDataDisplay(DSTELA50__AT_06__TAG, "Descricao");
    addToArray(makeLINCStringField(STSELEC__AT_07__TAG, 1, 25, 'A', false), 0, 7);
    setDataDisplay(STSELEC__AT_07__TAG, "Seleciona Opcao");
    addToArray(makeLINCStringField(DSTELA15__AT_07__TAG, 15, 26, 'A', true), 1, 7);
    setDataDisplay(DSTELA15__AT_07__TAG, "DSTELA15");
    addToArray(makeLINCStringField(DSTELA50__AT_07__TAG, 50, 27, 'A', true), 2, 7);
    setDataDisplay(DSTELA50__AT_07__TAG, "Descricao");
    addToArray(makeLINCStringField(STSELEC__AT_08__TAG, 1, 28, 'A', false), 0, 8);
    setDataDisplay(STSELEC__AT_08__TAG, "Seleciona Opcao");
    addToArray(makeLINCStringField(DSTELA15__AT_08__TAG, 15, 29, 'A', true), 1, 8);
    setDataDisplay(DSTELA15__AT_08__TAG, "DSTELA15");
    addToArray(makeLINCStringField(DSTELA50__AT_08__TAG, 50, 30, 'A', true), 2, 8);
    setDataDisplay(DSTELA50__AT_08__TAG, "Descricao");
    addToArray(makeLINCStringField(STSELEC__AT_09__TAG, 1, 31, 'A', false), 0, 9);
    setDataDisplay(STSELEC__AT_09__TAG, "Seleciona Opcao");
    addToArray(makeLINCStringField(DSTELA15__AT_09__TAG, 15, 32, 'A', true), 1, 9);
    setDataDisplay(DSTELA15__AT_09__TAG, "DSTELA15");
    addToArray(makeLINCStringField(DSTELA50__AT_09__TAG, 50, 33, 'A', true), 2, 9);
    setDataDisplay(DSTELA50__AT_09__TAG, "Descricao");
    addToArray(makeLINCStringField(STSELEC__AT_10__TAG, 1, 34, 'A', false), 0, 10);
    setDataDisplay(STSELEC__AT_10__TAG, "Seleciona Opcao");
    addToArray(makeLINCStringField(DSTELA15__AT_10__TAG, 15, 35, 'A', true), 1, 10);
    setDataDisplay(DSTELA15__AT_10__TAG, "DSTELA15");
    addToArray(makeLINCStringField(DSTELA50__AT_10__TAG, 50, 36, 'A', true), 2, 10);
    setDataDisplay(DSTELA50__AT_10__TAG, "Descricao");
    addToArray(makeLINCStringField(STSELEC__AT_11__TAG, 1, 37, 'A', false), 0, 11);
    setDataDisplay(STSELEC__AT_11__TAG, "Seleciona Opcao");
    addToArray(makeLINCStringField(DSTELA15__AT_11__TAG, 15, 38, 'A', true), 1, 11);
    setDataDisplay(DSTELA15__AT_11__TAG, "DSTELA15");
    addToArray(makeLINCStringField(DSTELA50__AT_11__TAG, 50, 39, 'A', true), 2, 11);
    setDataDisplay(DSTELA50__AT_11__TAG, "Descricao");
    addToArray(makeLINCStringField(STSELEC__AT_12__TAG, 1, 40, 'A', false), 0, 12);
    setDataDisplay(STSELEC__AT_12__TAG, "Seleciona Opcao");
    addToArray(makeLINCStringField(DSTELA15__AT_12__TAG, 15, 41, 'A', true), 1, 12);
    setDataDisplay(DSTELA15__AT_12__TAG, "DSTELA15");
    addToArray(makeLINCStringField(DSTELA50__AT_12__TAG, 50, 42, 'A', true), 2, 12);
    setDataDisplay(DSTELA50__AT_12__TAG, "Descricao");
    addToArray(makeLINCStringField(STSELEC__AT_13__TAG, 1, 43, 'A', false), 0, 13);
    setDataDisplay(STSELEC__AT_13__TAG, "Seleciona Opcao");
    addToArray(makeLINCStringField(DSTELA15__AT_13__TAG, 15, 44, 'A', true), 1, 13);
    setDataDisplay(DSTELA15__AT_13__TAG, "DSTELA15");
    addToArray(makeLINCStringField(DSTELA50__AT_13__TAG, 50, 45, 'A', true), 2, 13);
    setDataDisplay(DSTELA50__AT_13__TAG, "Descricao");
    AT_SIGN = makeLINCStringField(AT_SIGN__TAG, 1, 46, 'A', false);
    DSAMBIENTE = makeLINCStringField(DSAMBIENTE__TAG, 11, 47, 'A', true);
    setDataDisplay(DSAMBIENTE__TAG, "DS AMBIENTE");
    NMMUNAB1 = makeLINCStringField(NMMUNAB1__TAG, 20, 48, 'A', true);
    setDataDisplay(NMMUNAB1__TAG, "Nome Munic Abrev");
    DSTELA65 = makeLINCStringField(DSTELA65__TAG, 65, 49, 'A', true);
    setDataDisplay(DSTELA65__TAG, "Descricao");
    DSTELA65A = makeLINCStringField(DSTELA65A__TAG, 65, 50, 'A', true);
    setDataDisplay(DSTELA65A__TAG, "Descricao");
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
  public String getSTSELEC(int row) throws NoSuchFieldException {
    return arrayLookup(0, row).getValue();
  }

  public void setSTSELEC(int row, String newValue) throws NoSuchFieldException, LINCFieldException, PropertyVetoException {
    arrayLookup(0, row).setValue(newValue);
  }

  public String getDSTELA15(int row) throws NoSuchFieldException {
    return arrayLookup(1, row).getValue();
  }

  public String getDSTELA50(int row) throws NoSuchFieldException {
    return arrayLookup(2, row).getValue();
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
  public String getDSTELA65() {
    return DSTELA65.getValue();
  }
  public String getDSTELA65A() {
    return DSTELA65A.getValue();
  }
  public String getGenSourceFile() {
    return "";
  }
  public String getGenVersion() {
    return "3.3.3220";
  }
  public String getGenDate() {
    return "Sat, Mar 27 10:34:15 GMT-03:00 2010";
  }
  public String getGUID() {
    return "707BE71E1B6A0B764830CEC209CFA9AB";
  }

}
