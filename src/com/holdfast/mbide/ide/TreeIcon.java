package com.holdfast.mbide.ide;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author HoldFast
 */
public class TreeIcon extends DefaultTreeCellRenderer {

    Icon sourceIcon, projectIcon, lisFolder, resFolder, resourceIcon;
    String[] items;

    public TreeIcon(Icon sicon, Icon reicon, Icon picon, Icon licon, Icon ricon) {
        sourceIcon = sicon;
        resourceIcon = reicon;
        projectIcon = picon;
        lisFolder = licon;
        resFolder = ricon;
    }

    @Override
    public Component getTreeCellRendererComponent(
            JTree tree,
            Object value,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {

        super.getTreeCellRendererComponent(
                tree, value, sel,
                expanded, leaf, row,
                hasFocus);
        if (!leaf) {
            if (row == 0) {
                setIcon(projectIcon);
            } else if (is(value, "Исходники")) {
                setIcon(lisFolder);
            } else if (is(value, "Ресурсы")) {
                setIcon(resFolder);
            }
        } else {
            if (end(value, ".lis")) {
                setIcon(sourceIcon);
            } else {
                setIcon(resourceIcon);
            }
        }
        return this;
    }

    protected boolean is(Object value, String string) {
        DefaultMutableTreeNode node
                = (DefaultMutableTreeNode) value;
        return node.toString().equals(string);
    }

    protected boolean end(Object value, String string) {
        DefaultMutableTreeNode node
                = (DefaultMutableTreeNode) value;
        return node.toString().endsWith(string);
    }
}
