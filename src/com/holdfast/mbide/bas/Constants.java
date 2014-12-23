package com.holdfast.mbide.bas;

public interface Constants {

    static final int SIGNATURE_MB = 0x4d420001;
    static final int SIGNATURE_MB191 = 0x4d420191;

    static final String TYPE_STRING = "String",
            TYPE_DATA = "Data",
            TYPE_CONSTANT = "Operator",
            TYPE_VARIABLE = "Variable",
            TYPE_ARRAY_BYTE = "ArrayByte",
            TYPE_INTEGER = "Integer",
            TYPE_FLOAT = "Float";

    static final String[] small = new String[]{"", "", "RET", "", "", "", "", "", "", "", "GT", "GS", "SL", "PR", "", "", "", "TH", "", "", "DL", "FR", "DR", "FRR", "DRR", "FA", "DA", "DS", "SC", "", "", "", "", "", "IN", "", "", "", "", "DEL", "", "TR", "OP", "CL", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "SCW", "SCH", "", "", "STW", "STH", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "EF", "GF", "CF", "DF", "MSF", "", "", "", "", "", "", "", "", "", "", "", "", "PR", "IN", "", "GG", "DG", "SG", "SM", "SH", "", "", "GL", "GW", "GH",
        // ******** added by Mumey***********
        "PW", "PT", "IK", "SEL", "AL", "SF", "MA", "MI", "MR", "", "ES",
        //********    added 02.2013    ***********
        "RE", "SS", "", "AG", "CAG", "PFR", "", "", "MKDIR", "PP", "PD", "PH", "PX", "PY"};

    static final String[] ops = {" STOP ", " POP ", " RETURN ", " END ", " NEW ", " RUN ", " DIR ", " DEG ", " RAD ", " BYE ", " GOTO ",
        " GOSUB ", " SLEEP ", " PRINT ", " REM ", " DIM ", " IF ", " THEN ", " CLS ", " PLOT ", " DRAWLINE ",
        " FILLRECT ", " DRAWRECT ", " FILLROUNDRECT ", " DRAWROUNDRECT ", " FILLARC ",
        " DRAWARC ", " DRAWSTRING ", " SETCOLOR ", " BLIT ", " FOR ", " TO ", " STEP ", " NEXT ", " INPUT ",
        " LIST ", " ENTER ", " LOAD ", " SAVE ", " DELETE ", " EDIT ", " TRAP ", " OPEN ", " CLOSE ",
        " NOTE ", " POINT ", " PUT ", " GET ", " DATA ", " RESTORE ", " READ ", "=", "<>", "<", "<=",
        ">", ">=", "(", ")", ",", "+", "-", "-", "*", "/", "^", " BITAND ", " BITOR ", " BITXOR ", " NOT ",
        " AND ", " OR ", "SCREENWIDTH", "SCREENHEIGHT", " ISCOLOR ", " NUMCOLORS ", "STRINGWIDTH", "STRINGHEIGHT",
        "LEFT$", "MID$", "RIGHT$", "CHR$", "STR$", "LEN", "ASC", "VAL", " UP ", " DOWN ", " LEFT ", " RIGHT ",
        " FIRE ", " GAMEA ", " GAMEB ", " GAMEC ", " GAMED ", " DAYS ", " MILLISECONDS ",
        " YEAR ", " MONTH ", " DAY ", " HOUR ", " MINUTE ", " SECOND ", " MILLISECOND ", "RND", " ERR ",
        " FRE ", "MOD", "EDITFORM ", "GAUGEFORM ", "CHOICEFORM", "DATEFORM", "MESSAGEFORM",
        "LOG", "EXP", "SQR", "SIN", "COS", "TAN", "ASIN", "ACOS", "ATAN", "ABS", "=", "#", " PRINT ",
        " INPUT ", ":", " GELGRAB ", " DRAWGEL ", " SPRITEGEL ", " SPRITEMOVE ", " SPRITEHIT ",
        "READDIR$", "PROPERTY$", " GELLOAD ", " GELWIDTH", " GELHEIGHT", " PLAYWAV ", " PLAYTONE ",
        " INKEY", "SELECT", "ALERT ", " SETFONT ", " MENUADD ", " MENUITEM", " MENUREMOVE ",
        " CALL ", " ENDSUB ", " REPAINT", "SENDSMS ", " RAND", " ALPHAGEL ", " COLORALPHAGEL ", " PLATFORMREQUEST ", " DELGEL", " DELSPRITE ", "MKDIR", " POINTPRESSED", " POINTDRAGGED", " POINTHOLD", " POINTX", " POINTY"};

}
