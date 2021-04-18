/*    */ package dark.leech.text.ui.main.plugin;
/*    */ 
/*    */ import dark.leech.text.image.ImageLabel;
/*    */ import dark.leech.text.plugin.PluginGetter;
/*    */ import dark.leech.text.ui.button.SelectButton;
/*    */ import dark.leech.text.ui.material.DropShadowBorder;
/*    */ import dark.leech.text.ui.material.JMPanel;
/*    */ import dark.leech.text.util.FontUtils;
/*    */ import dark.leech.text.util.SettingUtils;
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.border.Border;
/*    */ import javax.swing.event.ChangeEvent;
/*    */ import javax.swing.event.ChangeListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PluginItem
/*    */   extends JMPanel
/*    */ {
/*    */   private ImageLabel pnIcon;
/*    */   private JLabel lbName;
/*    */   private JLabel lbInfo;
/*    */   private SelectButton btCheckBox;
/*    */   private PluginGetter pluginGetter;
/*    */   
/*    */   public PluginItem(PluginGetter pluginGetter) {
/* 31 */     this.pluginGetter = pluginGetter;
/* 32 */     onCreate();
/*    */   }
/*    */   
/*    */   private void onCreate() {
/* 36 */     this.pnIcon = new ImageLabel();
/* 37 */     this.lbName = new JLabel();
/* 38 */     this.lbInfo = new JLabel();
/* 39 */     this.btCheckBox = new SelectButton();
/*    */     
/* 41 */     add((Component)this.pnIcon);
/* 42 */     this.pnIcon.setBounds(10, 5, 55, 55);
/* 43 */     if (this.pluginGetter.getIcon() == null) {
/* 44 */       this.pnIcon.input(PluginItem.class.getResourceAsStream("/dark/leech/res/img/book.png"))
/* 45 */         .load();
/*    */     } else {
/* 47 */       this.pnIcon.input(this.pluginGetter.getIcon()).load();
/*    */     } 
/*    */     
/* 50 */     this.lbName.setText(this.pluginGetter.getName() + " - v" + this.pluginGetter.getVersion());
/* 51 */     this.lbName.setFont(FontUtils.TEXT_NORMAL);
/* 52 */     add(this.lbName);
/* 53 */     this.lbName.setBounds(70, 5, 220, 25);
/*    */ 
/*    */     
/* 56 */     this.lbInfo.setText(this.pluginGetter.getInfo());
/* 57 */     this.lbInfo.setFont(FontUtils.TEXT_THIN);
/* 58 */     add(this.lbInfo);
/* 59 */     this.lbInfo.setBounds(70, 35, 295, 25);
/*    */ 
/*    */     
/* 62 */     this.btCheckBox.setSelected(this.pluginGetter.isChecked());
/* 63 */     this.btCheckBox.addChangeListener(new ChangeListener()
/*    */         {
/*    */           public void stateChanged(ChangeEvent e) {
/* 66 */             PluginItem.this.pluginGetter.setChecked(PluginItem.this.btCheckBox.isSelected());
/*    */           }
/*    */         });
/* 69 */     add((Component)this.btCheckBox);
/* 70 */     this.btCheckBox.setBounds(340, 5, 30, 30);
/*    */     
/* 72 */     setBorder((Border)new DropShadowBorder(SettingUtils.THEME_COLOR, 5, 3));
/* 73 */     setPreferredSize(new Dimension(375, 70));
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Desktop\2018\LeechText\tools\LeechText.jar!\dark\leech\tex\\ui\main\plugin\PluginItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */