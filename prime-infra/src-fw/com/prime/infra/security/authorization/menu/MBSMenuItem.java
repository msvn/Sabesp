 package com.prime.infra.security.authorization.menu;
 
 public class MBSMenuItem
   implements MenuItem
 {
   private String nomeFuncao;
   private String nomeFuncaoPai;
   private String descricao;
   private String url;
   private String token;
 
   public String getNomeFuncao()
   {
/* 15 */     return this.nomeFuncao;
   }
 
   public void setNomeFuncao(String nomeFuncao) {
/* 19 */     this.nomeFuncao = nomeFuncao;
   }
 
   public String getNomeFuncaoPai() {
/* 23 */     return this.nomeFuncaoPai;
   }
 
   public void setNomeFuncaoPai(String nomeFuncaoPai) {
/* 27 */     this.nomeFuncaoPai = nomeFuncaoPai;
   }
 
   public String getDescricao()
   {
/* 36 */     return this.descricao;
   }
 
   public void setDescricao(String descricao) {
/* 40 */     this.descricao = descricao;
   }
 
   public String getUrl()
   {
/* 49 */     return this.url;
   }
 
   public void setUrl(String url) {
/* 53 */     this.url = url;
   }
 
   public String getToken() {
/* 57 */     return this.token;
   }
 
   public void setToken(String token) {
/* 61 */     this.token = token;
   }
 }