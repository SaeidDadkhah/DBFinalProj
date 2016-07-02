package client.ui.main.input;

import client.ui.component.PlaceHolder;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Saeid Dadkhah on 2016-07-01 8:32 PM.
 * Project: DBFinalProject
 */
public class BaseInput extends JDialog {

    // T stands for type
    public static final int T_GROUP = 0;
    public static final int T_CHANNEL = 1;

    private InputFetcher fetcher;

    private JTextField tf_name;

    private int type;

    public static void main(String[] args) {
        new BaseInput(new InputFetcher() {
            @Override
            public boolean newGroup(String name) {
                System.out.println("Add group: " + name);
                return false;
            }

            @Override
            public boolean newChannel(String name) {
                System.out.println("Add channel: " + name);
                return false;
            }
        }, 0);
    }

    public BaseInput(InputFetcher fetcher, int type) {
        this.fetcher = fetcher;
        this.type = type;
        init();
    }

    private void init() {
        Dimension ss = getToolkit().getScreenSize();
        setSize(5 * (int) ss.getWidth() / 11, 3 * (int) ss.getHeight() / 13);
        setLocation(3 * (int) ss.getWidth() / 11, 5 * (int) ss.getHeight() / 13);
        setModal(true);

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.top = 4;
        gbc.insets.right = 4;
        gbc.insets.left = 4;
        gbc.insets.bottom = 4;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        getContentPane().add(new PlaceHolder(this), gbc);

        gbc.gridx = 4;
        gbc.gridy = 2;
        getContentPane().add(new PlaceHolder(this), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        getContentPane().add(new JLabel("Please insert " + getString() + " name"), gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.8;
        tf_name = new JTextField();
        getContentPane().add(tf_name, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0;
        JButton b_add = new JButton("Add");
        b_add.addActionListener(e -> {
            switch (type) {
                case T_GROUP:
                    if (fetcher.newGroup(tf_name.getText()))
                        dispose();
                    else
                        JOptionPane.showMessageDialog(null, "Group name is already in use.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                case T_CHANNEL:
                    if (fetcher.newChannel(tf_name.getText()))
                        dispose();
                    else
                        JOptionPane.showMessageDialog(null, "Channel name is already in use.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                default:
                    System.err.println("Type is not valid.");
            }
        });
        getContentPane().add(b_add, gbc);

        setVisible(true);
    }

    private String getString() {
        switch (type) {
            case T_GROUP:
                return "Group";
            case T_CHANNEL:
                return "Channel";
            default:
                return null;
        }
    }

}
