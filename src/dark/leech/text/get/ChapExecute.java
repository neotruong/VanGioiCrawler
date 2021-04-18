/*    */ package dark.leech.text.get;
/*    */ 
/*    */ import dark.leech.text.action.Log;
/*    */ import dark.leech.text.listeners.ChangeListener;
/*    */ import dark.leech.text.models.Chapter;
/*    */ import dark.leech.text.util.AppUtils;
/*    */ import dark.leech.text.util.FileUtils;
/*    */ import dark.leech.text.util.SettingUtils;
/*    */ import dark.leech.text.util.SyntaxUtils;
/*    */ import javax.swing.SwingWorker;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChapExecute
/*    */   extends SwingWorker<String, Void>
/*    */ {
/*    */   private ChapGetter chapGetter;
/*    */   private Chapter chapter;
/*    */   private ChangeListener changeListener;
/*    */   private String text;
/*    */   private String savepath;
/* 22 */   private String chset = "utf-8";
/*    */ 
/*    */   
/*    */   protected String doInBackground() throws Exception {
/* 26 */     AppUtils.pause(SettingUtils.DELAY);
/* 27 */     return this.chapGetter.getter(this.chapter.getUrl());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void done() {
/*    */     try {
/* 33 */       this.text = get();
/* 34 */       this.chapter.setError(false);
/* 35 */       this.chapter.setEmpty(false);
/* 36 */       if (this.text == null || this.text.length() == 0) {
/* 37 */         this.chapter.setError(true);
/* 38 */       } else if (this.text.length() < 1000) {
/* 39 */         if ((this.text.split("<img ")).length > 1)
/* 40 */         { this.chapter.setImageChapter(true); }
/* 41 */         else { this.chapter.setEmpty(true); } 
/* 42 */       }  this.chapter.setCompleted(true);
/* 43 */       if (!this.chapter.isEmpty() || !this.chapter.isError())
/* 44 */         write(); 
/* 45 */     } catch (Exception e) {
/* 46 */       this.chapter.setError(true);
/*    */     } 
/* 48 */     this.changeListener.doChanger();
/*    */   }
/*    */ 
/*    */   
/*    */   public ChapExecute clazz(Class<ChapGetter> cl) {
/*    */     try {
/* 54 */       this.chapGetter = cl.newInstance();
/* 55 */     } catch (InstantiationException|IllegalAccessException e) {
/* 56 */       Log.add(e);
/*    */     } 
/* 58 */     return this;
/*    */   }
/*    */   
/*    */   public ChapExecute listener(ChangeListener changeListener) {
/* 62 */     this.changeListener = changeListener;
/* 63 */     return this;
/*    */   }
/*    */   
/*    */   public ChapExecute path(String savepath) {
/* 67 */     this.savepath = savepath;
/* 68 */     return this;
/*    */   }
/*    */   
/*    */   public ChapExecute charset(String chset) {
/* 72 */     this.chset = chset;
/* 73 */     return this;
/*    */   }
/*    */   
/*    */   public ChapExecute applyTo(Chapter chapter) {
/* 77 */     this.chapter = chapter;
/* 78 */     return this;
/*    */   }
/*    */   
/*    */   private void write() {
/* 82 */     FileUtils.string2file(Optimize(this.text), this.savepath + "/raw/" + this.chapter.getId() + ".txt", this.chset);
/*    */   }
/*    */ 
/*    */   
/*    */   private String Optimize(String src) {
/* 87 */     if (SyntaxUtils.REPLACE_FROM != null)
/* 88 */       for (int i = 0; i < SyntaxUtils.REPLACE_FROM.length; i++)
/* 89 */         src = src.replaceAll(SyntaxUtils.REPLACE_FROM[i], SyntaxUtils.REPLACE_TO[i]);  
/* 90 */     return src;
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Desktop\2018\LeechText\tools\LeechText.jar!\dark\leech\text\get\ChapExecute.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */