/*     */ package dark.leech.text.util;
/*     */ 
/*     */ import dark.leech.text.action.Log;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileUtils
/*     */ {
/*     */   public static void mkdir(String dir) {
/*  17 */     File file = new File(validate(dir));
/*  18 */     if (file.exists() && 
/*  19 */       file.isDirectory())
/*     */       return; 
/*  21 */     String[] path = dir.split(Pattern.quote(AppUtils.SEPARATOR));
/*  22 */     if (path.length == 1)
/*     */       return; 
/*  24 */     dir = path[0];
/*  25 */     for (int i = 1; i < path.length; i++) {
/*  26 */       dir = dir + AppUtils.SEPARATOR + path[i];
/*  27 */       file = new File(dir);
/*  28 */       if (!file.exists())
/*  29 */         file.mkdir(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String file2string(String path, String charset) {
/*     */     try {
/*  35 */       return new String(file2byte(path), charset);
/*  36 */     } catch (Exception e) {
/*  37 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String file2string(String path) {
/*  42 */     return file2string(path, "UTF-8");
/*     */   }
/*     */   
/*     */   public static byte[] stream2byte(InputStream in) {
/*     */     try {
/*  47 */       byte[] bytes = new byte[in.available()];
/*  48 */       in.read(bytes);
/*  49 */       return bytes;
/*  50 */     } catch (Exception e) {
/*  51 */       return null;
/*     */     } finally {
/*     */       try {
/*  54 */         if (in != null)
/*  55 */           in.close(); 
/*  56 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String stream2string(String path) {
/*  62 */     InputStream in = null;
/*     */     try {
/*  64 */       in = FileUtils.class.getResourceAsStream(path);
/*  65 */       byte[] bytes = new byte[in.available()];
/*  66 */       in.read(bytes);
/*  67 */       return new String(bytes, "UTF-8");
/*  68 */     } catch (Exception e) {
/*  69 */       return "";
/*     */     } finally {
/*     */       try {
/*  72 */         if (in != null)
/*  73 */           in.close(); 
/*  74 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] file2byte(File file) {
/*  80 */     FileInputStream fi = null;
/*     */     try {
/*  82 */       if (!file.exists())
/*  83 */         return null; 
/*  84 */       fi = new FileInputStream(file);
/*  85 */       byte[] b = new byte[(int)file.length()];
/*  86 */       fi.read(b);
/*  87 */       return b;
/*  88 */     } catch (Exception e) {
/*  89 */       return null;
/*     */     } finally {
/*     */       try {
/*  92 */         if (fi != null) fi.close(); 
/*  93 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] file2byte(String path) {
/*  99 */     return file2byte(new File(validate(path)));
/*     */   }
/*     */   
/*     */   public static void string2file(String source, String savepath) {
/* 103 */     string2file(source, savepath, "UTF-8");
/*     */   }
/*     */   
/*     */   public static void string2file(String src, String svp, String charset) {
/*     */     try {
/* 108 */       byte2file(src.getBytes(charset), svp);
/* 109 */     } catch (Exception e) {
/* 110 */       Log.add(src + e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static synchronized void byte2file(byte[] source, String savepath) {
/* 115 */     FileOutputStream fo = null;
/*     */     try {
/* 117 */       File f = new File(validate(savepath));
/* 118 */       if (!f.exists())
/* 119 */         f.createNewFile(); 
/* 120 */       fo = new FileOutputStream(f);
/* 121 */       fo.write(source);
/* 122 */     } catch (Exception e) {
/* 123 */       Log.add(e);
/*     */     } finally {
/*     */       try {
/* 126 */         if (fo != null) fo.close(); 
/* 127 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void add2file(String str, File file, String charset) {
/* 133 */     FileOutputStream fo = null;
/*     */     try {
/* 135 */       fo = new FileOutputStream(file, true);
/* 136 */       fo.write(str.getBytes(charset));
/* 137 */     } catch (Exception e) {
/* 138 */       Log.add(e);
/*     */     } finally {
/*     */       try {
/* 141 */         if (fo != null) fo.close(); 
/* 142 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void add2file(String str, File file) {
/* 148 */     add2file(str, file, "UTF-8");
/*     */   }
/*     */   
/*     */   public static void add2file(File from, File src) {
/* 152 */     FileOutputStream fo = null;
/*     */     try {
/* 154 */       fo = new FileOutputStream(src, true);
/* 155 */       fo.write("\n".getBytes());
/* 156 */       fo.write(file2byte(from));
/* 157 */     } catch (Exception e) {
/* 158 */       Log.add(e);
/*     */     } finally {
/*     */       try {
/* 161 */         if (fo != null) fo.close(); 
/* 162 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void copyFile(String from, String to) {
/* 168 */     byte2file(file2byte(from), to);
/*     */   }
/*     */   
/*     */   public static void cutFile(String from, String to) {
/* 172 */     copyFile(from, to);
/* 173 */     deleteFile(from);
/*     */   }
/*     */   
/*     */   public static void url2file(String url, String savePath) {
/*     */     try {
/* 178 */       byte2file(Http.connect(url)
/* 179 */           .execute()
/* 180 */           .bodyAsBytes(), savePath);
/* 181 */     } catch (IOException e) {
/* 182 */       Log.add(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void deleteFile(String path) {
/*     */     try {
/* 188 */       File f = new File(validate(path));
/* 189 */       if (f.exists())
/* 190 */         f.delete(); 
/* 191 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized String validate(String path) {
/* 196 */     return path.replace("/", AppUtils.SEPARATOR);
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Desktop\2018\LeechText\tools\LeechText.jar!\dark\leech\tex\\util\FileUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */