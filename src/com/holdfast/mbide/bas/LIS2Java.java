/**
 *
 * @author HoldFast
 */
package com.holdfast.mbide.bas;

import java.util.ArrayList;

public class LIS2Java implements Constants {

    private final String[] keywords = {ops[0], ops[1], ops[2], ops[3], ops[5], ops[6], ops[7], ops[8], ops[9], ops[18], ops[148], ops[149]};
    private final String[] functions = {ops[4], ops[10], ops[11], ops[12], ops[13], ops[125], ops[14], ops[15], ops[16], ops[17], ops[19], ops[20], ops[21], ops[22], ops[23], ops[24], ops[25], ops[26], ops[27], ops[28], ops[29], ops[30], ops[31], ops[32], ops[33], ops[34], ops[126], ops[35], ops[36], ops[37], ops[38], ops[39], ops[40], ops[41], ops[42], ops[43], ops[44], ops[45], ops[46], ops[47], ops[48], ops[49], ops[50], ops[66], ops[67], ops[68], ops[69], ops[70], ops[71], ops[72], ops[73], ops[74], ops[75], ops[76], ops[77], ops[78], ops[79], ops[80], ops[81], ops[82], ops[83], ops[84], ops[85], ops[86], ops[87], ops[88], ops[89], ops[90], ops[91], ops[92], ops[93], ops[94], ops[95], ops[96], ops[97], ops[98], ops[99], ops[100], ops[101], ops[102], ops[103], ops[104], ops[105], ops[106], ops[107], ops[108], ops[109], ops[110], ops[111], ops[112], ops[113], ops[114], ops[115], ops[116], ops[117], ops[118], ops[119], ops[120], ops[121], ops[122], ops[51], ops[123], ops[124], ops[13], ops[125], ops[34], ops[126], ops[127], ops[128], ops[129], ops[130], ops[131], ops[132], ops[133], ops[134], ops[135], ops[136], ops[137], ops[138], ops[139], ops[140], ops[141], ops[142], ops[143], ops[144], ops[145], ops[146], ops[147], ops[150], ops[151], ops[152], ops[153], ops[154], ops[155], ops[156], ops[157]};
    private final String[] func191 = {ops[149], ops[150], ops[151], ops[152], ops[153], ops[154], ops[155], ops[156], ops[157], ops[158], ops[159], ops[160], ops[161]};
    private final String[] func191small = {small[149], small[150], small[151], small[152], small[153], small[154], small[155], small[156], small[157], small[158], small[159], small[160], small[161]};

    private ArrayList lines, lexems, ltype, vars, varsdiff, linelex, linetype, floats, floatsdiff;
    private int currLine;
    private String codeerror;
    private boolean containPoint;
    private final boolean mb191 = true;
    private float prev_line;
    private int variableCount = 0;
    private String mainCode = "";
    private boolean equalsBracket = false;
    private boolean notEquals = false;
    private boolean NOT = false;
    private boolean RETURN = false;
    private boolean elseGoto = false;
    private boolean Goto = false;

    private final byte tokSTOP = 0;
    private final byte tokPOP = 1;
    private final byte tokRETURN = 2;
    private final byte tokEND = 3;
    private final byte tokNEW = 4;
    private final byte tokRUN = 5;
    private final byte tokDIR = 6;
    private final byte tokDEG = 7;
    private final byte tokRAD = 8;
    private final byte tokBYE = 9;
    private final byte tokGOTO = 10;
    private final byte tokGOSUB = 11;
    private final byte tokSLEEP = 12;
    private final byte tokPRINT = 13;
    private final byte tokREM = 14;
    private final byte tokDIM = 15;
    private final byte tokIF = 16;
    private final byte tokTHEN = 17;
    private final byte tokCLS = 18;
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
    private final byte tokTO = 31;
    private final byte tokSTEP = 32;
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
    private final byte tokEQUALS = 51;
    private final byte tokZPT = 59;
    private final byte tokNOTEQUALS = 52;
    private final byte tokLEFTBRACKET = 57;
    private final byte tokRIGHTBRACKET = 58;
    private final byte tokMEN = 53;
    private final byte tokBOL = 55;
    private final byte tokMENR = 54;
    private final byte tokBOLR = 56;
    private final byte tokUMN = 63;
    /*private final byte tok= = 51;
     private final byte tok<> = 52;
     private final byte tok< = 53;
     private final byte tok<= = 54;
     private final byte tok> = 55;
     private final byte tok>= = 56;
     private final byte tok( = 57;
     private final byte tok) = 58;
     private final byte tok, = 59;
     private final byte tok+ = 60;
     private final byte tok- = 61;
     private final byte tok- = 62;
     private final byte tok* = 63;
     private final byte tok/ = 64;
     private final byte tok^ = 65;*/
    private final byte tokPLUS = 60;
    private final byte tokMINUS = 61;
    // private final byte tokMINUS2 = 62;
    private final byte tokBITAND = 66;
    private final byte tokBITOR = 67;
    private final byte tokBITXOR = 68;
    private final byte tokNOT = 69;
    private final byte tokAND = 70;
    private final byte tokOR = 71;
    private final byte tokSCREENWIDTH = 72;
    private final byte tokSCREENHEIGHT = 73;
    private final byte tokISCOLOR = 74;
    private final byte tokNUMCOLORS = 75;
    private final byte tokSTRINGWIDTH = 76;
    private final byte tokSTRINGHEIGHT = 77;
    private final byte tokLEFT$ = 78;
    private final byte tokMID$ = 79;
    private final byte tokRIGHT$ = 80;
    private final byte tokCHR$ = 81;
    private final byte tokSTR$ = 82;
    private final byte tokLEN = 83;
    private final byte tokASC = 84;
    private final byte tokVAL = 85;
    private final byte tokUP = 86;
    private final byte tokDOWN = 87;
    private final byte tokLEFT = 88;
    private final byte tokRIGHT = 89;
    private final byte tokFIRE = 90;
    private final byte tokGAMEA = 91;
    private final byte tokGAMEB = 92;
    private final byte tokGAMEC = 93;
    private final byte tokGAMED = 94;
    private final byte tokDAYS = 95;
    private final byte tokMILLISECONDS = 96;
    private final byte tokYEAR = 97;
    private final byte tokMONTH = 98;
    private final byte tokDAY = 99;
    private final byte tokHOUR = 100;
    private final byte tokMINUTE = 101;
    private final byte tokSECOND = 102;
    private final byte tokMILLISECOND = 103;
    private final byte tokRND = 104;
    private final byte tokERR = 105;
    private final byte tokFRE = 106;
    private final byte tokMOD = 107;
    private final byte tokEDITFORM = 108;
    private final byte tokGAUGEFORM = 109;
    private final byte tokCHOICEFORM = 110;
    private final byte tokDATEFORM = 111;
    private final byte tokMESSAGEFORM = 112;
    private final byte tokLOG = 113;
    private final byte tokEXP = 114;
    private final byte tokSQR = 115;
    private final byte tokSIN = 116;
    private final byte tokCOS = 117;
    private final byte tokTAN = 118;
    private final byte tokASIN = 119;
    private final byte tokACOS = 120;
    private final byte tokATAN = 121;
    private final byte tokABS = 122;
//private final byte tok= = 123;
    private final byte tokHASH = 124;
    private final byte tokPRINTHASH = 125;
    private final byte tokINPUTHASH = 126;
//private final byte tok: = 127;
    private final byte tokOPS = 127;
    private final int tokGELGRAB = 128;
    private final int tokDRAWGEL = 129;
    private final int tokSPRITEGEL = 130;
    private final int tokSPRITEMOVE = 131;
    private final int tokSPRITEHIT = 132;
    private final int tokREADDIR$ = 133;
    private final int tokPROPERTY$ = 134;
    private final int tokGELLOAD = 135;
    private final int tokGELWIDTH = 136;
    private final int tokGELHEIGHT = 137;
    private final int tokPLAYWAV = 138;
    private final int tokPLAYTONE = 139;
    private final int tokINKEY = 140;
    private final int tokSELECT = 141;
    private final int tokALERT = 142;
    private final int tokSETFONT = 143;
    private final int tokMENUADD = 144;
    private final int tokMENUITEM = 145;
    private final int tokMENUREMOVE = 146;
    private final int tokCALL = 147;
    private final int tokENDSUB = 148;
    private final int tokREPAINT = 149;
    private final int tokSENDSMS = 150;
    private final int tokRAND = 151;
    private final int tokALPHAGEL = 152;
    private final int tokCOLORALPHAGEL = 153;
    private final int tokPLATFORMREQUEST = 154;
    private final int tokDELGEL = 155;
    private final int tokDELSPRITE = 156;
    private final int tokMKDIR = 157;
    private final int tokPOINTPRESSED = 158;
    private final int tokPOINTDRAGGED = 159;
    private final int tokPOINTHOLD = 160;
    private final int tokPOINTX = 161;
    private final int tokPOINTY = 162;
    private boolean ifStarted = false;
    private boolean ifEnded = true;

    void LIS2Java() {
    }

    public String compile(String data) {
        mainCode = "";
        if (data.trim().length() <= 0) {
            return "";
        }
        prev_line = Float.NaN;
        variableCount = 0;
        codeerror = "";
        containPoint = false;
        lines = new ArrayList(0);
        lexems = new ArrayList(0);
        vars = new ArrayList(0);
        varsdiff = new ArrayList(0);
        ltype = new ArrayList(0);
        linelex = new ArrayList(0);
        linetype = new ArrayList(0);
        floats = new ArrayList(0);
        char[] chr = data.toCharArray();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < chr.length; i++) {
            if ("\n".indexOf(chr[i]) != -1) {
                if (buffer.toString().trim().length() > 0) {
                    lines.add(buffer.toString().trim());
                    buffer.setLength(0);
                }
            } else {
                if ("\r".indexOf(chr[i]) == -1) {
                    buffer.append(chr[i]);
                }
            }
        }
        if (buffer.length() > 0) {
            lines.add(buffer.toString());
        }
        currLine = 0;
        for (Object line : lines) {
            line((String) line);
            setLex(lexems);
            setType(ltype);
            lexems = new ArrayList(0);
            ltype = new ArrayList(0);
            if (!"".equals(codeerror)) {
                break;
            } else {
                check(currLine);
            }
            currLine++;
        }

        if ("".equals(codeerror)) {
            // compileCode();
            translate2java();

        }

        return "";

    }

    private void line(String line) {
        int i = 0;
        while (i < line.length()) {
            i += getTokens(line, i);
            if (!"".equals(codeerror)) {
                break;
            }
        }
    }

    private int getLexLen(int lineNum) {
        return ((ArrayList) (linelex.get(lineNum))).size();
    }

    private void setLex(ArrayList l) {
        linelex.add(l);
    }

    private String getLex(int lineNum, int lexNum) {
        return (String) ((ArrayList) (linelex.get(lineNum))).get(lexNum);
    }

    private void setType(ArrayList l) {
        linetype.add(l);
    }

    private String getType(int lineNum, int typeNum) {
        return (String) ((ArrayList) (linetype.get(lineNum))).get(typeNum);
    }

    private void check(int lineNum) {
        int bracketCount = 0;
        if (!getType(lineNum, 0).equals(TYPE_INTEGER)) {
            codeerror = "Не правильный номер строки [" + getLex(lineNum, 0) + "]";
            return;
        } else {
            if (getType(lineNum, 1).equals(TYPE_VARIABLE)) {
                if (getLexLen(lineNum) <= 2) {
                    codeerror = "Неверная функция [" + getLex(lineNum, 1) + "] на строке " + getLex(lineNum, 0);
                    return;
                }
            }
        }
        if (prev_line >= Integer.parseInt(getLex(lineNum, 0))) {
            codeerror = "Нарушен порядок строк, строка [" + getLex(lineNum, 0) + "]";
            return;
        }
        prev_line = Integer.parseInt(getLex(lineNum, 0));

        if (codeerror.equals("")) {
            for (int ii = 0; ii < getLexLen(lineNum); ii++) {
                int i = ii;

                if (getLex(lineNum, i).equals("(")) {
                    bracketCount++;
                }
                if (getLex(lineNum, i).equals(")")) {
                    bracketCount--;
                }

                if (getLex(lineNum, i).equals("=")) {
                    if (getLexLen(lineNum) <= i + 1) {
                        codeerror = "Пусто после [=] на строке " + getLex(lineNum, 0);
                        return;
                    }
                }

                if (getType(lineNum, i).equals(TYPE_CONSTANT)) {
                    if (getLexLen(lineNum) >= i + 2) {
                        if (getLex(lineNum, i + 1).equals("=") && !getLex(lineNum, i).equals(")")) {
                            codeerror = "Присваивание для функции [" + getLex(lineNum, i) + "] не допустимо, на строке " + getLex(lineNum, 0);
                            return;
                        }
                    }
                    if (!mb191 && (inarr(func191, getLex(lineNum, i)) || inarr(func191small, getLex(lineNum, i)))) {
                        codeerror = "Оператор [" + getLex(lineNum, i) + "] не поддерживается версией MobileBASIC 1.8.6, строка " + getLex(lineNum, 0);
                        return;
                    } else if (inarr(keywords, getLex(lineNum, i))) {
                        if (getLexLen(lineNum) >= i + 2) {
                            if (!getLex(lineNum, i + 1).equals(":")) {
                                codeerror = "Оператор [" + getLex(lineNum, i) + "] не должен иметь аргументов, на строке " + getLex(lineNum, 0);
                                return;
                            }
                        }
                    } else if (inarr(functions, getLex(lineNum, i))) {
                        if (getLexLen(lineNum) <= i + 1) {
                            codeerror = "Оператор [" + getLex(lineNum, i) + "] должен иметь некоторые аргументы, на строке " + getLex(lineNum, 0);
                            return;
                        } else if (getLexLen(lineNum) >= i + 2) {
                            if (getLex(lineNum, i + 1).equals(":")) {
                                codeerror = "Оператор [" + getLex(lineNum, i) + "] должен иметь некоторые аргументы, на строке " + getLex(lineNum, 0);
                                return;
                            }
                        }
                    }
                }

                if (getLex(lineNum, i).equals("DIM")) {
                    ArrayList temp = (ArrayList) linetype.get(lineNum);
                    temp.add(i + 2, TYPE_ARRAY_BYTE);
                    linetype.set(lineNum, temp);
                    temp = (ArrayList) linelex.get(lineNum);
                    temp.add(i + 2, "");
                    linelex.set(lineNum, temp);
                }

                try {
                    if (getType(lineNum, i).equals(TYPE_VARIABLE) && getLex(lineNum, i + 1).equals("(")) {
                        ArrayList temp = (ArrayList) linetype.get(lineNum);
                        temp.add(i + 1, TYPE_ARRAY_BYTE);
                        linetype.set(lineNum, temp);
                        temp = (ArrayList) linelex.get(lineNum);
                        temp.add(i + 1, "");
                        linelex.set(lineNum, temp);
                    }
                } catch (Exception ex) {
                }

            }
            if (bracketCount != 0) {
                codeerror = "Не найдена открытая/закрытая скобка на строке " + getLex(lineNum, 0);
            }
        }
    }

    private int getTokens(String line, int startPos) {
        int initPos = startPos;
        String op = "";
        char curChar = Character.toUpperCase(line.charAt(startPos));
        if (('A' <= curChar) && (curChar <= 'Z')) {
            while (true) {
                curChar = Character.toUpperCase(line.charAt(startPos));
                if (('A' <= curChar && curChar <= 'Z')
                        || ('0' <= curChar && curChar <= '9')
                        || (curChar == '_') || (curChar == '$') || (curChar == '%')) {

                    op += curChar;
                    startPos++;
                    if (startPos >= line.length()) {
                        break;
                    }
                } else {
                    break;
                }
            }

            lexems.add(op);

            boolean isData = false;
            String data = "";
            if ("DATA".equals(op) || "REM".equals(op)) {
                isData = true;
                startPos++;
                while (startPos < line.length()) {
                    data += line.charAt(startPos);
                    startPos++;
                }
                lexems.add(data);
            }
            if (!inarr(ops, op) && !inarr(small, op)) {
                if (!invec(vars, op)) {
                    vars.add(op);
                    String opdiff = op;
                    String type = op.substring(op.length() - 1);
                    if (type.equals("%") || type.equals("$")) {
                        opdiff = op.substring(0, op.length() - 1);
                    }
                    if (!invec(varsdiff, opdiff)) {
                        varsdiff.add(opdiff);
                    } else {
                        varsdiff.add(opdiff + variableCount);
                        variableCount++;
                    }
                }
                ltype.add(TYPE_VARIABLE);
            } else {
                ltype.add(TYPE_CONSTANT);
                if (isData) {
                    ltype.add(TYPE_DATA);
                }
            }

        } else if (line.charAt(startPos) == '"') {
            startPos++;
            while (!(line.charAt(startPos) == '"') || (line.charAt(startPos - 1)) == '\\') {
                if (line.charAt(startPos) != '\\') {
                    op += line.charAt(startPos);
                }
                startPos++;
                if (startPos >= line.length()) {
                    setLex(lexems);
                    codeerror = "Не найдена закрытая кавычка на строке " + getLex(currLine, 0);
                    break;
                }
            }
            startPos++;
            lexems.add("\"" + op + "\"");
            ltype.add(TYPE_STRING);
        } else if (line.charAt(startPos) >= '0' && line.charAt(startPos) <= '9') {
            while (startPos < line.length() && (line.charAt(startPos) >= '0' && line.charAt(startPos) <= '9' || line.charAt(startPos) == '.')) {
                op += line.charAt(startPos);
                startPos++;
            }
            if (op.contains(".")) {
                if (!containPoint) {
                    containPoint = true;
                }
                if (op.charAt(op.length() - 1) == '.') {
                    setLex(lexems);
                    codeerror = "NumberLexem contains more than one point [" + op + "] at line " + getLex(currLine, 0);
                }
            }
            lexems.add(op);
            if (containPoint) {
                containPoint = false;
                ltype.add(TYPE_FLOAT);
                if (!invec(floats, op)) {
                    floats.add(op);
                }
            } else {
                ltype.add(TYPE_INTEGER);
            }

        } else if ((line.charAt(startPos) >= 'А' && line.charAt(startPos) <= 'я') || ((line.charAt(startPos) != ' ' && line.charAt(startPos) < 'А') && !inarr(ops, line.charAt(startPos) + "") && !inarr(small, line.charAt(startPos) + ""))) {
            setLex(lexems);
            codeerror = "Не правильный симовол [" + line.charAt(startPos) + "] на строке " + getLex(currLine, 0);
        } else if (inarr(ops, line.charAt(startPos) + "") || inarr(small, line.charAt(startPos) + "")) {
            op = line.charAt(startPos) + "";
            if (line.length() > startPos + 1) {
                if (line.charAt(startPos) == '<') {
                    if (line.charAt(startPos + 1) == '>') {
                        op = "<>";
                        startPos++;
                    }
                    if (line.charAt(startPos + 1) == '=') {
                        op = "<=";
                        startPos++;
                    }
                }
                if (line.charAt(startPos) == '>') {
                    if (line.charAt(startPos + 1) == '=') {
                        op = ">=";
                        startPos++;
                    }
                }
            }
            lexems.add(op);
            ltype.add(TYPE_CONSTANT);
            startPos++;
        } else {
            startPos++;
        }
        return startPos - initPos;
    }

    private int parseRValue(int linepos, int lexpos, int num, boolean add) {

        try {
            for (int i = lexpos + 1; i < getLexLen(linepos); i++) {
                lexpos = i;
                if (getType(linepos, i).equals(TYPE_STRING) || getType(linepos, i).equals(TYPE_INTEGER) || getType(linepos, i).equals(TYPE_FLOAT)) {
                    if (add) {
                        mainCode += getLex(linepos, i);
                    }
                    if (getLex(linepos, i - 1).equals(",")) {
                        num--;
                    }
                    lexpos++;
                    if (getLexLen(linepos) > i + 1) {
                        if (!getLex(linepos, i + 1).equals("+") && !getLex(linepos, i + 1).equals(",") && !getLex(linepos, i + 1).equals("-") && !getLex(linepos, i + 1).equals("/") && !getLex(linepos, i + 1).equals("*")) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (getType(linepos, i).equals(TYPE_VARIABLE)) {
                    for (int ii = 0; ii < vars.size(); ii++) {
                        if (getLex(linepos, lexpos).equals(vars.get(ii))) {
                            if (add) {
                                mainCode += ((String) varsdiff.get(ii)).toLowerCase();
                            }
                            num--;
                            lexpos++;
                        }
                    }
                }
                if (getType(linepos, i).equals(TYPE_CONSTANT)) {
                    if (getLex(linepos, i).equals(":")) {
                        break;
                    }

                    // System.err.println(getLex(linepos, i));
                    if (!getLex(linepos, i).equals("(") && !getLex(linepos, i).equals(")")) {
                        lexpos = parseCommand(indexInArray(ops, getLex(linepos, i)), linepos, i);
                        i = lexpos;
                    }
                    if (getLex(linepos, i - 1).equals(",")) {
                        num--;
                    }
                    lexpos++;

                }
                if (num <= 0) {
                    break;
                }
            }
        } catch (Exception e) {
            // System.err.println(linepos);
        }
        return lexpos;
    }

    private int parseCommand(int keyword, int linepos, int lexpos) {
        String nextLex = "";
        switch (keyword) {
            case tokOPS:
                mainCode += ";\n";
                break;
            case tokZPT:
                mainCode += ",";
                break;
            case tokEQUALS:
                //mainCode += (ifEnded) ? "= " : "== ";
                if (!ifEnded) {
                    if (getType(linepos, lexpos - 1).equals(TYPE_VARIABLE) && getLex(linepos, lexpos - 1).endsWith("$")) {
                        mainCode += ".equals(";
                        equalsBracket = true;
                    } else {
                        mainCode += " == ";
                    }
                } else {
                    mainCode += " = ";
                }
                break;
            case tokNOTEQUALS:
                if (getType(linepos, lexpos - 1).equals(TYPE_VARIABLE) && getLex(linepos, lexpos - 1).endsWith("$")) {
                    mainCode += ".equals(";
                    equalsBracket = true;
                    notEquals = true;
                } else {
                    mainCode += " != ";
                }
                break;
            case tokPLUS:
                mainCode += "+";
                break;
            case tokMINUS:
                mainCode += "-";
                break;
            case tokSTOP:
                mainCode += "_halt()";
                break;
            case tokLEFTBRACKET:
                mainCode += "(";
                break;
            case tokMEN:
                mainCode += "<";
                break;
            case tokBOL:
                mainCode += ">";
                break;
            case tokMENR:
                mainCode += "<=";
                break;
            case tokBOLR:
                mainCode += ">=";
                break;
            case tokUMN:
                mainCode += "*";
                break;
            case tokRIGHTBRACKET:
                //  System.err.println("left bracket");
                mainCode += ")";
                break;
            case tokPOP:

                break;
            case tokRETURN:
                RETURN = true;
                break;
            case tokEND:
                mainCode += "_halt()";
                break;
            case tokNEW:

                break;
            case tokRUN:

                break;
            case tokDIR:

                break;
            case tokDEG:
                break;
            case tokRAD:
                break;
            case tokBYE:
                mainCode += "_halt()";
                break;
            case tokGOTO:
                Goto = true;
                mainCode += "l = ";
                if (ifStarted) {
                    elseGoto = true;
                }
                break;
            case tokGOSUB:
                mainCode += "run(";
                lexpos = parseRValue(linepos, lexpos, 1, true);
                mainCode += ")";
                break;
            case tokSLEEP:
                mainCode += "_delay(";
                lexpos = parseRValue(linepos, lexpos, 1, true);
                mainCode += ")";

                break;
            case tokPRINT:
                mainCode += "System.out.println(";
                lexpos = parseRValue(linepos, lexpos, 1, true);
                mainCode += ")";
                break;
            case tokREM:
                mainCode += "///";
                break;
            case tokDIM:
                break;
            case tokIF:
                break;
            case tokTHEN:
                break;
            case tokCLS:
                mainCode += "_CLS()";
                break;
            case tokPLOT:
                mainCode += "_plot";
                lexpos = parseRValue(linepos, lexpos, 2, true);
                mainCode += ")";
                break;
            case tokDRAWLINE:
                mainCode += "_drawLine(";
                lexpos = parseRValue(linepos, lexpos, 4, true);
                mainCode += ")";
                break;
            case tokFILLRECT:
                mainCode += "_fillRect(";
                lexpos = parseRValue(linepos, lexpos, 4, true);
                mainCode += ")";
                break;
            case tokDRAWRECT:
                mainCode += "_drawRect(";
                lexpos = parseRValue(linepos, lexpos, 4, true);
                mainCode += ")";
                break;
            case tokFILLROUNDRECT:
                mainCode += "_fillRoundRect(";
                lexpos = parseRValue(linepos, lexpos, 4, true);
                mainCode += ")";
                break;
            case tokDRAWROUNDRECT:
                mainCode += "_drawRoundRect(";
                lexpos = parseRValue(linepos, lexpos, 4, true);
                mainCode += ")";
                break;
            case tokFILLARC:
                mainCode += "_fillArc(";
                lexpos = parseRValue(linepos, lexpos, 6, true);
                mainCode += ")";
                break;
            case tokDRAWARC:
                mainCode += "_drawArc(";
                lexpos = parseRValue(linepos, lexpos, 6, true);
                mainCode += ")";
                break;
            case tokDRAWSTRING:
                mainCode += "_drawText(";
                lexpos = parseRValue(linepos, lexpos, 3, true);
                mainCode += ")";
                break;
            case tokSETCOLOR:
                mainCode += "_setColor(";
                lexpos = parseRValue(linepos, lexpos, 3, true);
                mainCode += ")";
                break;
            case tokBLIT:
                break;
            case tokFOR:
                break;
            case tokTO:
                break;
            case tokSTEP:
                break;
            case tokNEXT:
                break;
            case tokINPUT:
                break;
            case tokLIST:
                break;
            case tokENTER:
                break;
            case tokLOAD:
                break;
            case tokSAVE:
                break;
            case tokDELETE:
                break;
            case tokEDIT:
                break;
            case tokTRAP:
                break;
            case tokOPEN:
                break;
            case tokCLOSE:
                break;
            case tokNOTE:
                break;
            case tokPOINT:
                break;
            case tokPUT:
                break;
            case tokGET:
                break;
            case tokDATA:
                break;
            case tokRESTORE:
                break;
            case tokREAD:
                break;
            /*case tok =:
             break;
             case tok < >:
             break;
             case tok <:
             break;
             case tok <=:
             break;
             case tok >:
             break;
             case tok >=:
             break;
             case tok(:
             break;
             case tok
             ):
             break;
             case tok
             ,:
             break;
             case tok +:
             break;
             case tok -:
             break;
             case tok -:
             break;
             case tok *:
             break;
             case tok /:
             break;
             case tok ^:
             break;*/
            case tokBITAND:
                break;
            case tokBITOR:
                break;
            case tokBITXOR:
                break;
            case tokNOT:
                mainCode += "!(";
                NOT = true;
                break;
            case tokAND:
                if (equalsBracket) {
                    mainCode += ")";
                    equalsBracket = false;
                }
                if (notEquals) {
                    mainCode += " == false";
                    notEquals = false;
                }
                if (NOT) {
                    mainCode += ")";
                    NOT = false;
                }
                mainCode += " && ";
                break;
            case tokOR:
                if (equalsBracket) {
                    mainCode += ")";
                    equalsBracket = false;
                }
                if (notEquals) {
                    mainCode += " == false";
                    notEquals = false;
                }
                if (NOT) {
                    mainCode += ")";
                    NOT = false;
                }
                mainCode += " || ";
                break;
            case tokSCREENWIDTH:
                mainCode += "_getHeight(";
                lexpos = parseRValue(linepos, lexpos, 1, true);
                mainCode += ")";
                break;
            case tokSCREENHEIGHT:
                mainCode += "_getWidth(";
                lexpos = parseRValue(linepos, lexpos, 1, true);
                mainCode += ")";
                break;
            case tokISCOLOR:
                break;
            case tokNUMCOLORS:
                break;
            case tokSTRINGWIDTH:
                mainCode += "_getStringWidth(";
                lexpos = parseRValue(linepos, lexpos, 1, true);
                mainCode += ")";
                break;
            case tokSTRINGHEIGHT:
                mainCode += "_getStringHeight(";
                lexpos = parseRValue(linepos, lexpos, 1, true);
                mainCode += ")";
                break;
            case tokLEFT$:
                break;
            case tokMID$:
                break;
            case tokRIGHT$:
                break;
            case tokCHR$:
                break;
            case tokSTR$:
                mainCode += "_str(";
                lexpos = parseRValue(linepos, lexpos, 1, true);
                mainCode += ")";
                break;
            case tokLEN:
                break;
            case tokASC:
                break;
            case tokVAL:
                break;
            case tokUP:
                mainCode += "_Up()";
                lexpos = parseRValue(linepos, lexpos, 1, false);
                nextLex = getLex(linepos, lexpos + 1);
                if (ifStarted) {
                    if (!nextLex.contains(">") && !nextLex.contains("<") && !nextLex.contains("=")) {
                        mainCode += " != 0";
                    }
                }
                break;
            case tokDOWN:
                mainCode += "_Down()";
                lexpos = parseRValue(linepos, lexpos, 1, false);
                nextLex = getLex(linepos, lexpos + 1);
                if (ifStarted) {
                    if (!nextLex.contains(">") && !nextLex.contains("<") && !nextLex.contains("=")) {
                        mainCode += " != 0";
                    }
                }
                break;
            case tokLEFT:
                mainCode += "_Left()";
                lexpos = parseRValue(linepos, lexpos, 1, false);
                nextLex = getLex(linepos, lexpos + 1);
                if (ifStarted) {
                    if (!nextLex.contains(">") && !nextLex.contains("<") && !nextLex.contains("=")) {
                        mainCode += " != 0";
                    }
                }
                break;
            case tokRIGHT:
                mainCode += "_Right()";
                lexpos = parseRValue(linepos, lexpos, 1, false);
                nextLex = getLex(linepos, lexpos + 1);
                if (ifStarted) {
                    if (!nextLex.contains(">") && !nextLex.contains("<") && !nextLex.contains("=")) {
                        mainCode += " != 0";
                    }
                }
                break;
            case tokFIRE:
                mainCode += "_Fire()";
                lexpos = parseRValue(linepos, lexpos, 1, false);
                nextLex = getLex(linepos, lexpos + 1);
                if (!nextLex.contains(">") && !nextLex.contains("<") && !nextLex.contains("=")) {
                    mainCode += " != 0";
                }
                break;
            case tokGAMEA:
                mainCode += "_GameA()";
                lexpos = parseRValue(linepos, lexpos, 1, false);
                nextLex = getLex(linepos, lexpos + 1);
                if (ifStarted) {
                    if (!nextLex.contains(">") && !nextLex.contains("<") && !nextLex.contains("=")) {
                        mainCode += " != 0";
                    }
                }
                break;
            case tokGAMEB:
                mainCode += "_GameB()";
                lexpos = parseRValue(linepos, lexpos, 1, false);
                nextLex = getLex(linepos, lexpos + 1);
                if (ifStarted) {
                    if (!nextLex.contains(">") && !nextLex.contains("<") && !nextLex.contains("=")) {
                        mainCode += " != 0";
                    }
                }
                break;
            case tokGAMEC:
                mainCode += "_GameC()";
                lexpos = parseRValue(linepos, lexpos, 1, false);
                nextLex = getLex(linepos, lexpos + 1);
                if (ifStarted) {
                    if (!nextLex.contains(">") && !nextLex.contains("<") && !nextLex.contains("=")) {
                        mainCode += " != 0";
                    }
                }
                break;
            case tokGAMED:
                mainCode += "_GameD()";
                lexpos = parseRValue(linepos, lexpos, 1, false);
                nextLex = getLex(linepos, lexpos + 1);
                if (ifStarted) {
                    if (!nextLex.contains(">") && !nextLex.contains("<") && !nextLex.contains("=")) {
                        mainCode += " != 0";
                    }
                }
                break;
            case tokDAYS:
                break;
            case tokMILLISECONDS:
                break;
            case tokYEAR:
                break;
            case tokMONTH:
                break;
            case tokDAY:
                break;
            case tokHOUR:
                break;
            case tokMINUTE:
                break;
            case tokSECOND:
                break;
            case tokMILLISECOND:
                break;
            case tokRND:
                break;
            case tokERR:
                break;
            case tokFRE:
                break;
            case tokMOD:
                break;
            case tokEDITFORM:
                break;
            case tokGAUGEFORM:
                break;
            case tokCHOICEFORM:
                break;
            case tokDATEFORM:
                break;
            case tokMESSAGEFORM:
                break;
            case tokLOG:
                break;
            case tokEXP:
                break;
            case tokSQR:
                break;
            case tokSIN:
                break;
            case tokCOS:
                break;
            case tokTAN:
                break;
            case tokASIN:
                break;
            case tokACOS:
                break;
            case tokATAN:
                break;
            case tokABS:
                break;
            /*case tok =:
             break;*/
            case tokHASH:
                break;
            case tokPRINTHASH:
                break;
            case tokINPUTHASH:
                break;
            /*case tok::
             break;*/
            case tokGELGRAB:
                break;
            case tokDRAWGEL:
                mainCode += "_DrawGel(";
                lexpos = parseRValue(linepos, lexpos, 3, true) - 1;
                mainCode += ")";
                break;
            case tokSPRITEGEL:
                mainCode += "_SpriteGEL(";
                lexpos = parseRValue(linepos, lexpos, 2, true) - 1;
                mainCode += ")";
                break;
            case tokSPRITEMOVE:
                mainCode += "_SpriteMove(";
                lexpos = parseRValue(linepos, lexpos, 3, true) - 1;
                mainCode += ")";
                break;
            case tokSPRITEHIT:
                mainCode += "_SpriteHit(";
                lexpos = parseRValue(linepos, lexpos, 2, true);
                mainCode += ")";
                if (ifStarted) {
                    if (!nextLex.contains(">") && !nextLex.contains("<") && !nextLex.contains("=")) {
                        mainCode += " != 0";
                    }
                }
                break;
            case tokREADDIR$:
                break;
            case tokPROPERTY$:
                break;
            case tokGELLOAD:
                mainCode += "_GelLoad(";
                lexpos = parseRValue(linepos, lexpos, 2, true) - 1;
                mainCode += ")";
                break;
            case tokGELWIDTH:
                mainCode += "_getImageWidth(";
                lexpos = parseRValue(linepos, lexpos, 1, true);
                mainCode += ")";
                break;
            case tokGELHEIGHT:
                mainCode += "_getImageHeight(";
                lexpos = parseRValue(linepos, lexpos, 1, true);
                mainCode += ")";
                break;
            case tokPLAYWAV:
                break;
            case tokINKEY:
                // System.err.println("here");
                mainCode += "_INKEY()";
                lexpos = parseRValue(linepos, lexpos, 1, false);
                if (ifStarted) {
                    if (!nextLex.contains(">") && !nextLex.contains("<") && !nextLex.contains("=")) {
                        mainCode += " != 0";
                    }
                }
                break;
            case tokSELECT:
                mainCode += "select(new String[]{";
               // lexpos = parseRValue(linepos, lexpos, true);
                mainCode += "})";
                break;
            case tokALERT:
                break;
            case tokSETFONT:
                mainCode += "_setFont(";
                lexpos = parseRValue(linepos, lexpos, 1, true);
                mainCode += ")";
                break;
            case tokMENUADD:
                break;
            case tokMENUITEM:
                break;
            case tokMENUREMOVE:
                break;
            case tokCALL:
                break;
            case tokENDSUB:
                break;
            case tokREPAINT:
                mainCode += "_repaint()";
                break;
            case tokSENDSMS:
                break;
            case tokRAND:
                mainCode += "_rand(";
                lexpos = parseRValue(linepos, lexpos, 2, true);
                mainCode += ")";
                break;
            case tokALPHAGEL:
                break;
            case tokCOLORALPHAGEL:
                break;
            case tokPLATFORMREQUEST:
                break;
            case tokDELGEL:
                break;
            case tokDELSPRITE:
                mainCode += "_DelSprite(";
                lexpos = parseRValue(linepos, lexpos, 1, true) - 1;
                mainCode += ")";
                break;
            case tokMKDIR:
                break;
            case tokPOINTPRESSED:
                break;
            case tokPOINTDRAGGED:
                break;
            case tokPOINTHOLD:
                break;
            case tokPOINTX:
                break;
            case tokPOINTY:
                break;

        }
        return lexpos;
    }

    private void translate2java() {

        ifStarted = false;
        ifEnded = true;
        boolean forStarted = false;

        for (int i = 0; i < vars.size(); i++) {
            Object var = vars.get(i);
            Object vard = varsdiff.get(i);
            String type = ((String) var).substring(((String) var).length() - 1);
            String varname = ((String) vard).toLowerCase();
            if (type.equals("$")) {
                mainCode += "public static String " + varname + " = \"\";\n";
            } else if (type.equals("%")) {
                mainCode += "public static int " + varname + " = 0;\n";
            } else {
                mainCode += "public static double " + varname + " = 0.0;\n";
            }

        }

        mainCode += "\nstatic void run(int l) {\n"
                + "bombom:\n"
                + "while (true) {\n"
                + " switch (l) {\n";
        line:
        for (int currsLine = 0; currsLine < lines.size(); currsLine++) {
            boolean notnull = false;
            for (int currLex = 1; currLex < getLexLen(currsLine); currLex++) {
                if (!getLex(currsLine, currLex).equals("")) {
                    notnull = true;
                    break;
                }
            }
            if (notnull) {
                mainCode += "   case " + Integer.parseInt(getLex(currsLine, 0)) + ":\n";
                if (currsLine == 0) {
                    mainCode += "   default:\n";
                }
                for (int currLex = 1; currLex < getLexLen(currsLine); currLex++) {

                    //mainCode += (ifEnded) ? " " : "";
                    if (getType(currsLine, currLex).equals(TYPE_VARIABLE)) {
                        ///переменные
                        for (int i = 0; i < vars.size(); i++) {
                            if (getLex(currsLine, currLex).equals(vars.get(i))) {
                                mainCode += ((String) varsdiff.get(i)).toLowerCase();
                            }
                        }
                    }

                    if (getType(currsLine, currLex).equals(TYPE_INTEGER)) {
                        mainCode += Integer.parseInt(getLex(currsLine, currLex));
                    }

                    if (getType(currsLine, currLex).equals(TYPE_FLOAT)) {
                        mainCode += getLex(currsLine, currLex);
                    }

                    if (getType(currsLine, currLex).equals(TYPE_STRING)) {
                        mainCode += getLex(currsLine, currLex);
                    }

                    if (getType(currsLine, currLex).equals(TYPE_CONSTANT)) {
                        ////обработка операторов
                        if (getLex(currsLine, currLex).equals(":")) {
                            mainCode += ";\n";
                            continue;
                        }

                        if (getLex(currsLine, currLex).equals("IF")) {
                            ifStarted = true;
                            ifEnded = false;
                            mainCode += "if (";

                            continue;
                        } else if (getLex(currsLine, currLex).equals("THEN")) {
                            ifEnded = true;

                            if (equalsBracket) {
                                mainCode += ")";
                                equalsBracket = false;
                            }
                            if (notEquals) {
                                mainCode += " == false";
                                notEquals = false;
                            }
                            if (NOT) {
                                mainCode += ")";
                                NOT = false;
                            }
                            mainCode += ") {\n";
                            continue;
                        } else if (getLex(currsLine, currLex).equals("FOR")) {
                            forStarted = true;
                        }

                        for (int i = 0; i < ops.length; i++) {
                            if (getLex(currsLine, currLex).equals(ops[i].trim()) || getLex(currsLine, currLex).equals(small[i].trim())) {
                                currLex = parseCommand(i, currsLine, currLex);
                                if (currLex >= getLexLen(currsLine)) {
                                    //System.err.print("exit from array");
                                    break;
                                }
                            }
                        }

                    }
                    if (currLex + 1 >= getLexLen(currsLine)) {
                        if (!RETURN) {
                            mainCode += ";";
                        }

                    }
                }

                if (ifStarted) {
                    mainCode += "\n}";
                    if (elseGoto && lines.size() > currsLine + 1) {
                        mainCode += " else l = " + getLex(currsLine + 1, 0) + ";";
                    }
                    ifStarted = false;
                }
                if (lines.size() > currsLine + 1) {
                    if (!elseGoto && !Goto) {
                        mainCode += "\n l = " + getLex(currsLine + 1, 0) + ";";
                    }
                    elseGoto = false;
                    Goto = false;

                }
                if (!ifEnded) {
                    mainCode += ";";
                }
                if (!RETURN) {
                    mainCode += "\ncontinue;\n";
                } else {
                    mainCode += "\nbreak bombom;\n";
                    RETURN = false;
                }
            }
        }
        mainCode += "   }\n"
                + " }\n"
                + "}\n";
        System.out.println(mainCode);
    }

    private boolean invec(ArrayList paramVector, String line) {
        return paramVector.contains(line);
        /*for (int i = 0; i < paramVector.size(); i++) {
         if (paramVector.get(i).equals(line)) {
         return true;
         }
         }
         return false;*/
    }

    private boolean inarr(String[] paramArrayOfString, String line) {
        for (String paramArrayOfString1 : paramArrayOfString) {
            if (paramArrayOfString1.trim().equals(line)) {
                return true;
            }
        }
        return false;
    }

    private int indexInArray(String[] arr, String key) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].trim().equals(key)) {
                return i;
            }
        }
        return -1;
    }
}
