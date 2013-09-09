 package com.prime.infra.batch;
 
 import com.prime.infra.config.RunningMode;
 import com.prime.infra.config.Version;
 import com.prime.infra.logging.LoggingConfigurator;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.BeanDefinitionStoreException;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Qualifier;
 import org.springframework.context.support.ClassPathXmlApplicationContext;
 
 public class BatchRunner<T, V>
 {
/*  26 */   private static Logger logger = LoggerFactory.getLogger(BatchRunner.class);
   public static final int BATCH_OK = 0;
   public static final int BATCH_WARN = 100;
   public static final int BATCH_ERROR = 1;
   private TaskManager taskManager;
   private Class<Task<T, V>> taskType;
   private int taskCounter;
   private RecordProducer<T> producer;
   private RecordConsumer<V> consumer;
   private boolean sharedOutput;
 
   @Autowired(required=false)
   @Qualifier("startHandler")
   private Handler startHandler;
 
   @Autowired(required=false)
   @Qualifier("endHandler")
   private Handler endHandler;
 
   public TaskManager getTaskManager()
   {
/*  49 */     return this.taskManager;
   }
 
   public void setTaskManager(TaskManager taskManager) {
/*  53 */     this.taskManager = taskManager;
   }
 
   public Class<Task<T, V>> getTaskType() {
/*  57 */     return this.taskType;
   }
 
   public void setTaskType(Class<Task<T, V>> taskType) {
/*  61 */     this.taskType = taskType;
   }
 
   public RecordProducer<T> getProducer() {
/*  65 */     return this.producer;
   }
 
   public void setProducer(RecordProducer<T> producer) {
/*  69 */     this.producer = producer;
   }
 
   public RecordConsumer<V> getConsumer() {
/*  73 */     return this.consumer;
   }
 
   public void setConsumer(RecordConsumer<V> consumer) {
/*  77 */     this.consumer = consumer;
   }
 
   public boolean isSharedOutput() {
/*  81 */     return this.sharedOutput;
   }
 
   public void setSharedOutput(boolean sharedOutput) {
/*  85 */     this.sharedOutput = sharedOutput;
   }
 
   private int run() {
/*  89 */     logger.info("================================================================");
/*  90 */     long t0 = System.currentTimeMillis();
/*  91 */     if (this.startHandler != null) {
/*  92 */       this.startHandler.handle();
     }
     try
     {
/*  96 */       this.taskManager.setOutput(this.consumer);
/*  97 */       this.taskManager.setSharedOutput(this.sharedOutput);
/*  98 */       this.taskManager.initialize();
 
/* 100 */       while ((this.taskManager.isAdding()) && (this.producer.hasNext())) {
/* 101 */         Object param = this.producer.next();
         try {
/* 103 */           Task task = createTask(param);
/* 104 */           this.taskManager.add(task);
         }
         catch (Exception e) {
/* 107 */           logger.error("Error creating task: " + e.getMessage(), e);
         }
       }
 
/* 111 */       if (!(this.taskManager.isTerminatedError())) {
/* 112 */         this.taskManager.waitCompletion();
       }
 
/* 115 */       int result = 0;
/* 116 */       if (this.taskManager.isTerminatedError()) {
/* 117 */         result = this.taskManager.getErrorCode();
       }
/* 119 */       else if (this.taskManager.isTerminatedWarning()) {
/* 120 */         result = this.taskManager.getWarningCode();
       }
 
/* 123 */       if (this.endHandler != null) {
/* 124 */         this.endHandler.handle();
       }
 
/* 127 */       return result;
     }
     finally {
/* 130 */       int n = this.taskManager.getNumResults();
/* 131 */       long t1 = System.currentTimeMillis();
/* 132 */       long t = t1 - t0;
/* 133 */       logger.info("================================================================");
/* 134 */       logger.info("* Total results: {}", Integer.valueOf(n));
/* 135 */       logger.info("* Total time: {} ms", Long.valueOf(t));
/* 136 */       if (n > 0) {
/* 137 */         logger.info("* Average time: {} ms", Long.valueOf(t / n));
/* 138 */         logger.info("* {} results/sec", Long.valueOf((long)(((double)n / (double)t) * 1000D)));
       }
     }
   }
 
   private Task createTask(Object o) throws IllegalAccessException, InstantiationException {
/* 144 */     Task task = (Task)this.taskType.newInstance();
/* 145 */     task.setId(this.taskCounter++);
/* 146 */     task.setParam(o);
/* 147 */     return task;
   }
 
   public static int main()
   {
     int retCode;
/* 153 */     logger.info("Starting BatchRunner");
/* 154 */     ClassPathXmlApplicationContext ctx = null;
     try {
/* 156 */       RunningMode.configure("batch");
 
/* 158 */       LoggingConfigurator loggingConfigurator = new LoggingConfigurator();
/* 159 */       loggingConfigurator.configure();
 
/* 161 */       Logger startup = LoggerFactory.getLogger("STARTUP");
/* 162 */       startup.info("================================================================");
/* 163 */       startup.info("  Starting system configuration...");
/* 164 */       startup.info("  Application Version [" + Version.getApplicationVersion() + "]");
/* 165 */       startup.info("  Framework Version   [" + Version.getFrameworkVersion() + "]");
/* 166 */       startup.info("  Running mode        " + RunningMode.get());
 
/* 168 */       ctx = new ClassPathXmlApplicationContext(new String[] { "/META-INF/root-context.xml", "META-INF/batch-context.xml" });
 
/* 170 */       startup.info("  Application context configured");
/* 171 */       startup.info("================================================================");
 
/* 173 */       BatchRunner batchRunner = (BatchRunner)ctx.getBean("batchRunner");
/* 174 */       retCode = batchRunner.run();
     }
     catch (BeanDefinitionStoreException beanDefinitionStoreException) {
/* 177 */       retCode = 1;
/* 178 */       logger.error("Erro configuracao", beanDefinitionStoreException);
/* 179 */       logger.error("Please check your configurations and ensure that 'runningMode' system property was set properly.");
     }
     catch (Throwable t) {
/* 182 */       retCode = 1;
/* 183 */       logger.error("Error running batch", t);
     }
     finally {
/* 186 */       if (ctx != null) {
/* 187 */         ctx.stop();
       }
     }
 
/* 191 */     logger.info("Finishing BatchRunner -> " + message(retCode));
/* 192 */     return retCode;
   }
 
   private static String message(int code) {
/* 196 */     if (code == 0) {
/* 197 */       return "OK:[" + code + "]";
     }
/* 199 */     if ((code >= 100) && (code <= 199)) {
/* 200 */       return "WARNING:[" + code + "]";
     }
/* 202 */     if ((code >= 1) && (code <= 100)) {
/* 203 */       return "ERROR:[" + code + "]";
     }
/* 205 */     return "UNKNOWN:[" + code + "]";
   }
 
   public static void main(String[] args) {
/* 209 */     System.exit(main());
   }
 }