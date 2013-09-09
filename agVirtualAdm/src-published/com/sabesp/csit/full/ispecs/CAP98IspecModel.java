package com.sabesp.csit.full.ispecs;

import java.beans.PropertyVetoException;

import com.unisys.jellybeans.ApplicationState;
import com.unisys.jellybeans.IspecModel;
import com.unisys.jellybeans.LINCFieldException;
import com.unisys.jellybeans.LINCNumericField;
import com.unisys.jellybeans.LINCStringField;

public class CAP98IspecModel extends IspecModel {

  public static final String _TOP_LINE___TAG = "_TOP_LINE_";
  private LINCStringField _TOP_LINE_;

  public static final String ACTMTH__TAG = "ACTMTH";
  private LINCNumericField ACTMTH;

  public static final String DSPROXTEL__TAG = "DSPROXTEL";
  private LINCStringField DSPROXTEL;

  public static final String NRRGIPROX__TAG = "NRRGIPROX";
  private LINCNumericField NRRGIPROX;

  public static final String AMREFPROX__TAG = "AMREFPROX";
  private LINCNumericField AMREFPROX;

  public static final String NRTELA5__TAG = "NRTELA5";
  private LINCNumericField NRTELA5;

  public static final String NRRGILIG__TAG = "NRRGILIG";
  private LINCNumericField NRRGILIG;

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

  public static final String STOK__TAG = "STOK";
  private LINCStringField STOK;

  public static final String STPRENAU__TAG = "STPRENAU";
  private LINCStringField STPRENAU;

  public static final String AT_SIGN__TAG = "AT_SIGN";
  private LINCStringField AT_SIGN;

  public static final String NMMUNICIP__TAG = "NMMUNICIP";
  private LINCStringField NMMUNICIP;

  public static final String DSAMBIENTE__TAG = "DSAMBIENTE";
  private LINCStringField DSAMBIENTE;

  public static final String NMMUNAB1__TAG = "NMMUNAB1";
  private LINCStringField NMMUNAB1;

  public static final String DSTELA30__TAG = "DSTELA30";
  private LINCStringField DSTELA30;

  public static final String SNFUNDAMEN__TAG = "SNFUNDAMEN";
  private LINCStringField SNFUNDAMEN;

  public static final String DSTELA35A__TAG = "DSTELA35A";
  private LINCStringField DSTELA35A;

  public static final String CDUNIDCO1__TAG = "CDUNIDCO1";
  private LINCNumericField CDUNIDCO1;

  public static final String CDLOGRADR__TAG = "CDLOGRADR";
  private LINCNumericField CDLOGRADR;

  public static final String QTCONSUMO__TAG = "QTCONSUMO";
  private LINCNumericField QTCONSUMO;

  public static final String VLLEIATU__TAG = "VLLEIATU";
  private LINCNumericField VLLEIATU;

  public static final String DTGERAL__TAG = "DTGERAL";
  private LINCNumericField DTGERAL;

  public static final String DTINI__TAG = "DTINI";
  private LINCNumericField DTINI;

  public static final String DSMES01__TAG = "DSMES01";
  private LINCStringField DSMES01;

  public static final String QTCONS01__TAG = "QTCONS01";
  private LINCNumericField QTCONS01;

  public static final String TPTELA1A__TAG = "TPTELA1A";
  private LINCStringField TPTELA1A;

  public static final String DTLEITURA__TAG = "DTLEITURA";
  private LINCNumericField DTLEITURA;

  public static final String DTTELA1__TAG = "DTTELA1";
  private LINCNumericField DTTELA1;

  public static final String NRGRUPFAT__TAG = "NRGRUPFAT";
  private LINCNumericField NRGRUPFAT;

  public static final String NRCICLO__TAG = "NRCICLO";
  private LINCNumericField NRCICLO;

  public static final String DSMES02__TAG = "DSMES02";
  private LINCStringField DSMES02;

  public static final String QTCONS02__TAG = "QTCONS02";
  private LINCNumericField QTCONS02;

  public static final String TPTELA1B__TAG = "TPTELA1B";
  private LINCStringField TPTELA1B;

  public static final String CDLEITURA__TAG = "CDLEITURA";
  private LINCNumericField CDLEITURA;

  public static final String DTSUPRESS__TAG = "DTSUPRESS";
  private LINCNumericField DTSUPRESS;

  public static final String QTCREDITO__TAG = "QTCREDITO";
  private LINCNumericField QTCREDITO;

  public static final String DSMES03__TAG = "DSMES03";
  private LINCStringField DSMES03;

  public static final String QTCONS03__TAG = "QTCONS03";
  private LINCNumericField QTCONS03;

  public static final String TPTELA1C__TAG = "TPTELA1C";
  private LINCStringField TPTELA1C;

  public static final String DSINTERV__TAG = "DSINTERV";
  private LINCStringField DSINTERV;

  public static final String STAGUA__TAG = "STAGUA";
  private LINCNumericField STAGUA;

  public static final String STESGOTO__TAG = "STESGOTO";
  private LINCNumericField STESGOTO;

  public static final String QTCREDIT1__TAG = "QTCREDIT1";
  private LINCNumericField QTCREDIT1;

  public static final String DSMES04__TAG = "DSMES04";
  private LINCStringField DSMES04;

  public static final String QTCONS04__TAG = "QTCONS04";
  private LINCNumericField QTCONS04;

  public static final String TPTELA1D__TAG = "TPTELA1D";
  private LINCStringField TPTELA1D;

  public static final String VLLEIANT__TAG = "VLLEIANT";
  private LINCNumericField VLLEIANT;

  public static final String STLIGACAO__TAG = "STLIGACAO";
  private LINCStringField STLIGACAO;

  public static final String TPTL__TAG = "TPTL";
  private LINCStringField TPTL;

  public static final String TPFATSLEIT__TAG = "TPFATSLEIT";
  private LINCStringField TPFATSLEIT;

  public static final String DSTELA8A__TAG = "DSTELA8A";
  private LINCStringField DSTELA8A;

  public static final String DSMES05__TAG = "DSMES05";
  private LINCStringField DSMES05;

  public static final String QTCONS05__TAG = "QTCONS05";
  private LINCNumericField QTCONS05;

  public static final String TPTELA1E__TAG = "TPTELA1E";
  private LINCStringField TPTELA1E;

  public static final String DTGERAL1__TAG = "DTGERAL1";
  private LINCNumericField DTGERAL1;

  public static final String DSTELA7A__TAG = "DSTELA7A";
  private LINCStringField DSTELA7A;

  public static final String CDCL2__TAG = "CDCL2";
  private LINCNumericField CDCL2;

  public static final String DSREDUZ7__TAG = "DSREDUZ7";
  private LINCStringField DSREDUZ7;

  public static final String DSMES06__TAG = "DSMES06";
  private LINCStringField DSMES06;

  public static final String QTCONS06__TAG = "QTCONS06";
  private LINCNumericField QTCONS06;

  public static final String TPTELA1F__TAG = "TPTELA1F";
  private LINCStringField TPTELA1F;

  public static final String DSTELA17A__TAG = "DSTELA17A";
  private LINCStringField DSTELA17A;

  public static final String DSMES07__TAG = "DSMES07";
  private LINCStringField DSMES07;

  public static final String QTCONS07__TAG = "QTCONS07";
  private LINCNumericField QTCONS07;

  public static final String TPTELA1G__TAG = "TPTELA1G";
  private LINCStringField TPTELA1G;

  public static final String CDHIDRO__TAG = "CDHIDRO";
  private LINCStringField CDHIDRO;

  public static final String CDCPH__TAG = "CDCPH";
  private LINCNumericField CDCPH;

  public static final String QTRES__TAG = "QTRES";
  private LINCNumericField QTRES;

  public static final String QTIND__TAG = "QTIND";
  private LINCNumericField QTIND;

  public static final String CDBANCO__TAG = "CDBANCO";
  private LINCNumericField CDBANCO;

  public static final String CDAGENCIA__TAG = "CDAGENCIA";
  private LINCNumericField CDAGENCIA;

  public static final String DSMES08__TAG = "DSMES08";
  private LINCStringField DSMES08;

  public static final String QTCONS08__TAG = "QTCONS08";
  private LINCNumericField QTCONS08;

  public static final String TPTELA1H__TAG = "TPTELA1H";
  private LINCStringField TPTELA1H;

  public static final String CDCPH1__TAG = "CDCPH1";
  private LINCNumericField CDCPH1;

  public static final String CDDIAMETR__TAG = "CDDIAMETR";
  private LINCNumericField CDDIAMETR;

  public static final String QTCOM__TAG = "QTCOM";
  private LINCNumericField QTCOM;

  public static final String QTPUB__TAG = "QTPUB";
  private LINCNumericField QTPUB;

  public static final String NRCONTAC__TAG = "NRCONTAC";
  private LINCStringField NRCONTAC;

  public static final String DSMES09__TAG = "DSMES09";
  private LINCStringField DSMES09;

  public static final String QTCONS09__TAG = "QTCONS09";
  private LINCNumericField QTCONS09;

  public static final String TPTELA1I__TAG = "TPTELA1I";
  private LINCStringField TPTELA1I;

  public static final String DTRETIRA__TAG = "DTRETIRA";
  private LINCNumericField DTRETIRA;

  public static final String QTDIGHIDR__TAG = "QTDIGHIDR";
  private LINCNumericField QTDIGHIDR;

  public static final String CDORGPUB__TAG = "CDORGPUB";
  private LINCNumericField CDORGPUB;

  public static final String CDENTIDAD__TAG = "CDENTIDAD";
  private LINCNumericField CDENTIDAD;

  public static final String CDFACE__TAG = "CDFACE";
  private LINCStringField CDFACE;

  public static final String DSMES10__TAG = "DSMES10";
  private LINCStringField DSMES10;

  public static final String QTCONS10__TAG = "QTCONS10";
  private LINCNumericField QTCONS10;

  public static final String TPTELA1J__TAG = "TPTELA1J";
  private LINCStringField TPTELA1J;

  public static final String DTINST__TAG = "DTINST";
  private LINCNumericField DTINST;

  public static final String DSTELA4__TAG = "DSTELA4";
  private LINCStringField DSTELA4;

  public static final String CDMOTBENEF__TAG = "CDMOTBENEF";
  private LINCStringField CDMOTBENEF;

  public static final String DTTELA2__TAG = "DTTELA2";
  private LINCNumericField DTTELA2;

  public static final String DTTELA3__TAG = "DTTELA3";
  private LINCNumericField DTTELA3;

  public static final String DSMES11__TAG = "DSMES11";
  private LINCStringField DSMES11;

  public static final String QTCONS11__TAG = "QTCONS11";
  private LINCNumericField QTCONS11;

  public static final String TPTELA1L__TAG = "TPTELA1L";
  private LINCStringField TPTELA1L;

  public static final String DSTELA20__TAG = "DSTELA20";
  private LINCStringField DSTELA20;

  public static final String DDVENC__TAG = "DDVENC";
  private LINCNumericField DDVENC;

  public static final String DSTELA3__TAG = "DSTELA3";
  private LINCStringField DSTELA3;

  public static final String DSMES12__TAG = "DSMES12";
  private LINCStringField DSMES12;

  public static final String QTCONS12__TAG = "QTCONS12";
  private LINCNumericField QTCONS12;

  public static final String TPTELA1M__TAG = "TPTELA1M";
  private LINCStringField TPTELA1M;

  public static final String DSCAVALET__TAG = "DSCAVALET";
  private LINCStringField DSCAVALET;

  public static final String DSTELA15__TAG = "DSTELA15";
  private LINCStringField DSTELA15;

  public static final String DSTELA3A__TAG = "DSTELA3A";
  private LINCStringField DSTELA3A;

  public static final String CDPROJETO__TAG = "CDPROJETO";
  private LINCStringField CDPROJETO;

  public static final String DSTECNPRO__TAG = "DSTECNPRO";
  private LINCStringField DSTECNPRO;

  public static final String DSCONFIRM__TAG = "DSCONFIRM";
  private LINCStringField DSCONFIRM;


  public CAP98IspecModel(ApplicationState appState) {
    super("CAP98");
    setAppState(appState);
    _TOP_LINE_ = makeLINCStringField(_TOP_LINE___TAG, 19, 1, 'A', true);
    setLockSign(_TOP_LINE___TAG, true);
    ACTMTH = makeLINCNumericField(ACTMTH__TAG, 4, 2, 'N', true, 0, LINCNumericField.NUL, 0);
    setLockSign(ACTMTH__TAG, true);
    setNotEntered(ACTMTH__TAG, true);
    DSPROXTEL = makeLINCStringField(DSPROXTEL__TAG, 5, 3, 'A', false);
    setDataDisplay(DSPROXTEL__TAG, "Proxima Tela");
    NRRGIPROX = makeLINCNumericField(NRRGIPROX__TAG, 10, 4, 'N', false, 0, LINCNumericField.NUL, 1);
    setDataDisplay(NRRGIPROX__TAG, "RGI navegacao");
    AMREFPROX = makeLINCNumericField(AMREFPROX__TAG, 4, 5, 'N', false, 0, LINCNumericField.NUL, 1);
    setDataDisplay(AMREFPROX__TAG, "Mes ref.prox.cta");
    NRTELA5 = makeLINCNumericField(NRTELA5__TAG, 5, 6, 'N', false, 0, LINCNumericField.NUL, 1);
    setDataDisplay(NRTELA5__TAG, "Num so para Tela");
    NRRGILIG = makeLINCNumericField(NRRGILIG__TAG, 10, 7, 'N', false, 0, LINCNumericField.NUL, 1);
    setDataDisplay(NRRGILIG__TAG, "Numero RGI");
    DSTPLOGAB = makeLINCStringField(DSTPLOGAB__TAG, 4, 8, 'A', false);
    setDataDisplay(DSTPLOGAB__TAG, "Ds Tp Log Abrev");
    SGTITLOG = makeLINCStringField(SGTITLOG__TAG, 5, 9, 'A', false);
    setDataDisplay(SGTITLOG__TAG, "Titulo");
    SGPREPLOG = makeLINCStringField(SGPREPLOG__TAG, 3, 10, 'A', false);
    setDataDisplay(SGPREPLOG__TAG, "Preposicao");
    DSENDEREC = makeLINCStringField(DSENDEREC__TAG, 30, 11, 'A', false);
    setDataDisplay(DSENDEREC__TAG, "Endereco");
    NRENDEREC = makeLINCNumericField(NRENDEREC__TAG, 5, 12, 'N', false, 0, LINCNumericField.NUL, 1);
    setDataDisplay(NRENDEREC__TAG, "Nro Endereco");
    DSCOMPLEM = makeLINCStringField(DSCOMPLEM__TAG, 10, 13, 'A', false);
    setDataDisplay(DSCOMPLEM__TAG, "Complemento");
    NMBAIRRO = makeLINCStringField(NMBAIRRO__TAG, 20, 14, 'A', false);
    setDataDisplay(NMBAIRRO__TAG, "Bairro");
    STOK = makeLINCStringField(STOK__TAG, 1, 15, 'A', false);
    setDataDisplay(STOK__TAG, "CAMPO ENVIAR");
    STPRENAU = makeLINCStringField(STPRENAU__TAG, 1, 16, 'A', false);
    AT_SIGN = makeLINCStringField(AT_SIGN__TAG, 1, 17, 'A', false);
    NMMUNICIP = makeLINCStringField(NMMUNICIP__TAG, 30, 18, 'A', true);
    setDataDisplay(NMMUNICIP__TAG, "Nome Municipio");
    DSAMBIENTE = makeLINCStringField(DSAMBIENTE__TAG, 11, 19, 'A', true);
    setDataDisplay(DSAMBIENTE__TAG, "DS AMBIENTE");
    NMMUNAB1 = makeLINCStringField(NMMUNAB1__TAG, 20, 20, 'A', true);
    setDataDisplay(NMMUNAB1__TAG, "Nome Munic Abrev");
    DSTELA30 = makeLINCStringField(DSTELA30__TAG, 30, 21, 'A', true);
    setDataDisplay(DSTELA30__TAG, "Descricao");
    SNFUNDAMEN = makeLINCStringField(SNFUNDAMEN__TAG, 1, 22, 'A', true);
    setDataDisplay(SNFUNDAMEN__TAG, "SN FUND.DENUN.FR");
    DSTELA35A = makeLINCStringField(DSTELA35A__TAG, 35, 23, 'A', true);
    setDataDisplay(DSTELA35A__TAG, "Descricao");
    CDUNIDCO1 = makeLINCNumericField(CDUNIDCO1__TAG, 3, 24, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(CDUNIDCO1__TAG, "Codigo Unidade");
    CDLOGRADR = makeLINCNumericField(CDLOGRADR__TAG, 9, 25, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(CDLOGRADR__TAG, "Cod Logradouro");
    QTCONSUMO = makeLINCNumericField(QTCONSUMO__TAG, 7, 26, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTCONSUMO__TAG, "Consumo");
    VLLEIATU = makeLINCNumericField(VLLEIATU__TAG, 7, 27, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(VLLEIATU__TAG, "Leitura Atual");
    DTGERAL = makeLINCNumericField(DTGERAL__TAG, 6, 28, 'D', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(DTGERAL__TAG, "Data");
    DTINI = makeLINCNumericField(DTINI__TAG, 6, 29, 'D', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(DTINI__TAG, "Data Inicio");
    DSMES01 = makeLINCStringField(DSMES01__TAG, 5, 30, 'A', true);
    setDataDisplay(DSMES01__TAG, "Literal");
    QTCONS01 = makeLINCNumericField(QTCONS01__TAG, 7, 31, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTCONS01__TAG, "Consumo");
    TPTELA1A = makeLINCStringField(TPTELA1A__TAG, 1, 32, 'A', true);
    setDataDisplay(TPTELA1A__TAG, "TIPO TELA A");
    DTLEITURA = makeLINCNumericField(DTLEITURA__TAG, 6, 33, 'D', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(DTLEITURA__TAG, "Data Leitura");
    DTTELA1 = makeLINCNumericField(DTTELA1__TAG, 6, 34, 'D', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(DTTELA1__TAG, "Data");
    NRGRUPFAT = makeLINCNumericField(NRGRUPFAT__TAG, 2, 35, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(NRGRUPFAT__TAG, "Grupo");
    NRCICLO = makeLINCNumericField(NRCICLO__TAG, 2, 36, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(NRCICLO__TAG, "Ciclo");
    DSMES02 = makeLINCStringField(DSMES02__TAG, 5, 37, 'A', true);
    setDataDisplay(DSMES02__TAG, "Literal");
    QTCONS02 = makeLINCNumericField(QTCONS02__TAG, 7, 38, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTCONS02__TAG, "Consumo");
    TPTELA1B = makeLINCStringField(TPTELA1B__TAG, 1, 39, 'A', true);
    setDataDisplay(TPTELA1B__TAG, "TIPO TELA B");
    CDLEITURA = makeLINCNumericField(CDLEITURA__TAG, 2, 40, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(CDLEITURA__TAG, "Codigo Leitura");
    DTSUPRESS = makeLINCNumericField(DTSUPRESS__TAG, 6, 41, 'D', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(DTSUPRESS__TAG, "Dt da Supressao");
    QTCREDITO = makeLINCNumericField(QTCREDITO__TAG, 7, 42, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTCREDITO__TAG, "Credito em m3");
    DSMES03 = makeLINCStringField(DSMES03__TAG, 5, 43, 'A', true);
    setDataDisplay(DSMES03__TAG, "Literal");
    QTCONS03 = makeLINCNumericField(QTCONS03__TAG, 7, 44, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTCONS03__TAG, "Consumo");
    TPTELA1C = makeLINCStringField(TPTELA1C__TAG, 1, 45, 'A', true);
    setDataDisplay(TPTELA1C__TAG, "TIPO TELA C");
    DSINTERV = makeLINCStringField(DSINTERV__TAG, 8, 46, 'A', true);
    setDataDisplay(DSINTERV__TAG, "Intervalo");
    STAGUA = makeLINCNumericField(STAGUA__TAG, 2, 47, 'N', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(STAGUA__TAG, "Situacao Agua");
    STESGOTO = makeLINCNumericField(STESGOTO__TAG, 2, 48, 'N', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(STESGOTO__TAG, "Situacao Esgoto");
    QTCREDIT1 = makeLINCNumericField(QTCREDIT1__TAG, 7, 49, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTCREDIT1__TAG, "Credito em m3");
    DSMES04 = makeLINCStringField(DSMES04__TAG, 5, 50, 'A', true);
    setDataDisplay(DSMES04__TAG, "Literal");
    QTCONS04 = makeLINCNumericField(QTCONS04__TAG, 7, 51, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTCONS04__TAG, "Consumo");
    TPTELA1D = makeLINCStringField(TPTELA1D__TAG, 1, 52, 'A', true);
    setDataDisplay(TPTELA1D__TAG, "TIPO TELA D");
    VLLEIANT = makeLINCNumericField(VLLEIANT__TAG, 7, 53, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(VLLEIANT__TAG, "Leit Ant");
    STLIGACAO = makeLINCStringField(STLIGACAO__TAG, 1, 54, 'A', true);
    setDataDisplay(STLIGACAO__TAG, "Situacao Ligacao");
    TPTL = makeLINCStringField(TPTL__TAG, 1, 55, 'A', true);
    setDataDisplay(TPTL__TAG, "TL");
    TPFATSLEIT = makeLINCStringField(TPFATSLEIT__TAG, 1, 56, 'A', true);
    setDataDisplay(TPFATSLEIT__TAG, "TP FAT SEM LEIT");
    DSTELA8A = makeLINCStringField(DSTELA8A__TAG, 8, 57, 'A', true);
    setDataDisplay(DSTELA8A__TAG, "DESC TELA");
    DSMES05 = makeLINCStringField(DSMES05__TAG, 5, 58, 'A', true);
    setDataDisplay(DSMES05__TAG, "Literal");
    QTCONS05 = makeLINCNumericField(QTCONS05__TAG, 7, 59, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTCONS05__TAG, "Consumo");
    TPTELA1E = makeLINCStringField(TPTELA1E__TAG, 1, 60, 'A', true);
    setDataDisplay(TPTELA1E__TAG, "TIPO TELA E");
    DTGERAL1 = makeLINCNumericField(DTGERAL1__TAG, 6, 61, 'D', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(DTGERAL1__TAG, "Data");
    DSTELA7A = makeLINCStringField(DSTELA7A__TAG, 7, 62, 'A', true);
    CDCL2 = makeLINCNumericField(CDCL2__TAG, 2, 63, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(CDCL2__TAG, "COD LEIT - TELA");
    DSREDUZ7 = makeLINCStringField(DSREDUZ7__TAG, 7, 64, 'A', true);
    setDataDisplay(DSREDUZ7__TAG, "DS REDUZIDA - 7P");
    DSMES06 = makeLINCStringField(DSMES06__TAG, 5, 65, 'A', true);
    setDataDisplay(DSMES06__TAG, "Literal");
    QTCONS06 = makeLINCNumericField(QTCONS06__TAG, 7, 66, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTCONS06__TAG, "Consumo");
    TPTELA1F = makeLINCStringField(TPTELA1F__TAG, 1, 67, 'A', true);
    setDataDisplay(TPTELA1F__TAG, "TIPO TELA F");
    DSTELA17A = makeLINCStringField(DSTELA17A__TAG, 17, 68, 'A', true);
    setDataDisplay(DSTELA17A__TAG, "DSTELA17A");
    DSMES07 = makeLINCStringField(DSMES07__TAG, 5, 69, 'A', true);
    setDataDisplay(DSMES07__TAG, "Literal");
    QTCONS07 = makeLINCNumericField(QTCONS07__TAG, 7, 70, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTCONS07__TAG, "Consumo");
    TPTELA1G = makeLINCStringField(TPTELA1G__TAG, 1, 71, 'A', true);
    setDataDisplay(TPTELA1G__TAG, "TIPO TELA G");
    CDHIDRO = makeLINCStringField(CDHIDRO__TAG, 10, 72, 'A', true);
    setDataDisplay(CDHIDRO__TAG, "Hidr\u00f4metro");
    CDCPH = makeLINCNumericField(CDCPH__TAG, 2, 73, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(CDCPH__TAG, "CPH");
    QTRES = makeLINCNumericField(QTRES__TAG, 4, 74, 'N', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(QTRES__TAG, "Qtde Residencias");
    QTIND = makeLINCNumericField(QTIND__TAG, 4, 75, 'N', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(QTIND__TAG, "Qtde Industrias");
    CDBANCO = makeLINCNumericField(CDBANCO__TAG, 3, 76, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(CDBANCO__TAG, "Codigo Banco");
    CDAGENCIA = makeLINCNumericField(CDAGENCIA__TAG, 4, 77, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(CDAGENCIA__TAG, "Codigo Agencia");
    DSMES08 = makeLINCStringField(DSMES08__TAG, 5, 78, 'A', true);
    setDataDisplay(DSMES08__TAG, "Literal");
    QTCONS08 = makeLINCNumericField(QTCONS08__TAG, 7, 79, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTCONS08__TAG, "Consumo");
    TPTELA1H = makeLINCStringField(TPTELA1H__TAG, 1, 80, 'A', true);
    setDataDisplay(TPTELA1H__TAG, "TIPO TELA H");
    CDCPH1 = makeLINCNumericField(CDCPH1__TAG, 2, 81, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(CDCPH1__TAG, "Codigo CPH");
    CDDIAMETR = makeLINCNumericField(CDDIAMETR__TAG, 2, 82, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(CDDIAMETR__TAG, "Codigo Diametro");
    QTCOM = makeLINCNumericField(QTCOM__TAG, 4, 83, 'N', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(QTCOM__TAG, "Qtde Econ Com");
    QTPUB = makeLINCNumericField(QTPUB__TAG, 4, 84, 'N', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(QTPUB__TAG, "Qtde Econ Pub");
    NRCONTAC = makeLINCStringField(NRCONTAC__TAG, 19, 85, 'A', true);
    setDataDisplay(NRCONTAC__TAG, "Conta Corrente");
    DSMES09 = makeLINCStringField(DSMES09__TAG, 5, 86, 'A', true);
    setDataDisplay(DSMES09__TAG, "Literal");
    QTCONS09 = makeLINCNumericField(QTCONS09__TAG, 7, 87, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTCONS09__TAG, "Consumo");
    TPTELA1I = makeLINCStringField(TPTELA1I__TAG, 1, 88, 'A', true);
    setDataDisplay(TPTELA1I__TAG, "TIPO TELA I");
    DTRETIRA = makeLINCNumericField(DTRETIRA__TAG, 6, 89, 'D', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(DTRETIRA__TAG, "Data Retir Hidro");
    QTDIGHIDR = makeLINCNumericField(QTDIGHIDR__TAG, 1, 90, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTDIGHIDR__TAG, "Qt.Digitos Hidro");
    CDORGPUB = makeLINCNumericField(CDORGPUB__TAG, 3, 91, 'N', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(CDORGPUB__TAG, "Cd Org Publico");
    CDENTIDAD = makeLINCNumericField(CDENTIDAD__TAG, 5, 92, 'N', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(CDENTIDAD__TAG, "Cod Entidade Pub");
    CDFACE = makeLINCStringField(CDFACE__TAG, 1, 93, 'A', true);
    setDataDisplay(CDFACE__TAG, "Face de Quadra");
    DSMES10 = makeLINCStringField(DSMES10__TAG, 5, 94, 'A', true);
    setDataDisplay(DSMES10__TAG, "Literal");
    QTCONS10 = makeLINCNumericField(QTCONS10__TAG, 7, 95, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTCONS10__TAG, "Consumo");
    TPTELA1J = makeLINCStringField(TPTELA1J__TAG, 1, 96, 'A', true);
    setDataDisplay(TPTELA1J__TAG, "TIPO TELA J");
    DTINST = makeLINCNumericField(DTINST__TAG, 6, 97, 'D', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(DTINST__TAG, "Data Instalacao");
    DSTELA4 = makeLINCStringField(DSTELA4__TAG, 4, 98, 'A', true);
    setDataDisplay(DSTELA4__TAG, "Descricao");
    CDMOTBENEF = makeLINCStringField(CDMOTBENEF__TAG, 1, 99, 'A', true);
    setDataDisplay(CDMOTBENEF__TAG, "just.conces.ben.");
    DTTELA2 = makeLINCNumericField(DTTELA2__TAG, 6, 100, 'D', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(DTTELA2__TAG, "Data");
    DTTELA3 = makeLINCNumericField(DTTELA3__TAG, 6, 101, 'D', true, 0, LINCNumericField.NUL, 0);
    setDataDisplay(DTTELA3__TAG, "Data");
    DSMES11 = makeLINCStringField(DSMES11__TAG, 5, 102, 'A', true);
    setDataDisplay(DSMES11__TAG, "Literal");
    QTCONS11 = makeLINCNumericField(QTCONS11__TAG, 7, 103, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTCONS11__TAG, "Consumo");
    TPTELA1L = makeLINCStringField(TPTELA1L__TAG, 1, 104, 'A', true);
    setDataDisplay(TPTELA1L__TAG, "TIPO TELA L");
    DSTELA20 = makeLINCStringField(DSTELA20__TAG, 20, 105, 'A', true);
    setDataDisplay(DSTELA20__TAG, "Descricao");
    DDVENC = makeLINCNumericField(DDVENC__TAG, 2, 106, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(DDVENC__TAG, "DD VENCTO CLIEN.");
    DSTELA3 = makeLINCStringField(DSTELA3__TAG, 3, 107, 'A', true);
    setDataDisplay(DSTELA3__TAG, "Descricao");
    DSMES12 = makeLINCStringField(DSMES12__TAG, 5, 108, 'A', true);
    setDataDisplay(DSMES12__TAG, "Literal");
    QTCONS12 = makeLINCNumericField(QTCONS12__TAG, 7, 109, 'N', true, 0, LINCNumericField.NUL, 1);
    setDataDisplay(QTCONS12__TAG, "Consumo");
    TPTELA1M = makeLINCStringField(TPTELA1M__TAG, 1, 110, 'A', true);
    setDataDisplay(TPTELA1M__TAG, "Tipo");
    DSCAVALET = makeLINCStringField(DSCAVALET__TAG, 15, 111, 'A', true);
    setDataDisplay(DSCAVALET__TAG, "Cavalete");
    DSTELA15 = makeLINCStringField(DSTELA15__TAG, 15, 112, 'A', true);
    DSTELA3A = makeLINCStringField(DSTELA3A__TAG, 3, 113, 'A', true);
    setDataDisplay(DSTELA3A__TAG, "Descricao Tela A");
    CDPROJETO = makeLINCStringField(CDPROJETO__TAG, 3, 114, 'A', true);
    setDataDisplay(CDPROJETO__TAG, "Codigo Projeto");
    DSTECNPRO = makeLINCStringField(DSTECNPRO__TAG, 30, 115, 'A', true);
    setDataDisplay(DSTECNPRO__TAG, "Descr Tecnica");
    DSCONFIRM = makeLINCStringField(DSCONFIRM__TAG, 14, 116, 'A', true);
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
  public String getNRRGIPROX() {
    return NRRGIPROX.getValue();
  }
  public void setNRRGIPROX(String newValue) throws LINCFieldException, PropertyVetoException {
    NRRGIPROX.setValue(newValue);
  }
  public String getAMREFPROX() {
    return AMREFPROX.getValue();
  }
  public void setAMREFPROX(String newValue) throws LINCFieldException, PropertyVetoException {
    AMREFPROX.setValue(newValue);
  }
  public String getNRTELA5() {
    return NRTELA5.getValue();
  }
  public void setNRTELA5(String newValue) throws LINCFieldException, PropertyVetoException {
    NRTELA5.setValue(newValue);
  }
  public String getNRRGILIG() {
    return NRRGILIG.getValue();
  }
  public void setNRRGILIG(String newValue) throws LINCFieldException, PropertyVetoException {
    NRRGILIG.setValue(newValue);
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
  public String getSTOK() {
    return STOK.getValue();
  }
  public void setSTOK(String newValue) throws LINCFieldException, PropertyVetoException {
    STOK.setValue(newValue);
  }
  public String getSTPRENAU() {
    return STPRENAU.getValue();
  }
  public void setSTPRENAU(String newValue) throws LINCFieldException, PropertyVetoException {
    STPRENAU.setValue(newValue);
  }
  public String getAT_SIGN() {
    return AT_SIGN.getValue();
  }
  public void setAT_SIGN(String newValue) throws LINCFieldException, PropertyVetoException {
    AT_SIGN.setValue(newValue);
  }
  public String getNMMUNICIP() {
    return NMMUNICIP.getValue();
  }
  public String getDSAMBIENTE() {
    return DSAMBIENTE.getValue();
  }
  public String getNMMUNAB1() {
    return NMMUNAB1.getValue();
  }
  public String getDSTELA30() {
    return DSTELA30.getValue();
  }
  public String getSNFUNDAMEN() {
    return SNFUNDAMEN.getValue();
  }
  public String getDSTELA35A() {
    return DSTELA35A.getValue();
  }
  public String getCDUNIDCO1() {
    return CDUNIDCO1.getValue();
  }
  public String getCDLOGRADR() {
    return CDLOGRADR.getValue();
  }
  public String getQTCONSUMO() {
    return QTCONSUMO.getValue();
  }
  public String getVLLEIATU() {
    return VLLEIATU.getValue();
  }
  public String getDTGERAL() {
    return DTGERAL.getValue();
  }
  public String getDTINI() {
    return DTINI.getValue();
  }
  public String getDSMES01() {
    return DSMES01.getValue();
  }
  public String getQTCONS01() {
    return QTCONS01.getValue();
  }
  public String getTPTELA1A() {
    return TPTELA1A.getValue();
  }
  public String getDTLEITURA() {
    return DTLEITURA.getValue();
  }
  public String getDTTELA1() {
    return DTTELA1.getValue();
  }
  public String getNRGRUPFAT() {
    return NRGRUPFAT.getValue();
  }
  public String getNRCICLO() {
    return NRCICLO.getValue();
  }
  public String getDSMES02() {
    return DSMES02.getValue();
  }
  public String getQTCONS02() {
    return QTCONS02.getValue();
  }
  public String getTPTELA1B() {
    return TPTELA1B.getValue();
  }
  public String getCDLEITURA() {
    return CDLEITURA.getValue();
  }
  public String getDTSUPRESS() {
    return DTSUPRESS.getValue();
  }
  public String getQTCREDITO() {
    return QTCREDITO.getValue();
  }
  public String getDSMES03() {
    return DSMES03.getValue();
  }
  public String getQTCONS03() {
    return QTCONS03.getValue();
  }
  public String getTPTELA1C() {
    return TPTELA1C.getValue();
  }
  public String getDSINTERV() {
    return DSINTERV.getValue();
  }
  public String getSTAGUA() {
    return STAGUA.getValue();
  }
  public String getSTESGOTO() {
    return STESGOTO.getValue();
  }
  public String getQTCREDIT1() {
    return QTCREDIT1.getValue();
  }
  public String getDSMES04() {
    return DSMES04.getValue();
  }
  public String getQTCONS04() {
    return QTCONS04.getValue();
  }
  public String getTPTELA1D() {
    return TPTELA1D.getValue();
  }
  public String getVLLEIANT() {
    return VLLEIANT.getValue();
  }
  public String getSTLIGACAO() {
    return STLIGACAO.getValue();
  }
  public String getTPTL() {
    return TPTL.getValue();
  }
  public String getTPFATSLEIT() {
    return TPFATSLEIT.getValue();
  }
  public String getDSTELA8A() {
    return DSTELA8A.getValue();
  }
  public String getDSMES05() {
    return DSMES05.getValue();
  }
  public String getQTCONS05() {
    return QTCONS05.getValue();
  }
  public String getTPTELA1E() {
    return TPTELA1E.getValue();
  }
  public String getDTGERAL1() {
    return DTGERAL1.getValue();
  }
  public String getDSTELA7A() {
    return DSTELA7A.getValue();
  }
  public String getCDCL2() {
    return CDCL2.getValue();
  }
  public String getDSREDUZ7() {
    return DSREDUZ7.getValue();
  }
  public String getDSMES06() {
    return DSMES06.getValue();
  }
  public String getQTCONS06() {
    return QTCONS06.getValue();
  }
  public String getTPTELA1F() {
    return TPTELA1F.getValue();
  }
  public String getDSTELA17A() {
    return DSTELA17A.getValue();
  }
  public String getDSMES07() {
    return DSMES07.getValue();
  }
  public String getQTCONS07() {
    return QTCONS07.getValue();
  }
  public String getTPTELA1G() {
    return TPTELA1G.getValue();
  }
  public String getCDHIDRO() {
    return CDHIDRO.getValue();
  }
  public String getCDCPH() {
    return CDCPH.getValue();
  }
  public String getQTRES() {
    return QTRES.getValue();
  }
  public String getQTIND() {
    return QTIND.getValue();
  }
  public String getCDBANCO() {
    return CDBANCO.getValue();
  }
  public String getCDAGENCIA() {
    return CDAGENCIA.getValue();
  }
  public String getDSMES08() {
    return DSMES08.getValue();
  }
  public String getQTCONS08() {
    return QTCONS08.getValue();
  }
  public String getTPTELA1H() {
    return TPTELA1H.getValue();
  }
  public String getCDCPH1() {
    return CDCPH1.getValue();
  }
  public String getCDDIAMETR() {
    return CDDIAMETR.getValue();
  }
  public String getQTCOM() {
    return QTCOM.getValue();
  }
  public String getQTPUB() {
    return QTPUB.getValue();
  }
  public String getNRCONTAC() {
    return NRCONTAC.getValue();
  }
  public String getDSMES09() {
    return DSMES09.getValue();
  }
  public String getQTCONS09() {
    return QTCONS09.getValue();
  }
  public String getTPTELA1I() {
    return TPTELA1I.getValue();
  }
  public String getDTRETIRA() {
    return DTRETIRA.getValue();
  }
  public String getQTDIGHIDR() {
    return QTDIGHIDR.getValue();
  }
  public String getCDORGPUB() {
    return CDORGPUB.getValue();
  }
  public String getCDENTIDAD() {
    return CDENTIDAD.getValue();
  }
  public String getCDFACE() {
    return CDFACE.getValue();
  }
  public String getDSMES10() {
    return DSMES10.getValue();
  }
  public String getQTCONS10() {
    return QTCONS10.getValue();
  }
  public String getTPTELA1J() {
    return TPTELA1J.getValue();
  }
  public String getDTINST() {
    return DTINST.getValue();
  }
  public String getDSTELA4() {
    return DSTELA4.getValue();
  }
  public String getCDMOTBENEF() {
    return CDMOTBENEF.getValue();
  }
  public String getDTTELA2() {
    return DTTELA2.getValue();
  }
  public String getDTTELA3() {
    return DTTELA3.getValue();
  }
  public String getDSMES11() {
    return DSMES11.getValue();
  }
  public String getQTCONS11() {
    return QTCONS11.getValue();
  }
  public String getTPTELA1L() {
    return TPTELA1L.getValue();
  }
  public String getDSTELA20() {
    return DSTELA20.getValue();
  }
  public String getDDVENC() {
    return DDVENC.getValue();
  }
  public String getDSTELA3() {
    return DSTELA3.getValue();
  }
  public String getDSMES12() {
    return DSMES12.getValue();
  }
  public String getQTCONS12() {
    return QTCONS12.getValue();
  }
  public String getTPTELA1M() {
    return TPTELA1M.getValue();
  }
  public String getDSCAVALET() {
    return DSCAVALET.getValue();
  }
  public String getDSTELA15() {
    return DSTELA15.getValue();
  }
  public String getDSTELA3A() {
    return DSTELA3A.getValue();
  }
  public String getCDPROJETO() {
    return CDPROJETO.getValue();
  }
  public String getDSTECNPRO() {
    return DSTECNPRO.getValue();
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
    return "Sat, Mar 27 10:09:24 GMT-03:00 2010";
  }
  public String getGUID() {
    return "FAAB467334D2C991A27A0739F1F466FD";
	//  return "707BE71E1B6A0B764830CEC209CFA9AB";
  }

}
