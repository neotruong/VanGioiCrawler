/*    */ package dark.leech.text.ui.main.plugin;
/*    */ 
/*    */ import dark.leech.text.plugin.PluginGetter;
/*    */ import dark.leech.text.plugin.PluginManager;
/*    */ import dark.leech.text.ui.PanelTitle;
/*    */ import dark.leech.text.ui.material.JMDialog;
/*    */ import dark.leech.text.ui.material.JMScrollPane;
/*    */ import dark.leech.text.util.AppUtils;
/*    */ import dark.leech.text.util.FileUtils;
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.JPanel;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ public class PluginUI
/*    */   extends JMDialog {
/*    */   private PanelTitle pnTitle;
/*    */   private JPanel pnList;
/*    */   private GridBagConstraints gbc;
/*    */   
/*    */   public PluginUI() {
/* 26 */     onCreate();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCreate() {
/* 31 */     super.onCreate();
/* 32 */     this.pnTitle = new PanelTitle();
/* 33 */     this.pnList = new JPanel(new GridBagLayout());
/*    */     
/* 35 */     this.pnTitle.setText("Plugins");
/* 36 */     this.pnTitle.addCloseListener(new ActionListener()
/*    */         {
/*    */           public void actionPerformed(ActionEvent e) {
/* 39 */             JSONObject json = new JSONObject();
/* 40 */             for (PluginGetter pl : PluginManager.getManager().list())
/* 41 */               json.put(pl.getName(), pl.isChecked()); 
/* 42 */             FileUtils.string2file(json.toString(), AppUtils.curDir + "/tools/plugins/plugin.json");
/* 43 */             PluginUI.this.close();
/*    */           }
/*    */         });
/* 46 */     this.container.add((Component)this.pnTitle);
/* 47 */     this.pnTitle.setBounds(0, 0, 380, 45);
/*    */     
/* 49 */     this.pnList.setBackground(Color.WHITE);
/* 50 */     GridBagConstraints gi = new GridBagConstraints();
/* 51 */     gi.gridwidth = 0;
/* 52 */     gi.weightx = 1.0D;
/* 53 */     gi.weighty = 1.0D;
/* 54 */     JMScrollPane scrollPane = new JMScrollPane(this.pnList);
/*    */     
/* 56 */     JPanel demo = new JPanel();
/* 57 */     demo.setBackground(Color.WHITE);
/* 58 */     this.pnList.add(demo, gi);
/* 59 */     scrollPane.setBorder(null);
/* 60 */     scrollPane.getVerticalScrollBar().setUnitIncrement(20);
/* 61 */     this.container.add((Component)scrollPane);
/* 62 */     scrollPane.setBounds(0, 45, 380, 350);
/*    */ 
/*    */     
/* 65 */     this.gbc = new GridBagConstraints();
/* 66 */     this.gbc.gridwidth = 0;
/* 67 */     this.gbc.weightx = 1.0D;
/* 68 */     this.gbc.fill = 2;
/* 69 */     runOnUiThread(new Runnable()
/*    */         {
/*    */           public void run() {
/* 72 */             for (PluginGetter pluginGetter : PluginManager.getManager().list()) {
/* 73 */               PluginUI.this.addItem(pluginGetter);
/*    */             }
/*    */           }
/*    */         });
/* 77 */     setSize(380, 400);
/*    */   }
/*    */ 
/*    */   
/*    */   private void addItem(PluginGetter pluginGetter) {
/* 82 */     PluginItem pluginItem = new PluginItem(pluginGetter);
/* 83 */     this.pnList.add((Component)pluginItem, this.gbc, 0);
/* 84 */     validate();
/* 85 */     repaint();
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Desktop\2018\LeechText\tools\LeechText.jar!\dark\leech\tex\\ui\main\plugin\PluginUI.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */