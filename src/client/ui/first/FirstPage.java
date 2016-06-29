package client.ui.first;

import client.ui.component.PlaceHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Saeid Dadkhah on 2016-06-20 6:50 AM.
 * Project: Server
 */
public class FirstPage extends JFrame {

    private static final int FP_WIDTH = 350;
    private static final int FP_HEIGHT = 200;

    private JTextField tf_username;
    private JPasswordField tf_password;
    private JButton b_logIn;

    private FirstPageFetcher fetcher;

    public static void main(String[] args) {
        FirstPageFetcher fetcher = new FirstPageFetcher() {
            @Override
            public boolean signUp(String username, String password) {
                System.out.println("Sign up:\n\tUsername: " + username + "\n\tPassword: " + password);
                return false;
            }

            @Override
            public boolean logIn(String username, String password) {
                System.out.println("Log in:\n\tUsername: " + username + "\n\tPassword: " + password);
                return false;
            }

            @Override
            public void closing() {
                System.out.println("closing!");
            }
        };
        new FirstPage(fetcher);
    }

    public FirstPage(FirstPageFetcher fetcher) {
        this.fetcher = fetcher;
        init();
    }

    private void init() {
        Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(FP_WIDTH, FP_HEIGHT);
        setLocation((int) (ss.getWidth() - FP_WIDTH) / 2, (int) (ss.getHeight() - FP_HEIGHT) / 2);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                fetcher.closing();
            }
        });

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.1;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(new PlaceHolder(this), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        getContentPane().add(new JLabel("Username"), gbc);

        gbc.gridy = 2;
        getContentPane().add(new JLabel("Password"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.6;
        tf_username = new JTextField();
        tf_username.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == '\n')
                    b_logIn.doClick();
            }
        });
        getContentPane().add(tf_username, gbc);
        tf_username.requestFocusInWindow();

        gbc.gridy = 2;
        tf_password = new JPasswordField();
        tf_password.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == '\n')
                    b_logIn.doClick();
            }
        });
        getContentPane().add(tf_password, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.NONE;
        JButton b_signUp = new JButton("Sign up");
        b_signUp.addActionListener(e -> {
            if (fetcher.signUp(tf_username.getText(), new String(tf_password.getPassword()))) {
                JOptionPane.showMessageDialog(null,
                        "You have been registered successfully!",
                        "Successful Register",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Username is already in use!",
                        "Unsuccessful Register",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        getContentPane().add(b_signUp, gbc);

        gbc.gridx = 3;
        b_logIn = new JButton("Log in");
        b_logIn.addActionListener(e -> {
            if (!fetcher.logIn(tf_username.getText(), new String(tf_password.getPassword()))) {
                JOptionPane.showMessageDialog(null, "Username or password is not correct.!", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });
        getContentPane().add(b_logIn, gbc);

        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.1;
        getContentPane().add(new PlaceHolder(this), gbc);

        setVisible(true);
        tf_username.requestFocus();
    }

}
