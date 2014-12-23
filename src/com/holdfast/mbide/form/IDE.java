package com.holdfast.mbide.form;

import com.holdfast.mbide.bas.BASDecompiler;
import com.holdfast.mbide.ide.Console;
import com.holdfast.mbide.ide.EditorArea;
import com.holdfast.mbide.ide.FileChoose;
import com.holdfast.mbide.ide.Highlight;
import com.holdfast.mbide.ide.JFontChooser;
import com.holdfast.mbide.ide.Project;
import com.holdfast.mbide.ide.Tree;
import com.holdfast.mbide.ide.TreeListener;
import com.holdfast.mbide.util.DesktopApi;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author -HoldFast-
 */
public class IDE extends javax.swing.JFrame implements TreeListener {

    public static Tree tree = null;
    public static Console console = null;
    public static Highlight editor = null;
    public static Project project = null;  
    public static Help help = null;
    public static Image icon;
    public static IDE ide;
    public static HashMap<String, Integer> openTab = new HashMap<String, Integer>();
    private final UIManager.LookAndFeelInfo[] lookAndFeelInfo;
    private JMenuItem[] lookAndFeelMenuItem;
    private final Icon closeIcon = new javax.swing.ImageIcon(getClass().getResource("/res/icon/tabclose.png"));
    private final Icon fileIcon = new javax.swing.ImageIcon(getClass().getResource("/res/icon/file.png"));
    public static int numModified = 0;
    private String findtext;
    public static final Font defaultfont = new java.awt.Font("Monospaced", 0, 13);

    public IDE() {
        icon = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/res/icon/project.png"));
        this.setIconImage(icon);
        initComponents();

        UIManager.put("OptionPane.cancelButtonText", "Отмена");

        // <editor-fold defaultstate="collapsed" desc="init theme"> 
        lookAndFeelInfo = UIManager.getInstalledLookAndFeels();
        if (lookAndFeelInfo != null) {
            lookAndFeelMenuItem = new JMenuItem[lookAndFeelInfo.length];
            for (int i = 0; i < lookAndFeelInfo.length; i++) {
                String tName = lookAndFeelInfo[i].getName();
                lookAndFeelMenuItem[i] = new JMenuItem(tName);
                lookAndFeelMenuItem[i].addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        Preferences.userRoot().put("mbtheme", evt.getActionCommand());
                        JOptionPane.showMessageDialog(IDE.this, "Необходима перезагрузка!", "Смена темы", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                jMenu6.add(lookAndFeelMenuItem[i]);
            }
        }

        btnVersion1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton7.setEnabled(false);
                buildapk.setEnabled(false);
            }
        });
        btnVersion2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton7.setEnabled(true);
                buildapk.setEnabled(true);
            }
        });
        // </editor-fold> 
        /*
         * init ide modules
         */
        ide = IDE.this;     
        help = new Help(null, false);
        console = new Console(consolePanel);
        tree = new Tree(jTree1, jTabbedPane1);
        tree.addTreeListener(IDE.this);        

    }

    public static boolean latin(String text) {
        Pattern pat = Pattern.compile("[a-zA-Z0-9-\\s]+");
        Matcher match = pat.matcher(text.trim());
        return match.matches();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        midletName = new javax.swing.JTextField();
        midletVendor = new javax.swing.JTextField();
        midletIcon = new javax.swing.JComboBox();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        btnVersion1 = new javax.swing.JRadioButton();
        btnVersion2 = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        obf = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        pack = new javax.swing.JTextField();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        consolePanel = new javax.swing.JEditorPane();
        jToolBar1 = new javax.swing.JToolBar();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        jButton1 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jButton5 = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        jButton6 = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        jButton7 = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        jButton8 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        buildapk = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        delete = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("MobileBASIC IDE 3.0");
        setMinimumSize(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jSplitPane2.setBorder(null);
        jSplitPane2.setDividerLocation(400);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setResizeWeight(1.0);
        jSplitPane2.setMaximumSize(null);

        jSplitPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jSplitPane1.setDividerLocation(270);
        jSplitPane1.setResizeWeight(0.1);

        jTabbedPane1.setEnabled(false);

        jScrollPane1.setBorder(null);

        jTree1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.setEnabled(false);
        jScrollPane1.setViewportView(jTree1);

        jTabbedPane1.addTab("Файлы", jScrollPane1);

        jLabel1.setText("MIDlet-Name:");

        jLabel2.setText("MIDlet-Vendor:");

        jLabel3.setText("MIDlet-Version:");

        jLabel4.setText("MIDlet-Icon:");

        midletVendor.setText("mbteam.ru");

        midletIcon.setModel(new javax.swing.DefaultComboBoxModel(new String[] { }));

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.1f), Float.valueOf(0.0f), null, Float.valueOf(0.1f)));

        jLabel5.setText("Версия MobileBASIC:");

        buttonGroup1.add(btnVersion1);
        btnVersion1.setText("1.8.6");

        buttonGroup1.add(btnVersion2);
        btnVersion2.setText("1.9.1");

        jLabel6.setText("Обфускация:");

        obf.setText("Вкл/Выкл");

        jLabel7.setText("Package:");

        pack.setText("com.holdfast.MBIDE");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(btnVersion2)
                            .addComponent(btnVersion1)
                            .addComponent(jLabel6)
                            .addComponent(obf))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(midletIcon, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(midletName)
                            .addComponent(midletVendor)
                            .addComponent(jSpinner1)
                            .addComponent(pack, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVersion1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVersion2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(obf)
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(midletName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(midletVendor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(midletIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(137, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(jPanel2);

        jTabbedPane1.addTab("Параметры", jScrollPane4);

        jSplitPane1.setLeftComponent(jTabbedPane1);
        jSplitPane1.setRightComponent(jTabbedPane2);

        jSplitPane2.setLeftComponent(jSplitPane1);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Консоль:"));
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setMaximumSize(null);

        consolePanel.setEditable(false);
        consolePanel.setBorder(null);
        consolePanel.setEnabled(false);
        consolePanel.setMaximumSize(null);
        consolePanel.setRequestFocusEnabled(false);
        jScrollPane3.setViewportView(consolePanel);

        jSplitPane2.setRightComponent(jScrollPane3);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.add(jSeparator9);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/folder_add.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);
        jToolBar1.add(jSeparator3);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/open.png"))); // NOI18N
        jButton2.setBorder(null);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);
        jToolBar1.add(jSeparator4);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/disk.png"))); // NOI18N
        jButton3.setBorder(null);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setEnabled(false);
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);
        jToolBar1.add(jSeparator5);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/arrow_undo.png"))); // NOI18N
        jButton4.setBorder(null);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setEnabled(false);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);
        jToolBar1.add(jSeparator6);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/arrow_redo.png"))); // NOI18N
        jButton5.setBorder(null);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.setEnabled(false);
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);
        jToolBar1.add(jSeparator7);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/build.png"))); // NOI18N
        jButton6.setBorder(null);
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.setEnabled(false);
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);
        jToolBar1.add(jSeparator8);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/build_android.png"))); // NOI18N
        jButton7.setBorder(null);
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.setEnabled(false);
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton7);
        jToolBar1.add(jSeparator10);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/run.png"))); // NOI18N
        jButton8.setBorder(null);
        jButton8.setEnabled(false);
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton8);

        jMenu1.setText("Файл");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/folder_add.png"))); // NOI18N
        jMenuItem1.setText("Новый проект");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/open.png"))); // NOI18N
        jMenuItem2.setText("Открыть проект");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/disk.png"))); // NOI18N
        jMenuItem3.setText("Сохранить все файлы");
        jMenuItem3.setEnabled(false);
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator1);

        jMenuItem17.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/disk.png"))); // NOI18N
        jMenuItem17.setText("Сохранить файл");
        jMenuItem17.setEnabled(false);
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem17);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/cross.png"))); // NOI18N
        jMenuItem4.setText("Выход");
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Правка");
        jMenu2.setEnabled(false);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/arrow_undo.png"))); // NOI18N
        jMenuItem5.setText("Отменить");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/arrow_redo.png"))); // NOI18N
        jMenuItem6.setText("Вернуть");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/find.png"))); // NOI18N
        jMenuItem7.setText("Найти");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/modified_find.png"))); // NOI18N
        jMenuItem8.setText("Найти следующее");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Проект");
        jMenu3.setEnabled(false);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/build.png"))); // NOI18N
        jMenuItem9.setText("Собрать");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        buildapk.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        buildapk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/build_android.png"))); // NOI18N
        buildapk.setText("Собрать apk");
        buildapk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buildapkActionPerformed(evt);
            }
        });
        jMenu3.add(buildapk);

        jMenuItem11.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/run.png"))); // NOI18N
        jMenuItem11.setText("Запустить");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem11);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/folder.png"))); // NOI18N
        jMenuItem10.setText("Открыть папку dist");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);
        jMenu3.add(jSeparator2);

        delete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/delete.png"))); // NOI18N
        delete.setText("Удалить");
        delete.setEnabled(false);
        jMenu3.add(delete);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/add.png"))); // NOI18N
        jMenu5.setText("Добавить");

        jMenuItem13.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ADD, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/lis.png"))); // NOI18N
        jMenuItem13.setText("Исходник");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem13);

        jMenuItem12.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ADD, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/attach.png"))); // NOI18N
        jMenuItem12.setText("Ресурсы");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem12);

        jMenu3.add(jMenu5);

        jMenuItem14.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/page_white_add.png"))); // NOI18N
        jMenuItem14.setText("Вставить из BAS");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem14);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Программа");

        jMenu6.setText("Тема");
        jMenu4.add(jMenu6);

        jMenuItem15.setText("Шрифт редактора");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem15);

        jMenuItem18.setText("Справка по языку");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpOpen(evt);
            }
        });
        jMenu4.add(jMenuItem18);

        jMenuItem16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon/help.png"))); // NOI18N
        jMenuItem16.setText("О программе");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem16);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(816, 639));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (numModified > 0 && project != null) {
            int dia = JOptionPane.showConfirmDialog(IDE.this, "Сохранить текущий проект?", "Открыть проект", JOptionPane.OK_CANCEL_OPTION);
            if (dia == JOptionPane.OK_OPTION) {
                saveAll();
                new newProject(this, true).setVisible(true);
            }
        } else {
            new newProject(this, true).setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if (numModified > 0 && project != null) {
            int dia = JOptionPane.showConfirmDialog(IDE.this, "Сохранить текущий проект?", "Открыть проект", JOptionPane.OK_CANCEL_OPTION);
            if (dia == JOptionPane.OK_OPTION) {
                saveAll();
                new newProject(this, true).setVisible(true);
            }
        } else {
            new newProject(this, true).setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed
    private void newProject() {
        String path = (String) FileChoose.chooseFile(false, this, "mb_mbp", "Выберите файл проекта", "Открыть", new String[]{".mbp"});
        if (path != null) {
            IDE.project = new Project();
            IDE.project.projectOpen(path);
        }
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (numModified > 0 && project != null) {
            int dia = JOptionPane.showConfirmDialog(IDE.this, "Сохранить текущий проект?", "Открыть проект", JOptionPane.OK_CANCEL_OPTION);
            if (dia == JOptionPane.OK_OPTION) {
                saveAll();
                newProject();
            }
        } else {
            newProject();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (numModified > 0 && project != null) {
            int dia = JOptionPane.showConfirmDialog(IDE.this, "Сохранить текущий проект?", "Открыть проект", JOptionPane.OK_CANCEL_OPTION);
            if (dia == JOptionPane.OK_OPTION) {
                saveAll();
                newProject();
            }
        } else {
            newProject();
        }

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        EditorArea area = getEditor();
        project.addSource(area.getDocName(), area.getText(), true);
        area.endModified();
        jMenuItem17.setEnabled(false);
        ((JLabel) ((JPanel) jTabbedPane2.getTabComponentAt(jTabbedPane2.getSelectedIndex())).getComponent(0)).setFont(new java.awt.Font("Tahoma", 0, 11));
        numModified--;
        if (numModified == 0) {
            jMenuItem3.setEnabled(false);
            jButton3.setEnabled(false);
        }
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void saveAll() {
        for (int i = 0; i < jTabbedPane2.getTabCount(); i++) {
            EditorArea area = (EditorArea) jTabbedPane2.getComponentAt(i);
            if (area.isModified()) {
                project.addSource(area.getDocName(), area.getText(), true);
                area.endModified();
                ((JLabel) ((JPanel) jTabbedPane2.getTabComponentAt(i)).getComponent(0)).setFont(new java.awt.Font("Tahoma", 0, 11));
                numModified--;
            }
        }
        project.saveProjectFile();
        jMenuItem3.setEnabled(false);
        jButton3.setEnabled(false);
        jMenuItem17.setEnabled(false);

    }
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        saveAll();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (numModified > 0) {
            int dia = JOptionPane.showConfirmDialog(null, "Сохранить все файлы?", "Выход", JOptionPane.OK_CANCEL_OPTION);
            if (dia == JOptionPane.OK_OPTION) {
                saveAll();
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    public EditorArea getEditor() {
        return ((EditorArea) jTabbedPane2.getSelectedComponent());
    }

    public static EditorArea getEditorByName(String name) {
        IDE.ide.treeSourceSelect(name);
        for (int i = 0; i < jTabbedPane2.getTabCount(); i++) {
            EditorArea area = (EditorArea) jTabbedPane2.getComponentAt(i);
            if (((JLabel) ((JPanel) jTabbedPane2.getTabComponentAt(i)).getComponent(0)).getText().equals(name)) {
                return area;
            }
        }
        return null;
    }

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        EditorArea area = getEditor();
        findtext = JOptionPane.showInputDialog(this, "Введите строку для поиска", "Поиск", JOptionPane.PLAIN_MESSAGE);
        if (findtext != null && !findtext.trim().equals("")) {
            area.find(findtext, false);
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        EditorArea area = getEditor();
        if (findtext != null && !findtext.trim().equals("")) {
            area.find(findtext, true);
        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        saveAll();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        saveAll();
        project.build(false);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        project.newSource();
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        DesktopApi.open(new File(project.getProjectDir() + "dist"));
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        project.newResource();
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        saveAll();
        project.build(false);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        saveAll();
        project.build(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        saveAll();
        project.build(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        String path = (String) FileChoose.chooseFile(false, this, "mb_bas", "Выберите файл BAS", "Открыть", new String[]{".bas"});
        if (path != null) {
            try {
                String bas = BASDecompiler.decompile(new FileInputStream(new File(path)));
                if (!"notbas".equals(bas) && !"obfuscate".equals(bas)) {
                    EditorArea area = getEditor();
                    area.setText(bas, false);
                } else {
                    console.error("Невозможно открыть файл");
                }
            } catch (Exception ex) {

            }
        }
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        EditorArea area = getEditor();
        area.undo();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        EditorArea area = getEditor();
        area.redo();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        EditorArea area = getEditor();
        area.undo();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        EditorArea area = getEditor();
        area.redo();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        new About(this, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        Font font = defaultfont;
        String getfont = Preferences.userRoot().get("mainfont", null);
        if (getfont != null) {
            String[] attr = getfont.split("\\|");
            font = new java.awt.Font(attr[0], Integer.parseInt(attr[1]), Integer.parseInt(attr[2]));
        }
        JFontChooser fontChooser = new JFontChooser();
        fontChooser.setSelectedFont(font);
        int result = fontChooser.showDialog(this);
        if (result == JFontChooser.OK_OPTION) {
            font = fontChooser.getSelectedFont();
            Preferences.userRoot().put("mainfont", font.getName() + "|" + font.getStyle() + "|" + font.getSize());
            for (int i = 0; i < jTabbedPane2.getTabCount(); i++) {
                EditorArea area = (EditorArea) jTabbedPane2.getComponentAt(i);
                area.area.setFont(font);
            }
        }
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        saveAll();
        project.buildAndroid();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void buildapkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buildapkActionPerformed
        saveAll();
        project.buildAndroid();
    }//GEN-LAST:event_buildapkActionPerformed

    private void helpOpen(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpOpen
       help.setVisible(true);
    }//GEN-LAST:event_helpOpen

    public void openTab() {
        openTab.clear();
        for (int ii = 0; ii < jTabbedPane2.getTabCount(); ii++) {
            EditorArea area = (EditorArea) jTabbedPane2.getComponentAt(ii);
            openTab.put(area.getDocName(), ii);

        }
    }

    public void addClosableTab(final JTabbedPane tabbedPane, final JComponent c, final String title, final Icon icon, final Icon close) {
        if (title.endsWith(".lis")) {
            tabbedPane.add(c);
            final int index = tabbedPane.indexOfComponent(c);
            openTab.put(title, index);
            FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);
            final JPanel pnlTab = new JPanel(f);
            pnlTab.setOpaque(false);
            JLabel lblTitle = new JLabel(title);
            lblTitle.setFont(new java.awt.Font("Tahoma", 0, 11));
            JButton btnClose = new JButton();
            btnClose.setOpaque(false);
            btnClose.setIcon(close);
            btnClose.setBorder(null);
            btnClose.setFocusable(false);
            btnClose.setBackground(new Color(0, 0, 0, 0));
            pnlTab.setBackground(new Color(0, 0, 0, 0));
            pnlTab.add(lblTitle);
            pnlTab.add(btnClose);
            pnlTab.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
            final javax.swing.JPopupMenu popup = new javax.swing.JPopupMenu();
            JMenuItem clear = new JMenuItem();
            clear.setText("Закрыть все");
            popup.add(clear);
            clear.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boolean saveall = false;
                    for (int ii = jTabbedPane2.getTabCount() - 1; ii > -1; ii--) {
                        EditorArea area = (EditorArea) jTabbedPane2.getComponentAt(ii);
                        if (!area.isModified()) {
                            jTabbedPane2.remove(ii);
                        } else {
                            if (!saveall) {
                                UIManager.put("OptionPane.yesButtonText", "Сохранить");
                                UIManager.put("OptionPane.noButtonText", "Сохранить все");
                                int dia = JOptionPane.showConfirmDialog(IDE.this, "Файл " + area.getDocName() + " был изменён, сохранить?", "Сохранение", JOptionPane.YES_NO_CANCEL_OPTION);
                                if (dia == JOptionPane.YES_OPTION || dia == JOptionPane.NO_OPTION) {
                                    if (dia == JOptionPane.NO_OPTION) {
                                        saveall = true;
                                    }
                                    project.addSource(area.getDocName(), area.getText(), true);
                                    tabbedPane.remove(ii);
                                    numModified--;
                                }

                                if (dia == JOptionPane.CANCEL_OPTION || dia == JOptionPane.CLOSED_OPTION) {
                                    break;
                                }
                            } else {
                                project.addSource(area.getDocName(), area.getText(), true);
                                tabbedPane.remove(ii);
                                numModified--;
                            }

                        }
                    }
                    jMenuItem3.setEnabled(false);
                    jButton3.setEnabled(false);
                    jMenuItem17.setEnabled(false);
                    openTab();

                }
            });
            pnlTab.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    tabbedPane.setSelectedComponent(c);
                    if (((EditorArea) c).isModified()) {
                        jMenuItem17.setEnabled(true);
                    } else {
                        jMenuItem17.setEnabled(false);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        popup.show(pnlTab, e.getX(), e.getY());
                    }
                }
            });
            tabbedPane.setTabComponentAt(index, pnlTab);
            tabbedPane.setSelectedIndex(index);

            btnClose.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    EditorArea area = (EditorArea) c;
                    if (!area.isModified()) {
                        tabbedPane.remove(c);
                        openTab();
                    } else {
                        int dia = JOptionPane.showConfirmDialog(IDE.this, "Файл " + area.getDocName() + " был изменён, сохранить?", "Сохранение", JOptionPane.OK_CANCEL_OPTION);
                        if (dia == JOptionPane.OK_OPTION) {
                            project.addSource(area.getDocName(), area.getText(), true);
                            tabbedPane.remove(c);
                            numModified--;
                            openTab();
                        }
                    }
                }
            });
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (Preferences.userRoot().get("mbtheme", "Metal").equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
        } catch (IllegalAccessException ex) {
        } catch (InstantiationException ex) {
        } catch (UnsupportedLookAndFeelException ex) {
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new IDE().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JRadioButton btnVersion1;
    public static javax.swing.JRadioButton btnVersion2;
    public static javax.swing.JMenuItem buildapk;
    public static javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JEditorPane consolePanel;
    public static javax.swing.JMenuItem delete;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    public static javax.swing.JButton jButton3;
    public static javax.swing.JButton jButton4;
    public static javax.swing.JButton jButton5;
    public static javax.swing.JButton jButton6;
    public static javax.swing.JButton jButton7;
    public static javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    public static javax.swing.JMenu jMenu2;
    public static javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    public static javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem2;
    public static javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    public static javax.swing.JSpinner jSpinner1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree1;
    public static javax.swing.JComboBox midletIcon;
    public static javax.swing.JTextField midletName;
    public static javax.swing.JTextField midletVendor;
    public static javax.swing.JCheckBox obf;
    public static javax.swing.JTextField pack;
    // End of variables declaration//GEN-END:variables

    @Override
    public void treeSourceSelect(String name) {
        Font font = defaultfont;
        String getfont = Preferences.userRoot().get("mainfont", null);
        if (getfont != null) {
            String[] attr = getfont.split("\\|");
            font = new java.awt.Font(attr[0], Integer.parseInt(attr[1]), Integer.parseInt(attr[2]));
        }
        EditorArea tmp = new EditorArea(new JTextPane(), jTabbedPane2, font);
        if (!openTab.containsKey(name)) {
            addClosableTab(jTabbedPane2, tmp, name, fileIcon, closeIcon);
            tmp.setDocName(name);
            tmp.setText(project.getSource(name), true);
        } else {
            jTabbedPane2.setSelectedIndex(openTab.get(name));
        }

    }

    @Override
    public void treeResourceSelect(String name) {
    }

    public void treeSourcePopupClick(int com, String name, int id) {
        if (!name.equals("Autorun.lis")) {
            switch (com) {
                case 0: //delete  
                    int c = JOptionPane.showConfirmDialog(this, "Удалить " + name + "?", "Удаление", JOptionPane.OK_CANCEL_OPTION);
                    if (c == JOptionPane.OK_OPTION) {
                        project.delSource(name);
                        if (openTab.containsKey(name)) {
                            jTabbedPane2.remove(openTab.get(name));
                            openTab();
                        }
                        tree.update(false);
                        delete.setText("Удалить");
                        delete.setEnabled(false);
                    }
                    break;

                case 1: //rename
                    UIManager.put("OptionPane.okButtonText", "Переименовать");
                    JTextField field = new JTextField(name.substring(0, name.lastIndexOf(".")));
                    String ex = name.substring(name.lastIndexOf("."));
                    int dia = JOptionPane.showConfirmDialog(this, field, "Переименовать " + name, JOptionPane.CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    String newname = field.getText().trim() + ex;
                    if (dia == JOptionPane.OK_OPTION && latin(field.getText().trim())) {
                        if (project.sourceRename(name, newname, id)) {
                            ((EditorArea) jTabbedPane2.getComponentAt(openTab.get(name))).setDocName(newname);
                            if (openTab.containsKey(name)) {
                                ((JLabel) ((JPanel) jTabbedPane2.getTabComponentAt(openTab.get(name))).getComponent(0)).setText(newname);
                                int indtab = openTab.get(name);
                                openTab.remove(name);
                                openTab.put(newname, indtab);
                            }

                        } else {
                            UIManager.put("OptionPane.okButtonText", "OK");
                            JOptionPane.showMessageDialog(IDE.this, "Файл с таким именем уже существует!", "Переименование", JOptionPane.INFORMATION_MESSAGE);

                        }

                        //tree.update(false);
                    }
                    UIManager.put("OptionPane.okButtonText", "OK");
                    break;
            }
        }
    }

    public void treeResourcePopupClick(int com, String name, int id) {
        switch (com) {

            case 0: //delete    
                int c = JOptionPane.showConfirmDialog(this, "Удалить " + name + "?", "Удаление", JOptionPane.OK_CANCEL_OPTION);
                if (c == JOptionPane.OK_OPTION) {
                    project.delResource(name);
                    tree.update(true);
                    project.getPNGFile(null);
                    delete.setText("Удалить");
                    delete.setEnabled(false);
                }
                break;
            case 1: //rename
                UIManager.put("OptionPane.okButtonText", "Переименовать");
                JTextField field = new JTextField(name.substring(0, name.lastIndexOf(".")));
                String ex = name.substring(name.lastIndexOf("."));
                int dia = JOptionPane.showConfirmDialog(this, field, "Переименовать " + name, JOptionPane.CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (dia == JOptionPane.OK_OPTION && latin(field.getText().trim())) {
                    if (!project.resourceRename(name, field.getText().trim() + ex, id)) {
                        UIManager.put("OptionPane.okButtonText", "OK");
                        JOptionPane.showMessageDialog(IDE.this, "Файл с таким именем уже существует!", "Переименование", JOptionPane.INFORMATION_MESSAGE);

                    }

                    //tree.update(false);
                }
                UIManager.put("OptionPane.okButtonText", "OK");
                break;
        }
    }
}
