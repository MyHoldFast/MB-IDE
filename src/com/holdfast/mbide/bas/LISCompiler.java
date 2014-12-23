package com.holdfast.mbide.bas;

import com.holdfast.mbide.form.IDE;
import com.holdfast.mbide.ide.EditorArea;
import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;

public class LISCompiler {

    private final int MAGIC191 = 0x4D420191;
    private final int MAGIC186 = 0x4D420001;

    private boolean mb = false;
    private boolean obf = false;
    private final byte[] code = new byte[256];
    private int codeLen = 0;
    private byte[] sourceProg = null;
    private int sourceSize = 0;
    private int sourceLen = 0;
    private int nvars;
    private final String[] varName = new String[256];
    private final byte[] varType = new byte[256];
    private String line;
    private int lineLen;
    private int lineOffset;
    private String nextToken;
    private String error = "";
    private final String tokenTable[] = {
        "\u1100\001\200STOP", "\u1100\001\200POP", "\u1100\001\200RETURN", "\u1100\001\200END", "\u1100\001\200NEW", "\u1100\001\200RUN", "\u1100\001\200DIR", "\u1100\001\200DEG", "\u1100\001\200RAD", "\u1100\001\200BYE",
        "\u1140\001\000GOTO", "\u1140\001\000GOSUB", "\u1140\001\000SLEEP", "@\001\uA700PRINT", "\u1100\001\000REM", "\u1140\001\u0200DIM", "\u1140\001\000IF", "\u11C2\001\000THEN", "\u1100\001\000CLS", "\u1140\001\000PLOT",
        "\u1140\001\000DRAWLINE", "\u1140\001\000FILLRECT", "\u1140\001\000DRAWRECT", "\u1140\001\000FILLROUNDRECT", "\u1140\001\000DRAWROUNDRECT", "\u1140\001\000FILLARC", "\u1140\001\000DRAWARC", "\u1140\001\000DRAWSTRING", "\u1140\001\000SETCOLOR", "\u1140\001\000BLIT",
        "\uF240\001\000FOR", "\u43C2\001\000TO", "\u43C2\001\000STEP", "\u1140\001\000NEXT", "\u1140\001\000INPUT", "\u1140\001\200LIST", "\u1140\001\000ENTER", "\u1140\001\000LOAD", "\u1140\001\000SAVE", "\u1140\001\000DELETE",
        "\u1140\001\uA700EDIT", "\u1140\001\000TRAP", "\u1140\001\000OPEN", "\u1140\001\000CLOSE", "\u1140\001\000NOTE", "\u1140\001\000POINT", "\u1140\001\000PUT", "\u1140\001\000GET", "\u1100\001\000DATA", "\u1140\001\000RESTORE",
        "\u1140\001\000READ", "\u8801@\247=", "\u8801@\247<>", "\u8801@\247<", "\u8801@\247<=", "\u8801@\247>", "\u8801@\247>=", "\uF201 \247(", "\u2E01\020\u0158)", "\u4301\b\247,",
        "\u9901@\247+", "\u9901@\247-", "\uCC01\200'-", "\uAA01@\247*", "\uAA01@\247/", "\uBB01@\247^", "\u88C1@\247BITAND", "\u88C1@\247BITOR", "\u88C1@\247BITXOR", "\u77C1\200'NOT",
        "\u66C1@\247AND", "\u55C1@\247OR", "\uF201\004 SCREENWIDTH", "\uF201\004 SCREENHEIGHT", "\uF201\004 ISCOLOR", "\uF201\004 NUMCOLORS", "\uF201\004 STRINGWIDTH", "\uF201\004 STRINGHEIGHT", "\uF201\004 LEFT$", "\uF201\004 MID$",
        "\uF201\004 RIGHT$", "\uF201\004 CHR$", "\uF201\004 STR$", "\uF201\004 LEN", "\uF201\004 ASC", "\uF201\004 VAL", "\uF201\004 UP", "\uF201\004 DOWN", "\uF201\004 LEFT", "\uF201\004 RIGHT",
        "\uF201\004 FIRE", "\uF201\004 GAMEA", "\uF201\004 GAMEB", "\uF201\004 GAMEC", "\uF201\004 GAMED", "\uF201\004 DAYS", "\uF201\004 MILLISECONDS", "\uF201\004 YEAR", "\uF201\004 MONTH", "\uF201\004 DAY",
        "\uF201\004 HOUR", "\uF201\004 MINUTE", "\uF201\004 SECOND", "\uF201\004 MILLISECOND", "\uF201\004 RND", "\uF201\004 ERR", "\uF201\004 FRE", "\uF201\004 MOD", "\uF201\004 EDITFORM", "\uF201\004 GAUGEFORM",
        "\uF201\004 CHOICEFORM", "\uF201\004 DATEFORM", "\uF201\004 MESSAGEFORM", "\uF201\004 LOG", "\uF201\004 EXP", "\uF201\004 SQR", "\uF201\004 SIN", "\uF201\004 COS", "\uF201\004 TAN", "\uF201\004 ASIN",
        "\uF201\004 ACOS", "\uF201\004 ATAN", "\uF201\004 ABS", "\u4302\001\000=", "\u4382\001\000#", "\u1140\001\000PRINT", "\u1140\001\000INPUT", "\002\001\000:", "\u1140\001\000GELGRAB", "\u1140\001\000DRAWGEL",
        "\u1140\001\000SPRITEGEL", "\u1140\001\000SPRITEMOVE", "\uF201\004 SPRITEHIT", "\uF201\004 READDIR$", "\uF201\004 PROPERTY$", "\u1140\001\000GELLOAD", "\uF201\004 GELWIDTH", "\uF201\004 GELHEIGHT",
        "\u1140\001\000PLAYWAV",
        "\u1140\001\000PLAYTONE",
        "\uF201\004 INKEY",
        "\uF201\004 SELECT",
        "\u1140\001\000ALERT",
        "\u1140\001\000SETFONT",
        "\u1140\001\000MENUADD",
        "\uF201\004 MENUITEM",
        "\u1140\001\000MENUREMOVE",
        "\u1140\001\000CALL",
        "\u1100\001\200ENDSUB",
        "\u1100\001\000REPAINT",
        "\uF201\004 SENDSMS",
        "\uF201\004 RAND",
        "\u1140\001\000ALPHAGEL",
        "\u1140\001\000COLORALPHAGEL",
        "\u1140\001\000PLATFORMREQUEST",
        "\u1140\001\000DELGEL",
        "\u1140\001\000DELSPRITE",
        "\uF201\004 MKDIR", "\uF201\004 POINTPRESSED", "\uF201\004 POINTDRAGGED", "\uF201\004 POINTHOLD", "\uF201\004 POINTX", "\uF201\004 POINTY", "\u1140\001\000TEXT"};
    private final String[] var_38c = new String[]{"", "", "RET", "", "", "", "", "", "", "", "GT", "GS", "SL", "PR", "", "", "", "TH", "", "", "DL", "FR", "DR", "FRR", "DRR", "FA", "DA", "DS", "SC", "", "", "", "", "", "IN", "", "", "", "", "DEL", "", "TR", "OP", "CL", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "SCW", "SCH", "", "", "STW", "STH", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "EF", "GF", "CF", "DF", "MSF", "", "", "", "", "", "", "", "", "", "", "", "", "PR", "IN", "", "GG", "DG", "SG", "SM", "SH", "", "", "GL", "GW", "GH",
        "PW", "PT", "IK", "SEL", "AL", "SF", "MA", "MI", "MR", "", "ES",
        "RE", "SS", "", "AG", "CAG", "PFR", "", "", "MKDIR", "PP", "PD", "PH", "PX", "PY", ""};

    private final byte TYPE_INTEGER = 0;
    private final byte TYPE_FLOAT = 1;
    private final byte TYPE_STRING = 2;
    private final byte tokGOTO = 10;
    private final byte tokGOSUB = 11;
    private final byte tokSLEEP = 12;
    private final byte tokPRINT = 13;
    private final byte tokREM = 14;
    private final byte tokDIM = 15;
    private final byte tokIF = 16;
    private final byte tokPLOT = 19;
    private final byte tokDRAWLINE = 20;
    private final byte tokFILLRECT = 21;
    private final byte tokDRAWRECT = 22;
    private final byte tokFILLROUNDRECT = 23;
    private final byte tokDRAWROUNDRECT = 24;
    private final byte tokFILLARC = 25;
    private final byte tokDRAWARC = 26;
    private final byte tokDRAWSTRING = 27;
    private final byte tokSETCOLOR = 28;
    private final byte tokBLIT = 29;
    private final byte tokFOR = 30;
    private final byte tokNEXT = 33;
    private final byte tokINPUT = 34;
    private final byte tokLIST = 35;
    private final byte tokENTER = 36;
    private final byte tokLOAD = 37;
    private final byte tokSAVE = 38;
    private final byte tokDELETE = 39;
    private final byte tokEDIT = 40;
    private final byte tokTRAP = 41;
    private final byte tokOPEN = 42;
    private final byte tokCLOSE = 43;
    private final byte tokNOTE = 44;
    private final byte tokPOINT = 45;
    private final byte tokPUT = 46;
    private final byte tokGET = 47;
    private final byte tokDATA = 48;
    private final byte tokRESTORE = 49;
    private final byte tokREAD = 50;
    private final byte tokLBRACKET = 57;
    private final byte tokRBRACKET = 58;
    private final byte tokEDITFORM = 108;
    private final byte tokGAUGEFORM = 109;
    private final byte tokCHOICEFORM = 110;
    private final byte tokDATEFORM = 111;
    private final byte tokMESSAGEFORM = 112;
    private final byte tokPRINTHASH = 125;
    private final byte tokINPUTHASH = 126;
    private final int tokGELGRAB = 128;
    private final int tokDRAWGEL = 129;
    private final int tokSPRITEGEL = 130;
    private final int tokSPRITEMOVE = 131;
    private final int tokSPRITEHIT = 132;
    private final int tokREADDIR$ = 133;
    private final int tokGELLOAD = 135;
    private final int tokGELWIDTH = 136;
    private final int tokGELHEIGHT = 137;
    private final int tokPLAYWAV = 138;
    private final int tokPLAYTONE = 139;
    private final int tokSELECT = 141;
    private final int tokALERT = 142;
    private final int tokSETFONT = 143;
    private final int tokMENUADD = 144;
    private final int tokMENUREMOVE = 146;
    private final int tokCALL = 147;

    private final int tokREPAINT = 149;
    private final int tokSENDSMS = 150;
    private final int tokRAND = 151;
    private final int tokAG = 152;
    private final int tokCAG = 153;
    private final int tokPLATFORMREQUEST = 154;
    private final int tokDELGEL = 155;
    private final int tokDELSPRITE = 156;
    private final int tokMKDIR = 157;
    private final int tokPOINTPRESSED = 158;
    private final int tokPOINTDRAGGED = 159;
    private final int tokPOINTHOLD = 160;
    private final int tokPOINTX = 161;
    private final int tokPOINTY = 162;
    private final int tokTEXT = 163;

    private final int tokMAKEREF = 247;
    private final int tokBYTE = 248;
    private final int tokUBYTE = 249;
    private final int tokUWORD = 250;
    private final int tokINTEGER = 251;
    private final int tokVARIABLE = 252;
    private final int tokSTRING = 253;
    private final int tokFLOAT = 254;
    private int errorline;

    public String compile(String name, String source, boolean b, boolean obf, DataOutputStream data) {
        mb = b;
        this.obf = obf;
        boolean err = Enter(source);
        if (err) {
            EditorArea area = IDE.getEditorByName(name);
            if (area != null) {
                area.error(errorline);
            }
            return error + " at line " + (errorline + 1);
        } else {
            SaveTo(data);
        }
        return "";
    }

    public LISCompiler() {

        int currentSize = 65536;

        while (sourceProg == null) {
            try {
                sourceProg = new byte[currentSize];
                sourceSize = currentSize;
            } catch (OutOfMemoryError e) {
                currentSize -= 256;
                if (currentSize <= 0) {
                    throw e;
                }
            }
        }

    }

    private void PutToken(String token) {
        nextToken = token;
    }

    private String GetToken() {
        String token = null;

        if (nextToken != null) {
            //lineOffset += nextToken.length();
            token = nextToken;
            nextToken = null;
        } else {
            while (lineOffset < lineLen) {
                if (line.charAt(lineOffset) != ' ') {
                    break;
                }
                lineOffset++;
            }

            if (lineOffset < lineLen) {
                StringBuilder sb = new StringBuilder();

                boolean forceUpperCase = false;
                boolean tokenValid = false;

                char ch = line.charAt(lineOffset++);
                if (ch == '\"') // Must be a String Constant
                {
                    boolean EscFlag = false;

                    sb.append(ch);

                    while (lineOffset < lineLen) {
                        ch = line.charAt(lineOffset++);

                        if (!EscFlag) {
                            if (ch == '\"') {
                                sb.append(ch);
                                tokenValid = true;
                                break;
                            } else if (ch == '\\') {
                                EscFlag = true;
                                continue;
                            }
                        }

                        sb.append(ch);
                        EscFlag = false;
                    }
                } else if ((ch >= '0') && (ch <= '9')) // Must be a Numeric Constant
                {
                    tokenValid = true;			// Just in case we reach EOL

                    int state = 0;

                    sb.append(ch);

                    whileLoop:
                    while (lineOffset < lineLen) {
                        ch = line.charAt(lineOffset);

                        switch (state) {
                            case -1:
                                break whileLoop;

                            case 0:				// Before Decimal Point
                                if ((ch >= '0') && (ch <= '9')) {
                                    sb.append(ch);
                                    lineOffset++;
                                } else if (ch == '.') // Decimal point?
                                {
                                    sb.append(ch);
                                    lineOffset++;
                                    state = 1;
                                } else if ((ch == 'E') || // E or e?
                                        (ch == 'e')) {
                                    tokenValid = false;
                                    sb.append('E');
                                    lineOffset++;
                                    state = 2;
                                } else {
                                    state = -1;			// 123
                                }
                                break;
                            case 1:				// After Decimal Point
                                if ((ch >= '0') && (ch <= '9')) {
                                    sb.append(ch);
                                    lineOffset++;
                                } else if ((ch == 'E') || // E or e?
                                        (ch == 'e')) {
                                    tokenValid = false;
                                    sb.append('E');
                                    lineOffset++;
                                    state = 2;
                                } else {
                                    state = -1;			// 123.[nnn]
                                }
                                break;
                            case 2:
                                if ((ch == '+') || (ch == '-')) {
                                    sb.append(ch);
                                    lineOffset++;
                                    state = 3;
                                } else {
                                    tokenValid = false;		// 123[.[nnn]]E (missing +|-)
                                    state = -1;
                                }
                                break;
                            case 3:
                                if ((ch >= '0') && (ch <= '9')) {
                                    tokenValid = true;
                                    sb.append(ch);
                                    lineOffset++;
                                    state = 4;
                                } else {
                                    tokenValid = false;		// 123[.[nnn]]E+ (missing val)
                                    state = -1;
                                }
                                break;
                            case 4:
                                if ((ch >= '0') && (ch <= '9')) {
                                    sb.append(ch);
                                    lineOffset++;
                                } else {
                                    state = -1;			// OK
                                }
                                break;
                        }
                    }
                } else if (ch == '_' || ((ch >= 'a') && (ch <= 'z'))
                        || ((ch >= 'A') && (ch <= 'Z'))) {
                    sb.append(ch);
                    forceUpperCase = true;
                    tokenValid = true;

                    while (lineOffset < lineLen) {
                        ch = line.charAt(lineOffset);
                        if (ch == '_' || ((ch >= 'a') && (ch <= 'z'))
                                || ((ch >= 'A') && (ch <= 'Z'))
                                || ((ch >= '0') && (ch <= '9'))) {
                            sb.append(ch);
                            lineOffset++;
                        } else if ((ch == '$') || (ch == '%')) {
                            sb.append(ch);
                            lineOffset++;
                            break;
                        } else {
                            break;
                        }
                    }
                } else // Symbol
                {
                    /*
                     * + - * / & | ^ = ( ) ,
                     * <> < <=
                     * >= >
                     */

                    //if ("+-*/&|^=(),#:".indexOf(ch) != -1)
                    if ("+-*/^=(),#:".indexOf(ch) != -1) {
                        sb.append(ch);
                        tokenValid = true;
                    } else if (ch == '<') {
                        sb.append(ch);
                        if (lineOffset < lineLen) {
                            ch = line.charAt(lineOffset);
                            if ((ch == '=') || (ch == '>')) {
                                sb.append(ch);
                                lineOffset++;
                            }
                        }
                        tokenValid = true;
                    } else if (ch == '>') {
                        sb.append(ch);
                        if (lineOffset < lineLen) {
                            ch = line.charAt(lineOffset);
                            if (ch == '=') {
                                sb.append(ch);
                                lineOffset++;
                            }
                        }
                        tokenValid = true;
                    }
                }

                if (tokenValid) {
                    token = sb.toString();
                    if ((token != null) && forceUpperCase) {
                        token = token.toUpperCase();
                    }
                }
            }
        }

        return token;
    }

    public String ReadLine(DataInput dataInput) {
        String s = null;

        if (dataInput != null) {
            StringBuilder sb = new StringBuilder();
            int ch = -1;

            try {
                while ((ch = dataInput.readByte()) != -1) {
                    if (ch == '\n') {
                        break;
                    } else if (ch != '\r') {
                        sb.append((char) ch);
                    }
                }
            } catch (EOFException e) {
            } catch (IOException e) {
            }

            if (sb.length() > 0) {
                s = sb.toString();
            } else {
                if (ch == '\n') {
                    s = "";
                }
            }
        }

        return s;
    }

    public boolean Enter(String lis) {
        boolean errorFlag = false;
        String s;
        BufferedReader buffer = new BufferedReader(new StringReader(lis));
        errorline = 0;
        try {
            while ((s = buffer.readLine()) != null) {
                boolean okFlag = parseLine(s, false);
                if (!okFlag) {
                    errorFlag = true;
                    break;
                }
                errorline++;
            }
        } catch (IOException ex) {
            errorFlag = true;
        }

        return errorFlag;
    }

    public boolean SaveTo(DataOutput dataOutput) {
        boolean errorFlag;
        try {
            if (mb) {
                dataOutput.writeInt(MAGIC191);
            } else {
                dataOutput.writeInt(MAGIC186);
            }
            dataOutput.writeShort(nvars);

            for (int var2 = 0; var2 < nvars; ++var2) {
                if (!obf) {
                    dataOutput.writeUTF(varName[var2]);
                } else {
                    dataOutput.writeUTF("");
                }
                dataOutput.writeByte(varType[var2]);
            }
            if (mb) {

                dataOutput.writeShort(CONST_FLOAT_INDEX);
                for (int i = 0; i < CONST_FLOAT_INDEX; ++i) {
                    dataOutput.writeFloat(CONSТ_FLOAT[i]);
                }

            }
            dataOutput.writeShort(sourceLen);
            dataOutput.write(sourceProg, 0, sourceLen);
            errorFlag = false;
        } catch (IOException t) {
            errorFlag = true;
        }

        return errorFlag;
    }

    private int FindLine(int lno, byte[] searchProg, int searchLen) {
        int linePC = 0;

        while (linePC < searchLen) {
            int lineNum;
            int lineLens;

            lineNum = ((searchProg[linePC] & 0xff) << 8)
                    + (searchProg[linePC + 1] & 0xff);
            lineLens = (searchProg[linePC + 2] & 0xff);

            if (lno == lineNum) {
                return linePC;
            } else if (lineNum > lno) {
                return -1;
            }

            linePC += lineLens;
        }
      
        return -1;
    }

    private void InsertLine(int lno, int len, byte[] buf) {
        RemoveLine(lno);

        int insertPos = 0;

        while (insertPos < sourceLen) {
            int lineNum;
            int lineLens;

            lineNum = ((sourceProg[insertPos] & 0xff) << 8)
                    + (sourceProg[insertPos + 1] & 0xff);
            lineLens = (sourceProg[insertPos + 2] & 0xff);

            if (lineNum > lno) {
                break;
            }

            insertPos += lineLens;
        }

        if ((sourceLen + len) <= sourceSize) {
            if (insertPos < sourceLen) {
                int src = sourceLen;
                sourceLen += len;
                int dest = sourceLen;

                while (src > insertPos) {
                    sourceProg[--dest] = sourceProg[--src];
                }
            } else {
                sourceLen += len;
            }

            buf[0] = (byte) ((lno >> 8) & 0xff);
            buf[1] = (byte) (lno & 0xff);
            buf[2] = (byte) (len);

            for (int i = 0; i < len; i++) {
                sourceProg[insertPos++] = buf[i];
            }
        } else {
            throw new BasicError(BasicError.OUT_OF_MEMORY, "Out of memory");
        }
    }

    /*
     * RemoveLine removes the specified line from the program
     */
    private void RemoveLine(int lno) {
        int linePC = FindLine(lno, sourceProg, sourceLen);
        if (linePC != -1) {
            int dest = linePC;
            int len = (sourceProg[linePC + 2] & 0xff);
            int src = linePC + len;

            while (src < sourceLen) {
                sourceProg[dest++] = sourceProg[src++];
            }

            sourceLen = dest;
        }
    }

    private int lookupToken(String var0, int var1) {
        int var2 = -1;
        int var3 = var0.length();

        for (int var4 = 0; var4 < tokenTable.length; ++var4) {
            if ((tokenTable[var4].charAt(0) & 15) == var1 || var1 == -1) {
                int var6 = tokenTable[var4].length() - 3;
                int var7 = var_38c[var4].length();
                if (var3 == var6 && var0.regionMatches(true, 0, tokenTable[var4], 3, var3) || var3 == var7 && var0.regionMatches(true, 0, var_38c[var4], 0, var3)) {
                    var2 = var4;
                    break;
                }
            }
        }
        if (!mb) {
            if (var2 >= 149 && var2 <= 163) {
                error = "Оператор " + tokenTable[var2].substring(3) + " не поддерживается в версии 1.8.6";
            }
        }
        return var2;
    }

    private int lookupVariable(String token) {
        int variableID = -1;

        for (int i = 0; i < nvars; ++i) {
            if (token.equals(varName[i])) {
                variableID = i;
                break;
            }
        }

        if (variableID == -1 && nvars < 256) {
            varName[nvars] = token;
            if (token.endsWith("$")) {
                varType[nvars] = TYPE_STRING;
            } else if (token.endsWith("%")) {
                varType[nvars] = TYPE_INTEGER;
            } else {
                varType[nvars] = TYPE_FLOAT;
            }

            variableID = nvars++;
        }

        return variableID;
    }

    private void lookupConstant(byte type, float fval, int ival, String sval) {
        int constantID = -1;

        switch (type) {
            case TYPE_STRING:
                break;
            case TYPE_INTEGER:
                break;
            case TYPE_FLOAT: {
                for (int i = 0; i < CONST_FLOAT_INDEX; ++i) {
                    if (fval == CONSТ_FLOAT[i]) {
                        constantID = i;
                        break;
                    }
                }

                break;
            }

        }

        if (constantID == -1) {
            switch (type) {
                case TYPE_STRING:
                    break;
                case TYPE_INTEGER:
                    break;
                case TYPE_FLOAT:
                    code[codeLen++] = (byte) CONST_FLOAT_INDEX;// индек float в константном пуле
                    CONSТ_FLOAT[CONST_FLOAT_INDEX++] = fval;//float сохраняю в константный пул
                    //System.out.println("ADD NEW CONST fval = " + fval);
                    break;

            }

        } else //если такая константа уже есть,просто записываю её индекс
        {
            switch (type) {
                case TYPE_STRING:
                    break;
                case TYPE_INTEGER:
                    break;
                case TYPE_FLOAT:
                    code[codeLen++] = (byte) constantID;// индек float в константном пуле
                    break;

            }

        }

    }

    public int CONST_FLOAT_INDEX = 0;
    private final float CONSТ_FLOAT[] = new float[256];

    private final int EXPR_END = 0x100;
    private final int EXPR_UNARY = 0x80;
    private final int EXPR_BINARY = 0x40;
    private final int EXPR_LBRACKET = 0x20;
    private final int EXPR_RBRACKET = 0x10;
    private final int EXPR_COMMA = 0x08;
    private final int EXPR_FUNCTION = 0x04;
    private final int EXPR_LVALUE = 0x02;
    private final int EXPR_CONSTANT = 0x01;

    boolean parseLValue() {
        boolean validFlag = false;
        boolean arrayFlag = false;

        String token = GetToken();
        if (token != null) {
            char ch = token.charAt(0);

            if ((ch >= 'A') && (ch <= 'Z')) {
                int toknum = lookupToken(token, 1);
                if (toknum == -1) {
                    code[codeLen++] = (byte) tokVARIABLE;
                    code[codeLen++] = (byte) lookupVariable(token);

                    token = GetToken();
                    if ((token != null) && (token.compareTo("(") == 0)) {
                        code[codeLen++] = (byte) tokMAKEREF;

                        code[codeLen++] = (byte) tokLBRACKET;
                        parseRValue(true);

                        token = GetToken();

                        if ((token != null) && (token.compareTo(")") == 0)) {
                            code[codeLen++] = (byte) tokRBRACKET;
                            arrayFlag = true;
                            validFlag = true;
                        }
                    } else {
                        PutToken(token);
                        validFlag = true;
                    }
                }
            }
        }

        if (!validFlag) {
            throw new BasicError(BasicError.LVALUE_EXPECTED, "LVALUE Expected");
        }

        return arrayFlag;
    }

    static public int fromString(String ascii) {
        int sign = 1;
        int exp = -1;
        int val = 0;
        int expVal = 0;
        int expSign = 1;
        int fp;

        final int ERROR = -1;
        final int SIGN_OR_DIGIT = 0;
        final int DIGIT1 = 1;
        final int DIGIT2 = 2;
        final int EXPSIGN = 3;
        final int GETEXP = 4;

        int state = SIGN_OR_DIGIT;

        int len = ascii.length();

        int offset = 0;
        while (offset < len) {
            char ch = ascii.charAt(offset);

            switch (state) {
                case ERROR:
                    throw new BasicError(8, "Bad Float: " + ascii);

                case SIGN_OR_DIGIT:
                    if (ch == '-') {
                        offset++;
                        sign = -1;
                        state = DIGIT1;
                    } else if ((ch >= '0') && (ch <= '9')) {
                        state = DIGIT1;
                    } else {
                        state = ERROR;
                    }
                    break;

                case DIGIT1:
                    if ((ch >= '0') && (ch <= '9')) {
                        val = val * 10 + (int) (ch - '0');
                        if (val != 0) {
                            exp++;
                        }
                        offset++;
                    } else if (ch == '.') {
                        state = DIGIT2;
                        offset++;
                    } else if ((ch == 'E') || (ch == 'e')) {
                        state = EXPSIGN;
                        offset++;
                    } else {
                        state = ERROR;
                    }
                    break;

                case DIGIT2:
                    if ((ch >= '0') && (ch <= '9')) {
                        val = val * 10 + (int) (ch - '0');
                        if (val == 0) {
                            exp--;
                        }
                        offset++;
                    } else if ((ch == 'E') || (ch == 'e')) {
                        state = EXPSIGN;
                        offset++;
                    } else {
                        state = ERROR;
                    }
                    break;

                case EXPSIGN:
                    if (ch == '+') {
                        expSign = 1;
                        offset++;
                        state = GETEXP;
                    } else if (ch == '-') {
                        expSign = -1;
                        offset++;
                        state = GETEXP;
                    } else {
                        state = ERROR;
                    }
                    break;

                case GETEXP:
                    if ((ch >= '0') && (ch <= '9')) {
                        expVal = expVal * 10 + (int) (ch - '0');
                        offset++;
                    } else {
                        state = ERROR;
                    }
                    break;

                default:
                    state = ERROR;
                    break;
            }
        }

        fp = MakeFP(val * sign, exp + expVal * expSign, false);

        return fp;
    }

    static public int MakeFP(long val, int exp, boolean adjustExp) {
        while (Math.abs(val) > 9999999) {
            val = val / 10;
            if (adjustExp) {
                exp += 1;
            }
        }

        if (Math.abs(val) > 0) {
            while (Math.abs(val) < 1000000) {
                val = val * 10;
                if (adjustExp) {
                    exp -= 1;
                }
            }
        } else {
            exp = 0;
            val = 0;
        }

        /*
         * Create result Float
         */
        int result;

        if (exp < 0) {
            exp = exp + 128;
        }

        result = ((int) val << 7) | exp;

        return result;
    }

    private void parseRValue(boolean commaValid) {
        //int currentTokenType = 0x0000;
        char validTokenTypes = EXPR_UNARY | EXPR_LBRACKET | EXPR_FUNCTION | EXPR_LVALUE | EXPR_CONSTANT;
        int bracketLevel = 0;
        boolean[] commaValidStack = new boolean[16];

        String token;
        while ((token = GetToken()) != null) {
            char ch;
            int len;
            if ((ch = token.charAt(0)) == '\"') {
                if ((EXPR_CONSTANT & validTokenTypes) == 0) {
                    PutToken(token);
                    break;
                }

                code[codeLen++] = (byte) tokSTRING;
                len = token.length() - 2;
                code[codeLen++] = (byte) len;

                for (int i = 0; i < len; ++i) {
                    char var11;
                    if ((var11 = token.charAt(1 + i)) >= 1040 && var11 <= 1103) {
                        var11 = (char) (var11 - 848); // еще трабла с win1251
                    }

                    code[codeLen++] = (byte) var11;
                }

                validTokenTypes = EXPR_BINARY | EXPR_RBRACKET | EXPR_COMMA | EXPR_END;
            } else if (ch >= '0' && ch <= '9') {
                if ((EXPR_CONSTANT & validTokenTypes) == 0) {
                    PutToken(token);
                    break;
                }

                try {
                    int tokval = Integer.parseInt(token);
                    if ((tokval >= -128) && (tokval < 128)) {
                        code[codeLen++] = (byte) tokBYTE;
                        code[codeLen++] = (byte) tokval;
                    } else if (tokval >= 0 && tokval < 256) {
                        code[codeLen++] = (byte) tokUBYTE;
                        code[codeLen++] = (byte) tokval;
                    } else if (tokval >= 0 && tokval < 65536) {
                        code[codeLen++] = (byte) tokUWORD;
                        code[codeLen++] = (byte) (tokval >> 8 & 255);
                        code[codeLen++] = (byte) (tokval & 255);
                    } else {
                        code[codeLen++] = (byte) tokINTEGER;
                        code[codeLen++] = (byte) (tokval >> 24 & 255);
                        code[codeLen++] = (byte) (tokval >> 16 & 255);
                        code[codeLen++] = (byte) (tokval >> 8 & 255);
                        code[codeLen++] = (byte) (tokval & 255);
                    }
                } catch (NumberFormatException var10) {
                    try {
                        if (mb) {
                            float fval = java.lang.Float.parseFloat(token);
                            code[codeLen++] = (byte) tokFLOAT;
                            lookupConstant(TYPE_FLOAT, fval, 0, "");
                        } else {
                            int var8 = fromString(token);
                            code[codeLen++] = (byte) tokFLOAT;
                            code[codeLen++] = (byte) (var8 >> 24 & 255);
                            code[codeLen++] = (byte) (var8 >> 16 & 255);
                            code[codeLen++] = (byte) (var8 >> 8 & 255);
                            code[codeLen++] = (byte) (var8 & 255);
                        }

                    } catch (NumberFormatException var9) {
                        throw new BasicError(BasicError.VALUE_ERROR, "Bad Constant2: " + token);
                    }
                }

                validTokenTypes = 344;
            } else {
                if ((len = lookupToken(token, 1)) != -1) {
                    label123:
                    {
                        if ((ch = token.charAt(0)) == '(') {
                            commaValidStack[bracketLevel++] = commaValid;
                            commaValid = true;
                        } else if (ch == ')') {
                            if (bracketLevel == 0) {
                                break label123;
                            }

                            --bracketLevel;
                            commaValid = commaValidStack[bracketLevel];
                        } else {
                            if (!commaValid && ch == 44) {
                                break label123;
                            }

                            if (ch == 45 && (validTokenTypes & 128) != 0) {
                                ++len;
                            }
                        }

                        if ((tokenTable[len].charAt(1) & validTokenTypes) != 0) {
                            code[codeLen++] = (byte) len;
                            validTokenTypes = tokenTable[len].charAt(2);
                            continue;
                        }
                    }
                } else if (ch >= 65 && ch <= 90) {
                    PutToken(token);
                    if ((2 & validTokenTypes) == 0) {
                        break;
                    }

                    parseLValue();
                    validTokenTypes = 344;
                    continue;
                }

                PutToken(token);
                break;
            }
        }

        if (bracketLevel != 0) {
            throw new BasicError(BasicError.PARENTHESIS_NESTING_ERROR, "Parenthesis Nesting Error");
        } else if ((256 & validTokenTypes) == 0) {
            throw new BasicError(BasicError.EXPRESSION_INCOMPLETE, "End of Expression not Expected");
        }
    }

    private void parseCommaList(String commaList) {
        for (int i = 0; i < commaList.length(); ++i) {
            switch (commaList.charAt(i)) {
                case 35:
                    String token;
                    if ((token = GetToken()) == null) {
                        throw new BasicError(BasicError.HASH_EXPECTED, "# expected");
                    }

                    if (token.compareTo("#") != 0) {
                        throw new BasicError(BasicError.HASH_EXPECTED, "# expected");
                    }

                    code[codeLen++] = 124;
                    break;
                case 44:
                    String var3;
                    if ((var3 = GetToken()) == null) {
                        throw new BasicError(BasicError.COMMA_EXPECTED, "Comma expected");
                    }

                    if (var3.compareTo(",") != 0) {
                        throw new BasicError(BasicError.COMMA_EXPECTED, "Comma expected");
                    }

                    code[codeLen++] = 59;
                    break;
                case 76:
                    parseLValue();
                    break;
                case 82:
                    parseRValue(false); // Don't parse commas at outer bracket level
                    break;
                default:
                    throw new BasicError(BasicError.INTERNAL_ERROR, "Bad item in comma list");
            }
        }

    }

    private void parseStatement() {
        boolean statementRequired = false; // A statement is only required following an IF or COLON

        String token;
        while ((token = GetToken()) != null) {
            statementRequired = false;
            int keyword;
            if ((keyword = lookupToken(token, 0)) != -1) {
                if (keyword == tokPRINT) {
                    if ((token = GetToken()) != null) {
                        if (token.compareTo("#") == 0) {
                            keyword = tokPRINTHASH;
                        }

                        PutToken(token);
                    }
                } else if (keyword == tokINPUT && (token = GetToken()) != null) {
                    if (token.compareTo("#") == 0) {
                        keyword = tokINPUTHASH;
                    }

                    PutToken(token);
                }

                code[codeLen++] = (byte) keyword;
                switch (keyword) {
                    case tokDIM:
                    case tokNEXT:
                    case tokREAD:
                        parseLValue();
                        break;
                    case tokOPEN:
                        parseCommaList("#R,R,R");
                        break;
                    case tokSENDSMS:
                    case tokRAND:
                    case tokAG:
                    case tokPLOT:
                    case tokSPRITEGEL:
                    case tokGELLOAD:
                    case tokPLAYTONE:
                        parseCommaList("R,R");
                        break;
                    case tokCAG:
                    case tokGELGRAB:
                    case tokALERT:
                        parseCommaList("R,R,R,R,R");
                        break;
                    case tokCLOSE:
                        parseCommaList("#R");
                        break;
                    case tokPOINT:
                    case tokPRINTHASH:
                    case tokPUT:
                        parseCommaList("#R,R");
                        break;
                    case tokNOTE:
                    case tokGET:
                    case tokTEXT:
                    case tokINPUTHASH:
                        parseCommaList("#R,L");
                        break;
                    case tokDRAWSTRING:
                    case tokSETCOLOR:
                    case tokDRAWGEL:
                    case tokSPRITEMOVE:
                    case tokMENUADD:
                        parseCommaList("R,R,R");
                        break;
                    case tokDRAWLINE:
                    case tokFILLRECT:
                    case tokDRAWRECT:
                        parseCommaList("R,R,R,R");
                        break;
                    case tokFILLROUNDRECT:
                    case tokDRAWROUNDRECT:
                    case tokFILLARC:
                    case tokDRAWARC:
                    case tokBLIT:
                        parseCommaList("R,R,R,R,R,R");
                        break;
                    case tokREM:
                    case tokDATA:
                        while (lineOffset < lineLen && line.charAt(lineOffset) == 32) {
                            ++lineOffset;
                        }

                        if ((token = line.substring(lineOffset)) != null) {
                            token = token.trim();
                        }

                        lineOffset = lineLen;
                        if (token == null || token.length() == 0) {
                            if (keyword != 14) {
                                throw new BasicError(BasicError.SYNTAX_ERROR, "No data on line!");
                            }

                            token = "";
                        }

                        int var3 = token.length();
                        code[codeLen++] = (byte) (var3 & 255);

                        for (int var4 = 0; var4 < var3; ++var4) {
                            char var5;
                            if ((var5 = token.charAt(var4)) >= 1040 && var5 <= 1103) {
                                var5 = (char) (var5 - 848);
                            }

                            code[codeLen++] = (byte) var5;
                        }
                        break;
                    case tokIF:
                        parseRValue(false);
                        if ((token = GetToken()) != null && (token.compareTo("THEN") == 0 || token.compareTo("TH") == 0)) {
                            code[codeLen++] = 17;
                            statementRequired = true;
                            continue;
                        }

                        throw new BasicError(BasicError.SYNTAX_ERROR, "THEN expected");

                    case tokGOTO:
                    case tokGOSUB:
                    case tokSLEEP:
                    case tokTRAP:
                    case tokRESTORE:
                    case tokPRINT:
                    case tokENTER:
                    case tokLOAD:
                    case tokSAVE:
                    case tokDELETE:
                    case tokEDIT:
                    case tokPLAYWAV:
                    case tokSETFONT:
                    case tokMENUREMOVE:
                    case tokCALL:
                    case tokPLATFORMREQUEST:
                    case tokDELGEL:
                    case tokDELSPRITE:

                        parseRValue(false);

                        break;
                    case tokLIST:
                        if ((token = GetToken()) != null) {
                            PutToken(token);
                            parseRValue(false);
                            if ((token = GetToken()) != null) {
                                if (token.compareTo(",") != 0) {
                                    throw new BasicError(BasicError.COMMA_EXPECTED, "Comma expected");
                                }

                                code[codeLen++] = 59;
                                parseRValue(false);
                            }
                        }
                        break;
                    case tokINPUT:
                        parseRValue(false);
                        if ((token = GetToken()) == null || token.compareTo(",") != 0) {
                            throw new BasicError(BasicError.COMMA_EXPECTED, "Comma expected");
                        }

                        code[codeLen++] = 59;
                        parseLValue();
                        break;
                    case tokFOR:
                        parseLValue();
                        if ((token = GetToken()) == null || token.compareTo("=") != 0) {
                            throw new BasicError(BasicError.SYNTAX_ERROR, "Assignment expected");
                        }

                        code[codeLen++] = 123;
                        parseRValue(false);
                        if ((token = GetToken()) == null || token.compareTo("TO") != 0) {
                            throw new BasicError(BasicError.SYNTAX_ERROR, "TO expected");
                        }

                        code[codeLen++] = 31;
                        parseRValue(false);
                        if ((token = GetToken()) != null) {
                            if (token.compareTo("STEP") == 0) {
                                code[codeLen++] = 32;
                                parseRValue(false);
                            } else {
                                PutToken(token);
                            }
                        }
                        break;
                }
            } else {
                PutToken(token);
                parseLValue();
                try {
                    if (GetToken().compareTo("=") != 0) {
                        throw new BasicError(BasicError.SYNTAX_ERROR, "Assignment expected");
                    }
                } catch (NullPointerException bombom) {
                    throw new BasicError(BasicError.SYNTAX_ERROR, "Assignment expected");
                }

                code[codeLen++] = -10;
                parseRValue(false);
            }

            if ((token = GetToken()) != null) {
                if (token.compareTo(":") != 0) {
                    throw new BasicError(BasicError.SYNTAX_ERROR, "Colon expected");
                }

                statementRequired = true;
                code[codeLen++] = 127;
            }
        }

        if (statementRequired) {
            throw new BasicError(BasicError.SYNTAX_ERROR, "Statement expected");
        }
    }

    public boolean parseLine(String var0, boolean var1) {
        line = var0;
        lineLen = var0.length();
        lineOffset = 0;
        nextToken = null;

        boolean var2;
        try {
            String var3 = GetToken();

            int var4;
            try {
                var4 = Integer.parseInt(var3);
            } catch (NumberFormatException var6) {
                PutToken(var3);
                var4 = -1;
            }

            codeLen = 3;
            parseStatement();
            String var5;
            if ((var5 = GetToken()) != null) {
                throw new BasicError(BasicError.SYNTAX_ERROR, "Trailing Junk: " + var5);
            }

            if (codeLen > 3) {
                code[codeLen++] = -1;
                code[2] = (byte) (codeLen & 255);
                if (var4 != -1) {
                    InsertLine(var4, codeLen, code);
                }
            } else if (var4 != -1) {
                RemoveLine(var4);
            }

            var2 = error.equals("");
        } catch (BasicError var7) {
            error = "Error " + var7.errorNumber + ": " + var7.getMessage() + " near " + line.substring(lineOffset - 1);
            var2 = false;
        }
        return var2;
    }

    private void List(DataOutput dataOutput, int lno1, int lno2, boolean editFlag) {
        int progPC;
        for (int i = 0; i < sourceLen; i = progPC) {
            int var6 = i;
            int var7 = ((sourceProg[i++] & 255) << 8) + (sourceProg[i++] & 255);
            int var8 = sourceProg[i++] & 255;
            progPC = var6 + var8;
            if (var7 >= lno1 && var7 <= lno2) {
                StringBuffer var9;
                (var9 = new StringBuffer()).append(Integer.toString(var7));
                boolean var10 = true;

                int var12;
                while (i < progPC) {
                    int var11;
                    if ((var11 = sourceProg[i++] & 255) < tokenTable.length && (tokenTable[var11].charAt(0) & 128) == 128) {
                        var10 = true;
                    }

                    if (var10) {
                        var9.append(' ');
                        var10 = false;
                    }

                    if (var11 < tokenTable.length && (tokenTable[var11].charAt(0) & 64) == 64) {
                        var10 = true;
                    }

                    if (var11 == 249) {
                        var12 = sourceProg[i++] & 255;
                        var9.append(var12);
                    } else if (var11 == 248) {
                        byte var17 = sourceProg[i++];
                        var9.append(var17);
                    } else if (var11 == 250) {
                        var12 = ((sourceProg[i++] & 255) << 8) + (sourceProg[i++] & 255);
                        var9.append(var12);
                    } else if (var11 == 251) {
                        var12 = ((sourceProg[i++] & 255) << 24) + ((sourceProg[i++] & 255) << 16) + ((sourceProg[i++] & 255) << 8) + (sourceProg[i++] & 255);
                        var9.append(var12);
                    } else if (var11 == 254) {
                        if (mb) {
                            float fval = CONSТ_FLOAT[sourceProg[i++] & 0xff];// float получаю из константного пула
                            var9.append(java.lang.Float.toString(fval));
                        } else {
                            var12 = ((sourceProg[i++] & 255) << 24) + ((sourceProg[i++] & 255) << 16) + ((sourceProg[i++] & 255) << 8) + (sourceProg[i++] & 255);
                            var9.append(Float.toString(var12));

                        }

                    } else if (var11 == 252) {
                        var12 = sourceProg[i++] & 255;
                        var9.append(varName[var12]);
                    } else {
                        int var13;
                        char var14;
                        if (var11 != 253) {
                            if (var11 != 14 && var11 != 48) {
                                if (var11 == 246) {
                                    var9.append("=");
                                } else if (var11 != 255 && var11 < tokenTable.length) {
                                    var9.append(tokenTable[var11].substring(3));
                                }
                            } else {
                                var9.append(tokenTable[var11].substring(3));
                                var9.append(' ');
                                var12 = sourceProg[i++] & 255;

                                for (var13 = 0; var13 < var12; ++var13) {
                                    if ((var14 = (char) (sourceProg[i++] & 255)) >= 192 && var14 <= 255) {
                                        var14 = (char) (var14 + 848);
                                    }

                                    var9.append(var14);
                                }
                            }
                        } else {
                            var9.append("\"");
                            var12 = sourceProg[i++] & 255;

                            for (var13 = 0; var13 < var12; ++var13) {
                                if ((var14 = (char) (sourceProg[i++] & 255)) >= 192 && var14 <= 255) {
                                    var14 = (char) (var14 + 848);
                                }

                                var9.append(var14);
                            }

                            var9.append("\"");
                        }
                    }
                }

                if (!editFlag) {
                    var9.append('\n');
                }

                String var16 = var9.toString();

                if (dataOutput != null) {
                    try {
                        int var13;
                        for (var12 = 0; var12 < var16.length(); ++var12) {
                            if (((var13 = var16.charAt(var12)) >= 'А') && (var13 <= 1103)) {
                                var13 = (char) (var13 - 848);
                            }
                            dataOutput.writeByte((byte) var13);
                        }
                    } catch (IOException var15) {
                        throw new BasicError(BasicError.IO_ERROR, "I/O Error");
                    }
                } else {
                    //   main.PrintString(var16);
                }

                if (i != progPC) {
                    throw new BasicError(BasicError.INTERNAL_ERROR, "List: Internal Error Line " + var7);
                }
            }
        }

    }

    public void sub_750(String var0) {
        line = var0;
        lineLen = var0.length();
        lineOffset = 0;
        nextToken = null;

        String var1;
        label42:
        while ((var1 = GetToken()) != null) {
            if (var1.equals("")) {
                return;
            }

            if (!var1.equals(":")) {
                int var2 = -1;
                int var3 = 0;

                while (true) {
                    if (var3 < tokenTable.length) {
                        if (!var1.equals(tokenTable[var3].substring(3))) {
                            ++var3;
                            continue;
                        }

                        var2 = var3;
                    }

                    if (var2 != -1) {
                        switch (var2) {
                            case tokINPUT:
                            case tokEDITFORM:
                            case tokGAUGEFORM:
                            case tokCHOICEFORM:
                            case tokDATEFORM:
                            case tokMESSAGEFORM:
                            case tokSELECT:
                            case tokALERT:
                                continue label42;
                            case tokGELLOAD:
                            case tokGELGRAB:
                            case tokDRAWGEL:
                            case tokSPRITEGEL:
                            case tokSPRITEMOVE:
                            case tokSPRITEHIT:
                            case tokGELWIDTH:
                            case tokGELHEIGHT:
                                continue label42;
                            case tokPLAYWAV:
                            case tokPLAYTONE:
                                continue label42;
                            case tokCALL:
                            case tokDELETE:
                            case tokOPEN:
                            case tokREADDIR$:
                        }
                    }
                    break;
                }
            }
        }
    }
}
