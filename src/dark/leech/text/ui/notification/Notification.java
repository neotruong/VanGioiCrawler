/*     */ package dark.leech.text.ui.notification;
import dark.leech.text.action.export.Text;
/*     */ 
/*     */ import dark.leech.text.image.ImageLabel;
/*     */ import dark.leech.text.util.AppUtils;
/*     */ import dark.leech.text.util.FontUtils;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
/*     */ import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
import java.util.Base64;
/*     */ import java.util.Date;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.swing.JLabel;
import javax.swing.JOptionPane;
/*     */ import javax.swing.JWindow;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.border.LineBorder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.zeroturnaround.zip.commons.IOUtils;
/*     */ 
/*     */ public class Notification
/*     */   extends JWindow
/*     */ {
/*     */   public static int pointX;
			static public JSONObject finalJson = new JSONObject();
/*     */   public static int pointY;
/*     */   private String name;
/*  25 */   private int timeDelay = 5000; private String contentNotification; private String imagePath; private InputStream imageStream;
/*     */   private boolean close = false;
/*     */   
/*     */   public static Notification build() {
/*  29 */     return new Notification();
/*     */   }
/*     */   
/*     */   public Notification title(String name) {
/*  33 */     this.name = name;
/*  34 */     return this;
/*     */   }
/*     */   
/*     */   public Notification content(String contentNotification) {
/*  38 */     this.contentNotification = contentNotification;
/*  39 */     return this;
/*     */   }
/*     */   
/*     */   public Notification path(String imagePath) {
/*  43 */     this.imagePath = imagePath;
/*  44 */     return this;
/*     */   }
/*     */   
/*     */   public Notification input(InputStream imageStream) {
/*  48 */     this.imageStream = imageStream;
/*  49 */     return this;
/*     */   }
/*     */   
/*     */   public Notification delay(int timeDelay) {
/*  53 */     this.timeDelay = timeDelay;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void onCreate() {
	
/*  60 */     setAlwaysOnTop(true);
/*  61 */     setSize(280, 90);
/*  62 */     ImageLabel lbCover = new ImageLabel();
/*  63 */     JLabel lbName = new JLabel();
/*  64 */     JLabel lbNoti = new JLabel();
/*  65 */     JLabel lbTime = new JLabel();
/*  66 */     Container panel1 = getContentPane();
/*  67 */     panel1.setBackground(Color.WHITE);
/*  68 */     panel1.setLayout((LayoutManager)null);
/*     */     
/*  70 */     lbCover.path(this.imagePath).input(this.imageStream).load();
/*  71 */     lbCover.setOpaque(true);
/*  72 */     panel1.add((Component)lbCover);
/*  73 */     lbCover.setBounds(5, 5, 54, 81);
/*     */ 
/*     */     
/*  76 */     lbName.setText(this.name);
/*  77 */     lbName.setFont(FontUtils.TEXT_NORMAL);
/*  78 */     panel1.add(lbName);
/*  79 */     lbName.setBounds(70, 5, 175, 30);
/*     */ 
/*     */     
/*  82 */     lbNoti.setText(this.contentNotification);
/*  83 */     lbNoti.setFont(FontUtils.TEXT_NORMAL);
/*  84 */     panel1.add(lbNoti);
/*  85 */     lbNoti.setBounds(70, 35, 170, 30);
/*     */ 
/*     */     
/*  88 */     Date todaysDate = new Date();
/*  89 */     DateFormat df = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
/*  90 */     lbTime.setText(df.format(todaysDate));
/*  91 */     lbTime.setFont(FontUtils.TEXT_THIN);
/*  92 */     lbTime.setHorizontalAlignment(4);
/*  93 */     panel1.add(lbTime);
/*  94 */     lbTime.setBounds(130, 65, 140, 20);
/*  95 */     getRootPane().setBorder(new LineBorder(Color.LIGHT_GRAY, 1));

			
/*     */   }
/*     */ 
/*     */   
/*     */   public void processCallPost() throws Exception {
				if(Text.chapArray.length() != 0) { 
					
					finalJson.put("listChaps",Text.chapArray);
					imageStream = new FileInputStream(this.imagePath);
					byte[] imgByte = IOUtils.toByteArray(imageStream);
					String base64 = Base64.getEncoder().encodeToString(imgByte);
					finalJson.put("img", "data:image/jpg;base64,"+base64);
					sendPost();
				}
				
/*     */   }

	
	private final static String USER_AGENT = "Mozilla/5.0";
	
	// HTTP POST request
		public static void sendPost() throws Exception {
		
		 String url_production = "https://vangioi.vn/api/crawler";
//		 String url = "http://localhost:8080/web_truyen/api/crawler";
		 URL obj = new URL(url_production);
		 HttpURLConnection   con = (HttpURLConnection ) obj.openConnection();
		 con.setRequestMethod("POST");
		
		
		 con.setRequestProperty("User-Agent", USER_AGENT);
		 con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		 con.setRequestProperty("Accept", "application/json");
		 con.setRequestProperty("Authorization","Bearer A3HbzO6K5nf8P81TNzOtDrgSBJ4KKHTpw1BqFaSoOZzJLbxizTuDH78OqMcn");
	
		 String jsonInputString = Notification.finalJson.toString();
		 // Send post request
		 con.setDoInput(true);
		 con.setDoOutput(true);
//		 OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
//		 wr.write(jsonInputString);
		
		 OutputStream os = con.getOutputStream();
		 os.write(jsonInputString.getBytes("UTF-8"));
		 
		 int responseCode = con.getResponseCode();
		 System.out.println("\nSending 'POST' request to URL : " + url);
		 System.out.println("Response Code : " + responseCode);
		

		 BufferedReader in = new BufferedReader(
		         new InputStreamReader(con.getInputStream()));
		 String inputLine;
		 StringBuffer response = new StringBuffer();
		
		 while ((inputLine = in.readLine()) != null) {
		  response.append(inputLine);
		 }
		 in.close();
		finalJson.remove("listChaps");
		Text.chapArray = new JSONArray();
		System.out.print(finalJson);
		 //print result
		 System.out.println(response.toString());
		 JOptionPane.showMessageDialog(null, "Cào truyện hoàn tất! vui lòng kiểm tra tại website");
		}
}