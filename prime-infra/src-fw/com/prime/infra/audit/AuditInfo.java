 package com.prime.infra.audit;
 
 import java.util.HashMap;
 import org.joda.time.DateTime;
 
 public class AuditInfo extends HashMap<String, Object>
 {
   public static final String DATA_HORA = "fw-data_hora";
   public static final String SERVICO = "fw-servico";
   public static final String METODO = "fw-metodo";
   public static final String CANAL = "fw-canal";
   public static final String TIPO_TERMINAL = "fw-tipo_terminal";
   public static final String NUMERO_TERMINAL = "fw-numero_terminal";
   public static final String SERVICO_OK = "fw-servico_ok";
   public static final String USUARIO = "fw-usuario";
 
   public DateTime getDataHora()
   {
/* 23 */     return ((DateTime)get("fw-data_hora"));
   }
 
   public String getServico() {
/* 27 */     return ((String)get("fw-servico"));
   }
 
   public String getMetodo() {
/* 31 */     return ((String)get("fw-metodo"));
   }
 
   public boolean isServicoOk() {
/* 35 */     return ((Boolean)get("fw-servico_ok")).booleanValue();
   }
 
   public String getCanal() {
/* 39 */     return ((String)get("fw-canal"));
   }
 
   public void setCanal(String canal) {
/* 43 */     put("fw-canal", canal);
   }
 
   public String getTipoTerminal() {
/* 47 */     return ((String)get("fw-tipo_terminal"));
   }
 
   public void setTipoTerminal(String tipoTerminal) {
/* 51 */     put("fw-tipo_terminal", tipoTerminal);
   }
 
   public String getNumeroTerminal() {
/* 55 */     return ((String)get("fw-numero_terminal"));
   }
 
   public void setNumeroTerminal(String numeroTerminal) {
/* 59 */     put("fw-numero_terminal", numeroTerminal);
   }
 
   public String getUsuario() {
/* 63 */     return ((String)get("fw-usuario"));
   }
 
   public void setUsuario(String usuario) {
/* 67 */     put("fw-usuario", usuario);
   }
 }