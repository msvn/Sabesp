 package com.prime.infra.security.authorization.impl;
 
 import com.prime.infra.security.SecurityInfo;
 import com.prime.infra.security.authorization.Authorization;
 import com.prime.infra.security.authorization.menu.MenuItem;
 import com.icesoft.faces.component.tree.IceUserObject;
 import javax.swing.tree.DefaultMutableTreeNode;
 import javax.swing.tree.DefaultTreeModel;
 import javax.swing.tree.MutableTreeNode;
 import javax.swing.tree.TreeModel;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public class DummyAuthorization
   implements Authorization
 {
/*  22 */   private static Logger logger = LoggerFactory.getLogger(DummyAuthorization.class);
   protected final String[][] pages;
 
   public DummyAuthorization()
   {
/*  33 */     this.pages = new String[][] { { "Emissão de Relatórios", "/pages/relatorios/rel_estoque_1.iface", "/pages/relatorios/rel_estoque_2.iface", "/pages/relatorios/rel_pedido_1.iface", "/pages/relatorios/rel_pedido_2.iface" }, { "Acompanhamento de pedidos", "/pages/pedidos.iface" }, { "Consulta de Estoque", "/pages/estoque.iface" } };
   }
 
   public boolean isUserAuthorized(SecurityInfo securityInfo, String object)
   {
/*  25 */     logger.debug("userName = " + securityInfo.getUserName());
/*  26 */     return (!(securityInfo.getUserName().equals("hacker")));
   }
 
   public Object getAuthorizationProvider() {
/*  30 */     return null;
   }
 
   public TreeModel getMenuTree(String user, String system)
   {
/*  46 */     DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
/*  47 */     rootNode.setAllowsChildren(true);
/*  48 */     MenuItemHolder holder = new MenuItemHolder(rootNode, new MenuNode("Menu", ""));
/*  49 */     holder.setLeaf(false);
/*  50 */     rootNode.setUserObject(holder);
/*  51 */     for (int i = 0; i < this.pages.length; ++i) {
/*  52 */       rootNode.insert(readLeaf(this.pages[i]), i);
     }
/*  54 */     return new DefaultTreeModel(rootNode);
   }
 
   protected MutableTreeNode readLeaf(String[] pages) {
/*  58 */     DefaultMutableTreeNode parent = new DefaultMutableTreeNode();
/*  59 */     parent.setAllowsChildren(true);
/*  60 */     if (pages.length == 2) {
/*  61 */       MenuItemHolder holder = new MenuItemHolder(parent, new MenuNode(pages[0], pages[1]));
/*  62 */       holder.setLeaf(false);
/*  63 */       parent.setUserObject(holder);
     }
     else {
/*  66 */       parent.setUserObject(new MenuItemHolder(parent, new MenuNode(pages[0], "")));
/*  67 */       for (int i = 1; i < pages.length; ++i) {
/*  68 */         DefaultMutableTreeNode node = new DefaultMutableTreeNode();
/*  69 */         MenuItemHolder holder = new MenuItemHolder(parent, new MenuNode(pages[0] + " - " + i, pages[i]));
/*  70 */         holder.setLeaf(true);
/*  71 */         node.setUserObject(holder);
/*  72 */         node.setAllowsChildren(false);
/*  73 */         parent.insert(node, i - 1);
       }
     }
 
/*  77 */     return parent;
   }
 
   public static class MenuNode
     implements MenuItem
   {
     private String url;
     private String name;
 
     public MenuNode(String name, String url)
     {
/* 103 */       this.url = url;
/* 104 */       this.name = name;
     }
 
     public String getUrl() {
/* 108 */       return this.url;
     }
 
     public String getName() {
/* 112 */       return this.name;
     }
 
     public String getDescricao() {
/* 116 */       return getName();
     }
   }
 
   public static class MenuItemHolder extends IceUserObject
   {
     private MenuItem menuItem;
 
     public MenuItemHolder(DefaultMutableTreeNode wrapper, MenuItem menuItem)
     {
/*  86 */       super(wrapper);
/*  87 */       this.menuItem = menuItem;
/*  88 */       setExpanded(true);
     }
 
     public MenuItem getMenuItem() {
/*  92 */       return this.menuItem;
     }
   }
 }