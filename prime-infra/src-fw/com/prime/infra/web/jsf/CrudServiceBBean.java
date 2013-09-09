 package com.prime.infra.web.jsf;
 
 import com.prime.app.service.CrudService;

 import java.io.Serializable;
 import java.lang.reflect.ParameterizedType;
 import java.lang.reflect.Type;
 import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
 
 public class CrudServiceBBean<T, ID extends Serializable> extends BasicBBean
   implements Serializable
 {
   private transient CrudService service;
   private Class<T> entityType;
 
   public CrudServiceBBean()
   {
/* 28 */     Type genericSuperclass = super.getClass().getGenericSuperclass();
/* 29 */     ParameterizedType type = (ParameterizedType)genericSuperclass;
/* 30 */     this.entityType = ((Class)type.getActualTypeArguments()[0]);
   }
 
   @Autowired
   public void setBasicService(CrudService service)
   {
/* 40 */     this.service = service;
   }
 
   protected T save(T entity)
   {
/* 50 */     return this.service.save(entity);
   }
 
   protected List<T> findAll(int maxResults)
   {
/* 60 */     return this.service.findAll(this.entityType, maxResults);
   }
 
   protected T get(ID id)
   {
/* 70 */     return this.service.get(this.entityType, id, false);
   }
 
   protected void remove(ID id)
   {
/* 79 */     this.service.remove(this.entityType, id);
   }
 }