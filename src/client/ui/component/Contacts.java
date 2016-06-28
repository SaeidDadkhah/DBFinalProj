package client.ui.component;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 * Created by Saeid Dadkhah on 2016-06-20 9:38 AM.
 * Project: Server
 */
public class Contacts extends JTree {

    public Contacts(DefaultMutableTreeNode root) {
        super(root);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

}
