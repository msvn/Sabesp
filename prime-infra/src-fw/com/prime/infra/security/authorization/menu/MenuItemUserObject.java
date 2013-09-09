 package com.prime.infra.security.authorization.menu;
 
 import javax.swing.tree.DefaultMutableTreeNode;
 
 public class MenuItemUserObject extends NodeUserObject
 {
   private MenuItem menuItem;
 
   public MenuItemUserObject(DefaultMutableTreeNode defaultMutableTreeNode)
   {
/* 14 */     super(defaultMutableTreeNode);
/* 15 */     setExpanded(true);
   }
 
   public MenuItem getMenuItem() {
/* 19 */     return this.menuItem;
   }
 
   public void setMenuItem(MenuItem menuItem) {
/* 23 */     this.menuItem = menuItem;
   }
 }