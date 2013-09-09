 package com.prime.infra.batch;
 
 import com.prime.infra.aop.FaultException;
 import com.prime.infra.dao.jdbc.GenericJdbcDao;
 import java.sql.Connection;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import java.sql.Statement;
 import java.util.List;
 import org.springframework.jdbc.datasource.DataSourceUtils;
 
 public class FromDatabaseProducer extends GenericJdbcDao
   implements RecordProducer<Object[]>
 {
   private Connection conn;
   private Statement statement;
   private ResultSet resultSet;
   private int columns;
   private String query;
 
   public void open()
   {
     try
     {
/*  34 */       this.conn = DataSourceUtils.getConnection(getDataSource());
/*  35 */       this.statement = this.conn.createStatement();
 
/*  37 */       this.resultSet = this.statement.executeQuery(this.query);
 
/*  39 */       this.columns = this.resultSet.getMetaData().getColumnCount();
     }
     catch (SQLException e) {
/*  42 */       throw new FaultException(e);
     }
   }
 
   public void close()
   {
     try
     {
/*  51 */       this.resultSet.close();
/*  52 */       this.statement.close();
/*  53 */       DataSourceUtils.releaseConnection(this.conn, getDataSource());
     }
     catch (SQLException e) {
/*  56 */       throw new FaultException(e);
     }
   }
 
   public boolean hasNext()
   {
     try
     {
/*  68 */       return this.resultSet.next();
     }
     catch (SQLException e) {
/*  71 */       throw new FaultException(e);
     }
   }
 
   public Object[] next()
   {
     try
     {
/*  82 */       Object[] row = new Object[this.columns];
/*  83 */       for (int i = 0; i < this.columns; ++i) {
/*  84 */         row[i] = this.resultSet.getObject(i + 1);
       }
/*  86 */       return row;
     }
     catch (SQLException e) {
/*  89 */       throw new FaultException(e);
     }
   }
 
   public String getQuery() {
/*  94 */     return this.query;
   }
 
   public void setQuery(String query) {
/*  98 */     this.query = query;
   }
 
   public List<Parameter> getParameters() {
/* 102 */     return null;
   }
 
   public void setParameters(List<Parameter> parameters)
   {
   }
 }