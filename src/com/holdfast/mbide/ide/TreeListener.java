package com.holdfast.mbide.ide;

/**
 *
 * @author HoldFast
 */
public interface TreeListener {

    public void treeSourceSelect(String name);

    public void treeResourceSelect(String name);

    public void treeSourcePopupClick(int index, String name, int id);

    public void treeResourcePopupClick(int index, String name, int id);
}
