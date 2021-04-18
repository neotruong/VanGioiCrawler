/*     */ package dark.leech.text.plugin;
/*     */ 
/*     */ import dark.leech.text.util.ZipUtils;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PluginGetter
/*     */ {
/*     */   private File plugin;
/*     */   private String classInfoGetter;
/*     */   private String classListGetter;
/*     */   private String classChapGetter;
/*     */   private String classPageGetter;
/*     */   private String classLoginGetter;
/*     */   private String info;
/*     */   private String icon;
/*     */   public double version;
/*     */   private boolean forum;
/*     */   private boolean checked = true;
/*     */   private String match;
/*     */   private String demoUrl;
/*     */   private Class infoGetter;
/*     */   private Class listGetter;
/*     */   private Class chapGetter;
/*     */   private Class pageGetter;
/*     */   private Class loginGetter;
/*     */   
/*     */   public PluginGetter(File plugin, boolean checked) {
/*  34 */     this.plugin = plugin;
/*  35 */     this.checked = checked;
/*  36 */     load();
/*     */   }
/*     */   
/*     */   public void load() {
/*  40 */     JSONObject js = new JSONObject(ZipUtils.readInZipAsString(this.plugin, "plugin.json"));
/*  41 */     JSONObject metadata = js.getJSONObject("metadata");
/*  42 */     this.info = metadata.getString("info");
/*  43 */     this.version = metadata.getDouble("version");
/*  44 */     this.match = metadata.getString("regex");
/*     */     try {
/*  46 */       this.icon = metadata.getString("icon");
/*  47 */     } catch (Exception exception) {}
/*     */     
/*     */     try {
/*  50 */       this.demoUrl = metadata.getString("demo");
/*  51 */     } catch (Exception exception) {}
/*     */     
/*     */     try {
/*  54 */       this.forum = metadata.getBoolean("forum");
/*  55 */     } catch (Exception exception) {}
/*     */     
/*  57 */     JSONObject manifest = js.getJSONObject("class");
/*  58 */     this.classInfoGetter = manifest.getString("InfoGetter");
/*  59 */     this.classListGetter = manifest.getString("ListGetter");
/*     */     try {
/*  61 */       this.classLoginGetter = manifest.getString("LoginGetter");
/*  62 */     } catch (Exception exception) {}
/*     */     
/*  64 */     if (!isForum()) {
/*  65 */       this.classChapGetter = manifest.getString("ChapGetter");
/*     */     } else {
/*  67 */       this.classPageGetter = manifest.getString("PageGetter");
/*     */     } 
/*     */   }
/*     */   public boolean isMatch(String url) {
/*  71 */     return url.matches(this.match);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  75 */     return this.plugin.getName();
/*     */   }
/*     */   
/*     */   public String getInfo() {
/*  79 */     return this.info;
/*     */   }
/*     */   
/*     */   public String getVersion() {
/*  83 */     return Double.toString(this.version);
/*     */   }
/*     */   
/*     */   public void setChecked(boolean checked) {
/*  87 */     this.checked = checked;
/*     */   }
/*     */   
/*     */   public boolean isChecked() {
/*  91 */     return this.checked;
/*     */   }
/*     */   
/*     */   public File getFile() {
/*  95 */     return this.plugin;
/*     */   }
/*     */   
/*     */   public InputStream getIcon() {
/*     */     try {
/* 100 */       return new ByteArrayInputStream(ZipUtils.readInZipAsByte(this.plugin, this.icon));
/* 101 */     } catch (Exception e) {
/* 102 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isForum() {
/* 108 */     return this.forum;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDemoUrl() {
/* 113 */     if (this.demoUrl == null) this.demoUrl = getName(); 
/* 114 */     return this.demoUrl;
/*     */   }
/*     */   
/*     */   public Class InfoGetter() {
/* 118 */     if (this.infoGetter == null) {
/* 119 */       PluginLoader pluginLoader = new PluginLoader(ZipUtils.readInZipAsByte(this.plugin, this.classInfoGetter));
/* 120 */       this.infoGetter = pluginLoader.loadClass(parseClassName(this.classInfoGetter));
/*     */     } 
/* 122 */     return this.infoGetter;
/*     */   }
/*     */   
/*     */   public Class ListGetter() {
/* 126 */     if (this.listGetter == null) {
/* 127 */       PluginLoader pluginLoader = new PluginLoader(ZipUtils.readInZipAsByte(this.plugin, this.classListGetter));
/* 128 */       this.listGetter = pluginLoader.loadClass(parseClassName(this.classListGetter));
/*     */     } 
/* 130 */     return this.listGetter;
/*     */   }
/*     */   
/*     */   public Class ChapGetter() {
/* 134 */     if (this.chapGetter == null) {
/* 135 */       PluginLoader pluginLoader = new PluginLoader(ZipUtils.readInZipAsByte(this.plugin, this.classChapGetter));
/* 136 */       this.chapGetter = pluginLoader.loadClass(parseClassName(this.classChapGetter));
/*     */     } 
/* 138 */     return this.chapGetter;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class PageGetter() {
/* 143 */     if (this.pageGetter == null) {
/* 144 */       PluginLoader pluginLoader = new PluginLoader(ZipUtils.readInZipAsByte(this.plugin, this.classPageGetter));
/* 145 */       this.pageGetter = pluginLoader.loadClass(parseClassName(this.classPageGetter));
/*     */     } 
/* 147 */     return this.pageGetter;
/*     */   }
/*     */   
/*     */   public Class LoginGetter() {
/* 151 */     if (this.classLoginGetter == null) return null; 
/* 152 */     if (this.loginGetter == null) {
/* 153 */       PluginLoader pluginLoader = new PluginLoader(ZipUtils.readInZipAsByte(this.plugin, this.classLoginGetter));
/* 154 */       this.loginGetter = pluginLoader.loadClass(parseClassName(this.classLoginGetter));
/*     */     } 
/* 156 */     return this.loginGetter;
/*     */   }
/*     */   
/*     */   private String parseClassName(String path) {
/* 160 */     path = path.replaceAll("(.*?/|^)(.*?).class", "$2");
/* 161 */     return path;
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Desktop\2018\LeechText\tools\LeechText.jar!\dark\leech\text\plugin\PluginGetter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */