package client.ui.main.profile;

import client.ui.component.PlaceHolder;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Saeid Dadkhah on 2016-06-29 6:06 PM.
 * Project: DBFinalProject
 */
public class ProfilePage extends JDialog {

    // LM stands for Layout Mode
    private static final int LM_LABEL = 0;
    private static final int LM_TEXT_FIELD = 1;

    // I stands for index
    private static final int I_NAME = 0;
    private static final int I_BIRTHDAY = 1;
    private static final int I_EMAIL = 2;
    private static final int I_PHONE = 3;
    private static final int I_PASSWORD = 4;
    private static final int NUM_OF_DYNAMIC_FIELDS = 5;

    private ProfilePageFetcher fetcher;

    private JLabel[] l_fields;
    private JTextField[] tf_fields;
    private Object[] gbcs;

    private JButton b_edit;

    private String name;
    private String username;
    private String birthday;
    private String email;
    private String biography;
    private String phone;
    private String password;
    private String profilePictureAddress;

    private int layoutMode;

    public static void main(String[] args) {
        new ProfilePage((name1,
                         birthday1,
                         email1,
                         biography1,
                         phone1,
                         password1)
                -> System.out.println("name: "
                + name1
                + "\nbirthday: "
                + birthday1
                + "\nEMail"
                + email1
                + "\nbiography: "
                + biography1
                + "\nphone: "
                + phone1
                + "\npassword: "
                + password1),
                "Saeid",
                "saeid",
                "1995.02.26",
                "saeid.dadkhah@live.com",
                "Computer Engineering student :)",
                "+989388835866",
                "noneOfUrBusiness",
                "files\\photo_2016-06-30_14-33-28.jpg");
    }

    public ProfilePage(ProfilePageFetcher fetcher,
                       String name,
                       String username,
                       String birthday,
                       String email,
                       String biography,
                       String phone,
                       String password,
                       String profilePictureAddress) {
        l_fields = new JLabel[NUM_OF_DYNAMIC_FIELDS];
        tf_fields = new JTextField[NUM_OF_DYNAMIC_FIELDS];
        gbcs = new Object[NUM_OF_DYNAMIC_FIELDS];

        this.fetcher = fetcher;
        this.name = name;
        this.username = username;
        this.birthday = birthday;
        this.email = email;
        this.biography = biography;
        this.phone = phone;
        this.password = password;
        this.profilePictureAddress = profilePictureAddress;

        layoutMode = LM_LABEL;

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
        gbc.gridheight = 5;
        gbc.weightx = 0.6;
        gbc.weighty = 0;
        JButton l_profilePicture = new JButton() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);

                ImageIcon imageIcon = new ImageIcon(profilePictureAddress);
                Image s = new ImageIcon(imageIcon.getImage().getScaledInstance(getWidth() - 20, getHeight() - 20, Image.SCALE_SMOOTH)).getImage();
                g.drawImage(s, 10, 10, getWidth() - 20, getHeight() - 20, null);
            }
        };
        getContentPane().add(l_profilePicture, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        getContentPane().add(new JLabel("Name"), gbc);

        gbc.gridx++;
        gbc.weightx = 0.4;
        gbcs[I_NAME] = gbc.clone();
        l_fields[I_NAME] = new JLabel(name);
        tf_fields[I_NAME] = new JTextField();
        getContentPane().add(l_fields[I_NAME], gbc);
        gbc.gridx--;
        gbc.weightx = 0;

        gbc.gridy = 2;
        getContentPane().add(new JLabel("Username"), gbc);

        gbc.gridx++;
        gbc.weightx = 0.4;
        getContentPane().add(new JLabel(username), gbc);
        gbc.gridx--;
        gbc.weightx = 0;

        gbc.gridy = 3;
        getContentPane().add(new JLabel("Birthday"), gbc);

        gbc.gridx++;
        gbc.weightx = 0.4;
        gbcs[I_BIRTHDAY] = gbc.clone();
        l_fields[I_BIRTHDAY] = new JLabel(birthday);
        tf_fields[I_BIRTHDAY] = new JTextField();
        getContentPane().add(l_fields[I_BIRTHDAY], gbc);
        gbc.gridx--;
        gbc.weightx = 0;

        gbc.gridy = 4;
        getContentPane().add(new JLabel("EMail"), gbc);

        gbc.gridx++;
        gbc.weightx = 0.4;
        gbcs[I_EMAIL] = gbc.clone();
        l_fields[I_EMAIL] = new JLabel(email);
        tf_fields[I_EMAIL] = new JTextField();
        getContentPane().add(l_fields[I_EMAIL], gbc);
        gbc.gridx--;
        gbc.weightx = 0;

        gbc.gridy = 5;
        getContentPane().add(new JLabel("Biography"), gbc);

        gbc.gridx++;
        gbc.weightx = 0.4;
        gbc.gridheight = 2;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.ABOVE_BASELINE;
        getContentPane().add(new JLabel(biography), gbc);
        gbc.gridx--;
        gbc.weightx = 0;

        gbc.gridy = 7;
        gbc.gridheight = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.BASELINE;
        getContentPane().add(new JLabel("Phone Number"), gbc);

        gbc.gridx++;
        gbc.weightx = 0.4;
        gbcs[I_PHONE] = gbc.clone();
        l_fields[I_PHONE] = new JLabel(phone);
        tf_fields[I_PHONE] = new JTextField();
        getContentPane().add(l_fields[I_PHONE], gbc);
        gbc.gridx--;
        gbc.weightx = 0;

        gbc.gridy = 8;
        getContentPane().add(new JLabel("Password"), gbc);

        gbc.gridx++;
        gbc.weightx = 0.4;
        gbcs[I_PASSWORD] = gbc.clone();
        l_fields[I_PASSWORD] = new JLabel(password);
        tf_fields[I_PASSWORD] = new JTextField();
        getContentPane().add(l_fields[I_PASSWORD], gbc);
        gbc.gridx--;
        gbc.weightx = 0;

        gbc.gridx = 3;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        b_edit = new JButton("Edit");
        b_edit.addActionListener(e -> changeMode());
        getContentPane().add(b_edit, gbc);

        gbc.gridx = 4;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.BOTH;
        getContentPane().add(new PlaceHolder(this), gbc);

        setVisible(true);
    }

    private void changeMode() {
        switch (layoutMode) {
            case LM_LABEL:
                for (int i = 0; i < NUM_OF_DYNAMIC_FIELDS; i++) {
                    tf_fields[i].setText(l_fields[i].getText());
                    getContentPane().remove(l_fields[i]);
                    getContentPane().add(tf_fields[i], gbcs[i]);
                }

                b_edit.setText("Ok");

                setSize(getWidth() - 1, getHeight() - 1);

                layoutMode = LM_TEXT_FIELD;
                break;
            case LM_TEXT_FIELD:
                for (int i = 0; i < NUM_OF_DYNAMIC_FIELDS; i++) {
                    l_fields[i].setText(tf_fields[i].getText());
                    getContentPane().remove(tf_fields[i]);
                    getContentPane().add(l_fields[i], gbcs[i]);
                }

                b_edit.setText("Edit");

                setSize(getWidth() + 1, getHeight() + 1);

                fetcher.setInformation(tf_fields[I_NAME].getText(),
                        tf_fields[I_BIRTHDAY].getText(),
                        tf_fields[I_EMAIL].getText(),
                        "bio",
                        tf_fields[I_PHONE].getText(),
                        tf_fields[I_PASSWORD].getText());
                layoutMode = LM_LABEL;
                break;
            default:
                System.err.println("Layout Mode is illegal");
        }
        repaint();
    }

}
