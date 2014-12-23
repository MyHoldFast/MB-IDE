/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holdfast.mbide.form;

import static com.holdfast.mbide.form.IDE.defaultfont;
import com.holdfast.mbide.ide.EditorArea;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Scanner;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

/**
 *
 * @author HoldFast
 */
public class Help extends javax.swing.JDialog {

    private final String[] titles = {"", "Основные команды", "Дата и время", "Высокоуровневый интерфейс", "Игровые функции", "Графика", "Команды ввода-вывода", "Математ. функции", "Спрайтовые команды", "Строковые функции", "Работа со звуком", "Дополн. функции", "Сенсорные функции", "Номера Ошибок"};

    public Help(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setIconImage(IDE.icon);
        Font font = defaultfont;
        String getfont = Preferences.userRoot().get("mainfont", null);
        if (getfont != null) {
            String[] attr = getfont.split("\\|");
            font = new java.awt.Font(attr[0], Integer.parseInt(attr[1]), Integer.parseInt(attr[2]));
        }
        for (int i = 1; i < titles.length; i++) {
            EditorArea tmp = new EditorArea(new JTextPane(), jTabbedPane1, font);
            tmp.setEditable(false);
            String text = new Scanner(this.getClass().getResourceAsStream("/res/help/" + i + ".txt"), "UTF-8").useDelimiter("\\A").next();
            tmp.setText(text, true);
            addTab(jTabbedPane1, tmp, titles[i]);
        }

    }

    private void addTab(final JTabbedPane tabbedPane, final JComponent c, final String title) {
        tabbedPane.add(c);
        final int index = tabbedPane.indexOfComponent(c);
        FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);
        final JPanel pnlTab = new JPanel(f);
        pnlTab.setOpaque(false);
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 11));
        pnlTab.add(lblTitle);
        pnlTab.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        tabbedPane.setTabComponentAt(index, pnlTab);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Справка");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
