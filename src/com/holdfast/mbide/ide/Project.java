package com.holdfast.mbide.ide;

import com.holdfast.mbide.bas.LIS2Java;
import com.holdfast.mbide.bas.LISCompiler;
import com.holdfast.mbide.bas.TextUtils;
import com.holdfast.mbide.form.IDE;
import com.holdfast.mbide.form.newProject;
import com.holdfast.mbide.util.Signer;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author HoldFast
 */
public final class Project {

    private String projectName = null;
    private String vendor = null;
    private String projectDir = null;
    private String currentFile = null;
    public static final String[] format = {".png", ".jpg", ".gif", ".mid", ".wav", ".mp3", ".txt", ".dat"};
    private static HashMap<String, String> source;
    private static ArrayList resource;
    public static Properties props = new Properties();
    int mb = 0; //0 - 1.8.6, 1 = 1.9.1

    public Project() {
        source = new HashMap<String, String>();
        resource = new ArrayList(0);
        IDE.jMenu2.setEnabled(true);
        IDE.jMenu3.setEnabled(true);
        IDE.jButton4.setEnabled(true);
        IDE.jButton5.setEnabled(true);
        IDE.jButton6.setEnabled(true);
        IDE.jButton8.setEnabled(true);

    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String name) {
        projectName = name;
        IDE.tree.setName(name);
    }

    public String getIcon() {
        String icon = "";
        try {
            icon = IDE.midletIcon.getSelectedItem().toString();
        } catch (NullPointerException e) {
        }
        return icon;
    }

    public String getMidletName() {
        return (!IDE.midletName.getText().trim().equals("")) ? IDE.midletName.getText().trim() : getProjectName();
    }

    public void setVendor(String name) {
        vendor = name;
        IDE.midletVendor.setText(name);
    }

    public String getVendor() {
        return (!IDE.midletVendor.getText().trim().equals("")) ? IDE.midletVendor.getText().trim() : vendor;

    }

    public void setMidletName(String name) {
        IDE.midletName.setText(name);
    }

    public String getPackage() {
        if (latin(IDE.pack.getText()) && !"".equals(IDE.pack.getText().trim())) {
            return IDE.pack.getText().trim();
        } else {
            return "com.holdfast.MBIDE";
        }
    }

    public void setPackage(String pack) {
        IDE.pack.setText(pack);
    }

    public boolean getObf() {
        return IDE.obf.isSelected();
    }

    public void setObf(boolean obf) {
        IDE.obf.setSelected(obf);
    }

    public void setCurrentFile(String name) {
        currentFile = name;
    }

    public String getCurrentFile() {
        return currentFile;
    }

    public String getProjectDir() {
        return projectDir;
    }

    public void setProjectDir(String dir) {
        projectDir = dir;
    }

    public void addSource(String name, String data, boolean save) {
        if (source.containsKey(name)) {
            source.remove(name);

        } else {
            IDE.tree.addSource(name);
        }
        source.put(name, data);

        if (save) {
            try {
                File f = new File(getProjectDir() + "src" + File.separator + name);
                f.mkdirs();
                f.delete();
                BufferedWriter writer = new BufferedWriter(new FileWriter(f));
                writer.write(data);
                writer.close();
                IDE.project.saveProjectFile();

            } catch (IOException ex) {

            }
        }

    }

    public void newSource() {
        UIManager.put("OptionPane.okButtonText", "Создать");
        String name = JOptionPane.showInputDialog(IDE.getWindows()[0], "Введите название (без .lis)", "Новый исходник", JOptionPane.PLAIN_MESSAGE);
        UIManager.put("OptionPane.okButtonText", "OK");
        if (name != null) {
            name = name.trim();
            if (!sourceExist(name + ".lis")) {
                if (IDE.latin(name)) {
                    addSource(name + ".lis", "", true);
                    IDE.tree.update(false);
                    IDE.project.saveProjectFile();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Файл с таким именем уже существует!", "Добавление", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void newResource() {

        File[] files = (File[]) FileChoose.chooseFile(true, null, "mb_res", "Выберете ресурсы", "Добавить", format);
        if (files != null) {
            for (File file : files) {
                File src = new File(getProjectDir() + "res" + File.separator + file.getName());
                try {
                    ///if (!Files.exists(Paths.get(getProjectDir() + "res"), LinkOption.NOFOLLOW_LINKS)) {
                    if (!new File(getProjectDir() + "res").exists()) {
                        new File(getProjectDir() + "res").mkdir();
                    }

                    copy(file, src);
                    addResource(file.getName());

                } catch (IOException ex) {
                }
            }
        }
        getPNGFile(null);
        IDE.tree.update(true);
        IDE.project.saveProjectFile();

    }

    public void addResource(String name) {
        if (!resourceExist(name)) {
            resource.add(name);
            IDE.tree.addResource(name);
        }

    }

    public void delSource(String name) {
        source.remove(name);
        new File(getProjectDir() + "src" + File.separator + name).delete();
        IDE.tree.delSource(name);
    }

    public void delResource(String name) {
        resource.remove(name);
        new File(getProjectDir() + "res" + File.separator + name).delete();
        IDE.tree.delResource(name);
    }

    public boolean resourceRename(String name, String newname, int id) {
        if (!resourceExist(newname)) {
            resource.remove(id);
            new File(getProjectDir() + "res" + File.separator + name).renameTo(new File(getProjectDir() + "res" + File.separator + newname));
            resource.add(id, newname);
            IDE.tree.resourceRename(id, newname);
            IDE.tree.update(false);
            IDE.project.saveProjectFile();
            return true;
        }
        return false;
    }

    public boolean sourceRename(String name, String newname, int id) {
        if (!sourceExist(newname)) {
            String code = source.get(name);
            delSource(name);
            IDE.tree.delSource(name);
            //IDE.tree.sourceRename(id, newname);
            addSource(newname, code, true);
            IDE.tree.update(false);
            IDE.project.saveProjectFile();
            return true;
        }
        return false;

    }

    public boolean sourceExist(String name) {

        for (String key1 : source.keySet()) {
            if (name.toLowerCase().equals(key1.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean resourceExist(String name) {
        return resource.contains(name);
    }

    public void build(final boolean run) {

        IDE.console.add("<b>Версия:</b> " + ((getVersion() == 1) ? "1.9.1" : "1.8.6"));
        IDE.console.add("<b>Обфускация:</b> " + ((getObf()) ? "вкл." : "выкл."));
        IDE.console.add("<b>Сборка..</b>");

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                ZipOutputStream zip;
                ByteArrayOutputStream bas = new ByteArrayOutputStream();
                DataOutputStream data = new DataOutputStream(bas);
                String log = "";

                File f = new File(getProjectDir() + "dist" + File.separator + getProjectName() + ".temp.jar");
                f.mkdirs();
                f.delete();
                try {

                    zip = new ZipOutputStream(new FileOutputStream(f));
                    zip.putNextEntry(new ZipEntry("META-INF/MANIFEST.MF"));
                    zip.write(getManifest().getBytes("UTF-8"));
                    zip.closeEntry();

                    for (String listSource : IDE.tree.getListSource()) {
                        bas = new ByteArrayOutputStream();
                        data = new DataOutputStream(bas);
                        LISCompiler lis = new LISCompiler();
                        LIS2Java l2j = new LIS2Java();
                        l2j.compile(getSource(listSource));
                        log = lis.compile(listSource, getSource(listSource), (getVersion() == 1), getObf(), data);

                        if (!log.equals("")) {
                            IDE.console.error(listSource, log);
                            break;
                        } else {
                            zip.putNextEntry(new ZipEntry(lisName(listSource) + ".bas"));
                            zip.write(bas.toByteArray());
                            zip.closeEntry();
                        }
                    }
                    byte[] buf = new byte[1024];
                    for (String listResource : IDE.tree.getListResource()) {
                        FileInputStream in = new FileInputStream(getProjectDir() + "res" + File.separator + listResource);
                        if (in.available() > 0) {
                            zip.putNextEntry(new ZipEntry(listResource));
                            int len;
                            while ((len = in.read(buf)) > 0) {
                                zip.write(buf, 0, len);
                            }
                            zip.closeEntry();
                            in.close();
                        }
                    }

                    String lib = ((getVersion() == 1) ? "/lib/mb191/" : "/lib/mb186/");
                    String main = ((getVersion() == 1) ? "Main" : "cpu");

                    for (int i = 0; i < ((getVersion() == 1) ? 7 : 10); i++) {
                        zip.putNextEntry(new ZipEntry(clazz[i] + ".class"));
                        InputStream in = this.getClass().getResourceAsStream(lib + clazz[i]);
                        buf = new byte[1024];
                        int len;
                        while ((len = in.read(buf)) > 0) {
                            zip.write(buf, 0, len);
                        }
                        zip.closeEntry();

                    }

                    zip.putNextEntry(new ZipEntry(main + ".class"));
                    InputStream in = this.getClass().getResourceAsStream(lib + main);
                    buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        zip.write(buf, 0, len);
                    }
                    zip.closeEntry();
                    zip.close();
                    data.close();
                } catch (IOException ex) {
                    IDE.console.error("Сборка завершена со сбоем. " + ex.getMessage());
                    log = ex.getMessage();

                } finally {
                    if (log.equals("")) {

                        File jar = new File(getProjectDir() + "dist" + File.separator + getProjectName() + ".jar");
                        try {
                            copy(f, jar);

                            IDE.console.success("Сборка успешно завершена.");
                            Toolkit.getDefaultToolkit().beep();
                            if (run) {
                                IDE.console.add("<b>Запуск эмулятора...</b>");
                                midletRun();
                            }

                        } catch (IOException ex) {
                            IDE.console.error("Сборка завершена со сбоем. " + ex.getMessage());
                        }
                    }

                    f.delete();
                }
            }
        });
    }

    public void buildAndroid() {
        IDE.console.add("<b>Версия:</b> Android");
        IDE.console.add("<b>Обфускация:</b> " + ((getObf()) ? "вкл." : "выкл."));
        IDE.console.add("<b>Сборка..</b>");

        SwingUtilities.invokeLater(new Runnable() {
            String log = "";

            public void run() {

                ZipOutputStream zip = null;
                ByteArrayOutputStream bas = new ByteArrayOutputStream();
                DataOutputStream data = new DataOutputStream(bas);

                File f = new File(getProjectDir() + File.separator + "dist" + File.separator + getProjectName() + ".temp.apk");
                f.mkdirs();
                f.delete();
                try {

                    zip = new ZipOutputStream(new FileOutputStream(f));
                    zip.putNextEntry(new ZipEntry("assets/BASIC.jad"));
                    zip.write(getAndroidJAD().getBytes("UTF-8"));
                    zip.closeEntry();

                    for (String listSource : IDE.tree.getListSource()) {
                        bas = new ByteArrayOutputStream();
                        data = new DataOutputStream(bas);
                        LISCompiler lis = new LISCompiler();
                        log = lis.compile(listSource, getSource(listSource), (getVersion() == 1), getObf(), data);

                        if (!log.equals("")) {
                            IDE.console.error(listSource, log);
                            break;
                        } else {
                            zip.putNextEntry(new ZipEntry("assets/" + lisName(listSource) + ".bas"));
                            zip.write(bas.toByteArray());
                            zip.closeEntry();
                        }
                    }
                    byte[] buf = new byte[1024];
                    for (String listResource : IDE.tree.getListResource()) {
                        FileInputStream in = new FileInputStream(getProjectDir() + File.separator + "res" + File.separator + listResource);
                        if (in.available() > 0) {
                            zip.putNextEntry(new ZipEntry("assets/" + listResource));
                            int len;
                            while ((len = in.read(buf)) > 0) {
                                zip.write(buf, 0, len);
                            }
                            zip.closeEntry();
                            in.close();
                        }
                    }

                    String files[] = {"classes"};

                    zip.putNextEntry(new ZipEntry("classes.dex"));

                    InputStream in = this.getClass().getResourceAsStream("/lib/android/classes");
                    buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        zip.write(buf, 0, len);
                    }
                    zip.closeEntry();

                    zip.putNextEntry(new ZipEntry("res/drawable/app_icon.png"));
                    in = (getIcon().equals("Нет") || getIcon() == null) ? this.getClass().getResourceAsStream("/res/icon/project.png") : new FileInputStream(getProjectDir() + File.separator + "res" + File.separator + getIcon().substring(1));

                    buf = new byte[1024];
                    while ((len = in.read(buf)) > 0) {
                        zip.write(buf, 0, len);
                    }
                    zip.closeEntry();

                    String packag = getPackage();
                    if (packag.length() < 40) {
                        packag += "ooooooooooooooooooooooooooooooo".substring(0, 40 - packag.length());
                    }
                    if (packag.length() > 40) {
                        packag = packag.substring(0, 40);
                    }
                    zip.putNextEntry(new ZipEntry("resources.arsc"));
                    in = this.getClass().getResourceAsStream("/lib/android/resources");
                    buf = new byte[188];
                    in.read(buf);
                    zip.write(buf);
                    in.skip(80);
                    zip.write(packag.getBytes("UTF-16LE"));

                    while ((len = in.read(buf)) > 0) {
                        zip.write(buf, 0, len);
                    }
                    zip.closeEntry();

                    zip.putNextEntry(new ZipEntry("AndroidManifest.xml"));

                    in = this.getClass().getResourceAsStream("/lib/android/AndroidManifest.xml");
                    buf = new byte[568];
                    in.read(buf);
                    zip.write(buf);
                    in.skip(80);

                    zip.write(packag.getBytes("UTF-16LE"));

                    buf = new byte[234];
                    in.read(buf);
                    zip.write(buf);
                    in.skip(40);

                    String name = getMidletName();
                    if (name.length() < 20) {
                        name += "                    ".substring(0, 20 - name.length());
                    }
                    if (name.length() > 20) {
                        name = name.substring(0, 20);
                    }
                    zip.write(name.getBytes("UTF-16LE"));
                    while ((len = in.read(buf)) > 0) {
                        zip.write(buf, 0, len);
                    }

                    zip.closeEntry();

                    zip.close();
                    data.close();

                } catch (Exception ex) {
                    IDE.console.error("Сборка завершена со сбоем. " + ex.getMessage());
                    log = ex.getMessage();

                } finally {

                    if (log.equals("")) {
                        File key = new File(getProjectDir() + File.separator + "dist" + File.separator + "debug.keystore");
                        key.deleteOnExit();
                        try {
                            key.createNewFile();
                            FileOutputStream out = new FileOutputStream(key);
                            InputStream store = this.getClass().getResourceAsStream("/lib/android/debug.keystore");
                            while (store.available() > 0) {
                                out.write(store.read());
                            }
                            out.close();
                        } catch (IOException ex) {
                        }

                        File jar = new File(getProjectDir() + File.separator + "dist" + File.separator + getProjectName() + ".apk");
                        try {
                            copy(f, jar);
                            try {
                                String sig = Signer.sign(null, jar, key, "android".toCharArray(), "androiddebugkey", "android".toCharArray());
                                if (sig == null) {
                                    IDE.console.success("Сборка успешно завершена");
                                } else {
                                    IDE.console.error(sig);
                                }
                            } catch (Exception ex) {
                                IDE.console.error("Сборка завершена, но приложение не подписано");
                            }
                            Toolkit.getDefaultToolkit().beep();
                        } catch (Exception ex) {
                            IDE.console.error("Сборка завершена со сбоем. " + ex.getMessage());
                        }
                    }

                    f.delete();
                }
            }
        });
    }

    public void midletRun() {
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", "microemulator/microemulator.jar" + File.pathSeparatorChar + "microemulator/lib/microemu-jsr-75.jar" + File.pathSeparatorChar + "microemulator/lib/microemu-nokiaui.jar" + File.pathSeparatorChar + "microemulator/lib/midpapi20.jar" + File.pathSeparatorChar + "microemulator/lib/cldcapi11.jar", "org.microemu.app.Main", "--impl", "org.microemu.cldc.file.FileSystem", "org.microemu.examples.fcview.FCViewMIDlet", getProjectDir() + "dist" + File.separator + getProjectName() + ".jar");
            pb.start();
        } catch (Exception ex) {
        }
    }

    public String lisName(String name) {
        return name.substring(0, name.lastIndexOf('.'));
    }

    public void saveProjectFile() {
        if (getProjectName() != null) {
            updateVersion();
            String info = "PrjName=" + getProjectName() + "\n";
            info += "Obfuscation=" + ((getObf()) ? "true" : "false") + "\n";
            info += "Version=" + ((getVersion() == 1) ? "191" : "186") + "\n";
            info += "MidletName=" + getMidletName() + "\n";
            info += "Vendor=" + getVendor() + "\n";
            info += "Midlet-Version=" + IDE.jSpinner1.getValue().toString() + "\n";
            info += "Midlet-Icon=" + getIcon() + "\n";
            info += "Package=" + getPackage() + "\n";
            info += "[Source]\n";
            for (String listSource : IDE.tree.getListSource()) {
                info += listSource + "\n";
            }
            info += "[Resource]\n";
            for (String listResource : IDE.tree.getListResource()) {
                info += listResource + "\n";
            }

            try {
                FileOutputStream out = new FileOutputStream(getProjectDir() + getProjectName().concat(".mbp"));
                out.write(TextUtils.toBase64(TextUtils.convUnicodeToCp1251(info)).getBytes());
                out.close();
            } catch (IOException ex) {

            }
        }

    }

    public String getManifest() {
        String manifest;
        String midletIcon = (getIcon().equals("") || getIcon().equals("Нет")) ? "" : getIcon().substring(1);

        manifest = "Manifest-Version: 1.0\n"
                + "Created-By: MobileBASIC IDE (http://mbteam.ru)\n"
                + "MIDlet-1: " + getMidletName() + "," + midletIcon + "," + ((getVersion() == 1) ? "Main" : "cpu") + "\n"
                + "MIDlet-Vendor: " + getVendor() + "\n"
                + "MIDlet-Version: " + IDE.jSpinner1.getValue().toString() + "\n"
                + "MIDlet-Name: " + getMidletName() + "\n"
                + "MicroEdition-Configuration: CLDC-1.1\n"
                + "MicroEdition-Profile: MIDP-2.0\n"
                + ((getVersion() == 1) ? "FullScreenMode: true\n" : "");

        return manifest;
    }

    public String getAndroidJAD() {
        return "Manifest-Version: 1.0\n"
                + "Created-By: MobileBASIC IDE (http://mbteam.ru)\n"
                + "MIDlet-1: " + getMidletName() + ",/icon.png,Main\n"
                + "MIDlet-Vendor: mbteam.ru\n"
                + "MIDlet-Version: " + IDE.jSpinner1.getValue().toString() + "\n"
                + "MIDlet-Name: " + getMidletName() + "\n"
                + "MicroEdition-Configuration: CLDC-1.1\n"
                + "MicroEdition-Profile: MIDP-2.0\n"
                + "FullScreenMode: true\n";
    }

    public void getPNGFile(String icon) {
        String current = (icon == null) ? getIcon() : icon;
        int curr = 0;
        int i = 1;
        ArrayList png = new ArrayList(0);
        png.add("Нет");
        for (Object resource1 : resource) {
            if (((String) resource1).endsWith(".png")) {
                png.add("/" + resource1);
                if (("/" + resource1).equals(current)) {
                    curr = i;
                }
                i++;
            }
        }
        String[] arr = new String[png.size()];
        png.toArray(arr);
        com.holdfast.mbide.form.IDE.midletIcon.setModel(new javax.swing.DefaultComboBoxModel(arr));
        com.holdfast.mbide.form.IDE.midletIcon.setSelectedIndex(curr);
    }

    public String getSource(String name) {
        return source.get(name);
    }

    public static boolean latin(String text) {
        Pattern pat = Pattern.compile("[a-zA-Z0-9-.\\s]+");
        Matcher match = pat.matcher(text.trim());
        return match.matches();

    }

    public void projectOpen(String path) {
        if (path != null && !path.equals("") && new File(path).exists()) {
            try {

                setProjectDir(new File(path).getParent() + File.separator);
                BufferedReader buffer = new BufferedReader(new FileReader(new File(path)));
                String prop = TextUtils.convCp1251ToUnicode(TextUtils.fromBase64(buffer.readLine()));
                StringReader string = new StringReader(prop);
                props.load(new StringReader(prop));
                String prgName = props.getProperty("PrjName");
                if (prgName != null && !prgName.equals("")) {
                    IDE.tree.reinit();
                    setProjectName(prgName);
                    setMidletName(props.getProperty("MidletName", prgName));
                    setVendor(props.getProperty("Vendor", "mbteam.ru"));
                    if (props.getProperty("Obfuscation").equals("true")) {
                        setObf(true);
                    } else {
                        setObf(false);
                    }

                    String mbVer = props.getProperty("Version", "0");
                    setPackage(props.getProperty("Package", "com.holdfast.MBIDE"));
                    String mdlVer = props.getProperty("Midlet-Version", "0");
                    String icon = props.getProperty("Midlet-Icon", "");
                    setVersion(mbVer.equals("191") ? 1 : 0);
                    buffer = new BufferedReader(string);
                    String line = buffer.readLine();

                    while (line != null) {

                        if (line.equals("[Source]")) {
                            line = buffer.readLine();
                            while (!line.equals("[Resource]")) {
                                try {
                                    InputStream lis = new FileInputStream(new File(getProjectDir() + "src" + File.separator + line));
                                    ByteArrayOutputStream buf = new ByteArrayOutputStream();

                                    int nRead;
                                    byte[] data = new byte[16384];

                                    while ((nRead = lis.read(data, 0, data.length)) != -1) {
                                        buf.write(data, 0, nRead);
                                    }
                                    buf.flush();

                                    addSource(line, TextUtils.byteArrayToString(buf.toByteArray(), ""), false);

                                } catch (IOException e) {
                                    if (line.equals("Autorun.lis")) {
                                        addSource("Autorun.lis", "", true);
                                    }
                                }
                                //read source
                                line = buffer.readLine();
                            }
                        } else if (line.equals("[Resource]")) {
                            line = buffer.readLine();
                            while (line != null) {
                                //if (Files.exists(Paths.get(getProjectDir() + "res" + File.separator + line), LinkOption.NOFOLLOW_LINKS)) {
                                if (new File(getProjectDir() + "res" + File.separator + line).exists()) {
                                    addResource(line); //read resource
                                }
                                line = buffer.readLine();
                            }
                        } else {
                            line = buffer.readLine();
                        }
                    }
                    if (getVersion() == 1) {
                        IDE.jButton7.setEnabled(true);
                        IDE.buildapk.setEnabled(true);
                    } else {
                        IDE.jButton7.setEnabled(false);
                        IDE.buildapk.setEnabled(false);
                    }
                    IDE.tree.enable();
                    IDE.console.enable();
                    IDE.tree.update(true);
                    IDE.jTabbedPane2.removeAll();
                    IDE.openTab = new HashMap<String, Integer>();
                    Tree.listener.treeSourceSelect("Autorun.lis");
                    getPNGFile(icon);
                    IDE.jSpinner1.setValue(Float.valueOf(mdlVer));
                    IDE.console.clean();
                    IDE.console.add("Проект " + prgName + " открыт.");
                    IDE.console.add("Готов.");
                } else {
                    IDE.tree.disable();
                    IDE.console.disable();
                    IDE.tree.update(true);
                    IDE.jTabbedPane2.removeAll();
                    IDE.jMenu2.setEnabled(false);
                    IDE.jMenu3.setEnabled(false);
                    IDE.jButton4.setEnabled(false);
                    IDE.jButton5.setEnabled(false);
                    IDE.jButton6.setEnabled(false);
                    IDE.jButton7.setEnabled(false);
                    IDE.jButton8.setEnabled(false);
                }

            } catch (IOException ex) {
                Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            }
            props.clear();
        }
    }

    public void setVersion(int mb) {
        this.mb = mb;
        IDE.buttonGroup1.setSelected((mb == 0) ? IDE.btnVersion1.getModel() : IDE.btnVersion2.getModel(), true);
    }

    public void updateVersion() {
        setVersion(IDE.buttonGroup1.isSelected(IDE.btnVersion1.getModel()) ? 0 : 1);
    }

    public int getVersion() {
        return mb;
    }

    public void projectCreate(String name, String path, int mb) {
        if (new File(path + File.separator + name + ".mbp").exists()) {
            JOptionPane.showMessageDialog(null, "Проект уже существует!", "Ошибка создания", JOptionPane.ERROR_MESSAGE);
            new newProject(null, true).setVisible(true);
        } else if (name != null && !name.equals("") && path != null && !path.equals("")) {
            setProjectDir(path + File.separator);
            if (mb == 1) {
                IDE.jButton7.setEnabled(true);
                IDE.buildapk.setEnabled(true);
            } else {
                IDE.jButton7.setEnabled(false);
                IDE.buildapk.setEnabled(false);
            }
            IDE.tree.reinit();
            IDE.tree.popup(true);
            IDE.console.enable();
            setProjectName(name);
            setMidletName(name);
            setVersion(mb);
            addSource("Autorun.lis", (mb == 1) ? "10 PRINT \"Hello, world!\"\n15 REPAINT\n20 SLEEP 5000\n30 END" : "10 PRINT \"Hello, world!\"\n20 SLEEP 5000\n30 END", true);
            IDE.tree.enable();
            IDE.tree.update(true);
            IDE.jTabbedPane2.removeAll();
            IDE.openTab = new HashMap<String, Integer>();
            Tree.listener.treeSourceSelect("Autorun.lis");
            getPNGFile(null);
            IDE.console.clean();
            IDE.console.add("Проект " + name + " создан.");
            IDE.console.add("Готов.");
        }
    }

    public static void copy(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    private final String[] clazz = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", ""};
}
