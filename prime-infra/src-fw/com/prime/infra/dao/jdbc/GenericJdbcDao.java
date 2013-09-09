 package com.prime.infra.dao.jdbc;
 
 import javax.sql.DataSource;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
 
 public abstract class GenericJdbcDao extends SimpleJdbcDaoSupport
 {
   @Autowired
   public void initDataSource(DataSource dataSource)
   {
/* 16 */     setDataSource(dataSource);
   }
 }