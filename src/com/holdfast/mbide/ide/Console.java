package com.holdfast.mbide.ide;

import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

/**
 *
 * @author HoldFast
 */
public class Console {

    private final javax.swing.JEditorPane console;
    private final javax.swing.JPopupMenu popup;
    private String text = "";
    private final ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/res/icon/comment_delete.png"));

    public Console(javax.swing.JEditorPane con) {
        console = con;
        console.setEditable(false);
        console.setContentType("text/html");

        popup = new javax.swing.JPopupMenu();
        JMenuItem clear = new JMenuItem();
        clear.setText("Очистить");
        clear.setIcon(icon);
        popup.add(clear);
        clear.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clean();
            }
        });
    }

    public void popupEnabled(boolean bo) {
        console.setComponentPopupMenu((bo) ? popup : null);
    }

    public void enable() {
        console.setEnabled(true);
        popupEnabled(true);
    }

    public void disable() {
        console.setEnabled(false);
    }

    public void add(String data) {
        text += data + "<br>";
        console.setText("<html><body style='font-family: Tahoma; font-size: 13'>" + text + "</body></html>");
    }

    public void error(String data) {
        add("<hr>[" + getTime() + "] <span style='font-weight: bold; color: red'>" + data + "</span>");
    }

    public void error(String fileName, String data) {
        add("<hr>[" + getTime() + "] <span style='font-weight: bold; color: red'>" + fileName + ": " + data + "</span>");
    }

    public void success(String data) {
        add("<hr>[" + getTime() + "] <span style='font-weight: bold; color: green'>" + data + "</span>");
    }

    public void clean() {
        console.setText("");
        text = "";
    }

    private String getTime() {
        Calendar c = Calendar.getInstance();
        String y = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
        String mi = Integer.toString(c.get(Calendar.MINUTE));
        String s = Integer.toString(c.get(Calendar.SECOND));
        mi = (mi.length() == 1) ? "0" + mi : mi;
        s = (s.length() == 1) ? "0" + s : s;
        return y + ":" + mi + ":" + s;
    }
}
