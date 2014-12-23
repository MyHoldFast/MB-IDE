/**
 *
 * @author HoldFast
 */
package com.holdfast.mbide.bas;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BASDecompiler implements Constants {

    private static DataInputStream data;
    private static boolean mb191;
    private static String[] varname;
    private static float[] floats;
    private static String line = "";
    private static String mainCode;
    private static int[] lims;
    private static int cur;
    private static int l;

    private static int tosbyte(int bytes) {
        if (0 <= bytes && bytes <= 63) {
            return (int) bytes;
        }
        if (64 <= bytes && bytes <= 191) {
            return (int) (-(128 - bytes));
        }
        if (192 <= bytes && bytes <= 255) {
            return (int) (-(256 - bytes));
        }
        return 0;
    }

    public static String decompile(InputStream bas) {
        try {
            data = new DataInputStream(bas);
            switch (data.readInt()) {
                case SIGNATURE_MB:
                    mb191 = false;
                    break;
                case SIGNATURE_MB191:
                    mb191 = true;
                    break;
                default:
                    return "notbas";
            }

            int varnum = data.readShort();
            varname = new String[varnum];
            String var;
            for (int i = 0; i < varnum; i++) {
                var = "";
                int varlength = data.readShort();
                for (int ii = 0; ii < varlength; ii++) {
                    var += (char) data.readUnsignedByte();
                }
                data.readByte();
                varname[i] = var;
            }

            if (!mb191) {
            } else {
                varnum = data.readShort();
                floats = new float[varnum];
                for (int i = 0; i < varnum; i++) {
                    floats[i] = data.readFloat();
                }
            }
            int codeln = data.readShort();
            if (codeln != data.available()) {
                return "obfuscate";
            }

            mainCode = "";
            while (data.available() > 0) {
                boolean first = true;
                line = "";
                line += data.readShort();
                int linelen = data.readUnsignedByte() - 4;
                lims = null;
                lims = new int[linelen];
                for (int iii = 0; iii < linelen; iii++) {
                    lims[iii] = data.readUnsignedByte();
                }
                cur = 0;

                while (cur < lims.length) {

                    int opType = lims[cur];
                    cur++;
                    if (opType == 0xfc) {
                        if (first) {
                            line += " " + varname[lims[cur]];
                        } else {
                            line += varname[lims[cur]];
                        }
                        cur++;

                    } else {
                        first = false;
                        switch (opType) {
                            case 0x0e:
                                line += " REM ";
                                l = lims[cur];
                                cur++;
                                for (int i = 0; i < l; i++) {
                                    line += (char) lims[cur];
                                    cur++;
                                }
                                break;

                            case 0xfd:
                                line += "\"";
                                l = lims[cur];
                                cur++;
                                for (int i = 0; i < l; i++) {
                                    line += (char) lims[cur];
                                    cur++;
                                }
                                line += "\"";
                                break;

                            case 0x30:
                                line += " DATA ";
                                l = lims[cur];
                                cur++;
                                for (int i = 0; i < l; i++) {
                                    line += (char) lims[cur];
                                    cur++;
                                }
                                break;
                            case 0xf6:
                                line += "=";
                                break;
                            case 0xf7:
                                continue;

                            case 0xf8:
                            case 0xf9:
                                line += lims[cur];
                                cur++;
                                break;
                            case 0xfa:
                                line += (lims[cur] * 256 + lims[cur + 1]);
                                cur = cur + 2;
                                break;
                            case 0xfb:
                                line += (lims[cur] * 16777216 + lims[cur + 1] * 65536 + lims[cur + 2] * 256 + lims[cur + 3]);
                                cur = cur + 4;
                                break;
                            case 0xfe:
                                if (mb191) {
                                    line += floats[lims[cur]];
                                    cur++;
                                } else {
                                    int exp = tosbyte(lims[cur + 3]);
                                    int m = (65536 + lims[cur] * 65536 + lims[cur + 1] * 256 + lims[cur + 2]) / 500000;
                                    float e = 1;
                                    float d = 1;
                                    if (exp > 0) {
                                        for (int i = 0; i < exp; i++) {
                                            d = d * 10;
                                        }
                                        e = d;
                                    }
                                    if (exp < 0) {
                                        for (int i = exp; i < 0; i++) {
                                            d = d / 10;
                                        }
                                        e = d;
                                    }
                                    line += (float) (e * m);
                                    cur = cur + 4;
                                }
                                break;

                            default:
                                line += ops[opType];
                                break;
                        }
                    }
                }
                data.readUnsignedByte();
                line += "\r\n";
                mainCode += line;
            }
            return TextUtils.convCp1251ToUnicode(mainCode);
        } catch (IOException ex) {

            return "";
        }
    }
}
