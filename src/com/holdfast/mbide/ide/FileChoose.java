package com.holdfast.mbide.ide;

import java.io.File;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author HoldFast
 */
public class FileChoose {

    static JFileChooser fr;

    public static class FolderFilter extends javax.swing.filechooser.FileFilter implements java.io.FileFilter {

        @Override
        public boolean accept(File f) {
            return f.isDirectory();
        }

        @Override
        public String getDescription() {
            return "Folder";
        }
    }

    private static class Filter extends FileFilter implements java.io.FileFilter {

        private final String[] end;
        private boolean bool = false;

        Filter(String[] ex) {
            end = ex;
        }

        @Override
        public boolean accept(File f) {
            for (String end1 : end) {
                if (f.getName().toLowerCase().endsWith(end1)) {
                    bool = true;
                }
            }

            if (f.isDirectory() || bool) {
                bool = false;
                return true;
            } else {
                return false;
            }
        }

        @Override
        public String getDescription() {
            String des = "";
            for (int i = 0; i < end.length; i++) {
                des += "*" + end[i];
                if (i != end.length - 1) {
                    des += ", ";
                }
            }
            return des;
        }
    }

    public static Object chooseFile(boolean multi, java.awt.Component frame, String prev, String title, String button, String[] ex) {
        fr = new JFileChooser();
        String dir = Preferences.userRoot().get(prev, fr.getCurrentDirectory().getPath());
        fr.setCurrentDirectory(new File(dir));
        fr.setMultiSelectionEnabled(multi);
        fr.setFileFilter(new Filter(ex));
        fr.setAcceptAllFileFilterUsed(false);
        fr.setDialogTitle(title);
        UIManager.put("FileChooser.cancelButtonText", "Отмена");
        UIManager.put("FileChooser.folderNameLabelText", "Папка:");
        SwingUtilities.updateComponentTreeUI(fr);
        int status = fr.showDialog(frame, button);
        if (status != JFileChooser.CANCEL_OPTION) {
            Preferences.userRoot().put(prev, fr.getSelectedFile().getAbsolutePath());
            return (multi) ? fr.getSelectedFiles() : fr.getSelectedFile().getPath();
        } else {
            return null;
        }
    }

    public static String chooseDir(java.awt.Component frame) {
        fr = new JFileChooser();
        String dir = Preferences.userRoot().get("mb_dir", fr.getCurrentDirectory().getPath());
        if (dir.endsWith(File.separator)) {
            dir = dir.substring(0, dir.length() - 1);
        }
        fr.setCurrentDirectory(new File(dir.substring(0, dir.lastIndexOf(File.separator))));
        fr.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fr.setFileFilter(new FolderFilter());
        fr.setAcceptAllFileFilterUsed(false);
        UIManager.put("FileChooser.cancelButtonText", "Отмена");
        UIManager.put("FileChooser.folderNameLabelText", "Папка:");
        SwingUtilities.updateComponentTreeUI(fr);

        fr.setDialogTitle("Выберите папку");
        int status = fr.showDialog(frame, "Выбрать");

        if (status != JFileChooser.CANCEL_OPTION) {
            Preferences.userRoot().put("mb_dir", fr.getSelectedFile().getPath() + File.separator);
            return fr.getSelectedFile().getPath();
        } else {
            return null;
        }

    }
}
