package client.ui.main;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

/**
 * Created by Saeid Dadkhah on 2016-06-20 6:14 AM.
 * Project: Server
 */
public class MainWindow extends JFrame {

    public static void main(String[] args) {
        new MainWindow();
    }

    public MainWindow() {
        init();
    }

    private void init() {
        Dimension ss = getToolkit().getScreenSize();
        setSize(5 * (int) ss.getWidth() / 7, 5 * (int) ss.getHeight() / 7);
        setLocation((int) ss.getWidth() / 7, (int) ss.getHeight() / 7);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // // Window
        JMenu m_window = new JMenu("Window");
        menuBar.add(m_window);

        JMenuItem mi_profile = new JMenuItem("Profile");
        m_window.add(mi_profile);

        JMenuItem mi_search = new JMenuItem("Search");
        m_window.add(mi_search);

        m_window.addSeparator();

        JMenuItem mi_exit = new JMenuItem("Exit");
        mi_exit.addActionListener(e -> dispose());
        m_window.add(mi_exit);

        // // Friend
        JMenu m_friend = new JMenu("Friend");
        menuBar.add(m_friend);

        JMenuItem mi_friendProfile = new JMenuItem("Profile");
        m_friend.add(mi_friendProfile);

        JMenuItem mi_privateChat = new JMenuItem("Private Chat");
        m_friend.add(mi_privateChat);

        JMenuItem mi_report = new JMenuItem("Report");
        m_friend.add(mi_report);

        JMenuItem mi_unfriend = new JMenuItem("Unfriend");
        m_friend.add(mi_unfriend);

        // Content Pane
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.top = 4;
        gbc.insets.left = 4;
        gbc.insets.right = 4;
        gbc.insets.bottom = 4;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        getContentPane().add(new JLabel("USERNAME"), gbc);

        gbc.gridy = 1;
        gbc.weighty = 1;
        getContentPane().add(new JTextArea(), gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weighty = 0;
        JTextField tf_message = new JTextField();
        getContentPane().add(tf_message, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        getContentPane().add(new JButton("Send"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        getContentPane().add(new JLabel("Destruction Time"), gbc);

        gbc.gridx = 1;
        gbc.ipadx = 30;
        getContentPane().add(new JTextField(), gbc);

        gbc.gridx = 3;
        gbc.ipadx = 0;
        getContentPane().add(new JButton("Refresh"), gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 4;
        gbc.weighty = 1;

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Chats");
        root.add(new DefaultMutableTreeNode("Friends"));
        root.add(new DefaultMutableTreeNode("Groups"));
        Contacts contacts = new Contacts(root);
        getContentPane().add(contacts, gbc);

        setVisible(true);
        tf_message.requestFocus();
    }

}
