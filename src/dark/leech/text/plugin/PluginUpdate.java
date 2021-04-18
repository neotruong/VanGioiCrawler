/*    */ package dark.leech.text.plugin;
/*    */ 
/*    */ import dark.leech.text.action.Log;
/*    */ import dark.leech.text.ui.notification.Toast;
/*    */ import dark.leech.text.util.AppUtils;
/*    */ import dark.leech.text.util.FileUtils;
/*    */ import dark.leech.text.util.Http;
/*    */ import java.io.IOException;
/*    */ import org.json.JSONArray;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PluginUpdate
/*    */ {
/*    */   private static final String URL = "https://dl.dropboxusercontent.com/s/vws6ggvv96er1iv/plugin.json?dl=1";
/*    */   private static PluginUpdate pluginUpdate;
/*    */   
/*    */   public static PluginUpdate getUpdate() {
/* 25 */     if (pluginUpdate == null) pluginUpdate = new PluginUpdate(); 
/* 26 */     return pluginUpdate;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void checkUpdate() {
/*    */     try {
/* 34 */       String js = new String(Http.connect("https://dl.dropboxusercontent.com/s/vws6ggvv96er1iv/plugin.json?dl=1").execute().bodyAsBytes(), "UTF-8");
/* 35 */       JSONArray objArr = new JSONArray(js);
/* 36 */       for (int i = 0; i < objArr.length(); i++) {
/* 37 */         JSONObject obj = objArr.getJSONObject(i);
/* 38 */         boolean have = false;
/* 39 */         for (PluginGetter pluginGetter : PluginManager.getManager().list()) {
/* 40 */           if (obj.getString("name").equals(pluginGetter.getName())) {
/* 41 */             have = true;
/* 42 */             if (obj.getDouble("version") > pluginGetter.version) {
/* 43 */               FileUtils.url2file(obj.getString("url"), AppUtils.curDir + "/tools/plugins/" + pluginGetter.getName());
/* 44 */               pluginGetter.load();
/* 45 */               Toast.Build()
/* 46 */                 .content("Đã update plugin " + pluginGetter.getName().replace(".jar", "") + " v" + pluginGetter.getVersion())
/* 47 */                 .time(3000)
/* 48 */                 .open();
/*    */             } 
/*    */           } 
/*    */         } 
/* 52 */         if (!have) {
/* 53 */           FileUtils.url2file(obj.getString("url"), AppUtils.curDir + "/tools/plugins/" + obj.getString("name"));
/* 54 */           PluginManager.getManager().add(AppUtils.curDir + "/tools/plugins/" + obj.getString("name"));
/* 55 */           Toast.Build()
/* 56 */             .content("Đã tải xuống plugin " + obj.getString("name").replace(".jar", ""))
/* 57 */             .time(3000)
/* 58 */             .open();
/*    */         } 
/*    */       } 
/* 61 */     } catch (IOException e) {
/* 62 */       Log.add(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Desktop\2018\LeechText\tools\LeechText.jar!\dark\leech\text\plugin\PluginUpdate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */