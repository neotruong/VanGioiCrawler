/*     */ package dark.leech.text.action;
/*     */ 
/*     */ import dark.leech.text.get.ChapExecute;
/*     */ import dark.leech.text.listeners.BlurListener;
/*     */ import dark.leech.text.listeners.ChangeListener;
/*     */ import dark.leech.text.listeners.TableListener;
/*     */ import dark.leech.text.models.Chapter;
/*     */ import dark.leech.text.models.Properties;
/*     */ import dark.leech.text.plugin.PluginGetter;
/*     */ import dark.leech.text.plugin.PluginManager;
/*     */ import dark.leech.text.ui.button.BasicButton;
/*     */ import dark.leech.text.ui.material.JMDialog;
/*     */ import dark.leech.text.util.FileUtils;
/*     */ import java.awt.Component;
/*     */ import java.awt.Desktop;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.swing.JLabel;
/*     */ 
/*     */ public class Config {
/*     */   private List<Chapter> chapList;
/*     */   private String path;
/*     */   private TableListener tableListener;
/*     */   private int index;
/*     */   private PluginGetter pluginGetter;
/*     */   private BlurListener blurListener;
/*     */   
/*     */   public Config(List<Chapter> chapList) {
/*  34 */     this.chapList = chapList;
/*     */   }
/*     */   
/*     */   public void setPath(String path) {
/*  38 */     this.path = path;
/*     */   }
/*     */   
/*     */   public List<Chapter> checkError() {
/*  42 */     ArrayList<Chapter> imgErr = new ArrayList<>();
/*  43 */     for (int i = 0; i < this.chapList.size(); i++) {
/*  44 */       if (((Chapter)this.chapList.get(i)).isError())
/*  45 */         imgErr.add(this.chapList.get(i)); 
/*  46 */     }  return imgErr;
/*     */   }
/*     */   
/*     */   public List<Chapter> checkImg() {
/*  50 */     ArrayList<Chapter> imgList = new ArrayList<>();
/*  51 */     for (int i = 0; i < this.chapList.size(); i++) {
/*  52 */       if (((Chapter)this.chapList.get(i)).isImageChapter())
/*  53 */         imgList.add(this.chapList.get(i)); 
/*     */     } 
/*  55 */     return imgList;
/*     */   }
/*     */   
/*     */   public ArrayList<Chapter> checkName() {
/*  59 */     ArrayList<Chapter> nameList = new ArrayList<>();
/*  60 */     for (Chapter c : this.chapList) {
/*  61 */       if (findMatchs(c.getChapName(), "^(Ch..ng|H.i) \\d+([\\+\\.-]\\d+|)") != 1) {
/*  62 */         nameList.add(c); continue;
/*  63 */       }  if (findMatchs(c.getChapName(), "Ch..ng \\d+") > 1)
/*  64 */         nameList.add(c); 
/*  65 */     }  return nameList;
/*     */   }
/*     */   
/*     */   private void splitPartName(Chapter chapter) throws Exception {
/*  69 */     if (chapter.getPartName().length() > 1)
/*  70 */       return;  String regex = "((Quy.n |Q.|Q)\\d+\\s*[:-](.*?)*)\\s*(([Cc]h..ng|Hồi)\\s+\\d+)";
/*  71 */     if (findMatchs(chapter.getChapName(), "(Quy.n |Q\\.|Q)\\d+([\\+\\.-]\\d+|)") == 1) {
/*  72 */       String partName = splitMatchs(chapter.getChapName(), regex, 1).replaceAll("\\s*[:-]\\s*$", "");
/*  73 */       String chapName = chapter.getChapName();
/*  74 */       chapName = chapName.replace(partName, "").replaceAll("^\\s*[:-]\\s*", "");
/*  75 */       partName = partName.replaceAll("Q\\.|Q(\\d+)", "Quyển $1");
/*  76 */       chapter.setChapName(chapName);
/*  77 */       chapter.setPartName(partName);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void autoFixName() {
/*  82 */     for (int i = 0; i < this.chapList.size(); i++) {
/*     */       try {
/*  84 */         Chapter chapter = new Chapter(((Chapter)this.chapList.get(i)).getUrl(), ((Chapter)this.chapList.get(i)).getId(), ((Chapter)this.chapList.get(i)).getPartName(), ((Chapter)this.chapList.get(i)).getChapName());
/*  85 */         splitPartName(chapter);
/*  86 */         chapter.setChapName(fixName(chapter.getChapName()));
/*  87 */         chapter.setPartName(fixName(chapter.getPartName()));
/*  88 */         this.tableListener.updateData(i, chapter);
/*  89 */       } catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String fixName(String name) {
/*  95 */     if (name == null) return ""; 
/*  96 */     if (name.length() == 0) return name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     name = name.replaceAll("Chương \\d+\\s*[:-]\\s*(Chương \\d+.*?$)", "$1").replaceAll("^([hH]ồi|[đĐ]ệ) (\\d+)", "Chương $1").replaceAll("(\\d+) [Cc]h..ng", "Chương $1").replaceAll("\\s+", " ").replaceAll("Chương (\\d+)\\s*[-\\+:]\\s*(\\d+)", "Chương $1+$2").replaceAll("(Chương \\d+)\\s*[;:-]+\\s*", "$1: ").replaceAll("(Chương \\d+\\+\\d+)\\s*[;:-]+\\s*", "$1: ");
/* 104 */     return name;
/*     */   }
/*     */   
/*     */   private String upperFirst(String name) {
/* 108 */     name = name.toLowerCase();
/* 109 */     char[] ch = name.toCharArray();
/* 110 */     ch[0] = Character.toUpperCase(ch[0]);
/* 111 */     for (int i = 1; i < ch.length; i++) {
/* 112 */       if (Character.toLowerCase(ch[i - 1]) == Character.toUpperCase(ch[i - 1]))
/* 113 */         ch[i] = Character.toUpperCase(ch[i]); 
/*     */     } 
/* 115 */     return new String(ch);
/*     */   }
/*     */   
/*     */   public void Optimize() {
/* 119 */     for (int i = 0; i < this.chapList.size(); i++) {
/*     */       try {
/* 121 */         Chapter chapter = new Chapter(((Chapter)this.chapList.get(i)).getUrl(), ((Chapter)this.chapList.get(i)).getId(), ((Chapter)this.chapList.get(i)).getPartName(), ((Chapter)this.chapList.get(i)).getChapName());
/* 122 */         chapter.setChapName(Optimize(chapter.getChapName()));
/* 123 */         chapter.setPartName(Optimize(chapter.getPartName()));
/* 124 */         this.tableListener.updateData(i, chapter);
/* 125 */       } catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String Optimize(String name) {
/* 131 */     if (name.length() == 0) return name; 
/* 132 */     name = upperFirst(name);
/* 133 */     name = fixName(name);
/* 134 */     return name;
/*     */   }
/*     */   
/*     */   public void downloadChap(Properties properties) {
/* 138 */     this.pluginGetter = PluginManager.getManager().get(properties.getUrl());
/* 139 */     download(properties);
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void download(final Properties properties) {
/* 144 */     (new ChapExecute())
/* 145 */       .clazz(this.pluginGetter.ChapGetter())
/* 146 */       .listener(new ChangeListener()
/*     */         {
/*     */           public void doChanger() {
/* 149 */             final Chapter chapter = Config.this.chapList.get(Config.this.index);
/* 150 */             if (chapter.isError()) {
/* 151 */               Config.ConfirmDialog confirmDialog = new Config.ConfirmDialog(chapter);
/* 152 */               confirmDialog.setBlurListener(Config.this.blurListener);
/* 153 */               confirmDialog.setConfirmListener(new Config.ConfirmListener()
/*     */                   {
/*     */                     public void confirm() {
/* 156 */                       Config.this.download(properties);
/*     */                     }
/*     */ 
/*     */                     
/*     */                     public void cancel() {
/* 161 */                       Config.this.tableListener.updateData(Config.this.index, chapter);
/* 162 */                       Config.this.index++;
/* 163 */                       if (Config.this.index >= Config.this.chapList.size()) {
/* 164 */                         History.getHistory().save(properties);
/*     */                         return;
/*     */                       } 
/* 167 */                       Config.this.download(properties);
/*     */                     }
/*     */                   });
/* 170 */               confirmDialog.open();
/*     */             } else {
/* 172 */               Config.this.tableListener.updateData(Config.this.index, chapter);
/* 173 */               Config.this.index++;
/* 174 */               if (Config.this.index >= Config.this.chapList.size()) {
/* 175 */                 History.getHistory().save(properties);
/*     */                 return;
/*     */               } 
/* 178 */               Config.this.download(properties);
/*     */             }
/*     */           
/*     */           }
/* 182 */         }).charset(properties.getCharset())
/* 183 */       .path(properties.getSavePath())
/* 184 */       .applyTo(this.chapList.get(this.index))
/* 185 */       .execute();
/*     */   }
/*     */   
/*     */   public void downloadImg() {
/* 189 */     for (int i = 0; i < this.chapList.size(); i++) {
/*     */       try {
/* 191 */         downloadImg(this.chapList.get(i));
/* 192 */         this.tableListener.updateData(i, this.chapList.get(i));
/* 193 */       } catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void downloadImg(Chapter chapter) {
/* 199 */     String text = FileUtils.file2string(this.path + "/raw/" + chapter.getId() + ".txt");
/* 200 */     ArrayList<String> imgList = new ArrayList<>();
/* 201 */     Pattern r = Pattern.compile("<img.*?src=\"(.*?)\"", 8);
/* 202 */     Matcher m = r.matcher(text);
/* 203 */     while (m.find())
/* 204 */       imgList.add(m.group(1)); 
/* 205 */     for (int i = 0; i < imgList.size(); i++) {
/* 206 */       String imgPath = imgList.get(i);
/* 207 */       if (imgPath.startsWith("http")) {
/* 208 */         String img = imgPath.substring(imgPath.lastIndexOf("."), imgPath.length()).toLowerCase();
/* 209 */         text = text.replace(imgPath, "../Images/" + chapter.getId() + "_" + Integer.toString(i) + img).replace("\">", "\"/>");
/* 210 */         img = this.path + "/data/Images/" + chapter.getId() + "_" + Integer.toString(i) + img;
/* 211 */         FileUtils.url2file(imgList.get(i), img);
/*     */       } 
/* 213 */     }  FileUtils.string2file(text, this.path + "/raw/" + chapter.getId() + ".txt");
/*     */   }
/*     */   
/*     */   private int findMatchs(String src, String regex) {
/* 217 */     if (src == null) return 0; 
/* 218 */     Pattern r = Pattern.compile(regex);
/* 219 */     Matcher m = r.matcher(src);
/* 220 */     int match = 0;
/* 221 */     while (m.find()) {
/* 222 */       match++;
/*     */     }
/* 224 */     return match;
/*     */   }
/*     */   
/*     */   private String splitMatchs(String src, String regex, int group) {
/* 228 */     if (src == null) return ""; 
/* 229 */     Pattern r = Pattern.compile(regex);
/* 230 */     Matcher m = r.matcher(src);
/* 231 */     String math = "";
/* 232 */     if (m.find())
/* 233 */       math = m.group(group); 
/* 234 */     return math;
/*     */   }
/*     */   
/*     */   public void setBlurListener(BlurListener blurListener) {
/* 238 */     this.blurListener = blurListener;
/*     */   }
/*     */   
/*     */   public void addTableListener(TableListener tableListener) {
/* 242 */     this.tableListener = tableListener;
/*     */   }
/*     */   
/*     */   public static interface ConfirmListener
/*     */   {
/*     */     void confirm();
/*     */     
/*     */     void cancel();
/*     */   }
/*     */   
/*     */   class ConfirmDialog
/*     */     extends JMDialog {
/*     */     private BasicButton btOk;
/*     */     private BasicButton btConfirm;
/*     */     private BasicButton btCancel;
/*     */     private Config.ConfirmListener confirmListener;
/*     */     private Chapter chapter;
/*     */     
/*     */     public ConfirmDialog(Chapter chapter) {
/* 261 */       this.chapter = chapter;
/* 262 */       setSize(300, 150);
/* 263 */       runOnUiThread(new Runnable()
/*     */           {
/*     */             public void run() {
/* 266 */               Config.ConfirmDialog.this.onCreate();
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onCreate() {
/* 273 */       super.onCreate();
/* 274 */       this.btOk = new BasicButton();
/* 275 */       this.btConfirm = new BasicButton();
/* 276 */       this.btCancel = new BasicButton();
/* 277 */       JLabel label = new JLabel("Lỗi: " + this.chapter.getChapName());
/* 278 */       JLabel label1 = new JLabel("Mở Browser???");
/*     */       
/* 280 */       this.btOk.setText("MỞ");
/* 281 */       this.btConfirm.setText("XÁC NHẬN");
/* 282 */       this.btCancel.setText("HỦY");
/*     */       
/* 284 */       this.container.add(label);
/* 285 */       label.setBounds(30, 10, 250, 30);
/* 286 */       this.container.add(label1);
/* 287 */       label1.setBounds(110, 50, 100, 30);
/* 288 */       this.container.add((Component)this.btOk);
/* 289 */       this.btOk.setBounds(10, 100, 100, 35);
/* 290 */       this.container.add((Component)this.btConfirm);
/* 291 */       this.btConfirm.setBounds(10, 100, 100, 35);
/* 292 */       this.container.add((Component)this.btCancel);
/* 293 */       this.btCancel.setBounds(190, 100, 100, 35);
/*     */       
/* 295 */       this.btConfirm.setVisible(false);
/* 296 */       this.btOk.addActionListener(new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent e) {
/*     */               try {
/* 300 */                 Desktop.getDesktop().browse((new URL(Config.ConfirmDialog.this.chapter.getUrl())).toURI());
/* 301 */               } catch (Exception exception) {}
/*     */               
/* 303 */               Config.ConfirmDialog.this.btConfirm.setVisible(true);
/* 304 */               Config.ConfirmDialog.this.btOk.setVisible(false);
/*     */             }
/*     */           });
/* 307 */       this.btConfirm.addActionListener(new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent e) {
/* 310 */               if (Config.ConfirmDialog.this.confirmListener != null)
/* 311 */                 Config.ConfirmDialog.this.confirmListener.confirm(); 
/* 312 */               Config.ConfirmDialog.this.close();
/*     */             }
/*     */           });
/* 315 */       this.btCancel.addActionListener(new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent e) {
/* 318 */               if (Config.ConfirmDialog.this.confirmListener != null)
/* 319 */                 Config.ConfirmDialog.this.confirmListener.cancel(); 
/* 320 */               Config.ConfirmDialog.this.close();
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setConfirmListener(Config.ConfirmListener confirmListener) {
/* 328 */       this.confirmListener = confirmListener;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Desktop\2018\LeechText\tools\LeechText.jar!\dark\leech\text\action\Config.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */