/*    */ package dark.leech.text.plugin;
/*    */ 
/*    */ import dark.leech.text.action.Log;
/*    */ 
/*    */ 
/*    */ public class PluginLoader
/*    */   extends ClassLoader
/*    */ {
/*    */   private byte[] plugin;
/*    */   
/*    */   public PluginLoader(byte[] plugin) {
/* 12 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   public Class loadClass(String name) {
/* 16 */     return loadClass(name, true);
/*    */   }
/*    */   
/*    */   public Class loadClass(String classname, boolean resolve) {
/* 20 */     if (classname == null || classname.length() == 0) return null; 
/*    */     try {
/* 22 */       Class<?> c = findLoadedClass(classname);
/* 23 */       if (c == null) {
/*    */         try {
/* 25 */           c = findSystemClass(classname);
/* 26 */         } catch (Exception exception) {}
/*    */       }
/* 28 */       if (c == null)
/* 29 */         c = defineClass(classname, this.plugin, 0, this.plugin.length); 
/* 30 */       if (resolve)
/* 31 */         resolveClass(c); 
/* 32 */       return c;
/* 33 */     } catch (Exception ex) {
/* 34 */       Log.add(ex);
/* 35 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Desktop\2018\LeechText\tools\LeechText.jar!\dark\leech\text\plugin\PluginLoader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */