/*    */ package dark.leech.text.plugin;
/*    */ 
/*    */ import dark.leech.text.util.AppUtils;
/*    */ import dark.leech.text.util.FileUtils;
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PluginManager
/*    */ {
/*    */   private static PluginManager manager;
/*    */   private static ArrayList<PluginGetter> pluginList;
/*    */   
/*    */   private PluginManager() {
/* 19 */     (new Thread(new Runnable()
/*    */         {
/*    */           public void run() {
/* 22 */             PluginManager.pluginList = new ArrayList();
/* 23 */             File[] files = (new File(FileUtils.validate(AppUtils.curDir + "/tools/plugins"))).listFiles();
/* 24 */             if (files == null)
/* 25 */               return;  String js = FileUtils.file2string(AppUtils.curDir + "/tools/plugins/plugin.json");
/* 26 */             JSONObject obj = null;
/* 27 */             if (js != null)
/* 28 */               obj = new JSONObject(js); 
/* 29 */             for (File f : files) {
/* 30 */               if (f.getName().endsWith(".jar")) {
/*    */                 try {
/* 32 */                   PluginManager.pluginList.add(new PluginGetter(f, obj.getBoolean(f.getName())));
/* 33 */                 } catch (Exception e) {
/*    */                   try {
/* 35 */                     PluginManager.pluginList.add(new PluginGetter(f, true));
/* 36 */                   } catch (Exception exception) {}
/*    */                 } 
/*    */               }
/*    */             } 
/* 40 */             PluginUpdate.getUpdate().checkUpdate();
/*    */           }
/* 42 */         })).start();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static PluginManager getManager() {
/* 48 */     if (manager == null) manager = new PluginManager(); 
/* 49 */     return manager;
/*    */   }
/*    */   
/*    */   public void add(String path) {
/* 53 */     pluginList.add(new PluginGetter(new File(path), true));
/*    */   }
/*    */   
/*    */   public PluginGetter get(String url) {
/* 57 */     for (PluginGetter pluginGetter : pluginList) {
/* 58 */       if (pluginGetter.isMatch(url)) return pluginGetter; 
/* 59 */     }  return null;
/*    */   }
/*    */   
/*    */   public ArrayList<PluginGetter> list() {
/* 63 */     return pluginList;
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Desktop\2018\LeechText\tools\LeechText.jar!\dark\leech\text\plugin\PluginManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */