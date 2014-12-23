package com.holdfast.mbide.ide;

import com.holdfast.mbide.form.IDE;
import com.holdfast.mbide.util.FileDrop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author HoldFast
 */
public class Tree {

    public static TreeListener listener;
    private static TreeMouseListener mouse;
    private final TreeSelection change = new TreeSelection();
    private static JTree tree;
    private static JTabbedPane tab;
    private static DefaultMutableTreeNode source;
    private static DefaultMutableTreeNode res;
    private static DefaultMutableTreeNode root;
    private static ImageIcon lis, attach, project, isource, resource, del;
    private static javax.swing.JPopupMenu popup, sourcepopup, resourcepopup;
    private final SourcePopup splistener = new SourcePopup();
    private final ResourcePopup rplistener = new ResourcePopup();
    private boolean delsource = false;
    private static final String[] item = {"Исходники", "Ресурсы"};
    private static String name = "";
    private int id;

    private class SourcePopup implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                for (int i = 0; sourcepopup.getComponentCount() > i; i++) {
                    if (((JMenuItem) sourcepopup.getComponent(i)).equals(e.getSource())) {
                        listener.treeSourcePopupClick(i, name, id);
                        break;
                    }
                }
            } catch (Exception ee) {
            }
        }
    }

    private class deleteDown implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (delsource) {
                listener.treeSourcePopupClick(0, name, id);
            } else {
                listener.treeResourcePopupClick(0, name, id);
            }
        }
    }

    private class ResourcePopup implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                for (int i = 0; resourcepopup.getComponentCount() > i; i++) {
                    if (((JMenuItem) resourcepopup.getComponent(i)).equals(e.getSource())) {
                        listener.treeResourcePopupClick(i, name, id);
                        break;
                    }
                }
            } catch (Exception ee) {
            }
        }
    }

    private class TreeSelection implements TreeSelectionListener {

        public void valueChanged(TreeSelectionEvent e) {
            TreePath path = e.getPath();
            if (path != null) {

                try {
                    IDE.delete.setEnabled(true);
                    if (source.getPath()[1].equals(path.getParentPath().getLastPathComponent()) && !path.getLastPathComponent().toString().equals("Autorun.lis")) {
                        delsource = true;
                        name = path.getLastPathComponent().toString();
                        id = tree.getSelectionModel().getSelectionRows()[0] - 2;
                        IDE.delete.setText("Удалить " + Tree.tree.getSelectionPath().getLastPathComponent().toString());

                    } else if (res.getPath()[1].equals(path.getParentPath().getLastPathComponent())) {
                        delsource = false;
                        name = path.getLastPathComponent().toString();
                        int shift = (tree.isExpanded(1)) ? source.getChildCount() : 0;
                        id = tree.getSelectionModel().getSelectionRows()[0] - shift - 3;
                        IDE.delete.setText("Удалить " + Tree.tree.getSelectionPath().getLastPathComponent().toString());
                        IDE.delete.removeAll();

                    } else {
                        IDE.delete.setText("Удалить");
                        IDE.delete.setEnabled(false);
                    }
                } catch (Exception ee) {
                    IDE.delete.setEnabled(false);
                }
            }
        }

    }

    private class TreeMouseListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            int selRow = Tree.tree.getRowForLocation(e.getX(), e.getY());
            TreePath selPath = Tree.tree.getPathForLocation(e.getX(), e.getY());
            if (selRow != -1) {
                if (e.getClickCount() == 2) {
                    try {
                        if (selPath.getParentPath().getLastPathComponent().toString().equals(item[0])) {
                            listener.treeSourceSelect(Tree.tree.getSelectionPath().getLastPathComponent().toString());
                        } else if (selPath.getParentPath().getLastPathComponent().toString().equals(item[1])) {
                            listener.treeResourceSelect(Tree.tree.getSelectionPath().getLastPathComponent().toString());
                        }
                    } catch (Exception ex) {
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            /* add DELETE item text here */
            int x = e.getX();
            int y = e.getY();
            TreePath path = Tree.tree.getPathForLocation(x, y);

            if (e.isPopupTrigger()) {

                if (path != null) {
                    try {
                        if (source.getPath()[1].equals(path.getParentPath().getLastPathComponent()) && !path.getLastPathComponent().toString().equals("Autorun.lis")) {
                            ((JMenuItem) sourcepopup.getComponent(0)).setText("Удалить " + path.getLastPathComponent().toString());

                            Tree.tree.setSelectionPath(path);
                            sourcepopup.show((JComponent) e.getSource(), x, y);
                            name = path.getLastPathComponent().toString();
                            id = tree.getSelectionModel().getSelectionRows()[0] - 2;
                            /*
                             * popup source
                             */
                        } else if (res.getPath()[1].equals(path.getParentPath().getLastPathComponent())) {
                            ((JMenuItem) resourcepopup.getComponent(0)).setText("Удалить " + path.getLastPathComponent().toString());

                            Tree.tree.setSelectionPath(path);
                            resourcepopup.show((JComponent) e.getSource(), x, y);
                            name = path.getLastPathComponent().toString();
                            int shift = (tree.isExpanded(1)) ? source.getChildCount() : 0;
                            id = tree.getSelectionModel().getSelectionRows()[0] - shift - 3;
                            /*
                             * popup resource
                             */
                        } else if (!path.getLastPathComponent().toString().equals("Autorun.lis")) {
                            popup.show(Tree.tree, x, y);
                        }
                    } catch (Exception ex) {
                    }

                } else {
                    popup.show(Tree.tree, x, y);
                }
            } else {

            }
        }
    }

    public Tree(JTree tree, JTabbedPane tab) {
        Tree.tree = tree;
        Tree.tab = tab;

        mouse = new TreeMouseListener();
        lis = createImageIcon("/res/icon/lis.png");
        del = createImageIcon("/res/icon/delete.png");
        attach = createImageIcon("/res/icon/attach.png");
        project = createImageIcon("/res/icon/project.png");
        isource = createImageIcon("/res/icon/source.png");
        resource = createImageIcon("/res/icon/resource.png");
        IDE.delete.addActionListener(new deleteDown());

        popup = new javax.swing.JPopupMenu();
        sourcepopup = new javax.swing.JPopupMenu();
        resourcepopup = new javax.swing.JPopupMenu();

        sourcepopup.add("");
        resourcepopup.add("");
        sourcepopup.add("Переименовать");
        resourcepopup.add("Переименовать");

        for (int i = 0; sourcepopup.getComponentCount() > i; i++) {
            ((JMenuItem) sourcepopup.getComponent(i)).addActionListener(splistener);
        }
        for (int i = 0; resourcepopup.getComponentCount() > i; i++) {
            ((JMenuItem) resourcepopup.getComponent(i)).addActionListener(rplistener);
        }
        JMenuItem addSource = new JMenuItem();
        JMenuItem addResource = new JMenuItem();
        addSource.setIcon(lis);
        addResource.setIcon(attach);
        addSource.setText("Добавить исходник");
        addResource.setText("Добавить ресурсы");
        popup.add(addSource);
        popup.add(addResource);

        addSource.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IDE.project.newSource();
            }
        });

        addResource.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IDE.project.newResource();
            }
        });

        TreeIcon treeicon = new TreeIcon(lis, attach, project, isource, resource);
        Tree.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        root = new DefaultMutableTreeNode("Проект");
        source = new DefaultMutableTreeNode(item[0]);
        res = new DefaultMutableTreeNode(item[1]);
        root.add(source);
        root.add(res);
        javax.swing.tree.DefaultTreeModel model = new javax.swing.tree.DefaultTreeModel(root, true);
        Tree.tree.setModel(model);
        Tree.tree.setCellRenderer(treeicon);

        FileDrop fileDrop = new FileDrop(Tree.tree, new FileDrop.Listener() {
            public void filesDropped(java.io.File[] files) {
                if (Tree.tree.isEnabled()) {
                    try {
                        if (files != null) {
                            for (File file : files) {
                                File src = new File(IDE.project.getProjectDir() + "res" + File.separator + file.getName());
                                try {
                                    ///if (!Files.exists(Paths.get(getProjectDir() + "res"), LinkOption.NOFOLLOW_LINKS)) {
                                    if (!new File(IDE.project.getProjectDir() + "res").exists()) {
                                        new File(IDE.project.getProjectDir() + "res").mkdir();
                                    }
                                    boolean add = false;
                                    for (String end : Project.format) {
                                        if (file.getName().toLowerCase().endsWith(end)) {
                                            add = true;
                                        }
                                    }
                                    if (add) {
                                        Project.copy(file, src);
                                        IDE.project.addResource(file.getName());
                                    }
                                } catch (IOException ex) {
                                }
                            }
                        }
                        IDE.project.getPNGFile(null);
                        IDE.tree.update(true);
                        IDE.project.saveProjectFile();
                    } catch (Exception ex) {
                    }
                }
            }
        });

    }

    public void reinit() {
        source = new DefaultMutableTreeNode(item[0]);
        res = new DefaultMutableTreeNode(item[1]);
        root.add(source);
        root.add(res);
    }

    public void setName(String name) {
        root = new DefaultMutableTreeNode(name);
        root.add(source);
        root.add(res);

    }

    private static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Tree.class
                .getResource(path);
        if (imgURL
                != null) {
            return new ImageIcon(imgURL);
        } else {
            return null;
        }
    }

    public void popup(boolean vis) {
        if (vis) {
            tree.addMouseListener(mouse);
            tree.addTreeSelectionListener(change);
        } else {
            tree.removeMouseListener(mouse);
        }
    }

    public void enable() {
        tree.setEnabled(true);
        tab.setEnabled(true);
        popup(true);
    }

    public void disable() {
        tree.setEnabled(false);
        tab.setEnabled(false);
        popup(false);
    }

    public void update(boolean expandAll) {
        boolean expandSource = expandAll;
        boolean expandResource = expandAll;
        tree.updateUI();
        SwingUtilities.updateComponentTreeUI(popup);
        if (tree.isExpanded(1)) {
            expandSource = true;

            if (tree.isExpanded(source.getChildCount() + 2)) {
                expandResource = true;
            }
        } else if (tree.isExpanded(2)) {
            expandResource = true;
        }
        tree.setModel(new javax.swing.tree.DefaultTreeModel(root, true));

        if (expandSource) {
            tree.expandRow(1);
        }
        if (expandResource) {
            if (tree.isExpanded(1)) {
                tree.expandRow(source.getChildCount() + 2);
            } else {
                tree.expandRow(2);
            }
        }
    }

    public String[] getListSource() {
        String[] list = new String[source.getChildCount()];
        for (int i = 0; i < source.getChildCount(); i++) {
            list[i] = source.getChildAt(i).toString();
        }
        return list;
    }

    public String[] getListResource() {
        String[] list = new String[res.getChildCount()];
        for (int i = 0; i < res.getChildCount(); i++) {
            list[i] = res.getChildAt(i).toString();
        }
        return list;
    }

    public void addTreeListener(TreeListener listener) {
        Tree.listener = listener;
    }

    public void addSource(String name) {
        source.add(new DefaultMutableTreeNode(name, false));
    }

    public void addResource(String name) {
        res.add(new DefaultMutableTreeNode(name, false));
    }

    public void delSource(String name) {
        for (int i = 0; i < source.getChildCount(); i++) {
            if (name.equals(source.getChildAt(i).toString())) {
                source.remove(i);
            }
        }
    }

    public void delResource(String name) {
        for (int i = 0; i < res.getChildCount(); i++) {
            if (name.equals(res.getChildAt(i).toString())) {
                res.remove(i);
            }
        }
    }

    public void sourceRename(int id, String name) {
        id += 2;
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getPathForRow(id).getLastPathComponent();
        node.setUserObject(name);
    }

    public void resourceRename(int id, String name) {
        if (tree.isExpanded(1)) {
            id += source.getChildCount();
        }
        id += 3;
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getPathForRow(id).getLastPathComponent();
        node.setUserObject(name);

    }
}
