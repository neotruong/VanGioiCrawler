/*    */ package dark.leech.text.get;
/*    */ 
/*    */ import dark.leech.text.action.Log;
/*    */ import dark.leech.text.listeners.ChangeListener;
/*    */ import dark.leech.text.models.Chapter;
/*    */ import dark.leech.text.models.Pager;
/*    */ import dark.leech.text.models.Properties;
/*    */ import java.util.List;
/*    */ import javax.swing.SwingWorker;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListExecute
/*    */   extends SwingWorker
/*    */ {
/*    */   private ListGetter listGetter;
/*    */   private ChangeListener changeListener;
/*    */   private Properties properties;
/*    */   private boolean success;
/*    */   
/*    */   public ListExecute clazz(Class<ListGetter> cl) {
/*    */     try {
/* 23 */       this.listGetter = cl.newInstance();
/* 24 */     } catch (InstantiationException e) {
/* 25 */       Log.add(e);
/* 26 */     } catch (IllegalAccessException e) {
/* 27 */       Log.add(e);
/*    */     } 
/*    */     
/* 30 */     return this;
/*    */   }
/*    */   
/*    */   public ListExecute listener(ChangeListener changeListener) {
/* 34 */     this.changeListener = changeListener;
/* 35 */     return this;
/*    */   }
/*    */   
/*    */   public ListExecute applyTo(Properties properties) {
/* 39 */     this.properties = properties;
/* 40 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Void doInBackground() throws Exception {
/*    */     try {
/* 47 */       this.listGetter.getter(this.properties);
/* 48 */       this.success = true;
/* 49 */     } catch (Exception e) {
/* 50 */       Log.add(e);
/*    */     } 
/* 52 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void done() {
/* 57 */     if (this.success)
/* 58 */       if (this.properties.isForum()) {
/* 59 */         List<Pager> pageList = this.properties.getPageList();
/* 60 */         for (int i = 0; i < pageList.size(); i++) {
/* 61 */           Pager pager = pageList.get(i);
/* 62 */           if (pager.getName() == null)
/* 63 */             pager.setName("Trang " + Integer.toString(i + 1)); 
/* 64 */           pager.setId(i);
/*    */         } 
/*    */       } else {
/* 67 */         List<Chapter> chapList = this.properties.getChapList();
/* 68 */         for (int i = 0; i < chapList.size(); i++) {
/* 69 */           ((Chapter)chapList.get(i)).setId(i);
/*    */         }
/*    */       }  
/* 72 */     this.changeListener.doChanger();
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Desktop\2018\LeechText\tools\LeechText.jar!\dark\leech\text\get\ListExecute.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */