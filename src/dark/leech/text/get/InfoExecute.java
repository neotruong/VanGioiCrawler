/*    */ package dark.leech.text.get;
/*    */ 
/*    */ import dark.leech.text.action.Log;
/*    */ import dark.leech.text.listeners.ChangeListener;
/*    */ import dark.leech.text.models.Properties;
/*    */ import dark.leech.text.util.SyntaxUtils;
/*    */ import javax.swing.SwingWorker;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InfoExecute
/*    */   extends SwingWorker
/*    */ {
/*    */   private InfoGetter infoGetter;
/*    */   private ChangeListener changeListener;
/*    */   private Properties properties;
/*    */   private boolean success;
/*    */   
/*    */   public InfoExecute clazz(Class<InfoGetter> cl) {
/*    */     try {
/* 21 */       this.infoGetter = cl.newInstance();
/* 22 */     } catch (InstantiationException e) {
/* 23 */       Log.add(e);
/* 24 */     } catch (IllegalAccessException e) {
/* 25 */       Log.add(e);
/*    */     } 
/* 27 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoExecute listener(ChangeListener changeListener) {
/* 32 */     this.changeListener = changeListener;
/* 33 */     return this;
/*    */   }
/*    */   
/*    */   public InfoExecute applyTo(Properties properties) {
/* 37 */     this.properties = properties;
/* 38 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Void doInBackground() throws Exception {
/*    */     try {
/* 45 */       this.infoGetter.getter(this.properties);
/* 46 */       this.success = true;
/* 47 */     } catch (Exception exception) {}
/*    */     
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void done() {
/* 54 */     if (this.success && 
/* 55 */       this.properties.getGioiThieu() != null)
/* 56 */       this.properties.setGioiThieu("<p>" + SyntaxUtils.Optimize(this.properties.getGioiThieu()).replace("\n", "</p>\n<p>") + "</p>"); 
/* 57 */     this.changeListener.doChanger();
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Desktop\2018\LeechText\tools\LeechText.jar!\dark\leech\text\get\InfoExecute.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */