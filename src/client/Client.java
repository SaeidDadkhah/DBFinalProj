package client;

import client.ui.first.FirstPage;
import client.ui.first.FirstPageFetcher;
import client.ui.main.MainPage;
import client.ui.main.MainPageFetcher;
import client.ui.main.profile.ProfilePageFetcher;
import client.ui.main.search.SearchPage;
import client.ui.main.search.SearchPageFetcher;
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
public class Client implements FirstPageFetcher, MainPageFetcher, ProfilePageFetcher, SearchPageFetcher {

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
                    field = Constants.F_FRIENDS;
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
                String[] names;
                if (response.get(field) != null) {
                    JSONArray array = (JSONArray) response.get(field);
                    names = new String[array.size()];
                    for (int i = 0; i < array.size(); i++) {
                        names[i] = (String) ((JSONObject) array.get(i)).get(Constants.F_USERNAME);
                        System.out.println(names[i]);
                    }
                } else {
                    names = null;
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
    public void showProfilePage() {
        try {
            JSONObject request = new JSONObject();
            request.put(Constants.F_REQUEST, Constants.RQ_GET_PROFILE);
            request.put(Constants.F_USERNAME, currentUser);
            dos.writeUTF(request.toJSONString());

            System.out.println(dis.readUTF());

//            JSONObject response = (JSONObject) parser.parse(dis.readUTF());
//            new ProfilePage(this,
//                    (String) response.get(Constants.F_NAME),
//                    (String) response.get(Constants.F_USERNAME),
//                    (String) response.get(Constants.F_BIRTHDAY),
//                    (String) response.get(Constants.F_EMAIL),
//                    (String) response.get(Constants.F_BIOGRAPHY),
//                    (String) response.get(Constants.F_PHONE_NUMBER),
//                    (String) response.get(Constants.F_PASSWORD),
//                    (String) response.get(Constants.F_PICTURE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showSearchPage() {
        new SearchPage(this);
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
        return currentContact != null;
    }

    @Override
    public void setInformation(String name, String birthday, String email, String biography, String phone, String password) {
        JSONObject request = new JSONObject();
        request.put(Constants.F_REQUEST, Constants.RQ_UPDATE_PROFILE);
        request.put(Constants.F_NAME, name);
        request.put(Constants.F_BIRTHDAY, birthday);
        request.put(Constants.F_EMAIL, email);
        request.put(Constants.F_BIOGRAPHY, biography);
        request.put(Constants.F_BIRTHDAY, birthday);
        request.put(Constants.F_PHONE_NUMBER, phone);
        request.put(Constants.F_PASSWORD, password);
    }

    @Override
    public String[] searchName(String name) {
//        JSONObject request = new JSONObject();
//        request.put(Constants.F_REQUEST, Constants.rq)
        return new String[]{"hasti", "saeid2"};
    }

    @Override
    public String[] searchHashtag(String hashtag) {
        return new String[]{"message1", "message2"};
    }

    @Override
    public void add(String name) {

    }

    @Override
    public void message(String name) {

    }
}
