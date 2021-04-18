/*    */ package dark.leech.text.ui.main;
/*    */ 
/*    */ import dark.leech.text.ui.Animation;
/*    */ import dark.leech.text.util.AppUtils;
/*    */ import dark.leech.text.util.SettingUtils;
/*    */ import java.awt.Point;
/*    */ import javax.swing.UIManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class App
/*    */ {
/*    */   private static MainUI mainFrame;
/*    */   private static int openCount;
/*    */   private static int closeCount;
/*    */   
/*    */   public static void main(String[] args) {
/* 18 */     (new Thread(new Runnable()
/*    */         {
/*    */           public void run() {
/*    */             try {
/* 22 */               UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
/* 23 */             } catch (Exception exception) {}
/*    */             
/* 25 */             AppUtils.doLoad();
/* 26 */             SettingUtils.doLoad();
/* 27 */             App.mainFrame = new MainUI();
/* 28 */             Animation.fadeIn(App.mainFrame);
/* 29 */             App.mainFrame.setVisible(true);
/* 30 */             AppUtils.LOCATION = new Point((App.mainFrame.getLocation()).x, (App.mainFrame.getLocation()).y + 20);
/*    */           }
/* 32 */         })).start();
/*    */   }
/*    */   
/*    */   public static MainUI getMain() {
/* 36 */     return mainFrame;
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Desktop\2018\LeechText\tools\LeechText.jar!\dark\leech\tex\\ui\main\App.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */