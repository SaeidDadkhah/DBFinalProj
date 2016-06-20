package ui.first;

import ui.component.PlaceHolder;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Saeid Dadkhah on 2016-06-20 6:50 AM.
 * Project: DBFinalProject
 */

public class FirstPage extends JFrame {

    private static final int FP_WIDTH = 350;
    private static final int FP_HEIGHT = 200;

    public static void main(String[] args) {
        new FirstPage();
    }

    public FirstPage() {
        init();
    }

    private void init() {
        Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(FP_WIDTH, FP_HEIGHT);
        setLocation((int) (ss.getWidth() - FP_WIDTH) / 2, (int) (ss.getHeight() - FP_HEIGHT) / 2);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.1;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(new PlaceHolder(), gbc);

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
        getContentPane().add(new TextField(), gbc);

        gbc.gridy = 2;
        getContentPane().add(new TextField(), gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.NONE;
        getContentPane().add(new JButton("Sign up"), gbc);

        gbc.gridx = 3;
        getContentPane().add(new JButton("Log in"), gbc);

        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.1;
        getContentPane().add(new PlaceHolder(), gbc);

        setVisible(true);
    }

}
