package com.holdfast.mbide.ide;

public class Token {

    public static final int ERROR = -1;
    public static final int EOL = 0;
    public static final int WHITESPACE = 1;
    public static final int LITERAL = 2;
    public static final int INTEGER = 3;
    public static final int LONG = 4;
    public static final int REAL = 5;
    public static final int STRING = 6;
    public static final int SPECIAL = 7;
    public static final int COMMENT = 8;
    public static final int DATA = 9;
    public int tokenType;
    public int tokenStart;
    public int tokenEnd;
    public long value;

    public Token() {
        this.tokenType = 0;
        this.tokenStart = 0;
        this.tokenEnd = 0;
        this.value = 0L;
    }

    Token(Token token) {
        this.tokenType = token.tokenType;
        this.tokenStart = token.tokenStart;
        this.tokenEnd = token.tokenEnd;
        this.value = token.value;
    }
}
