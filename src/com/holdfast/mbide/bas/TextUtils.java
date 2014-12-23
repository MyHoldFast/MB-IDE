package com.holdfast.mbide.bas;

import java.io.*;

public class TextUtils {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
    private static final char[] MAP = "\u0402\u0403\u201A\u0453\u201E\u2026\u2020\u2021\u20AC\u2030\u0409\u2039\u040A\u040C\u040B\u040F\u0452\u2018\u2019\u201C\u201D\u2022\u2013\u2014\uFFFD\u2122\u0459\u203A\u045A\u045C\u045B\u045F\u00A0\u040E\u045E\u0408\u00A4\u0490\u00A6\u00A7\u0401\u00A9\u0404\u00AB\u00AC\u00AD\u00AE\u0407\u00B0\u00B1\u0406\u0456\u0491\u00B5\u00B6\u00B7\u0451\u2116\u0454\u00BB\u0458\u0405\u0455\u0457\u0410\u0411\u0412\u0413\u0414\u0415\u0416\u0417\u0418\u0419\u041A\u041B\u041C\u041D\u041E\u041F\u0420\u0421\u0422\u0423\u0424\u0425\u0426\u0427\u0428\u0429\u042A\u042B\u042C\u042D\u042E\u042F\u0430\u0431\u0432\u0433\u0434\u0435\u0436\u0437\u0438\u0439\u043A\u043B\u043C\u043D\u043E\u043F\u0440\u0441\u0442\u0443\u0444\u0445\u0446\u0447\u0448\u0449\u044A\u044B\u044C\u044D\u044E\u044F".toCharArray();

    public static String byteArrayToString(byte[] bytes, String encoding) {
        if (encoding.equals("win1251")) {
            char[] chars = new char[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[i] = (b >= 0) ? (char) b : MAP[b + 128];
            }
            String s = new String(chars);
            return s;
        } else {
            try {
                return decodeUTF8(bytes, false);
            } catch (UTFDataFormatException udfe) {
            }
        }

        char[] chars = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            chars[i] = (b >= 0) ? (char) b : MAP[b + 128];
        }
        String s = new String(chars);
        return s;

    }

    public static String convCp1251ToUnicode(final byte[] buf) {
        if (buf == null) {
            return null;
        }
        int count = buf.length;
        char[] chars = new char[count];
        for (int i = 0; i < count; i++) {
            byte b = buf[i];
            char ch;
            // украинские буквы
            if (b == -77) {
                ch = 0x456;
            } else if (b == -65) {
                ch = 0x457;
            } else if (b == -70) {
                ch = 0x454;
            } else if (b == -76) {
                ch = 0x491;
            } else if (b == -78) {
                ch = 0x406;
            } else if (b == -81) {
                ch = 0x407;
            } else if (b == -91) {
                ch = 0x490;
            } else if (b == -86) {
                ch = 0x404;
            } // остальные
            else if (b == -70) {
                ch = 0x401;
            } else if (b == -72) {
                ch = 0x451;
            } else if (b < 0) {
                ch = (char) (0x400 | ((b & 0x7f) - 0x30));
            } else {
                ch = (char) b;
            }
            chars[i] = ch;
        }
        return new String(chars);
    }

    public static String convCp1251ToUnicode(final String s) {
        if (s == null) {
            return null;
        }
        StringBuilder b = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            // украинские буквы
            if (ch == 0xb3) {
                ch = 0x456;
            } else if (ch == 0xbf) {
                ch = 0x457;
            } else if (ch == 0xba) {
                ch = 0x454;
            } else if (ch == 0xb4) {
                ch = 0x491;
            } else if (ch == 0xb2) {
                ch = 0x406;
            } else if (ch == 0xaf) {
                ch = 0x407;
            } else if (ch == 0xa5) {
                ch = 0x490;
            } else if (ch == 0xaa) {
                ch = 0x404;
            } // остальные
            else if (ch > 0xbf && ch < 0x410 && ch != 0x401) {
                ch += 0x410 - 0xc0;
            } else if (ch == 0xa8) {
                ch = 0x401;
            } else if (ch == 0xb8) {
                ch = 0x451;
            }
            b.append(ch);
        }
        return b.toString();
    }

    public static String convUnicodeToCp1251(final String s) {
        if (s == null) {
            return null;
        }
        StringBuilder b = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            // украинские буквы
            if (ch == 0x456) {
                ch = 0xb3;
            } else if (ch == 0x457) {
                ch = 0xbf;
            } else if (ch == 0x454) {
                ch = 0xba;
            } else if (ch == 0x491) {
                ch = 0xb4;
            } else if (ch == 0x406) {
                ch = 0xb2;
            } else if (ch == 0x407) {
                ch = 0xaf;
            } else if (ch == 0x490) {
                ch = 0xa5;
            } else if (ch == 0x404) {
                ch = 0xaa;
            } // остальные
            else if (ch == 0x401) {
                ch = 0xa8; //Ё
            } else if (ch == 0x451) {
                ch = 0xb8; //ё
            } else if (ch > 0x409) {
                ch += 0xc0 - 0x410;
            }
            b.append(ch);
        }
        return b.toString();
    }

    public static String decodeUTF8(byte[] data, boolean gracious) throws UTFDataFormatException {
        byte a, b, c;
        StringBuilder ret = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            try {
                a = data[i];
                if ((a & 0x80) == 0) {
                    ret.append((char) a);
                } else if ((a & 0xe0) == 0xc0) {
                    b = data[i + 1];
                    if ((b & 0xc0) == 0x80) {
                        ret.append((char) (((a & 0x1F) << 6) | (b & 0x3F)));
                        i++;
                    } else {
                        throw new UTFDataFormatException("Illegal 2-byte group");
                    }
                } else if ((a & 0xf0) == 0xe0) {
                    b = data[i + 1];
                    c = data[i + 2];
                    if (((b & 0xc0) == 0x80) && ((c & 0xc0) == 0x80)) {
                        ret.append((char) (((a & 0x0F) << 12) | ((b & 0x3F) << 6) | (c & 0x3F)));
                        i += 2;
                    } else {
                        throw new UTFDataFormatException("Illegal 3-byte group");
                    }
                } else if (((a & 0xf0) == 0xf0) || ((a & 0xc0) == 0x80)) {
                    throw new UTFDataFormatException("Illegal first byte of a group");
                }
            } catch (UTFDataFormatException udfe) {
                if (gracious) {
                    ret.append("?");
                } else {
                    throw udfe;
                }
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                if (gracious) {
                    ret.append("?");
                } else {
                    throw new UTFDataFormatException("Unexpected EOF");
                }
            }
        }
        return ret.toString();
    }

    public static byte[] fromBase64(String s) {
        return baosFromBase64(s).toByteArray();
    }

    public static String toBase64(String source) {
        int len = source.length();
        char[] out = new char[((len + 2) / 3) * 4];
        for (int i = 0, index = 0; i < len; i += 3, index += 4) {
            boolean trip = false;
            boolean quad = false;

            int val = (0xFF & source.charAt(i)) << 8;
            if ((i + 1) < len) {
                val |= (0xFF & source.charAt(i + 1));
                trip = true;
            }
            val <<= 8;
            if ((i + 2) < len) {
                val |= (0xFF & source.charAt(i + 2));
                quad = true;
            }
            out[index + 3] = ALPHABET.charAt((quad ? (val & 0x3F) : 64));
            val >>= 6;
            out[index + 2] = ALPHABET.charAt((trip ? (val & 0x3F) : 64));
            val >>= 6;
            out[index + 1] = ALPHABET.charAt(val & 0x3F);
            val >>= 6;
            out[index] = ALPHABET.charAt(val & 0x3F);
        }
        return new String(out);
    }

    private static ByteArrayOutputStream baosFromBase64(String s) {
        int padding = 0;
        int ibuf = 1;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);
        for (int i = 0; i < s.length(); i++) {
            int nextChar = s.charAt(i);
            //if( nextChar == -1 )
            //    throw new EndOfXMLException();
            int base64 = -1;
            if (nextChar > 'A' - 1 && nextChar < 'Z' + 1) {
                base64 = nextChar - 'A';
            } else if (nextChar > 'a' - 1 && nextChar < 'z' + 1) {
                base64 = nextChar + 26 - 'a';
            } else if (nextChar > '0' - 1 && nextChar < '9' + 1) {
                base64 = nextChar + 52 - '0';
            } else if (nextChar == '+') {
                base64 = 62;
            } else if (nextChar == '/') {
                base64 = 63;
            } else if (nextChar == '=') {
                base64 = 0;
                padding++;
            } else if (nextChar == '<') {
                break;
            }
            if (base64 >= 0) {
                ibuf = (ibuf << 6) + base64;
            }
            if (ibuf >= 0x01000000) {
                baos.write((ibuf >> 16) & 0xff);                   //00xx0000 0,1,2 =
                if (padding < 2) {
                    baos.write((ibuf >> 8) & 0xff);     //0000xx00 0,1 =
                }
                if (padding == 0) {
                    baos.write(ibuf & 0xff);         //000000xx 0 =
                }
                //len+=3;
                ibuf = 1;
            }
        }
        try {
            baos.close();
        } catch (IOException ignored) {
        }
        return baos;
    }

    public static byte[] convUnicodeToCp1251Byte(final String s) {
        char ch[] = convUnicodeToCp1251(s).toCharArray();
        int count = ch.length;
        byte b[] = new byte[count];
        for (int i = 0; i < count; i++) {
            b[i] = (byte) ch[i];
        }
        return b;
    }
}
