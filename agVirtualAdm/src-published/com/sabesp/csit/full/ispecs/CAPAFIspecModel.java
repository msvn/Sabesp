package com.sabesp.csit.full.ispecs;

import java.beans.PropertyVetoException;

import com.unisys.jellybeans.IspecModel;
import com.unisys.jellybeans.LINCField;
import com.unisys.jellybeans.LINCStringField;
import com.unisys.jellybeans.LINCNumericField;
import com.unisys.jellybeans.LINCFieldException;
import com.unisys.jellybeans.NoSuchFieldException;
import com.unisys.jellybeans.ApplicationState;

public class CAPAFIspecModel extends IspecModel {

  public static final String _TOP_LINE___TAG = "_TOP_LINE_";
  private LINCStringField _TOP_LINE_;

  public static final String ACTMTH__TAG = "ACTMTH";
  private LINCNumericField ACTMTH;

  public static final String DSPROXTEL__TAG = "DSPROXTEL";
  private LINCStringField DSPROXTEL;

  public static final String CDCTA__AT_00__TAG = "CDCTA__AT_00";

  public static final String VLCONTAG__AT_00__TAG = "VLCONTAG__AT_00";

  public static final String AAMMREFER__AT_00__TAG = "AAMMREFER__AT_00";

  public static final String DTTELA8A__AT_00__TAG = "DTTELA8A__AT_00";

  public static final String STACATAM__AT_00__TAG = "STACATAM__AT_00";

  public static final String CDDEVOLU__AT_00__TAG = "CDDEVOLU__AT_00";

  public static final String DSTELA8__AT_00__TAG = "DSTELA8__AT_00";

  public static final String STSELEC__AT_00__TAG = "STSELEC__AT_00";

  public static final String CDMOTVIA__AT_00__TAG = "CDMOTVIA__AT_00";

  public static final String CDCTA__AT_01__TAG = "CDCTA__AT_01";

  public static final String VLCONTAG__AT_01__TAG = "VLCONTAG__AT_01";

  public static final String AAMMREFER__AT_01__TAG = "AAMMREFER__AT_01";

  public static final String DTTELA8A__AT_01__TAG = "DTTELA8A__AT_01";

  public static final String STACATAM__AT_01__TAG = "STACATAM__AT_01";

  public static final String CDDEVOLU__AT_01__TAG = "CDDEVOLU__AT_01";

  public static final String DSTELA8__AT_01__TAG = "DSTELA8__AT_01";

  public static final String STSELEC__AT_01__TAG = "STSELEC__AT_01";

  public static final String CDMOTVIA__AT_01__TAG = "CDMOTVIA__AT_01";

  public static final String CDCTA__AT_02__TAG = "CDCTA__AT_02";

  public static final String VLCONTAG__AT_02__TAG = "VLCONTAG__AT_02";

  public static final String AAMMREFER__AT_02__TAG = "AAMMREFER__AT_02";

  public static final String DTTELA8A__AT_02__TAG = "DTTELA8A__AT_02";

  public static final String STACATAM__AT_02__TAG = "STACATAM__AT_02";

  public static final String CDDEVOLU__AT_02__TAG = "CDDEVOLU__AT_02";

  public static final String DSTELA8__AT_02__TAG = "DSTELA8__AT_02";

  public static final String STSELEC__AT_02__TAG = "STSELEC__AT_02";

  public static final String CDMOTVIA__AT_02__TAG = "CDMOTVIA__AT_02";

  public static final String CDCTA__AT_03__TAG = "CDCTA__AT_03";

  public static final String VLCONTAG__AT_03__TAG = "VLCONTAG__AT_03";

  public static final String AAMMREFER__AT_03__TAG = "AAMMREFER__AT_03";

  public static final String DTTELA8A__AT_03__TAG = "DTTELA8A__AT_03";

  public static final String STACATAM__AT_03__TAG = "STACATAM__AT_03";

  public static final String CDDEVOLU__AT_03__TAG = "CDDEVOLU__AT_03";

  public static final String DSTELA8__AT_03__TAG = "DSTELA8__AT_03";

  public static final String STSELEC__AT_03__TAG = "STSELEC__AT_03";

  public static final String CDMOTVIA__AT_03__TAG = "CDMOTVIA__AT_03";

  public static final String CDCTA__AT_04__TAG = "CDCTA__AT_04";

  public static final String VLCONTAG__AT_04__TAG = "VLCONTAG__AT_04";

  public static final String AAMMREFER__AT_04__TAG = "AAMMREFER__AT_04";

  public static final String DTTELA8A__AT_04__TAG = "DTTELA8A__AT_04";

  public static final String STACATAM__AT_04__TAG = "STACATAM__AT_04";

  public static final String CDDEVOLU__AT_04__TAG = "CDDEVOLU__AT_04";

  public static final String DSTELA8__AT_04__TAG = "DSTELA8__AT_04";

  public static final String STSELEC__AT_04__TAG = "STSELEC__AT_04";

  public static final String CDMOTVIA__AT_04__TAG = "CDMOTVIA__AT_04";

  public static final String STOK__TAG = "STOK";
  private LINCStringField STOK;

  public static final String AT_SIGN__TAG = "AT_SIGN";
  private LINCStringField AT_SIGN;

  public static final String DSAMBIENTE__TAG = "DSAMBIENTE";
  private LINCStringField DSAMBIENTE;

  public static final String NMMUNAB1__TAG = "NMMUNAB1";
  private LINCStringField NMMUNAB1;

  public static final String NRRGILIG__TAG = "NRRGILIG";
  private LINCNumericField NRRGILIG;

  public static final String DSTELA65__TAG = "DSTELA65";
  private LINCStringField DSTELA65;

  public static final String NMBAIRRO2__TAG = "NMBAIRRO2";
  private LINCStringField NMBAIRRO2;

  public static final String QTCONTA__TAG = "QTCONTA";
  private LINCNumericField QTCONTA;

  public static final String QTCONTA1__TAG = "QTCONTA1";
  private LINCNumericField QTCONTA1;

  public static final String DSTELA40__TAG = "DSTELA40";
  private LINCStringField DSTELA40;

  public static final String DSCONFIRM__TAG = "DSCONFIRM";
  private LINCStringField DSCONFIRM;


  public CAPAFIspecModel(ApplicationState appState) {
    super("CAPAF", 9, 5);
    setAppState(appState);
    _TOP_LINE_ = makeLINCStringField(_TOP_LINE___TAG, 19, 1, 'A', true);
    setLockSign(_TOP_LINE___TAG, true);
    ACTMTH = makeLINCNumericField(ACTMTH__TAG, 4, 2, 'N', true, 0, LINCNumericField.NUL, 0);
    setLockSign(ACTMTH__TAG, true);
    setNotEntered(ACTMTH__TAG, true);
    DSPROXTEL = makeLINCStringField(DSPROXTEL__TAG, 5, 3, 'A', false);
    setDataDisplay(DSPROXTEL__TAG, "Proxima Tela");
    addToArray(makeLINCNumericField(CDCTA__AT_00__TAG, 13, 4, 'N', true, 0, LINCNumericField.NUL, 1), 0, 0);
    setDataDisplay(CDCTA__AT_00__TAG, "Codigo Conta");
    setNotEntered(CDCTA__AT_00__TAG, true);
    addToArray(makeLINCNumericField(VLCONTAG__AT_00__TAG, 14, 5, 'N', true, 2, ',', 1), 1, 0);
    setDataDisplay(VLCONTAG__AT_00__TAG, "Valor Conta");
    addToArray(makeLINCNumericField(AAMMREFER__AT_00__TAG, 4, 6, 'N', true, 0, LINCNumericField.NUL, 0), 2, 0);
    setDataDisplay(AAMMREFER__AT_00__TAG, "Mes Referencia");
    setNotEntered(AAMMREFER__AT_00__TAG, true);
    addToArray(makeLINCStringField(DTTELA8A__AT_00__TAG, 8, 7, 'A', true), 3, 0);
    setDataDisplay(DTTELA8A__AT_00__TAG, "Data");
    addToArray(makeLINCStringField(STACATAM__AT_00__TAG, 1, 8, 'A', true), 4, 0);
    setDataDisplay(STACATAM__AT_00__TAG, "St Acatam Conta");
    addToArray(makeLINCNumericField(CDDEVOLU__AT_00__TAG, 2, 9, 'N', true, 0, LINCNumericField.NUL, 1), 5, 0);
    setDataDisplay(CDDEVOLU__AT_00__TAG, "Cd Mot Devolucao");
    addToArray(makeLINCStringField(DSTELA8__AT_00__TAG, 8, 10, 'A', false), 6, 0);
    addToArray(makeLINCStringField(STSELEC__AT_00__TAG, 1, 11, 'A', false), 7, 0);
    setDataDisplay(STSELEC__AT_00__TAG, "Seleciona Opcao");
    addToArray(makeLINCNumericField(CDMOTVIA__AT_00__TAG, 1, 12, 'N', false, 0, LINCNumericField.NUL, 1), 8, 0);
    setDataDisplay(CDMOTVIA__AT_00__TAG, "Cd Motivo 2a Via");
    addToArray(makeLINCNumericField(CDCTA__AT_01__TAG, 13, 13, 'N', true, 0, LINCNumericField.NUL, 1), 0, 1);
    setDataDisplay(CDCTA__AT_01__TAG, "Codigo Conta");
    setNotEntered(CDCTA__AT_01__TAG, true);
    addToArray(makeLINCNumericField(VLCONTAG__AT_01__TAG, 14, 14, 'N', true, 2, ',', 1), 1, 1);
    setDataDisplay(VLCONTAG__AT_01__TAG, "Valor Conta");
    addToArray(makeLINCNumericField(AAMMREFER__AT_01__TAG, 4, 15, 'N', true, 0, LINCNumericField.NUL, 0), 2, 1);
    setDataDisplay(AAMMREFER__AT_01__TAG, "Mes Referencia");
    setNotEntered(AAMMREFER__AT_01__TAG, true);
    addToArray(makeLINCStringField(DTTELA8A__AT_01__TAG, 8, 16, 'A', true), 3, 1);
    setDataDisplay(DTTELA8A__AT_01__TAG, "Data");
    addToArray(makeLINCStringField(STACATAM__AT_01__TAG, 1, 17, 'A', true), 4, 1);
    setDataDisplay(STACATAM__AT_01__TAG, "St Acatam Conta");
    addToArray(makeLINCNumericField(CDDEVOLU__AT_01__TAG, 2, 18, 'N', true, 0, LINCNumericField.NUL, 1), 5, 1);
    setDataDisplay(CDDEVOLU__AT_01__TAG, "Cd Mot Devolucao");
    addToArray(makeLINCStringField(DSTELA8__AT_01__TAG, 8, 19, 'A', false), 6, 1);
    addToArray(makeLINCStringField(STSELEC__AT_01__TAG, 1, 20, 'A', false), 7, 1);
    setDataDisplay(STSELEC__AT_01__TAG, "Seleciona Opcao");
    addToArray(makeLINCNumericField(CDMOTVIA__AT_01__TAG, 1, 21, 'N', false, 0, LINCNumericField.NUL, 1), 8, 1);
    setDataDisplay(CDMOTVIA__AT_01__TAG, "Cd Motivo 2a Via");
    addToArray(makeLINCNumericField(CDCTA__AT_02__TAG, 13, 22, 'N', true, 0, LINCNumericField.NUL, 1), 0, 2);
    setDataDisplay(CDCTA__AT_02__TAG, "Codigo Conta");
    setNotEntered(CDCTA__AT_02__TAG, true);
    addToArray(makeLINCNumericField(VLCONTAG__AT_02__TAG, 14, 23, 'N', true, 2, ',', 1), 1, 2);
    setDataDisplay(VLCONTAG__AT_02__TAG, "Valor Conta");
    addToArray(makeLINCNumericField(AAMMREFER__AT_02__TAG, 4, 24, 'N', true, 0, LINCNumericField.NUL, 0), 2, 2);
    setDataDisplay(AAMMREFER__AT_02__TAG, "Mes Referencia");
    setNotEntered(AAMMREFER__AT_02__TAG, true);
    addToArray(makeLINCStringField(DTTELA8A__AT_02__TAG, 8, 25, 'A', true), 3, 2);
    setDataDisplay(DTTELA8A__AT_02__TAG, "Data");
    addToArray(makeLINCStringField(STACATAM__AT_02__TAG, 1, 26, 'A', true), 4, 2);
    setDataDisplay(STACATAM__AT_02__TAG, "St Acatam Conta");
    addToArray(makeLINCNumericField(CDDEVOLU__AT_02__TAG, 2, 27, 'N', true, 0, LINCNumericField.NUL, 1), 5, 2);
    setDataDisplay(CDDEVOLU__AT_02__TAG, "Cd Mot Devolucao");
    addToArray(makeLINCStringField(DSTELA8__AT_02__TAG, 8, 28, 'A', false), 6, 2);
    addToArray(makeLINCStringField(STSELEC__AT_02__TAG, 1, 29, 'A', false), 7, 2);
    setDataDisplay(STSELEC__AT_02__TAG, "Seleciona Opcao");
    addToArray(makeLINCNumericField(CDMOTVIA__AT_02__TAG, 1, 30, 'N', false, 0, LINCNumericField.NUL, 1), 8, 2);
    setDataDisplay(CDMOTVIA__AT_02__TAG, "Cd Motivo 2a Via");
    addToArray(makeLINCNumericField(CDCTA__AT_03__TAG, 13, 31, 'N', true, 0, LINCNumericField.NUL, 1), 0, 3);
    setDataDisplay(CDCTA__AT_03__TAG, "Codigo Conta");
    setNotEntered(CDCTA__AT_03__TAG, true);
    addToArray(makeLINCNumericField(VLCONTAG__AT_03__TAG, 14, 32, 'N', true, 2, ',', 1), 1, 3);
    setDataDisplay(VLCONTAG__AT_03__TAG, "Valor Conta");
    addToArray(makeLINCNumericField(AAMMREFER__AT_03__TAG, 4, 33, 'N', true, 0, LINCNumericField.NUL, 0), 2, 3);
    setDataDisplay(AAMMREFER__AT_03__TAG, "Mes Referencia");
    setNotEntered(AAMMREFER__AT_03__TAG, true);
    addToArray(makeLINCStringField(DTTELA8A__AT_03__TAG, 8, 34, 'A', true), 3, 3);
    setDataDisplay(DTTELA8A__AT_03__TAG, "Data");
    addToArray(makeLINCStringField(STACATAM__AT_03__TAG, 1, 35, 'A', true), 4, 3);
    setDataDisplay(STACATAM__AT_03__TAG, "St Acatam Conta");
    addToArray(makeLINCNumericField(CDDEVOLU__AT_03__TAG, 2, 36, 'N', true, 0, LINCNumericField.NUL, 1), 5, 3);
    setDataDisplay(CDDEVOLU__AT_03__TAG, "Cd Mot Devolucao");
    addToArray(makeLINCStringField(DSTELA8__AT_03__TAG, 8, 37, 'A', false), 6, 3);
    addToArray(makeLINCStringField(STSELEC__AT_03__TAG, 1, 38, 'A', false), 7, 3);
    setDataDisplay(STSELEC__AT_03__TAG, "Seleciona Opcao");
    addToArray(makeLINCNumericField(CDMOTVIA__AT_03__TAG, 1, 39, 'N', false, 0, LINCNumericField.NUL, 1), 8, 3);
    setDataDisplay(CDMOTVIA__AT_03__TAG, "Cd Motivo 2a Via");
    addToArray(makeLINCNumericField(CDCTA__AT_04__TAG, 13, 40, 'N', true, 0, LINCNumericField.NUL, 1), 0, 4);
    setDataDisplay(CDCTA__AT_04__TAG, "Codigo Conta");
    setNotEntered(CDCTA__AT_04__TAG, true);
    addToArray(makeLINCNumericField(VLCONTAG__AT_04__TAG, 14, 41, 'N', true, 2, ',', 1), 1, 4);
    setDataDisplay(VLCONTAG__AT_04__TAG, "Valor Conta");
    addToArray(makeLINCNumericField(AAMMREFER__AT_04__TAG, 4, 42, 'N', true, 0, LINCNumericField.NUL, 0), 2, 4);
    setDataDisplay(AAMMREFER__AT_04__TAG, "Mes Referencia");
    setNotEntered(AAMMREFER__AT_04__TAG, true);
    addToArray(makeLINCStringField(DTTELA8A__AT_04__TAG, 8, 43, 'A', true), 3, 4);
    setDataDisplay(DTTELA8A__AT_04__TAG, "Data");
    addToArray(makeLINCStringField(STACATAM__AT_04__TAG, 1, 44, 'A', true), 4, 4);
    setDataDisplay(STACATAM__AT_04__TAG, "St Acatam Conta");
    addToArray(makeLINCNumericField(CDDEVOLU__AT_04__TAG, 2, 45, 'N', true, 0, LINCNumericField.NUL, 1), 5, 4);
    setDataDisplay(CDDEVOLU__AT_04__TAG, "Cd Mot Devolucao");
    addToArray(makeLINCStringField(DSTELA8__AT_04__TAG, 8, 46, 'A', false), 6, 4);
    addToArray(makeLINCStringField(STSELEC__AT_04__TAG, 1, 47, 'A', false), 7, 4);
    setDataDisplay(STSELEC__AT_04__TAG, "Seleciona Opcao");
    addToArray(makeLINCNumericField(CDMOTVIA__AT_04__TAG, 1, 48, 'N', false, 0, LINCNumericField.NUL, 1), 8, 4);
    setDataDisplay(CDMOTVIA__AT_04__TAG, "Cd Motivo 2a Via");
    STOK = makeLINCStringField(STOK__TAG, 1, 49, 'A', false);
    setDataDisplay(STOK__TAG, "StatConfirmacao");
    AT_SIGN = makeLINCStringField(AT_SIGN__TAG, 1, 50, 'A', false);
    DSAMBIENTE = makeLINCStringField(DSAMBIENTE__TAG, 11, 51, 'A', true);
    setDataDisplay(DSAMBIENTE__TAG, "DS AMBIENTE");
    NMMUNAB1 = makeLINCStringField(NMMUNAB1__TAG, 20, 52, 'A', true);
    setDataDisplay(NMMUNAB1__TAG, "Nome Munic Abrev");
    NRRGILIG = makeLINCNumericField(NRRGILIG__TAG, 10, 53, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(NRRGILIG__TAG, "Numero RGI");
    DSTELA65 = makeLINCStringField(DSTELA65__TAG, 65, 54, 'A', true);
    setDataDisplay(DSTELA65__TAG, "Descricao");
    NMBAIRRO2 = makeLINCStringField(NMBAIRRO2__TAG, 20, 55, 'A', true);
    setDataDisplay(NMBAIRRO2__TAG, "Nome do Bairro");
    QTCONTA = makeLINCNumericField(QTCONTA__TAG, 5, 56, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTCONTA__TAG, "Qtde de Contas");
    QTCONTA1 = makeLINCNumericField(QTCONTA1__TAG, 5, 57, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTCONTA1__TAG, "Qtde Contas");
    DSTELA40 = makeLINCStringField(DSTELA40__TAG, 40, 58, 'A', true);
    setDataDisplay(DSTELA40__TAG, "Descricao");
    DSCONFIRM = makeLINCStringField(DSCONFIRM__TAG, 14, 59, 'A', true);
    setDataDisplay(DSCONFIRM__TAG, "Confirmacao");
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
  public String getCDCTA(int row) throws NoSuchFieldException {
    return arrayLookup(0, row).getValue();
  }

  public String getVLCONTAG(int row) throws NoSuchFieldException {
    return arrayLookup(1, row).getValue();
  }

  public String getAAMMREFER(int row) throws NoSuchFieldException {
    return arrayLookup(2, row).getValue();
  }

  public String getDTTELA8A(int row) throws NoSuchFieldException {
    return arrayLookup(3, row).getValue();
  }

  public String getSTACATAM(int row) throws NoSuchFieldException {
    return arrayLookup(4, row).getValue();
  }

  public String getCDDEVOLU(int row) throws NoSuchFieldException {
    return arrayLookup(5, row).getValue();
  }

  public String getDSTELA8(int row) throws NoSuchFieldException {
    return arrayLookup(6, row).getValue();
  }

  public void setDSTELA8(int row, String newValue) throws NoSuchFieldException, LINCFieldException, PropertyVetoException {
    arrayLookup(6, row).setValue(newValue);
  }

  public String getSTSELEC(int row) throws NoSuchFieldException {
    return arrayLookup(7, row).getValue();
  }

  public void setSTSELEC(int row, String newValue) throws NoSuchFieldException, LINCFieldException, PropertyVetoException {
    arrayLookup(7, row).setValue(newValue);
  }

  public String getCDMOTVIA(int row) throws NoSuchFieldException {
    return arrayLookup(8, row).getValue();
  }

  public void setCDMOTVIA(int row, String newValue) throws NoSuchFieldException, LINCFieldException, PropertyVetoException {
    arrayLookup(8, row).setValue(newValue);
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
  public String getNRRGILIG() {
    return NRRGILIG.getValue();
  }
  public String getDSTELA65() {
    return DSTELA65.getValue();
  }
  public String getNMBAIRRO2() {
    return NMBAIRRO2.getValue();
  }
  public String getQTCONTA() {
    return QTCONTA.getValue();
  }
  public String getQTCONTA1() {
    return QTCONTA1.getValue();
  }
  public String getDSTELA40() {
    return DSTELA40.getValue();
  }
  public String getDSCONFIRM() {
    return DSCONFIRM.getValue();
  }
  public String getGenSourceFile() {
    return "";
  }
  public String getGenVersion() {
    return "3.3.3220";
  }
  public String getGenDate() {
    return "Sat, Mar 27 10:09:36 GMT-03:00 2010";
  }
  public String getGUID() {
    return "53C57AAB8C0FB2884C0ABD25C2EF53F9";
	  //return "707BE71E1B6A0B764830CEC209CFA9AB";
	  
  }

}
