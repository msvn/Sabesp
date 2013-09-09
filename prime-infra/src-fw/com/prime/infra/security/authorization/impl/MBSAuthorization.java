 package com.prime.infra.security.authorization.impl;
 
 import br.com.gpnet.mbs.caller.MBSCaller;
 import br.com.gpnet.mbs.caller.MBSCaller.RetornoRecuperaHierarquiaFuncao;
 import br.com.gpnet.mbs.caller.MBSCallerImpl;
 import br.com.gpnet.mbs.caller.MBSException;
 import com.prime.infra.security.SecurityInfo;
 import com.prime.infra.security.authorization.Authorization;
 import com.prime.infra.security.authorization.AuthorizationException;
 import com.prime.infra.security.authorization.menu.MBSMenuItem;
 import com.prime.infra.security.authorization.menu.MenuItem;
 import com.prime.infra.security.authorization.menu.MenuItemUserObject;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.Collection;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.swing.tree.DefaultMutableTreeNode;
 import javax.swing.tree.DefaultTreeModel;
 import javax.swing.tree.TreeModel;
 import org.apache.commons.collections.map.LRUMap;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.InitializingBean;
 
 public class MBSAuthorization
   implements Authorization, InitializingBean
 {
/*  38 */   private static final Logger LOGGER = LoggerFactory.getLogger(MBSAuthorization.class);
   private static final String NO_PARENT = "0";
   private String url;
   private MBSCaller mbs;
   private Map<String, UserFunctions> functionsCache;
   private int cacheSize;
   private long timeout;
 
   public MBSAuthorization()
   {
/*  48 */     this.timeout = 1200000L; }
 
   public void afterPropertiesSet() throws Exception {
     try {
/*  52 */       this.mbs = MBSCallerImpl.getInstance(new URL(this.url));
     }
     catch (Exception e) {
/*  55 */       throw new IllegalArgumentException("Erro no acesso ao MBS: " + e.getMessage(), e);
     }
/*  57 */     this.functionsCache = new LRUMap(this.cacheSize);
   }
 
   public void setUrl(String url) {
/*  61 */     this.url = url;
   }
 
   public void setCacheSize(int cacheSize) {
/*  65 */     this.cacheSize = cacheSize;
   }
 
   public void setTimeout(long timeout) {
/*  69 */     this.timeout = timeout;
   }
 
   public boolean isUserAuthorized(SecurityInfo securityInfo, String object) {
/*  73 */     String userName = securityInfo.getUserName();
/*  74 */     String systemId = securityInfo.getSystemId();
/*  75 */     LOGGER.debug("userName = " + userName + " systemId = " + systemId + " object = " + object);
 
/*  77 */     String cacheKey = userName + systemId;
/*  78 */     LOGGER.trace("cacheKey = {}", cacheKey);
 
/*  81 */     UserFunctions userFunctions = null;
/*  82 */     synchronized (this.functionsCache) {
/*  83 */       userFunctions = (UserFunctions)this.functionsCache.get(cacheKey);
/*  84 */       if ((userFunctions == null) || (userFunctions.isExpired()))
       {
         try
         {
/*  89 */           LOGGER.trace("Retrieving functions from MBS...");
/*  90 */           userFunctions = new UserFunctions(getFunctions(userName, systemId));
/*  91 */           this.functionsCache.put(cacheKey, userFunctions);
/*  92 */           LOGGER.trace("Added to functionsCache");
         }
         catch (MBSException e) {
/*  95 */           LOGGER.error("Error retrieving data for user[" + userName + "] and system[" + systemId + "]");
/*  96 */           return false;
         }
       }
 
     }
 
/* 102 */     if (checkAuthorization(userFunctions, securityInfo, object)) {
/* 103 */       if (LOGGER.isDebugEnabled()) {
/* 104 */         LOGGER.debug("MBS autorizado. userName = " + userName + " systemId = " + systemId + " object = " + object);
       }
/* 106 */       return true;
     }
 
/* 110 */     if (LOGGER.isDebugEnabled()) {
/* 111 */       LOGGER.debug("MBS nao autorizado! userName = " + userName + " systemId = " + systemId + " object = " + object);
     }
/* 113 */     return false;
   }
 
   private Collection<String> getFunctions(String user, String system)
     throws MBSException
   {
     int i;
/* 118 */     LOGGER.trace("getFunctions({},{})", user, system);
 
/* 120 */     MBSCaller.RetornoRecuperaHierarquiaFuncao hiearquia = this.mbs.recuperaHierarquiaFuncao(user, system);
 
/* 122 */     Collection<String> funcoes = Arrays.asList(this.mbs.recuperarFuncaoSistema(user, system).split(";"));
 
/* 124 */     if (LOGGER.isTraceEnabled()) {
/* 125 */       for (String f : funcoes) {
/* 126 */         LOGGER.trace("Hiearquia: " + f);
       }
     }
 
/* 130 */     if (LOGGER.isTraceEnabled()) {
/* 131 */       i = 0;
/* 132 */       for (String f : funcoes) {
/* 133 */         LOGGER.trace("Funcoes [" + i + "]: " + f);
/* 134 */         ++i;
       }
     }
 
/* 138 */     return funcoes;
   }
 
   private boolean checkAuthorization(UserFunctions functions, SecurityInfo securityInfo, String object) {
/* 142 */     StringBuilder mbsFunction = new StringBuilder();
/* 143 */     mbsFunction.append(securityInfo.getSystemId());
/* 144 */     mbsFunction.append("-");
/* 145 */     mbsFunction.append(object);
 
/* 147 */     String func = mbsFunction.toString().toUpperCase();
/* 148 */     LOGGER.debug("func = {}", func);
/* 149 */     boolean b = functions.hasFunction(func);
/* 150 */     LOGGER.debug("checkAuthorization = {}", Boolean.valueOf(b));
/* 151 */     return b;
   }
 
   public Object getAuthorizationProvider() {
/* 155 */     return this.mbs;
   }
 
   public TreeModel getMenuTree(String user, String system) {
/* 159 */     return treeMenuAssembler(getMenuItems(user, system));
   }
 
   private List<MBSMenuItem> getMenuItems(String user, String system) {
/* 163 */     List menuItens = new ArrayList();
     try
     {
/* 167 */       MBSCaller.RetornoRecuperaHierarquiaFuncao hiearquia = this.mbs.recuperaHierarquiaFuncao(user, system);
/* 168 */       for (int i = 0; i < hiearquia.hierarquia.length; ++i) {
/* 169 */         MBSMenuItem menuItem = new MBSMenuItem();
/* 170 */         menuItem.setNomeFuncao(hiearquia.hierarquia[i][0]);
/* 171 */         menuItem.setNomeFuncaoPai(hiearquia.hierarquia[i][1]);
/* 172 */         menuItem.setDescricao(hiearquia.hierarquia[i][2]);
/* 173 */         menuItem.setUrl(hiearquia.hierarquia[i][3]);
/* 174 */         menuItem.setToken(hiearquia.hierarquia[i][4]);
/* 175 */         menuItens.add(menuItem);
       }
     }
     catch (MBSException e) {
/* 179 */       throw new AuthorizationException("Impossible to retrieve user's data.User: " + user + ", System: " + system, e);
     }
 
/* 182 */     return menuItens;
   }
 
   private DefaultTreeModel treeMenuAssembler(List<MBSMenuItem> menuItens) {
/* 186 */     DefaultTreeModel result = null;
/* 187 */     List parents = new ArrayList();
/* 188 */     String lastParent = "0";
/* 189 */     String possibleParent = "";
/* 190 */     int level = 0;
 
/* 192 */     Map levelParent = new HashMap();
/* 193 */     Map parentNodes = new HashMap();
 
/* 196 */     DefaultMutableTreeNode possibleParentNode = null;
 
/* 198 */     for (MBSMenuItem menuItem : menuItens) {
/* 199 */       if (menuItem.getNomeFuncaoPai().equals("0")) {
/* 200 */         if (LOGGER.isDebugEnabled()) {
/* 201 */           LOGGER.debug("Root: " + menuItem.getDescricao() + "[" + menuItem.getNomeFuncao() + "]");
         }
/* 203 */         lastParent = menuItem.getNomeFuncao();
/* 204 */         parents.add(lastParent);
/* 205 */         levelParent.put(menuItem.getNomeFuncao(), Integer.valueOf(level));
/* 206 */         DefaultMutableTreeNode rootNode = addNode(null, menuItem.getDescricao(), menuItem);
/* 207 */         parentNodes.put(menuItem.getNomeFuncao(), rootNode);
/* 208 */         result = new DefaultTreeModel(rootNode);
       }
       else
       {
         DefaultMutableTreeNode leafNode;
/* 212 */         if (parents.contains(menuItem.getNomeFuncaoPai())) {
/* 213 */           ++level;
/* 214 */           possibleParent = menuItem.getNomeFuncao();
/* 215 */           if (levelParent.get(menuItem.getNomeFuncaoPai()) != null) {
/* 216 */             level = ((Integer)levelParent.get(menuItem.getNomeFuncaoPai())).intValue();
           }
/* 218 */           if (LOGGER.isDebugEnabled()) {
/* 219 */             LOGGER.debug(indent(level) + "[" + menuItem.getNomeFuncaoPai() + "]" + "Leaf: " + menuItem.getNomeFuncao());
           }
 
/* 222 */           leafNode = addNode((DefaultMutableTreeNode)parentNodes.get(menuItem.getNomeFuncaoPai()), menuItem.getDescricao(), menuItem);
/* 223 */           possibleParentNode = leafNode;
         }
/* 225 */         else if (possibleParent.equals(menuItem.getNomeFuncaoPai()))
         {
/* 227 */           lastParent = possibleParent;
/* 228 */           parentNodes.put(menuItem.getNomeFuncaoPai(), possibleParentNode);
/* 229 */           ++level;
/* 230 */           possibleParent = menuItem.getNomeFuncao();
/* 231 */           if (LOGGER.isDebugEnabled()) {
/* 232 */             LOGGER.debug(indent(level) + "[" + menuItem.getNomeFuncaoPai() + "]" + "Leaf: " + menuItem.getNomeFuncao());
           }
 
/* 235 */           leafNode = addNode((DefaultMutableTreeNode)parentNodes.get(menuItem.getNomeFuncaoPai()), menuItem.getDescricao(), menuItem);
/* 236 */           parents.add(lastParent);
/* 237 */           levelParent.put(menuItem.getNomeFuncaoPai(), Integer.valueOf(level));
         }
         else
         {
/* 241 */           throw new AuthorizationException("Please check the data in MBS database. Element [" + menuItem.getDescricao() + "] has no parent.");
         }
       }
     }
 
/* 246 */     return result;
   }
 
   private DefaultMutableTreeNode addNode(DefaultMutableTreeNode parent, String caption, MenuItem menuItem) {
/* 250 */     DefaultMutableTreeNode node = new DefaultMutableTreeNode();
/* 251 */     MenuItemUserObject userObject = new MenuItemUserObject(node);
/* 252 */     node.setUserObject(userObject);
/* 253 */     userObject.setMenuItem(menuItem);
 
/* 255 */     if (caption != null) {
/* 256 */       userObject.setText(caption);
     }
     else {
/* 259 */       userObject.setText(menuItem.getDescricao());
     }
 
/* 262 */     if (parent != null) {
/* 263 */       parent.add(node);
     }
 
/* 266 */     return node;
   }
 
   private String indent(int level) {
/* 270 */     StringBuilder result = new StringBuilder();
/* 271 */     for (int i = 0; i < level; ++i) {
/* 272 */       result.append("\t");
     }
/* 274 */     return result.toString();
   }
 
   private class UserFunctions {
     private Collection<String> functions;
/* 279 */     private Long createdTime = Long.valueOf(System.currentTimeMillis());
     private Long timeout;
 
     public UserFunctions(Collection<String> functions) {
/* 283 */       this.functions = functions;
/* 284 */       this.timeout = timeout;
     }
 
     public synchronized void addFunction(String function) {
/* 288 */       this.functions.add(function);
     }
 
     public synchronized boolean hasFunction(String function) {
/* 292 */       return this.functions.contains(function);
     }
 
     public void updateFunctions(Collection<String> functions) {
/* 296 */       this.functions = functions;
     }
 
     public boolean isExpired() {
/* 300 */       long expiredTime = this.createdTime.longValue() + this.timeout.longValue();
/* 301 */       boolean isExpired = expiredTime < System.currentTimeMillis();
/* 302 */       if (isExpired)
/* 303 */         MBSAuthorization.LOGGER.trace("User Functions is expired");
       else
/* 305 */         MBSAuthorization.LOGGER.trace("User Functions is not expired (" + expiredTime + ")");
/* 306 */       return isExpired;
     }
   }
 }