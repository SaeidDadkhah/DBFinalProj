package client.ui.main;

import client.ui.component.Contacts;
import client.ui.main.profile.ProfilePage;
import client.ui.main.profile.ProfilePageFetcher;
import common.Constants;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Saeid Dadkhah on 2016-06-20 6:14 AM.
 * Project: Server
 */
public class MainPage extends JFrame {

    private JLabel l_username;
    private JTextArea ta_messages;
    private JTextField tf_message;
    private JButton b_send;
    private Contacts contacts;

    private MainPageFetcher fetcher;

    public static void main(String[] args) {
        new MainPage(new MainPageFetcher() {

            @Override
            public boolean deleteAccount(String password) {
                System.out.println("delete account:\n\tpassword: " + password);
                return false;
            }

            @Override
            public void logout() {
                System.out.println("Log Out");
            }

            @Override
            public boolean send(String message) {
                System.out.println("\nmessage: " + message);
                return true;
            }

            @Override
            public boolean setCurrentContact(String contact) {
                System.out.println("Current contact: " + contact);
                return false;
            }

            @Override
            public ProfilePageFetcher getProfilePageFetcher() {
                return (name, birthday, email, biography, phone, password) -> System.out.println("name: "
                        + name
                        + "\nbirthday: "
                        + birthday
                        + "\nEMail"
                        + email
                        + "\nbiography: "
                        + biography
                        + "\nphone: "
                        + phone
                        + "\npassword: "
                        + password);
            }
        });
    }

    public MainPage(MainPageFetcher fetcher) {
        this.fetcher = fetcher;
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

        JMenuItem mi_profile = new JMenuItem("Profile Page");
        mi_profile.addActionListener(e -> new ProfilePage(fetcher.getProfilePageFetcher(),
                "Saeid",
                "saeid",
                "1995.02.26",
                "saeid.dadkhah@live.com",
                "Computer Engineering student :)",
                "+989388835866",
                "noneOfUrBusiness",
                "files\\photo_2016-06-30_14-33-28.jpg"));
        m_window.add(mi_profile);

        JMenuItem mi_search = new JMenuItem("Search");
        m_window.add(mi_search);

        JMenuItem mi_deactive = new JMenuItem("Delete Account");
        mi_deactive.addActionListener(e -> {
            String password = JOptionPane.showInputDialog(null,
                    "Please enter your password to verify delete account.",
                    "Password",
                    JOptionPane.QUESTION_MESSAGE);
            fetcher.deleteAccount(password);
        });
        m_window.add(mi_deactive);

        m_window.addSeparator();

        JMenuItem mi_logout = new JMenuItem("Log Out");
        mi_logout.addActionListener(e -> fetcher.logout());
        m_window.add(mi_logout);

        JMenuItem mi_exit = new JMenuItem("Exit");
        mi_exit.addActionListener(e -> dispose());
        m_window.add(mi_exit);

        // // Friend
        JMenu m_friend = new JMenu("Friend");
        menuBar.add(m_friend);

        JMenuItem mi_friendProfile = new JMenuItem("Profile Page");
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
        l_username = new JLabel("Select a friend, group or channel to see messages and chat... ");
        getContentPane().add(l_username, gbc);

        gbc.gridy = 1;
        gbc.weighty = 1;
        ta_messages = new JTextArea();
        ta_messages.setEditable(false);
        getContentPane().add(ta_messages, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weighty = 0;
        tf_message = new JTextField();
        tf_message.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == '\n')
                    b_send.doClick();
            }
        });
        getContentPane().add(tf_message, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        b_send = new JButton("Send");
        b_send.addActionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) contacts.getLastSelectedPathComponent();
            if (tf_message.getText().length() > 0
                    && node != null) {
                if (fetcher.send(tf_message.getText())) {
                    ta_messages.append("me: " + tf_message.getText() + "\n");
                    tf_message.setText("");
                }

            }
        });
        getContentPane().add(b_send, gbc);

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
        root.add(new DefaultMutableTreeNode("Channels"));
        contacts = new Contacts(root);
        contacts.addTreeSelectionListener(e -> setCurrentContact());
        getContentPane().add(contacts, gbc);

        tf_message.setEditable(false);
        fetcher.setCurrentContact(null);

        setVisible(true);
        tf_message.requestFocus();
    }

    public void setContacts(String type, String[] names) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) contacts.getModel().getRoot();
        switch (type) {
            case Constants.F_FRIENDS:
                node = (DefaultMutableTreeNode) node.getChildAt(0);
                break;
            case Constants.F_GROUP_NAME:
                node = (DefaultMutableTreeNode) node.getChildAt(1);
                break;
            case Constants.F_CHANNEL_NAME:
                node = (DefaultMutableTreeNode) node.getChildAt(2);
                break;
            default:
                System.err.println("invalid type");
        }
        node.removeAllChildren();
        for (String friend : names) {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(friend);
            node.add(child);
        }
    }

    private void setCurrentContact() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) contacts.getLastSelectedPathComponent();
        if (node != null && node.isLeaf()) {
            l_username.setText((String) node.getUserObject());
            tf_message.setEditable(true);
            fetcher.setCurrentContact((String) node.getUserObject());
        }
    }

}
