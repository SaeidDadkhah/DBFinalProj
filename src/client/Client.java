package client;

import client.ui.first.FirstPage;
import client.ui.first.FirstPageFetcher;
import client.ui.main.MainPage;
import client.ui.main.MainPageFetcher;
import client.ui.main.input.BaseInput;
import client.ui.main.input.InputFetcher;
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
import java.util.ArrayList;

/**
 * Created by Saeid Dadkhah on 2016-06-27 11:06 AM.
 * Project: DBFinalProject
 */
@SuppressWarnings("unchecked")
public class Client implements
        FirstPageFetcher,
        MainPageFetcher,
        ProfilePageFetcher,
        SearchPageFetcher,
        InputFetcher {

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
                    field = Constants.F_GROUP_MEMBER;
                    break;
                case Constants.RQ_UPDATE_CHANNELS:
                    responseType = Constants.RS_UPDATE_CHANNELS;
                    field = Constants.F_CHANNEL_MEMBER;
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
                        names[i] = (String) ((JSONObject) array.get(i)).get(field);
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
    public void showNewGroupPage() {
        new BaseInput(this, BaseInput.T_GROUP);
    }

    @Override
    public void showNewChannelPage() {
        new BaseInput(this, BaseInput.T_CHANNEL);
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
            String type = wis(currentContact);

            JSONObject request = new JSONObject();
            if (type != null)
                switch (type) {
                    case Constants.F_USERNAME:
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
                    case Constants.F_GROUP_NAME:
                        request.put(Constants.F_REQUEST, Constants.RQ_GROUP_MESSAGING);
                        request.put(Constants.F_SENDER, currentUser);
                        request.put(Constants.F_RECEIVER, currentContact);
                        request.put(Constants.F_MESSAGE, message);
                        System.out.println(request.toJSONString());
                        dos.writeUTF(request.toJSONString());

                        response = (JSONObject) parser.parse(dis.readUTF());
                        if (Constants.RS_SUCCESSFUL_GROUP_MESSAGING.equals(response.get(Constants.F_RESPONSE)))
                            return true;
                        else if (Constants.RS_UNSUCCESSFUL_GROUP_MESSAGING.equals(response.get(Constants.F_RESPONSE)))
                            return false;
                        else
                            return false;
                    case Constants.F_CHANNEL_NAME:
                        request.put(Constants.F_REQUEST, Constants.RQ_CHANNEL_MESSAGING);
                        request.put(Constants.F_SENDER, currentUser);
                        request.put(Constants.F_RECEIVER, currentContact);
                        request.put(Constants.F_MESSAGE, message);
                        System.out.println(request.toJSONString());
                        dos.writeUTF(request.toJSONString());

                        response = (JSONObject) parser.parse(dis.readUTF());
                        if (Constants.RS_SUCCESSFUL_CHANNEL_MESSAGING.equals(response.get(Constants.F_RESPONSE)))
                            return true;
                        else if (Constants.RS_UNSUCCESSFUL_CHANNEL_MESSAGING.equals(response.get(Constants.F_RESPONSE)))
                            return false;
                        else
                            return false;
                }
            System.err.println("Type is not valid.");
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
        try {
            JSONObject request = new JSONObject();
            request.put(Constants.F_REQUEST, Constants.RQ_NAME_SEARCH);
            request.put(Constants.F_NAME, name);
            dos.writeUTF(request.toJSONString());

            JSONObject response = (JSONObject) parser.parse(dis.readUTF());
            if (Constants.RS_SUCCESSFUL_NAME_SEARCH.equals(response.get(Constants.F_RESPONSE))) {
                ArrayList<String> res = new ArrayList();

                JSONArray array = (JSONArray) response.get(Constants.F_USERNAME);
                //noinspection Convert2streamapi
                for (Object anArray1 : array)
                    res.add((String) ((JSONObject) anArray1).get(Constants.F_USERNAME));

                array = (JSONArray) response.get(Constants.F_GROUP_NAME);
                //noinspection Convert2streamapi
                for (Object anArray : array)
                    res.add((String) ((JSONObject) anArray).get(Constants.F_GROUP_NAME));

                array = (JSONArray) response.get(Constants.F_CHANNEL_NAME);
                //noinspection Convert2streamapi
                for (Object anArray : array)
                    res.add((String) ((JSONObject) anArray).get(Constants.F_CHANNEL_NAME));
                String[] resArray = new String[res.size()];
                for (int i = 0; i < res.size(); i++)
                    resArray[i] = res.get(i);
                return resArray;
            } else if (Constants.RS_UNSUCCESSFUL_NAME_SEARCH.equals(response.get(Constants.F_RESPONSE))) {
                return new String[0];
            } else {
                return new String[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        } catch (ParseException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    @Override
    public String[] searchHashtag(String hashtag) {
        try {
            JSONObject request = new JSONObject();
            request.put(Constants.F_REQUEST, Constants.RQ_HASHTAG_SEARCH);
            request.put(Constants.F_HASHTAG, hashtag);
            System.out.println(request.toJSONString());
            dos.writeUTF(request.toJSONString());

            JSONObject response = (JSONObject) parser.parse(dis.readUTF());
            if (Constants.RS_SUCCESSFUL_HASHTAG_SEARCH.equals(response.get(Constants.F_RESPONSE))) {
                return new String[0];
            } else if (Constants.RS_UNSUCCESSFUL_HASHTAG_SEARCH.equals(response.get(Constants.F_RESPONSE))) {
                return new String[0];
            } else {
                System.err.println("Response type is not valid.");
                return new String[0];
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    @Override
    public void add(String name) {
        try {
            String type = wis(name);
            JSONObject request = new JSONObject();
            switch (type) {
                case Constants.F_USERNAME:
                    request.put(Constants.F_REQUEST, Constants.RQ_ADD_FRIENDS);
                    request.put(Constants.F_USERNAME, currentUser);
                    request.put(Constants.F_FRIEND, name);
                    System.out.println(request.toJSONString());
                    dos.writeUTF(request.toJSONString());

                    JSONObject response = (JSONObject) parser.parse(dis.readUTF());
                    if (Constants.RS_SUCCESSFUL_ADD_FRIENDS.equals(response.get(Constants.F_RESPONSE))) {
                        updateContacts(Constants.RQ_UPDATE_FRIENDS);
                    } else if (Constants.RS_UNSUCCESSFUL_ADD_FRIENDS.equals(response.get(Constants.F_RESPONSE))) {
                    } else {
                    }
                    break;
                case Constants.F_GROUP_NAME:
                    request.put(Constants.F_REQUEST, Constants.RQ_ADD_TO_GROUP);
                    request.put(Constants.F_GROUP_MEMBER, currentUser);
                    request.put(Constants.F_GROUP_NAME, name);
                    System.out.println(request.toJSONString());
                    dos.writeUTF(request.toJSONString());

                    response = (JSONObject) parser.parse(dis.readUTF());
                    if (Constants.RS_SUCCESSFUL_ADD_TO_GROUP.equals(response.get(Constants.F_RESPONSE))) {
                        updateContacts(Constants.RQ_UPDATE_GROUPS);
                    } else if (Constants.RS_UNSUCCESSFUL_ADD_TO_GROUP.equals(response.get(Constants.F_RESPONSE))) {
                    } else {
                    }
                    break;
                case Constants.F_CHANNEL_NAME:
                    request.put(Constants.F_REQUEST, Constants.RQ_JOIN_A_CHANNEL);
                    request.put(Constants.F_CHANNEL_MEMBER, currentUser);
                    request.put(Constants.F_CHANNEL_NAME, name);
                    System.out.println(request.toJSONString());
                    dos.writeUTF(request.toJSONString());

                    response = (JSONObject) parser.parse(dis.readUTF());
                    if (Constants.RS_SUCCESSFUL_JOIN_A_CHANNEL.equals(response.get(Constants.F_RESPONSE))) {
                        updateContacts(Constants.RQ_UPDATE_CHANNELS);
                    } else if (Constants.RS_SUCCESSFUL_JOIN_A_CHANNEL.equals(response.get(Constants.F_RESPONSE))) {
                    } else {
                    }
                    break;
                default:
                    System.err.println("Response field is invalid.");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void message(String message) {
    }

    @Override
    public boolean newGroup(String name) {
        try {
            JSONObject request = new JSONObject();
            request.put(Constants.F_REQUEST, Constants.RQ_NEW_GROUP);
            request.put(Constants.F_ADMIN, currentUser);
            request.put(Constants.F_GROUP_NAME, name);
            System.out.println(request.toJSONString());
            dos.writeUTF(request.toJSONString());

            JSONObject response = (JSONObject) parser.parse(dis.readUTF());
            if (Constants.RS_SUCCESSFUL_NEW_GROUP.equals(response.get(Constants.F_RESPONSE))) {
                add(name);
                return true;
            } else if (Constants.RS_UNSUCCESSFUL_NEW_CHANNEL.equals(response.get(Constants.F_RESPONSE))) {
                return false;
            } else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean newChannel(String name) {
        try {
            JSONObject request = new JSONObject();
            request.put(Constants.F_REQUEST, Constants.RQ_JOIN_A_CHANNEL);
            request.put(Constants.F_CHANNEL_MEMBER, currentUser);
            request.put(Constants.F_CHANNEL_NAME, name);
            System.out.println(request.toJSONString());
            dos.writeUTF(request.toJSONString());

            JSONObject response = (JSONObject) parser.parse(dis.readUTF());
            if (Constants.RS_SUCCESSFUL_JOIN_A_CHANNEL.equals(response.get(Constants.F_RESPONSE))) {
                return true;
            } else if (Constants.RS_UNSUCCESSFUL_JOIN_A_CHANNEL.equals(response.get(Constants.F_RESPONSE))) {
                return false;
            } else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String wis(String name) {
        try {
            JSONObject request = new JSONObject();
            request.put(Constants.F_REQUEST, Constants.RQ_WTF);
            request.put(Constants.F_NAME, name);
            System.out.println(request.toJSONString());
            dos.writeUTF(request.toJSONString());

            JSONObject response = (JSONObject) parser.parse(dis.readUTF());
            if (Constants.RS_SUCCESSFUL_WTF.equals(response.get(Constants.F_RESPONSE)))
                return (String) response.get(Constants.F_NAME);
            else if (Constants.RS_UNSUCCESSFUL_WTF.equals(response.get(Constants.F_RESPONSE)))
                return null;
            else
                return null;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
