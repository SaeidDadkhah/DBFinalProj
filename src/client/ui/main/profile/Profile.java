package client.ui.main.profile;

import client.ui.component.PlaceHolder;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Saeid Dadkhah on 2016-06-29 6:06 PM.
 * Project: DBFinalProject
 */
public class Profile extends JDialog {

    private String name;
    private String username;
    private String birthday;
    private String email;
    private String biography;
    private String phone;
    private String password;
    private String profilePictureAddress;

    public static void main(String[] args) {
        new Profile("Saeid",
                "saeid",
                "1995.02.26",
                "saeid.dadkhah@live.com",
                "Computer Engineering student :)",
                "+989388835866",
                "noneOfUrBusiness",
                "nowhere");
    }

    public Profile(String name,
                   String username,
                   String birthday,
                   String email,
                   String biography,
                   String phone,
                   String password,
                   String profilePictureAddress) {
        this.name = name;
        this.username = username;
        this.birthday = birthday;
        this.email = email;
        this.biography = biography;
        this.phone = phone;
        this.password = password;
        this.profilePictureAddress = profilePictureAddress;
        init();
    }

    private void init() {
        Dimension ss = getToolkit().getScreenSize();
        setSize(3 * (int) ss.getWidth() / 7, 3 * (int) ss.getHeight() / 7);
        setLocation(2 * (int) ss.getWidth() / 7, 2 * (int) ss.getHeight() / 7);
        setModal(true);

        setTitle(name + "'s profile");

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.top = 4;
        gbc.insets.left = 4;
        gbc.insets.right = 4;
        gbc.insets.bottom = 4;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        getContentPane().add(new PlaceHolder(this), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 8;
        gbc.weightx = 0.4;
        gbc.weighty = 1;
        JButton l_profilePicture = new JButton("a");
        l_profilePicture.setBackground(Color.black);
        l_profilePicture.setForeground(Color.black);
        getContentPane().add(l_profilePicture, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        getContentPane().add(new JLabel("Name"), gbc);

        gbc.gridx++;
        getContentPane().add(new JLabel(name), gbc);
        gbc.gridx--;

        gbc.gridy = 2;
        getContentPane().add(new JLabel("Username"), gbc);

        gbc.gridx++;
        getContentPane().add(new JLabel(username), gbc);
        gbc.gridx--;

        gbc.gridy = 3;
        getContentPane().add(new JLabel("Birthday"), gbc);

        gbc.gridx++;
        getContentPane().add(new JLabel(birthday), gbc);
        gbc.gridx--;

        gbc.gridy = 4;
        getContentPane().add(new JLabel("EMail"), gbc);

        gbc.gridx++;
        getContentPane().add(new JLabel(email), gbc);
        gbc.gridx--;

        gbc.gridy = 5;
        getContentPane().add(new JLabel("Biography"), gbc);

        gbc.gridx++;
        gbc.gridheight = 2;
        gbc.weighty = 1;
        getContentPane().add(new JLabel(biography), gbc);
        gbc.gridx--;

        gbc.gridy = 7;
        gbc.gridheight = 1;
        gbc.weighty = 0;
        getContentPane().add(new JLabel("Phone Number"), gbc);

        gbc.gridx++;
        getContentPane().add(new JLabel(phone), gbc);
        gbc.gridx--;

        gbc.gridy = 8;
        getContentPane().add(new JLabel("Password"), gbc);

        gbc.gridx++;
        getContentPane().add(new JLabel(password), gbc);
        gbc.gridx--;

        gbc.gridx = 3;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        getContentPane().add(new JButton("Edit"), gbc);

        gbc.gridx = 4;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.BOTH;
        getContentPane().add(new PlaceHolder(this), gbc);

        setVisible(true);
    }

}
