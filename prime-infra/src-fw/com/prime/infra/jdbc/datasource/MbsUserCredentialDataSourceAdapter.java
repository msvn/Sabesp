 package com.prime.infra.jdbc.datasource;
 
 import br.com.gpnet.mbs.caller.MBSCaller;
 import br.com.gpnet.mbs.caller.MBSCallerImpl;
 import br.com.gpnet.mbs.caller.MBSException;
 import java.net.URL;
 import java.util.Arrays;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.jdbc.datasource.UserCredentialsDataSourceAdapter;
 
 public class MbsUserCredentialDataSourceAdapter extends UserCredentialsDataSourceAdapter
 {
/* 19 */   private Logger logger = LoggerFactory.getLogger(MbsUserCredentialDataSourceAdapter.class);
 
   public MbsUserCredentialDataSourceAdapter(URL webService, String sistema, String frase) throws Exception {
/* 22 */     MBSCaller mbs = MBSCallerImpl.getInstance(webService);
/* 23 */     getUserCredentials(mbs, sistema, frase);
   }
 
   protected void getUserCredentials(MBSCaller mbs, String sistema, String frase) throws MBSException {
/* 27 */     long mbsStarted = System.currentTimeMillis();
/* 28 */     String[] result = mbs.getMBS(sistema, frase);
/* 29 */     long mbsFinished = System.currentTimeMillis();
/* 30 */     if ((result != null) && (result.length == 3)) {
/* 31 */       if (result[0].equals("1")) {
/* 32 */         throw new IllegalStateException("Sistema e Frase nao sao os esperados.");
       }
/* 34 */       setUsername(result[0]);
/* 35 */       setPassword(result[1]);
 
/* 37 */       this.logger.info("MBS usuario e senha ok! Tempo da chamada: " + (mbsFinished - mbsStarted) + "ms.");
     } else {
/* 39 */       throw new IllegalStateException("Resposta do MBS invalida. Resposta +" + Arrays.deepToString(result));
     }
   }
 }