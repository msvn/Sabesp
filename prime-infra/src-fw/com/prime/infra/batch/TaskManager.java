 package com.prime.infra.batch;
 
 import java.util.concurrent.ArrayBlockingQueue;
 import java.util.concurrent.BlockingQueue;
 import java.util.concurrent.CompletionService;
 import java.util.concurrent.ExecutionException;
 import java.util.concurrent.ExecutorCompletionService;
 import java.util.concurrent.Future;
 import java.util.concurrent.RejectedExecutionHandler;
 import java.util.concurrent.ThreadPoolExecutor;
 import java.util.concurrent.TimeUnit;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public class TaskManager
 {
/*  14 */   private static Logger logger = LoggerFactory.getLogger(TaskManager.class);
   private State state;
   private int poolSize;
   private ThreadPoolExecutor executor;
   private CompletionService completionService;
   private long singleTaskTimeout;
   private int numTasks;
   private int numResults;
   private int numExceptions;
   private int flushFrequency;
   private RecordConsumer output;
   private boolean sharedOutput;
   private Thread resultsThread;
   private int firstWarningCode;
   private int errorCode;
/* 265 */   private static final Task MARKER_TASK = new MarkerTask();
 
   public TaskManager()
   {
/*  20 */     this.state = State.INITIALIZING;
 
/*  25 */     this.singleTaskTimeout = 30L;
 
/*  31 */     this.flushFrequency = 1;
 
/*  38 */     this.firstWarningCode = -1;
/*  39 */     this.errorCode = -1; }
 
   public int getWarningCode() {
/*  42 */     return this.firstWarningCode;
   }
 
   public int getErrorCode() {
/*  46 */     return this.errorCode;
   }
 
   public boolean isAdding() {
/*  50 */     return this.state.equals(State.ADDING);
   }
 
   public boolean isTerminatedError() {
/*  54 */     return this.state.equals(State.TERMINATED_ERROR);
   }
 
   public boolean isTerminatedWarning() {
/*  58 */     return ((this.state.equals(State.TERMINATED)) && (this.firstWarningCode != -1));
   }
 
   public int getPoolSize() {
/*  62 */     return this.poolSize;
   }
 
   public void setPoolSize(int poolSize) {
/*  66 */     if (!(this.state.equals(State.INITIALIZING))) {
/*  67 */       throw new IllegalStateException();
     }
 
/*  70 */     this.poolSize = poolSize;
   }
 
   public ThreadPoolExecutor getExecutor() {
/*  74 */     return this.executor;
   }
 
   public void setExecutor(ThreadPoolExecutor executor) {
/*  78 */     if (!(this.state.equals(State.INITIALIZING))) {
/*  79 */       throw new IllegalStateException();
     }
 
/*  82 */     this.executor = executor;
   }
 
   public int getNumTasks() {
/*  86 */     return this.numTasks;
   }
 
   public int getNumResults() {
/*  90 */     return this.numResults;
   }
 
   public int getNumExceptions() {
/*  94 */     return this.numExceptions;
   }
 
   public int getFlushFrequency() {
/*  98 */     return this.flushFrequency;
   }
 
   public void setFlushFrequency(int flushFrequency) {
/* 102 */     if (!(this.state.equals(State.INITIALIZING))) {
/* 103 */       throw new IllegalStateException();
     }
 
/* 106 */     this.flushFrequency = flushFrequency;
   }
 
   public RecordConsumer getOutput() {
/* 110 */     return this.output;
   }
 
   public void setOutput(RecordConsumer output) {
/* 114 */     if (!(this.state.equals(State.INITIALIZING))) {
/* 115 */       throw new IllegalStateException();
     }
 
/* 118 */     this.output = output;
   }
 
   public boolean isSharedOutput() {
/* 122 */     return this.sharedOutput;
   }
 
   public void setSharedOutput(boolean sharedOutput) {
/* 126 */     if (!(this.state.equals(State.INITIALIZING))) {
/* 127 */       throw new IllegalStateException();
     }
 
/* 130 */     this.sharedOutput = sharedOutput;
   }
 
   private ThreadPoolExecutor createDefaultExecutor() {
/* 134 */     return new ThreadPoolExecutor(this.poolSize, this.poolSize, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(this.poolSize), new CallerBlocksPolicy());
   }
 
   public void initialize()
   {
/* 143 */     if (!(this.state.equals(State.INITIALIZING))) {
/* 144 */       throw new IllegalStateException();
     }
 
/* 147 */     if (this.executor == null) {
/* 148 */       this.executor = createDefaultExecutor();
     }
 
/* 151 */     this.completionService = new ExecutorCompletionService(this.executor);
/* 152 */     this.numTasks = 0;
/* 153 */     this.numResults = 0;
/* 154 */     this.numExceptions = 0;
/* 155 */     this.state = State.ADDING;
 
/* 157 */     this.resultsThread = new Thread(new Runnable() {
       public void run() {
/* 159 */         TaskManager.logger.debug("resultsThread running");
/* 160 */         TaskManager.this.collectResults();
       }
     });
/* 163 */     this.resultsThread.start();
/* 164 */     logger.info("Initialized");
   }
 
   public void stop() {
/* 168 */     this.executor.shutdownNow();
   }
 
   public void add(Task task) {
/* 172 */     if (!(this.state.equals(State.ADDING))) {
/* 173 */       throw new IllegalStateException();
     }
 
/* 176 */     logger.trace("Adding task " + task);
 
/* 178 */     if (this.sharedOutput) {
/* 179 */       task.setOutput(this.output);
     }
 
/* 182 */     this.completionService.submit(task);
/* 183 */     this.numTasks += 1;
   }
 
   public void waitCompletion() {
/* 187 */     if (!(this.state.equals(State.ADDING))) {
/* 188 */       throw new IllegalStateException();
     }
 
/* 191 */     this.state = State.WAITING;
/* 192 */     logger.info("Number of tasks: " + this.numTasks);
 
/* 194 */     this.completionService.submit(MARKER_TASK);
     try {
/* 196 */       this.resultsThread.join();
     }
     catch (InterruptedException e) {
/* 199 */       logger.warn("resultsThread.join() interrupted", e);
     }
/* 201 */     logger.info("All tasks finished");
   }
 
   private void collectResults() {
/* 205 */     while ((isAdding()) || (this.numResults + this.numExceptions < this.numTasks)) {
       try {
/* 207 */         Future future = this.completionService.poll(this.singleTaskTimeout, TimeUnit.SECONDS);
/* 208 */         if (future != null) {
/* 209 */           Object result = future.get();
/* 210 */           if (result != MARKER_TASK) {
/* 211 */             this.numResults += 1;
/* 212 */             if (!(this.sharedOutput)) {
/* 213 */               logger.trace("Writing result = " + result);
/* 214 */               this.output.consume(result);
             }
 
/* 217 */             if (this.numResults % this.flushFrequency == 0) {
/* 218 */               logger.trace("Flush...");
/* 219 */               this.output.flush();
             }
           }
         }
         else {
/* 224 */           logger.debug("No task completed in " + this.singleTaskTimeout + " seconds");
         }
       }
       catch (InterruptedException e) {
/* 228 */         logger.error("Thread interrupted while waiting", e);
       }
       catch (ExecutionException e) {
/* 231 */         this.numExceptions += 1;
/* 232 */         handleException(e.getCause());
       }
     }
 
/* 236 */     this.output.flush();
 
/* 238 */     logger.info("Total tasks: " + this.numTasks);
/* 239 */     logger.info("Total exceptions: " + this.numExceptions);
/* 240 */     logger.info("Total results: " + this.numResults);
/* 241 */     this.state = State.TERMINATED;
   }
 
   private void handleException(Throwable cause) {
/* 245 */     if (cause instanceof BatchErrorException) {
/* 246 */       this.state = State.TERMINATED_ERROR;
/* 247 */       BatchErrorException be = (BatchErrorException)cause;
/* 248 */       this.errorCode = be.getCode();
/* 249 */       throw be;
     }
 
/* 252 */     int warningCode = 100;
/* 253 */     if (cause instanceof BatchWarningException) {
/* 254 */       warningCode = ((BatchWarningException)cause).getCode();
     }
 
/* 257 */     logger.warn("Exception in task: Warning code = " + warningCode);
/* 258 */     logger.debug("Exception in task: " + cause.getMessage(), cause);
/* 259 */     if (this.firstWarningCode == -1)
/* 260 */       this.firstWarningCode = warningCode;
   }
 
   private static class CallerBlocksPolicy
     implements RejectedExecutionHandler
   {
     public void rejectedExecution(Runnable r, ThreadPoolExecutor executor)
     {
       try
       {
/* 276 */         executor.getQueue().put(r);
       }
       catch (InterruptedException ex) {
/* 279 */         TaskManager.logger.error("CallerBlocksPolicy: " + ex.getMessage(), ex);
/* 280 */         Thread.interrupted();
       }
     }
   }
 
   private static class MarkerTask extends Task
   {
     public Object execute(Object data)
       throws Exception
     {
/* 269 */       return this;
     }
   }
 
   private static enum State
   {
/*  16 */     INITIALIZING, ADDING, WAITING, TERMINATED, TERMINATED_ERROR;
   }
 }