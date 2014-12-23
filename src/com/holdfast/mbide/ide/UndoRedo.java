package com.holdfast.mbide.ide;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HoldFast
 */
public class UndoRedo {

    private final List data = new ArrayList();
    private final List pos = new ArrayList();
    private int position = 0;
    private final EditorArea area;

    UndoRedo(final EditorArea area) {
        this.area = area;
    }

    public void add(String text, int offset) {
        if (position <= 0) {
            data.clear();
            pos.clear();
        }
        position++;
        data.add(text);
        pos.add(offset);
    }

    public void link(int pos, int off) {
        area.area.setSelectionStart((Integer) this.pos.get(pos) + off);
    }

    public void undo() {

        area.insertUndo((String) data.get(position - 1), true);
        link(position - 1, 1);
        position--;

    }

    public void redo() {

        area.insertUndo((String) data.get(position + 1), true);
        link(position + 1, 1);
        position++;
    }

    public boolean canUndo() {
        return (position >= 1);
    }

    public boolean canRedo() {
        return (position < data.size() - 1);
    }

}
