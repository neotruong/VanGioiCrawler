package dark.leech.text.ui.main;

import dark.leech.text.image.GaussianBlurFilter;
import dark.leech.text.listeners.AddListener;
import dark.leech.text.listeners.BlurListener;
import dark.leech.text.plugin.PluginManager;
import dark.leech.text.ui.Animation;
import dark.leech.text.ui.button.CircleButton;
import dark.leech.text.ui.button.CloseButton;
import dark.leech.text.ui.download.AddURL;
import dark.leech.text.ui.download.DownloadUI;
import dark.leech.text.ui.main.plugin.PluginUI;
import dark.leech.text.ui.material.JMMenuItem;
import dark.leech.text.ui.material.JMPopupMenu;
import dark.leech.text.ui.notification.Notification;
import dark.leech.text.util.AppUtils;
import dark.leech.text.util.ColorUtils;
import dark.leech.text.util.FontUtils;
import dark.leech.text.util.GraphicsUtils;
import dark.leech.text.util.StringUtils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.RescaleOp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class MainUI extends JFrame implements BlurListener, ActionListener {
  private DownloadUI downloadUI;
  
  private SettingUI setting;
  
  private JPanel appBar;
  
  private CircleButton btAdd;
  
  private CircleButton btMenu;
  
  private JPanel statusBar;
  
  private CircleButton btBack;
  
  private CircleButton btOk;
  
  private JLabel lbLogo;
  
  private JPanel pnHeader;
  
  private JMPopupMenu menu;
  
  private JMMenuItem pnSetting;
  
  private JMenuItem pnHelp;
  
  private JMMenuItem pnPlugin;
  
  private CloseButton btExit;
  
  private JLabel lbStatus;
  
  private Container container;
  
  private Point initialClick;
  
  private BufferedImage blurBuffer;
  
  private BufferedImage backBuffer;
  
  private Timer timer;
  
  private int i = 0;
  
  public MainUI() {
    setLocation(AppUtils.width / 2 - 200, AppUtils.height /2  - 350);
    setSize(390, 555);
    getRootPane().setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
    setUndecorated(true);
    setTitle("LeechText");
    setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/dark/leech/res/icon.png")));
    (new Thread(new Runnable() {
          public void run() {
            MainUI.this.onCreate();
          }
        })).start();
  }
  
  private void onCreate()  {
    this.container = getContentPane();
    this.container.setLayout((LayoutManager)null);
    this.container.setBackground(Color.WHITE);
    onCreateStatusBar();
    onCreateAppBar();
    createPanelHeaderUI();
    createPopupMenu();
    this.downloadUI = new DownloadUI();
    this.downloadUI.add(this.appBar);
    this.appBar.setBounds(0, 0, 390, 55);
    this.container.add((Component)this.downloadUI);
    this.downloadUI.setBounds(0, 20, 390, 535);
    this.setting = new SettingUI();
    this.setting.add(this.pnHeader);
    this.setting.setBounds(390, 20, 390, 535);
    this.pnHeader.setBounds(0, 0, 390, 55);
    this.container.add(this.setting);
    checkUpdate();
    try {
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  private void actionExit() {
    Animation.fadeOut(this);
  }
  
  private void actionAdd() {
    AddURL addURL = new AddURL();
    addURL.setAddListener((AddListener)this.downloadUI);
    addURL.open();
  }
  
  private void onCreateStatusBar() {
    this.statusBar = new JPanel();
    this.statusBar.setBackground(ColorUtils.STATUS_BAR);
    this.statusBar.setLayout((LayoutManager)null);
    this.btExit = new CloseButton();
    this.btExit.setFont(FontUtils.iconFont(18.0F));
    this.btExit.addActionListener(this);
    this.statusBar.add((Component)this.btExit);
    this.btExit.setBounds(360, 0, 20, 20);
    this.lbStatus = new JLabel();
    this.lbStatus.setForeground(Color.white);
    this.lbStatus.setFocusable(false);
    this.lbStatus.setFont(FontUtils.textFont(13.0F, 0));
    this.statusBar.add(this.lbStatus);
    this.lbStatus.setBounds(5, 0, 315, 20);
    this.timer = new Timer(1000, new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date date = new Date();
            MainUI.this.lbStatus.setText(dateFormat.format(date));
          }
        });
    this.container.add(this.statusBar);
    this.statusBar.setBounds(0, 0, 390, 20);
    this.statusBar.addMouseListener(new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
            MainUI.this.initialClick = e.getPoint();
            MainUI.this.getComponentAt(MainUI.this.initialClick);
          }
        });
    this.statusBar.addMouseMotionListener(new MouseMotionAdapter() {
          public void mouseDragged(MouseEvent e) {
            MainUI.this.movieWindows(e);
          }
        });
  }
  
  private void onCreateAppBar() {
    this.appBar = new JPanel();
    this.appBar.setBackground(ColorUtils.THEME_COLOR);
    this.appBar.setLayout((LayoutManager)null);
    this.btAdd = new CircleButton(StringUtils.ADD, 25f);
    this.btAdd.addActionListener(this);
    this.appBar.add((Component)this.btAdd);
    this.btAdd.setBounds(305, 5, 45, 45);
    JLabel logo = new JLabel();
    logo.setText("VanGioi.vn - Cào truyện");
    logo.setFont(FontUtils.TITLE_BIG);
    logo.setForeground(Color.white);
    logo.setHorizontalAlignment(0);
    this.appBar.add(logo);
    logo.setBounds(20, 0, (logo.getPreferredSize()).width + 50, 55);
    this.btMenu = new CircleButton(StringUtils.MORE, 25f);
    this.btMenu.addActionListener(this);
    this.appBar.add((Component)this.btMenu);
    this.btMenu.setBounds(355, 5, 30, 45);
  }
  
  private void createPanelHeaderUI() {
    this.pnHeader = new JPanel();
    this.pnHeader.setBackground(ColorUtils.THEME_COLOR);
    this.pnHeader.setLayout((LayoutManager)null);
    this.btBack = new CircleButton(StringUtils.BACK, 25f);
    this.btBack.addActionListener(this);
    this.pnHeader.add((Component)this.btBack);
    this.btBack.setBounds(5, 5, 45, 45);
    this.lbLogo = new JLabel();
    this.lbLogo.setText("Cài Đặt");
    this.lbLogo.setFont(FontUtils.TITLE_BIG);
    this.lbLogo.setForeground(Color.white);
    this.lbLogo.setHorizontalAlignment(0);
    this.pnHeader.add(this.lbLogo);
    this.lbLogo.setBounds(55, 0, 100, 55);
    this.btOk = new CircleButton(StringUtils.CHECK, 25f);
    this.btOk.addActionListener(this);
    this.pnHeader.add((Component)this.btOk);
    this.btOk.setBounds(335, 5, 45, 45);
  }
  
  private void createPopupMenu() {
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if (e.getSource() == MainUI.this.pnSetting) {
            Animation.go((JPanel)MainUI.this.downloadUI, MainUI.this.setting);
            MainUI.this.setting.load();
          } 
          if (e.getSource() == MainUI.this.pnHelp)
            (new HelpUI()).open(); 
          if (e.getSource() == MainUI.this.pnPlugin)
            (new PluginUI()).open(); 
        }
      };
    this.menu = new JMPopupMenu();
    this.pnSetting = new JMMenuItem("Cài Đặt");
    this.pnSetting.addActionListener(actionListener);
    this.menu.add((JMenuItem)this.pnSetting);
    this.pnPlugin = new JMMenuItem("Plugins");
    this.pnPlugin.addActionListener(actionListener);
    this.menu.add((JMenuItem)this.pnPlugin);
    this.pnHelp = (JMenuItem)new JMMenuItem("Thtin");
    this.pnHelp.addActionListener(actionListener);
    this.menu.add(this.pnHelp);
    this.container.add((Component)this.menu);
  }
  
  private void movieWindows(MouseEvent e) {
    int thisX = (getLocation()).x;
    int thisY = (getLocation()).y;
    int xMoved = thisX + e.getX() - thisX + this.initialClick.x;
    int yMoved = thisY + e.getY() - thisY + this.initialClick.y;
    int X = thisX + xMoved;
    X = (X < 10) ? 10 : X;
    X = (X + getWidth() > AppUtils.width) ? (AppUtils.width - getWidth() - 10) : X;
    int Y = thisY + yMoved;
    Y = (Y + getHeight() > AppUtils.height) ? (AppUtils.height - getHeight() - 10) : Y;
    Y = (Y < 10) ? 10 : Y;
    setLocation(X, Y);
    AppUtils.LOCATION = getLocation();
  }
  
  private void checkUpdate() {
    this.i = 0;
    final String[] s = { ".", "..", "...", "...." };
    Timer time = new Timer(200, new ActionListener() {
          public void actionPerformed(ActionEvent e) {
           // MainUI.this.lbStatus.setText("kitra cnh+ s[MainUI.this.i = (MainUI.this.i + 1) % 4]);
          }
        });
    time.start();
    PluginManager.getManager();
    UpdateUI.checkUpdate();
    time.stop();
    this.timer.start();
  }
  
  private void createBlur() {
    Component root = getRootPane();
    this.blurBuffer = GraphicsUtils.createCompatibleImage(getWidth(), getHeight());
    Graphics2D g2 = this.blurBuffer.createGraphics();
    root.paint(g2);
    g2.dispose();
    this.backBuffer = this.blurBuffer;
    this.blurBuffer = GraphicsUtils.createThumbnailFast(this.blurBuffer, getWidth() / 2);
    this.blurBuffer = (new GaussianBlurFilter(3)).filter(this.blurBuffer, null);
    RescaleOp op = new RescaleOp(0.9F, 0.0F, null);
    this.blurBuffer = op.filter(this.blurBuffer, (BufferedImage)null);
  }
  
  public void setBlur(boolean blur) {
    if (blur) {
      createBlur();
    } else {
      this.blurBuffer = null;
    } 
    repaint();
  }
  
  public void paint(Graphics g) {
    super.paint(g);
    if (isVisible() && this.blurBuffer != null) {
      Graphics2D g2 = (Graphics2D)g.create();
      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g2.drawImage(this.backBuffer, 0, 0, (ImageObserver)null);
      g2.setComposite(AlphaComposite.SrcOver.derive(0.9F));
      g2.drawImage(this.blurBuffer, 0, 0, getWidth(), getHeight(), null);
      g2.dispose();
    } 
  }
  
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.btMenu)
      this.menu.show((Component)this.btMenu, this.btMenu.getWidth() / 2 - 70, this.btMenu.getHeight() / 2 - 20); 
    if (e.getSource() == this.btExit)
      actionExit(); 
    if (e.getSource() == this.btAdd)
      actionAdd(); 
    if (e.getSource() == this.btBack)
      Animation.go(this.setting, (JPanel)this.downloadUI); 
    if (e.getSource() == this.btOk) {
      this.setting.save();
      Animation.go(this.setting, (JPanel)this.downloadUI);
    } 
  }
}
