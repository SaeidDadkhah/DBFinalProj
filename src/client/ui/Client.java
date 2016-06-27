package client.ui;

import client.ui.first.FirstPage;
import client.ui.first.FirstPageFetcher;
import common.Constants;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Saeid Dadkhah on 2016-06-27 11:06 AM.
 * Project: DBFinalProject
 */
public class Client implements FirstPageFetcher {

    private DataInputStream dis;
    private DataOutputStream dos;

    private FirstPage firstPage;

    public static void main(String[] args) {
        new Client();
    }

    public Client() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (info.getName().equals("Nimbus")) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            Socket socket = new Socket(Constants.SN_NAME, Constants.SN_PORT);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        firstPage = new FirstPage(this);
    }


    @Override
    public boolean signUp(String username, String password) {
        int response = -1;
        try {
            dos.writeInt(Constants.R_SIGNUP);
            dos.writeUTF(username + " " + password);
            response = dis.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == Constants.RS_SUCCESSFUL_SIGNUP)
            return true;
        else if (response == Constants.RS_UNSUCCESSFUL_SIGNUP)
            return false;
        else
            return false;
    }

    @Override
    public boolean logIn(String username, String password) {
        int response = -1;
        try {
            dos.writeInt(Constants.R_LOGIN);
            dos.writeUTF(username + " " + password);
            response = dis.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == Constants.RS_SUCCESSFUL_SIGNUP) {
            firstPage.dispose();
            return true;
        } else if (response == Constants.RS_UNSUCCESSFUL_SIGNUP)
            return false;
        else
            return false;
    }

    @Override
    public void closing() {
        try {
            dos.writeInt(Constants.R_DISCONNECT);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
