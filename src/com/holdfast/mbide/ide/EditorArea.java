package com.holdfast.mbide.ide;

import com.holdfast.mbide.form.IDE;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author HoldFast
 */
public class EditorArea extends JScrollPane {

    public JTextPane area;
    private String document = "";
    private boolean modified = false;
    private boolean set = false;
    private boolean undoredo = false;
    private boolean findline = false;
    private UndoRedo undoManager;
    private final SimpleAttributeSet highlightLineStyle = new SimpleAttributeSet();
    private final SimpleAttributeSet defaultHighlightLineStyle = new SimpleAttributeSet();
    private int offset = 0;
    private final Highlight highlight;

    public EditorArea(final JTextPane area, final JTabbedPane tab, Font font) {
        super(area);
        this.area = area;
        this.undoManager = new UndoRedo(EditorArea.this);
        StyleConstants.setBackground(highlightLineStyle, Color.pink);
        StyleConstants.setBackground(defaultHighlightLineStyle, Color.white);
        area.setEnabled(true);
        area.setFont(font);
        setBorder(null);

        TextLineNumber tln = new TextLineNumber(area);
        tln.setUpdateFont(true);
        setRowHeaderView(tln);

        highlight = new Highlight(area);
        area.getDocument().addDocumentListener(new DocumentListener() {
            StyledDocument doc = (StyledDocument) area.getDocument();

            public void insertUpdate(DocumentEvent e) {
                if (!undoredo) {
                    undoManager.add(getText(), e.getOffset());
                }
                if (findline) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            doc.setCharacterAttributes(0, doc.getLength(), defaultHighlightLineStyle, false);
                        }
                    });
                    findline = false;
                }
                if (!set && !modified) {

                    modified = true;
                    ((JLabel) ((JPanel) tab.getTabComponentAt(tab.getSelectedIndex())).getComponent(0)).setFont(new java.awt.Font("Tahoma", 1, 11));
                    IDE.jMenuItem17.setEnabled(true);
                    IDE.jMenuItem3.setEnabled(true);
                    IDE.jButton3.setEnabled(true);
                    IDE.numModified++;
                }
            }

            public void removeUpdate(DocumentEvent e) {
                if (!undoredo) {
                    undoManager.add(getText(), e.getOffset());
                }
                if (findline) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            doc.setCharacterAttributes(0, doc.getLength(), defaultHighlightLineStyle, false);
                        }
                    });
                    findline = false;
                }
                if (!set && !modified) {
                    modified = true;
                    ((JLabel) ((JPanel) tab.getTabComponentAt(tab.getSelectedIndex())).getComponent(0)).setFont(new java.awt.Font("Tahoma", 1, 11));
                    IDE.jMenuItem17.setEnabled(true);
                    IDE.jMenuItem3.setEnabled(true);
                    IDE.jButton3.setEnabled(true);
                    IDE.numModified++;
                }
            }

            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    public void undo() {
        if (undoManager.canUndo()) {
            undoManager.undo();
        }
    }

    public void redo() {
        if (undoManager.canRedo()) {
            undoManager.redo();

        }
    }

    public void active() {
        area.setEnabled(true);
    }

    public void unactive() {
        area.setEnabled(false);
    }

    public void setText(String text, boolean s) {
        set = s;
        area.setText(text);
        area.setSelectionStart(0);
        area.setSelectionEnd(0);
        set = false;

    }

    public void insertUndo(String text, boolean s) {
        undoredo = true;
        area.setText(text);
        area.setSelectionStart(0);
        area.setSelectionEnd(0);
        undoredo = false;
    }

    static int[] getLineStartOffset(JTextPane comp, int line) throws BadLocationException {
        Element map = comp.getDocument().getDefaultRootElement();
        if (line < 0) {
            throw new BadLocationException("Negative line", -1);
        } else if (line >= map.getElementCount()) {
            throw new BadLocationException("No such line", comp.getDocument().getLength() + 1);
        } else {
            Element lineElem = map.getElement(line);
            return (new int[]{lineElem.getStartOffset(), lineElem.getEndOffset()});
        }
    }

    public void error(int line) {
        try {
            findline = true;
            int[] off = getLineStartOffset(area, line);
            highlight.error = true;
            highlight.start = off[0];
            highlight.end = off[1] - off[0];
            highlight.error();
        } catch (Exception ex) {
        }
    }

    public void find(String text, boolean next) {
        StyledDocument doc = (StyledDocument) area.getDocument();
        text = text.trim().toUpperCase();
        String script = getText().trim().toUpperCase();
        if (!next) {
            doc.setCharacterAttributes(0, script.length(), defaultHighlightLineStyle, false);
            offset = 0;
        }
        try {
            if (script.substring(offset).contains(text)) {
                doc.setCharacterAttributes(offset - text.length(), offset, defaultHighlightLineStyle, false);
                offset = script.substring(offset).indexOf(text) + offset + text.length();
                doc.setCharacterAttributes(offset - text.length(), offset, highlightLineStyle, false);
                doc.setCharacterAttributes(offset, script.length(), defaultHighlightLineStyle, false);
                setAutoscrolls(true);
                Rectangle viewRect = area.modelToView(offset);
                area.scrollRectToVisible(viewRect);
                findline = true;

            } else {
                if (offset != 0) {
                    find(text, false);
                }
            }
        } catch (BadLocationException e) {
            offset = 0;
        }
    }

    public void endModified() {
        modified = false;
    }

    public void setDocName(String name) {
        document = name;
    }

    public boolean isModified() {
        return modified;
    }

    public String getDocName() {
        return document;
    }

    public String getText() {
        StyledDocument doc = (StyledDocument) area.getDocument();
        String text;
        try {
            int len = doc.getLength();
            text = doc.getText(0, len);
        } catch (BadLocationException e) {
            text = area.getText();
        }
        return text;
    }

    public void setEditable(boolean editable) {
        area.setEditable(editable);
    }
}
