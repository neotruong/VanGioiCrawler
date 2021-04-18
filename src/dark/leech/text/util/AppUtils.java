/*    */ package dark.leech.text.util;
/*    */ 
/*    */ import java.awt.GraphicsDevice;
/*    */ import java.awt.GraphicsEnvironment;
/*    */ import java.awt.Point;
/*    */ import org.json.JSONArray;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AppUtils
/*    */ {
/*    */   public static final String VERSION = "2018.09.08";
/*    */   public static final String TIME = "12:00";
/* 17 */   public static String curDir = System.getProperty("user.dir");
/* 18 */   public static final String SEPARATOR = System.getProperty("file.separator");
/* 19 */   public static Point LOCATION = new Point();
/* 20 */   private static final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
/* 21 */   public static final int width = gd.getDisplayMode().getWidth();
/* 22 */   public static final int height = gd.getDisplayMode().getHeight();
/*    */ 
/*    */   
/*    */   public static void doLoad() {
/*    */     try {
/* 27 */       if (curDir.endsWith(SEPARATOR)) curDir = curDir.substring(0, curDir.length() - 1); 
/* 28 */       JSONObject json = new JSONObject(FileUtils.file2string(curDir + "/tools/syntax.json"));
/* 29 */       JSONObject find = json.getJSONObject("find");
/* 30 */       SyntaxUtils.CHAP_NAME = find.getString("chap");
/* 31 */       SyntaxUtils.PART_NAME = find.getString("part");
/* 32 */       JSONArray optimize = json.getJSONArray("optimize");
/* 33 */       String[] REPLACE_FROM = new String[optimize.length()];
/* 34 */       String[] REPLACE_TO = new String[optimize.length()];
/* 35 */       for (int i = 0; i < optimize.length(); i++) {
/* 36 */         REPLACE_FROM[i] = optimize.getJSONObject(i).getString("replace");
/* 37 */         REPLACE_TO[i] = optimize.getJSONObject(i).getString("to");
/*    */       } 
/* 39 */       SyntaxUtils.REPLACE_FROM = REPLACE_FROM;
/* 40 */       SyntaxUtils.REPLACE_TO = REPLACE_TO;
/*    */     }
/* 42 */     catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getX() {
/* 47 */     return LOCATION.x;
/*    */   }
/*    */   
/*    */   public static int getY() {
/* 51 */     return LOCATION.y;
/*    */   }
/*    */   
/*    */   public static Point getLocation() {
/* 55 */     return LOCATION;
/*    */   }
/*    */   
/*    */   public static void pause(int milliseconds) {
/*    */     try {
/* 60 */       Thread.sleep(milliseconds);
/* 61 */     } catch (InterruptedException interruptedException) {}
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Desktop\2018\LeechText\tools\LeechText.jar!\dark\leech\tex\\util\AppUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */