package com.holdfast.mbide.ide;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author HoldFast
 */
public class Highlight implements Runnable, DocumentListener {

    private final JTextPane editor;
    private final boolean SyntaxHighlightingEnabled = true;
    private int lastHighlightStart;
    private int lastHighlightEnd;
    private int highlightOffset;
    private boolean runHighlightFlag = false;
    private int syntaxHighlightBegin;
    private int syntaxHighlightEnd;
    private final SimpleAttributeSet defaultStyle;
    private final SimpleAttributeSet keywordStyle;
    private final SimpleAttributeSet commentStyle;
    private final SimpleAttributeSet dataStyle;
    private final SimpleAttributeSet integerConstantStyle;
    private final SimpleAttributeSet longConstantStyle;
    private final SimpleAttributeSet realConstantStyle;
    private final SimpleAttributeSet stringConstantStyle;
    private final SimpleAttributeSet integerIdentifierStyle;
    private final SimpleAttributeSet longIdentifierStyle;
    private final SimpleAttributeSet stringIdentifierStyle;
    private final SimpleAttributeSet highlightLineStyle = new SimpleAttributeSet();
    public boolean error = false;
    public int start = 0;
    public int end = 0;

    public Highlight(JTextPane editor) {
        this.editor = editor;
        editor.getDocument().addDocumentListener(Highlight.this);

        StyleConstants.setBackground(highlightLineStyle, Color.pink);

        defaultStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(defaultStyle, Color.black);

        keywordStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(keywordStyle, new Color(127, 0, 85));

        commentStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(commentStyle, new Color(127, 0, 85));

        dataStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(dataStyle, new Color(127, 0, 85));

        integerConstantStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(integerConstantStyle, new Color(17, 102, 68));

        longConstantStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(longConstantStyle, new Color(17, 102, 68));

        realConstantStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(realConstantStyle, new Color(17, 102, 68));

        stringConstantStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(stringConstantStyle, Color.red);

        integerIdentifierStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(integerIdentifierStyle, Color.blue);

        longIdentifierStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(longIdentifierStyle, Color.blue);

        stringIdentifierStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(stringIdentifierStyle, Color.blue);
    }

    private void Highlight(int highlightStart, int highlightEnd, boolean makeVisible) {
        if (lastHighlightStart != -1 && lastHighlightEnd != -1) {
            SyntaxHighlight(lastHighlightStart, lastHighlightEnd - lastHighlightStart);
        }
        lastHighlightStart = highlightStart;
        lastHighlightEnd = highlightEnd;
        if (makeVisible) {
            editor.setCaretPosition(highlightStart);
        }
    }

    void HighlightEOL(int startOffset, boolean makeVisible) {
        String text = GetSourceCode();
        int endOfLine = startOffset;
        if (endOfLine < text.length() && text.charAt(endOfLine) != '\n') {
            for (; endOfLine < text.length() && text.charAt(endOfLine) != '\n'; endOfLine++);
        }
        Highlight(startOffset, endOfLine, makeVisible);
        highlightOffset = startOffset;
    }

    void HighlightLine(int offset) {
        SyntaxHighlight(-1, -1);
        HighlightLine(offset, offset, true);
        highlightOffset = offset;
        runHighlightFlag = true;
    }

    void error() {

        try {
            editor.getStyledDocument().setCharacterAttributes(start, end, highlightLineStyle, false);
            Rectangle viewRect = editor.modelToView(start);
            editor.scrollRectToVisible(viewRect);
            error = false;
        } catch (Exception ex) {

        }

    }

    private void HighlightLine(int startOfLine, int endOfLine, boolean makeVisible) {
        String text = GetSourceCode();
        if (startOfLine > 0 && text.charAt(startOfLine) != '\n') {
            for (; startOfLine > 0 && text.charAt(startOfLine - 1) != '\n'; startOfLine--);
        }
        if (endOfLine < text.length() && text.charAt(endOfLine) != '\n') {
            for (; endOfLine < text.length() && text.charAt(endOfLine) != '\n'; endOfLine++);
        }
        Highlight(startOfLine, endOfLine, makeVisible);
    }

    public void run() {
        synchronized (this) {
            if (runHighlightFlag) {
                if (editor.isEnabled()) {
                    SyntaxHighlight(syntaxHighlightBegin, syntaxHighlightEnd - syntaxHighlightBegin);
                    if (highlightOffset != -1) {
                        HighlightLine(highlightOffset, highlightOffset, false);
                        highlightOffset = -1;
                    }
                    runHighlightFlag = false;
                } else {
                    try {
                        Thread.sleep(10L);
                    } catch (InterruptedException ex) {
                    }
                    SwingUtilities.invokeLater(this);
                }
            }
        }
        if (error) {
            error = false;
            error();
        }
    }

    String GetSourceCode() {
        StyledDocument doc = (StyledDocument) editor.getDocument();
        String text;
        try {
            int len = doc.getLength();
            text = doc.getText(0, len);
        } catch (BadLocationException e) {
            text = editor.getText();
        }
        return text;
    }

    private int FindStartOfLine(String text, int offset) {
        if (text != null) {
            if (offset > text.length()) {
                offset = text.length();
            }
            for (; offset > 0 && text.charAt(offset - 1) != '\n'; offset--);
        }
        return offset;
    }

    private int FindEndOfLine(String text, int offset) {
        if (text != null) {
            if (offset < text.length()) {
                for (; offset < text.length() && text.charAt(offset) != '\n'; offset++);
            } else {
                offset = text.length();
            }
        }
        return offset;
    }

    private void SyntaxHighlight(int startOffset, int length) {
        StyledDocument doc = (StyledDocument) editor.getDocument();
        if (SyntaxHighlightingEnabled) {

            int endOffset = startOffset + length;
            String text;
            try {
                int len = doc.getLength();
                text = doc.getText(0, len);
            } catch (BadLocationException e) {
                text = editor.getText();
            }
            int startOfLineOffset;
            int endOfLineOffset;
            if (startOffset == -1) {
                startOfLineOffset = 0;
                endOfLineOffset = text.length() - 1;
            } else {
                startOfLineOffset = FindStartOfLine(text, startOffset);
                endOfLineOffset = FindEndOfLine(text, endOffset);
            }
            Scanner scanner = new Scanner(text.substring(startOfLineOffset, endOfLineOffset));
            Token token = new Token();
            boolean replace = true;
            int offset = startOfLineOffset;
            while (scanner.GetToken(token)) {
                switch (token.tokenType) {
                    default:
                        break;

                    case -1:
                        doc.setCharacterAttributes(offset + token.tokenStart, token.tokenEnd - token.tokenStart, defaultStyle, replace);
                        break;

                    case 0:
                        doc.setCharacterAttributes(offset + token.tokenStart, token.tokenEnd - token.tokenStart, defaultStyle, replace);
                        break;

                    case 1:
                        doc.setCharacterAttributes(offset + token.tokenStart, token.tokenEnd - token.tokenStart, defaultStyle, replace);
                        break;

                    case 2:
                        String tokenText = text.substring(offset + token.tokenStart, offset + token.tokenEnd);
                        if (tokenText.endsWith("%")) {
                            doc.setCharacterAttributes(offset + token.tokenStart, token.tokenEnd - token.tokenStart, integerIdentifierStyle, replace);
                            break;
                        }
                        if (tokenText.endsWith("#")) {
                            doc.setCharacterAttributes(offset + token.tokenStart, token.tokenEnd - token.tokenStart, longIdentifierStyle, replace);
                            break;
                        }
                        if (tokenText.endsWith("$")) {
                            doc.setCharacterAttributes(offset + token.tokenStart, token.tokenEnd - token.tokenStart, stringIdentifierStyle, replace);
                        } else {
                            doc.setCharacterAttributes(offset + token.tokenStart, token.tokenEnd - token.tokenStart, keywordStyle, replace);
                        }
                        break;

                    case 3:
                        doc.setCharacterAttributes(offset + token.tokenStart, token.tokenEnd - token.tokenStart, integerConstantStyle, replace);
                        break;

                    case 4:
                        doc.setCharacterAttributes(offset + token.tokenStart, token.tokenEnd - token.tokenStart, longConstantStyle, replace);
                        break;

                    case 5:
                        doc.setCharacterAttributes(offset + token.tokenStart, token.tokenEnd - token.tokenStart, realConstantStyle, replace);
                        break;

                    case 6:
                        doc.setCharacterAttributes(offset + token.tokenStart, token.tokenEnd - token.tokenStart, stringConstantStyle, replace);
                        break;

                    case 7:
                        doc.setCharacterAttributes(offset + token.tokenStart, token.tokenEnd - token.tokenStart, defaultStyle, replace);
                        break;

                    case 8:
                        doc.setCharacterAttributes(offset + token.tokenStart, token.tokenEnd - token.tokenStart, commentStyle, replace);
                        break;

                    case 9:
                        doc.setCharacterAttributes(offset + token.tokenStart, token.tokenEnd - token.tokenStart, dataStyle, replace);
                        break;
                }
            }
        } else {
            doc.setCharacterAttributes(0, doc.getLength(), defaultStyle, true);
        }

    }

    public void insertUpdate(DocumentEvent e) {

        synchronized (this) {
            if (runHighlightFlag) {
                int begin = e.getOffset();
                int end = begin + e.getLength();
                if (begin < syntaxHighlightBegin) {
                    syntaxHighlightBegin = begin;
                }
                if (end > syntaxHighlightEnd) {
                    syntaxHighlightEnd = end;
                }
            } else {
                syntaxHighlightBegin = e.getOffset();
                syntaxHighlightEnd = syntaxHighlightBegin + e.getLength();
                runHighlightFlag = true;
            }
        }
        SwingUtilities.invokeLater(this);

    }

    public void removeUpdate(DocumentEvent e) {
        synchronized (this) {
            if (runHighlightFlag) {
                int begin = e.getOffset();
                int end = begin + e.getLength();
                if (begin < syntaxHighlightBegin) {
                    syntaxHighlightBegin = begin;
                }
                if (end > syntaxHighlightEnd) {
                    syntaxHighlightEnd = end;
                }
            } else {
                syntaxHighlightBegin = e.getOffset();
                syntaxHighlightEnd = e.getOffset();
                runHighlightFlag = true;
            }
        }
        SwingUtilities.invokeLater(this);
    }

    public void changedUpdate(DocumentEvent documentevent) {
    }

}
