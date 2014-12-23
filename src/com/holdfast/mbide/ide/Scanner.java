package com.holdfast.mbide.ide;

public class Scanner {

    String inputText;
    int inputLength;
    int offset;
    private final Token tokenBuffer = new Token();
    private boolean bufferValid = false;

    public Scanner(String inputText) {
        this.inputText = inputText;
        this.inputLength = inputText.length();
        this.offset = 0;
    }

    private boolean GetNewToken(Token token) {
        boolean tokenValid = false;

        if (this.offset < this.inputLength) {
            char ch = this.inputText.charAt(this.offset);

            if (ch == '\n') {
                tokenValid = true;
                token.tokenType = 0;
                token.tokenStart = this.offset;
                token.tokenEnd = (++this.offset);
            } else if ((ch == ' ') || (ch == '\t')) {
                tokenValid = true;
                token.tokenType = 1;
                token.tokenStart = this.offset;
                token.tokenEnd = (++this.offset);

                while (this.offset < this.inputLength) {
                    ch = this.inputText.charAt(this.offset);
                    if ((ch != ' ') && (ch != '\t')) {
                        break;
                    }
                    token.tokenEnd = (++this.offset);
                }

            } else if ((ch >= '0') && (ch <= '9')) {
                tokenValid = true;
                token.tokenType = 3;
                token.tokenStart = this.offset;
                token.tokenEnd = this.offset;

                token.value = 0L;

                while (this.offset < this.inputLength) {
                    ch = this.inputText.charAt(this.offset);
                    if ((ch >= '0') && (ch <= '9')) {
                        token.value = (token.value * 10L + (ch - '0'));
                        token.tokenEnd = (++this.offset);
                    } else {
                        if (ch != '#') {
                            break;
                        }
                        token.tokenType = 4;
                        token.tokenEnd = (++this.offset);
                        break;
                    }

                }

            } else if (ch == '"') {
                tokenValid = true;
                token.tokenType = 6;
                token.tokenStart = this.offset;
                token.tokenEnd = (++this.offset);

                boolean escFlag = false;

                while (this.offset < this.inputLength) {
                    ch = this.inputText.charAt(this.offset);
                    if (escFlag) {
                        token.tokenEnd = (++this.offset);
                        escFlag = false;
                    } else if (ch == '\\') {
                        token.tokenEnd = (++this.offset);
                        escFlag = true;
                    } else if (ch != '"') {
                        token.tokenEnd = (++this.offset);
                    } else {
                        token.tokenEnd = (++this.offset);
                        break;
                    }
                }

            } else if (((ch >= 'a') && (ch <= 'z')) || ((ch >= 'A') && (ch <= 'Z'))) {
                tokenValid = true;
                token.tokenType = 2;
                token.tokenStart = this.offset;
                token.tokenEnd = (++this.offset);

                while (this.offset < this.inputLength) {
                    ch = this.inputText.charAt(this.offset);
                    if (((ch >= 'a') && (ch <= 'z'))
                            || ((ch >= 'A') && (ch <= 'Z'))
                            || ((ch >= '0') && (ch <= '9'))
                            || (ch == '_')) {
                        token.tokenEnd = (++this.offset);
                    } else {
                        if ((ch != '%') && (ch != '$') && (ch != '#')) {
                            break;
                        }
                        token.tokenEnd = (++this.offset);
                        break;
                    }

                }

            } else {
                tokenValid = true;
                token.tokenType = 7;
                token.tokenStart = this.offset;
                token.tokenEnd = (++this.offset);

                if (ch == '<') {
                    if (this.offset < this.inputLength) {
                        ch = this.inputText.charAt(this.offset);
                        if ((ch == '=') || (ch == '>')) {
                            token.tokenEnd = (++this.offset);
                        }
                    }
                } else if (ch == '>') {
                    if (this.offset < this.inputLength) {
                        ch = this.inputText.charAt(this.offset);
                        if (ch == '=') {
                            token.tokenEnd = (++this.offset);
                        }
                    }
                }
            }
        }
        return tokenValid;
    }

    public boolean GetToken(Token token) {
        boolean tokenValid;

        if (this.bufferValid) {
            token.tokenType = this.tokenBuffer.tokenType;
            token.tokenStart = this.tokenBuffer.tokenStart;
            token.tokenEnd = this.tokenBuffer.tokenEnd;
            token.value = this.tokenBuffer.value;
            this.bufferValid = false;
            tokenValid = true;
        } else {
            tokenValid = GetNewToken(token);
        }

        return tokenValid;
    }
}
