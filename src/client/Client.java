package client;

import client.ui.first.FirstPage;
import client.ui.first.FirstPageFetcher;
import client.ui.main.MainPage;
import client.ui.main.MainPageFetcher;
import common.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Saeid Dadkhah on 2016-06-27 11:06 AM.
 * Project: DBFinalProject
 */
@SuppressWarnings("unchecked")
public class Client implements FirstPageFetcher, MainPageFetcher {

    private DataInputStream dis;
    private DataOutputStream dos;

    private JSONParser parser;

    private FirstPage firstPage;
    private MainPage mainPage;

    private String currentUser;
    private String currentContact;

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

        parser = new JSONParser();

        currentUser = null;
        firstPage = new FirstPage(this);
    }

    @Override
    public boolean signUp(String username, String password) {
        JSONObject request = new JSONObject();
        try {
            request.put(Constants.F_REQUEST, Constants.RQ_SIGNUP);
            request.put(Constants.F_USERNAME, username);
            request.put(Constants.F_PASSWORD, password);
            System.out.println(request.toJSONString());
            dos.writeUTF(request.toJSONString());

            JSONObject response = (JSONObject) parser.parse(dis.readUTF());
            String responseType = (String) response.get(Constants.F_RESPONSE);
            if (Constants.RS_SUCCESSFUL_SIGNUP.equals(responseType))
                return true;
            else if (Constants.RS_UNSUCCESSFUL_SIGNUP.equals(responseType))
                return false;
            else
                return false;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean logIn(String username, String password) {
        try {
            JSONObject request = new JSONObject();
            request.put(Constants.F_REQUEST, Constants.RQ_LOGIN);
            request.put(Constants.F_USERNAME, username);
            request.put(Constants.F_PASSWORD, password);
            System.out.println(request.toJSONString());
            dos.writeUTF(request.toJSONString());

            JSONObject response = (JSONObject) parser.parse(dis.readUTF());
            if (Constants.RS_SUCCESSFUL_LOGIN.equals(response.get(Constants.F_RESPONSE))) {
                firstPage.dispose();
                currentUser = username;
                mainPage = new MainPage(this);
                updateContacts(Constants.RQ_UPDATE_FRIENDS);
                updateContacts(Constants.RQ_UPDATE_GROUPS);
                updateContacts(Constants.RQ_UPDATE_CHANNELS);
                return true;
            } else if (Constants.RS_UNSUCCESSFUL_LOGIN.equals(response.get(Constants.F_RESPONSE)))
                return false;
            else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void updateContacts(String type) {
        try {
            JSONObject request = new JSONObject();
            request.put(Constants.F_REQUEST, type);
            request.put(Constants.F_USERNAME, currentUser);
            System.out.println(request.toJSONString());
            dos.writeUTF(request.toJSONString());

            JSONObject response = (JSONObject) parser.parse(dis.readUTF());
            String responseType;
            String field;
            switch (type) {
                case Constants.RQ_UPDATE_FRIENDS:
                    responseType = Constants.RS_UPDATE_FRIENDS;
                    field = Constants.F_FRIEND;
                    break;
                case Constants.RQ_UPDATE_GROUPS:
                    responseType = Constants.RS_UPDATE_GROUPS;
                    field = Constants.F_GROUP_NAME;
                    break;
                case Constants.RQ_UPDATE_CHANNELS:
                    responseType = Constants.RS_UPDATE_CHANNELS;
                    field = Constants.F_CHANNEL_NAME;
                    break;
                default:
                    System.err.println("Type name is not correct");
                    return;
            }
            if (responseType.equals(response.get(Constants.F_RESPONSE))) {
                JSONArray array = (JSONArray) response.get(field);
                String[] names = new String[array.size()];
                for (int i = 0; i < array.size(); i++) {
                    names[i] = (String) ((JSONObject) array.get(i)).get(Constants.F_USERNAME);
                    System.out.println(names[i]);
                }
                mainPage.setContacts(field, names);
            } else {
                System.err.println("Response name is not correct.");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closing() {
        try {
            JSONObject request = new JSONObject();
            request.put(Constants.F_REQUEST, Constants.RQ_DISCONNECT);
            System.out.println(request.toJSONString());
            dos.writeUTF(request.toJSONString());
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteAccount(String password) {
        try {
            JSONObject request = new JSONObject();
            request.put(Constants.F_REQUEST, Constants.RQ_DELETE_ACCOUNT);
            request.put(Constants.F_USERNAME, currentUser);
            request.put(Constants.F_PASSWORD, password);
            System.out.println(request.toJSONString());
            dos.writeUTF(request.toJSONString());

            JSONObject response = (JSONObject) parser.parse(dis.readUTF());
            if (Constants.RS_SUCCESSFUL_DELETE_ACCOUNT.equals(response.get(Constants.F_RESPONSE))) {
                mainPage.dispose();
                firstPage = new FirstPage(this);
                return true;
            } else if (Constants.RS_UNSUCCESSFUL_DELETE_ACCOUNT.equals(response.get(Constants.F_RESPONSE))) {
                return false;
            } else {
                return false;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void logout() {
        if (currentUser != null) {
            currentUser = null;
            mainPage.dispose();
            firstPage = new FirstPage(this);
        }
    }

    @Override
    public boolean send(String message) {
        try {
            JSONObject request = new JSONObject();
            request.put(Constants.F_REQUEST, Constants.RQ_MESSAGING);
            request.put(Constants.F_SENDER, currentUser);
            request.put(Constants.F_RECEIVER, currentContact);
            request.put(Constants.F_MESSAGE, message);
            System.out.println(request.toJSONString());
            dos.writeUTF(request.toJSONString());

            JSONObject response = (JSONObject) parser.parse(dis.readUTF());
            if (Constants.RS_SUCCESSFUL_MESSAGING.equals(response.get(Constants.F_RESPONSE)))
                return true;
            else if (Constants.RS_UNSUCCESSFUL_MESSAGING.equals(response.get(Constants.F_RESPONSE)))
                return false;
            else
                return false;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean setCurrentContact(String currentContact) {
        this.currentContact = currentContact;
        if (currentContact == null) {
            return false;
        } else {
            return true;
        }
    }

}
