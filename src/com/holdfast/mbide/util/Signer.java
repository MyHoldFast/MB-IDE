package com.holdfast.mbide.util;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;

public class Signer {

    private static final String SIGNED = "SIGNED";
    private static final String SIGNED_UNALIGNED = "SIGNED";
    public static final String REGEX_APK_FILES = "(?si).+\\.apk";
    public static final String REGEX_KEYSTORE_FILES = "(?si).+\\.keystore";
    public static final String REGEX_JAR_FILES = "(?si).+\\.jar";
    public static final String REGEX_ZIP_FILES = "(?si).+\\.zip";
    public static final String FILE_EXT_APK = ".apk";

    public static String sign(File jdkPath, File targetFile, File keyFile,
            char[] storepass, String alias, char[] keypass) throws IOException,
            InterruptedException {

        //String jarsigner = jdkPath != null && jdkPath.isDirectory() ? jdkPath
        //   .getAbsolutePath() + "/jarsigner.exe" : "jarsigner";
        /*
         * jarsigner -keystore KEY_FILE -sigalg MD5withRSA -digestalg SHA1
         * -storepass STORE_PASS -keypass KEY_PASS APK_FILE ALIAS_NAME
         */
        ProcessBuilder pb = new ProcessBuilder(new String[]{"jarsigner",
            "-keystore", keyFile.getAbsolutePath(), "-sigalg",
            "MD5withRSA", "-digestalg", "SHA1", "-storepass",
            new String(storepass), "-keypass", new String(keypass),
            targetFile.getAbsolutePath(), alias});
        Process p = pb.start();

        StringBuilder console = new StringBuilder();
        InputStream stream = p.getInputStream();
        try {
            int read;
            byte[] buf = new byte[1024 * 99];
            while ((read = stream.read(buf)) > 0) {
                console.append(new String(buf, 0, read));
            }
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        p.waitFor();

        final String result = console.toString().trim();

        if (result.isEmpty() || result.matches("(?sim)^jar signed.+")) {
            final String oldName = targetFile.getName();
            String newName;
            if (oldName.matches("(?si).*?unsigned.+")) {
                if (oldName.matches(REGEX_APK_FILES)) {
                    newName = oldName.replaceFirst("(?si)unsigned",
                            Matcher.quoteReplacement(SIGNED_UNALIGNED));
                } else {
                    newName = oldName.replaceFirst("(?si)unsigned",
                            Matcher.quoteReplacement(SIGNED));
                }
            } else if (oldName.matches(REGEX_APK_FILES)) {
                newName = appendFilename(oldName, '_' + SIGNED_UNALIGNED);
            } else if (oldName.matches(REGEX_JAR_FILES)
                    || oldName.matches(REGEX_ZIP_FILES)) {
                newName = appendFilename(oldName, '_' + SIGNED);
            } else {
                newName = String.format("%s_%s", oldName, SIGNED);
            }
            File target = new File(targetFile.getParent()
                    + File.separator + newName);
            target.delete();
            if (targetFile.renameTo(target)) {
                return null;
            }

            return "Error";
        } else {
            return result;
        }
    }

    public static String appendFilename(String fileName, String suffix) {
        if (fileName.matches("(?si).+\\.[^ \t]+")) {
            final int iPeriod = fileName.lastIndexOf(KeyEvent.VK_PERIOD);
            return fileName.substring(0, iPeriod) + suffix
                    + (char) KeyEvent.VK_PERIOD
                    + fileName.substring(iPeriod + 1);
        }

        return fileName + suffix;
    }
}
