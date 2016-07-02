package server;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import common.Constants;
import org.bson.Document;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.XML;
import org.json.simple.JSONArray;

/**
 * Created by Saeid Dadkhah on 2016-06-20 6:11 AM. esme manam mineveshti inja dg!!!!!!
 * Project: Server
 */
@SuppressWarnings("unchecked")
public class Server implements Runnable {

    private MongoDatabase db;

    private Socket socket;
    private DataOutputStream dos;

    private JSONParser parser;
    private JSONObject request;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(Constants.SN_PORT);
            serverSocket.setSoTimeout(1000000);

            //noinspection InfiniteLoopStatement
            while (true) {
                System.out.println("Waiting...");
                Socket socket = serverSocket.accept();
                System.out.println("Connecting to: " + socket.getRemoteSocketAddress());
                new Server(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server(Socket socket) {
        this.socket = socket;

        parser = new JSONParser();

        MongoClient client = new MongoClient();
        db = client.getDatabase(Constants.DB_NAME);

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            String request;
            do {
                this.request = (JSONObject) parser.parse(dis.readUTF());
                request = (String) this.request.get(Constants.F_REQUEST);

                System.out.println("Request: " + request);
                switch (request) {
                    case Constants.RQ_SIGNUP:
                        signUp();
                        break;
                    case Constants.RQ_LOGIN:
                        logIn();
                        break;
                    case Constants.RQ_DELETE_ACCOUNT:
                        deleteAccount();
                        break;
                    case Constants.RQ_MESSAGING:
                        messaging();
                        break;
                    case Constants.RQ_NEW_CHANNEL:
                        newChanal();
                        break;
                    case Constants.RQ_NEW_GROUP:
                        newGroup();
                        break;
                    case Constants.RQ_ADD_TO_GROUP:
                        addToGroup();
                        break;
                    case Constants.RQ_JOIN_A_CHANNEL:
                        joinAChannel();
                        break;
                    case Constants.RQ_UPDATE_CHANNELS:
                        updateChannels();
                        break;
                    case Constants.RQ_UPDATE_GROUPS:
                        updateGroups();
                        break;
                    case Constants.RQ_UPDATE_FRIENDS:
                        updateFriends();
                        break;
                    case Constants.RQ_UPDATE_PROFILE:
                        updateProfile();
                        break;
                    case Constants.RQ_GET_PROFILE:
                        getProfile();
                        break;
                    case Constants.RQ_HASHTAG:
                        hashtag();
                        break;
                    case Constants.RQ_MENTION:
                        mention();
                        break;
                    case Constants.RQ_HASHTAG_SEARCH:
                        hashtagSearch();
                        break;
                    case Constants.RQ_NAME_SEARCH:
                        nameSearch();
                        break;
                    case Constants.RQ_WTF:
                        wis();
                        break;
                    case Constants.RQ_DISCONNECT:
                        socket.close();
                        break;
                    default:
                        System.err.println("Invalid request. Request: " + request);
                }
            } while (!Constants.RQ_DISCONNECT.equals(request));
            System.out.println("Disconnecting from: " + socket.getRemoteSocketAddress());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean signUp() {
        try {
            FindIterable<Document> res = db.getCollection(Constants.C_USERS)
                    .find(new Document()
                            .append(Constants.F_USERNAME, request.get(Constants.F_USERNAME)));
            if (res.first() == null) {
                db.getCollection(Constants.C_USERS).insertOne(new Document()
                        .append(Constants.F_USERNAME, request.get(Constants.F_USERNAME))
                        .append(Constants.F_PASSWORD, request.get(Constants.F_PASSWORD)));

                JSONObject response = new JSONObject();
                response.put(Constants.F_RESPONSE, Constants.RS_SUCCESSFUL_SIGNUP);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return true;
            } else {
                JSONObject response = new JSONObject();
                response.put(Constants.F_RESPONSE, Constants.RS_UNSUCCESSFUL_SIGNUP);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean logIn() {
        try {
            FindIterable<Document> res = db.getCollection(Constants.C_USERS)
                    .find(new Document()
                            .append(Constants.F_USERNAME, request.get(Constants.F_USERNAME))
                            .append(Constants.F_PASSWORD, request.get(Constants.F_PASSWORD)));

            if (res.first() != null) {
                JSONObject response = new JSONObject();
                response.put(Constants.F_RESPONSE, Constants.RS_SUCCESSFUL_LOGIN);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return true;
            } else {
                JSONObject response = new JSONObject();
                response.put(Constants.F_RESPONSE, Constants.RS_UNSUCCESSFUL_LOGIN);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAccount() {
        try {
            FindIterable<Document> res = db.getCollection(Constants.C_USERS)
                    .find(new Document()
                            .append(Constants.F_USERNAME, request.get(Constants.F_USERNAME))
                            .append(Constants.F_PASSWORD, request.get(Constants.F_PASSWORD)));
            if (res.first() != null) {
                db.getCollection(Constants.C_USERS).deleteOne(new Document()
                        .append(Constants.F_USERNAME, request.get(Constants.F_USERNAME)));

                JSONObject response = new JSONObject();
                response.put(Constants.F_RESPONSE, Constants.RS_SUCCESSFUL_DELETE_ACCOUNT);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return true;
            } else {
                JSONObject response = new JSONObject();
                response.put(Constants.F_RESPONSE, Constants.RS_UNSUCCESSFUL_DELETE_ACCOUNT);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean messaging() {

        try {
            FindIterable<Document> res = db.getCollection(Constants.C_USERS)
                    .find(new Document()
                            .append(Constants.F_RECEIVER, request.get(Constants.F_RECEIVER)));
            if (res.first() != null) {
                db.getCollection(Constants.C_MESSAGES).insertOne(new Document()
                        .append(Constants.F_SENDER, request.get(Constants.F_SENDER))
                        .append(Constants.F_MID, db.getCollection(Constants.C_MESSAGES).count())
                        .append(Constants.F_RECEIVER, request.get(Constants.F_RECEIVER))
                        .append(Constants.F_MESSAGE, request.get(Constants.F_MESSAGE))
                        .append(Constants.F_TIME, System.currentTimeMillis()));

                JSONObject response = new JSONObject();
                response.put(Constants.F_RESPONSE, Constants.RS_SUCCESSFUL_MESSAGING);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return true;
            } else {
                JSONObject response = new JSONObject();
                response.put(Constants.F_RESPONSE, Constants.RS_UNSUCCESSFUL_MESSAGING);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean newChanal() {
        try {
            FindIterable<Document> res = db.getCollection(Constants.C_USERS)
                    .find(new Document()
                            .append(Constants.F_ADMIN, request.get(Constants.F_USERNAME)));

            if (res.first() != null) {
                FindIterable<Document> res2 = db.getCollection(Constants.C_CHANNELS)
                        .find(new Document()
                                .append(Constants.F_CHANNEL_NAME, request.get(Constants.F_CHANNEL_NAME)));
                if (res2.first() == null) {

                    db.getCollection(Constants.C_CHANNELS).insertOne(new Document()
                            .append(Constants.F_ADMIN, request.get(Constants.F_ADMIN))
                            .append(Constants.F_CHANNEL_NAME, request.get(Constants.F_CHANNEL_NAME)));

                    JSONObject response = new JSONObject();
                    response.put(Constants.F_RESPONSE, Constants.RS_SUCCESSFUL_NEW_CHANNEL);
                    System.out.println(response.toJSONString());
                    dos.writeUTF(response.toJSONString());
                    return true;
                } else {
                    JSONObject response = new JSONObject();
                    response.put(Constants.F_RESPONSE, Constants.RS_UNSUCCESSFUL_NEW_CHANNEL);
                    System.out.println(response.toJSONString());
                    dos.writeUTF(response.toJSONString());
                    return false;
                }

            } else {
                JSONObject response = new JSONObject();
                response.put(Constants.F_RESPONSE, Constants.RS_UNSUCCESSFUL_NEW_CHANNEL);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean newGroup() {
        try {
            FindIterable<Document> res = db.getCollection(Constants.C_USERS)
                    .find(new Document()
                            .append(Constants.F_USERNAME, request.get(Constants.F_ADMIN)));
            if (res.first() != null) {
                FindIterable<Document> res2 = db.getCollection(Constants.C_GROUPS)
                        .find(new Document()
                                .append(Constants.F_GROUP_NAME, request.get(Constants.F_GROUP_NAME)));
                if (res2.first() == null) {

                    db.getCollection(Constants.C_GROUPS).insertOne(new Document()
                            .append(Constants.F_ADMIN, request.get(Constants.F_ADMIN))
                            .append(Constants.F_GROUP_NAME, request.get(Constants.F_GROUP_NAME)));

                    JSONObject response = new JSONObject();
                    response.put(Constants.F_RESPONSE, Constants.RS_SUCCESSFUL_NEW_GROUP);
                    System.out.println(response.toJSONString());
                    dos.writeUTF(response.toJSONString());
                    return true;
                } else {
                    JSONObject response = new JSONObject();
                    response.put(Constants.F_RESPONSE, Constants.RS_UNSUCCESSFUL_NEW_GROUP);
                    System.out.println(response.toJSONString());
                    dos.writeUTF(response.toJSONString());
                    return false;
                }

            } else {
                JSONObject response = new JSONObject();
                response.put(Constants.F_RESPONSE, Constants.RS_UNSUCCESSFUL_NEW_GROUP);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addToGroup() {
        try {
            FindIterable<Document> res = db.getCollection(Constants.C_USERS)
                    .find(new Document()
                            .append(Constants.F_USERNAME, request.get(Constants.F_GROUP_MEMBER)));
            if (res.first() != null) {
                FindIterable<Document> res2 = db.getCollection(Constants.C_GROUPS)
                        .find(new Document()
                                .append(Constants.F_GROUP_NAME, request.get(Constants.F_GROUP_NAME)));
                if (res2.first() != null) {

                    ArrayList<Document> members = (ArrayList<Document>) res2.first().get(Constants.F_GROUP_MEMBER);
                    members.add(new Document(Constants.F_GROUP_MEMBER, request.get(Constants.F_GROUP_MEMBER)));

                    db.getCollection(Constants.C_GROUPS)
                            .updateOne(new Document(Constants.F_GROUP_NAME, request.get(Constants.F_GROUP_NAME)),
                                    new Document("$set", new Document(Constants.F_GROUP_MEMBER, members)));

                    JSONObject response = new JSONObject();
                    response.put(Constants.F_RESPONSE, Constants.RS_SUCCESSFUL_ADD_TO_GROUP);
                    System.out.println(response.toJSONString());
                    dos.writeUTF(response.toJSONString());
                    return true;
                } else {
                    JSONObject response = new JSONObject();
                    response.put(Constants.F_RESPONSE, Constants.RS_UNSUCCESSFUL_ADD_TO_GROUP);
                    System.out.println(response.toJSONString());
                    dos.writeUTF(response.toJSONString());
                    return false;
                }

            } else {
                JSONObject response = new JSONObject();
                response.put(Constants.F_RESPONSE, Constants.RS_UNSUCCESSFUL_ADD_TO_GROUP);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean joinAChannel() {
        try {
            FindIterable<Document> res = db.getCollection(Constants.C_CHANNELS)
                    .find(new Document()
                            .append(Constants.F_CHANNEL_NAME, request.get(Constants.F_CHANNEL_NAME)));
            if (res.first() != null) {

                ArrayList<Document> members = (ArrayList<Document>) res.first().get(Constants.F_CHANNEL_MEMBER);
                members.add(new Document(Constants.F_CHANNEL_MEMBER, request.get(Constants.F_CHANNEL_MEMBER)));

                db.getCollection(Constants.C_CHANNELS)
                        .updateOne(new Document(Constants.F_CHANNEL_NAME, request.get(Constants.F_CHANNEL_NAME)),
                                new Document("$set", new Document(Constants.F_CHANNEL_MEMBER, members)));

                JSONObject response = new JSONObject();
                response.put(Constants.F_RESPONSE, Constants.RS_SUCCESSFUL_JOIN_A_CHANNEL);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return true;


            } else {
                JSONObject response = new JSONObject();
                response.put(Constants.F_RESPONSE, Constants.RS_UNSUCCESSFUL_JOIN_A_CHANNEL);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateChannels() {
        try {
            FindIterable<Document> res = db.getCollection(Constants.C_CHANNELS)
                    .find(new Document()
                            .append(Constants.F_CHANNEL_MEMBER, request.get(Constants.F_USERNAME)));


            JSONObject response = new JSONObject();
            response.put(Constants.F_RESPONSE, Constants.RS_UPDATE_CHANNELS);

            for (Document document : res)
                response.put(Constants.F_CHANNEL_NAME, document.get(Constants.F_CHANNEL_NAME));


            System.out.println(response.toJSONString());
            dos.writeUTF(response.toJSONString());
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateGroups() {
        try {
            FindIterable<Document> res = db.getCollection(Constants.C_GROUPS)
                    .find(new Document()
                            .append(Constants.F_GROUP_MEMBER, request.get(Constants.F_USERNAME)));


            JSONObject response = new JSONObject();
            response.put(Constants.F_RESPONSE, Constants.RS_UPDATE_GROUPS);

            for (Document document : res)
                response.put(Constants.F_GROUP_NAME, document.get(Constants.F_GROUP_NAME));


            System.out.println(response.toJSONString());
            dos.writeUTF(response.toJSONString());
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFriends() {
        try {
            FindIterable<Document> res = db.getCollection(Constants.C_USERS)
                    .find(new Document()
                            .append(Constants.F_USERNAME, request.get(Constants.F_USERNAME)));

            JSONObject response = new JSONObject();
            response.put(Constants.F_RESPONSE, Constants.RS_UPDATE_FRIENDS);

            if (res.first().get(Constants.F_FRIENDS) != null) {
                JSONArray arr = new JSONArray();

                for (Document doc : (ArrayList<Document>) res.first().get(Constants.F_FRIENDS))
                    arr.add(doc);

                response.put(Constants.F_FRIENDS, arr);
            }

            System.out.println(response.toJSONString());
            dos.writeUTF(response.toJSONString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean getProfile() {
        try {
            FindIterable<Document> res = db.getCollection(Constants.C_USERS)
                    .find(new Document()
                            .append(Constants.F_USERNAME, request.get(Constants.F_USERNAME)));


            JSONObject response = new JSONObject();
            response.put(Constants.F_RESPONSE, Constants.RS_GET_PROFILE);
            response.put(Constants.F_NAME, res.first().get(Constants.F_NAME));
            response.put(Constants.F_USERNAME, res.first().get(Constants.F_USERNAME));
            response.put(Constants.F_BIRTHDAY, res.first().get(Constants.F_BIRTHDAY));
            response.put(Constants.F_EMAIL, res.first().get(Constants.F_EMAIL));
            response.put(Constants.F_BIOGRAPHY, res.first().get(Constants.F_BIOGRAPHY));
            response.put(Constants.F_PHONE_NUMBER, res.first().get(Constants.F_PHONE_NUMBER));
            response.put(Constants.F_PICTURE, res.first().get(Constants.F_PICTURE));
            response.put(Constants.F_PASSWORD, res.first().get(Constants.F_PASSWORD));

            String xml = XML.toString(response);


            System.out.println(xml);
            dos.writeUTF(xml);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProfile() {
        try {
            FindIterable<Document> res = db.getCollection(Constants.C_USERS)
                    .find(new Document()
                            .append(Constants.F_USERNAME, request.get(Constants.F_USERNAME)));

            db.getCollection(Constants.C_USERS)
                    .updateOne(new Document(Constants.F_USERNAME, request.get(Constants.F_USERNAME)),
                            new Document("$set", new Document(Constants.F_NAME, res.first().get(Constants.F_NAME))));

            db.getCollection(Constants.C_USERS)
                    .updateOne(new Document(Constants.F_USERNAME, request.get(Constants.F_USERNAME)),
                            new Document("$set", new Document(Constants.F_BIRTHDAY, res.first().get(Constants.F_BIRTHDAY))));

            db.getCollection(Constants.C_USERS)
                    .updateOne(new Document(Constants.F_USERNAME, request.get(Constants.F_USERNAME)),
                            new Document("$set", new Document(Constants.F_EMAIL, res.first().get(Constants.F_EMAIL))));

            db.getCollection(Constants.C_USERS)
                    .updateOne(new Document(Constants.F_USERNAME, request.get(Constants.F_USERNAME)),
                            new Document("$set", new Document(Constants.F_BIOGRAPHY, res.first().get(Constants.F_BIOGRAPHY))));

            db.getCollection(Constants.C_USERS)
                    .updateOne(new Document(Constants.F_USERNAME, request.get(Constants.F_USERNAME)),
                            new Document("$set", new Document(Constants.F_PHONE_NUMBER, res.first().get(Constants.F_PHONE_NUMBER))));

//            db.getCollection(Constants.C_USERS)
//                            .updateOne(new Document(Constants.F_USERNAME, request.get(Constants.F_USERNAME)),
//                                       new Document("$set", new Document(Constants.F_PICTURE, res.first().get(Constants.F_PICTURE))));
//
            db.getCollection(Constants.C_USERS)
                    .updateOne(new Document(Constants.F_USERNAME, request.get(Constants.F_USERNAME)),
                            new Document("$set", new Document(Constants.F_PASSWORD, res.first().get(Constants.F_PASSWORD))));


            JSONObject response = new JSONObject();
            response.put(Constants.F_RESPONSE, Constants.RS_UPDATE_PROFILE);


            System.out.println(response.toJSONString());
            dos.writeUTF(response.toJSONString());
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hashtag() {
        try {
            JSONObject response = new JSONObject();
            FindIterable<Document> res = db.getCollection(Constants.C_HASHTAGS)
                    .find(new Document()
                            .append(Constants.F_HASHTAG, request.get(Constants.F_HASHTAG)));

            if (res.first() == null) {
                ArrayList<Document> arr = new ArrayList<>();
                arr.add(new Document().append(Constants.F_MID, request.get(Constants.F_MID)));
                db.getCollection(Constants.C_HASHTAGS).insertOne(new Document()
                        .append(Constants.F_HASHTAG, request.get(Constants.F_HASHTAG))
                        .append(Constants.F_MID, arr));
            } else {
                ArrayList<Document> arr = (ArrayList<Document>) res.first().get(Constants.F_MID);
                arr.add(new Document().append(Constants.F_MID, request.get(Constants.F_MID)));
                db.getCollection(Constants.C_HASHTAGS)
                        .updateOne(new Document(Constants.F_HASHTAG, request.get(Constants.F_HASHTAG)),
                                new Document("$set", new Document(Constants.F_MID, arr)));

            }

            response.put(Constants.F_RESPONSE, Constants.RS_HASHTAG);
            System.out.println(response.toJSONString());
            dos.writeUTF(response.toJSONString());
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean mention() {///////////////kamel nis
        try {
            FindIterable<Document> res = db.getCollection(Constants.C_GROUPS)
                    .find(new Document()
                            .append(Constants.F_GROUP_NAME, request.get(Constants.F_GROUP_NAME)));
            if (res.first() != null) {

                ArrayList<Document> members = (ArrayList<Document>) res.first().get(Constants.F_GROUP_MEMBER);
                boolean flag = false;
                for (Document temp : members)
                    if (temp.get(Constants.F_MENTIONED).equals(request.get(Constants.F_MENTIONED))) {
                        flag = true;
                        break;
                    }
                if (flag) {
                    db.getCollection(Constants.C_MENTIONS).insertOne(new Document()
                            .append(Constants.F_USERNAME, request.get(Constants.F_USERNAME))
                            .append(Constants.F_MENTIONED, request.get(Constants.F_MENTIONED))
                            .append(Constants.F_GROUP_NAME, request.get(Constants.F_GROUP_NAME))
                            .append(Constants.F_MID, request.get(Constants.F_MID)));

                    JSONObject response = new JSONObject();
                    response.put(Constants.F_RESPONSE, Constants.RS_SUCCESSFUL_MENTION);
                    System.out.println(response.toJSONString());
                    dos.writeUTF(response.toJSONString());
                    return true;
                } else {

                    JSONObject response = new JSONObject();
                    response.put(Constants.F_RESPONSE, Constants.RS_UNSUCCESSFUL_MENTION);
                    System.out.println(response.toJSONString());
                    dos.writeUTF(response.toJSONString());
                    return false;
                }
            } else {
                JSONObject response = new JSONObject();
                response.put(Constants.F_RESPONSE, Constants.RS_UNSUCCESSFUL_MENTION);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return false;
            }


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hashtagSearch() {
        try {

            JSONObject response = new JSONObject();

            FindIterable<Document> res = db.getCollection(Constants.C_HASHTAGS).find(new Document()
                    .append(Constants.F_HASHTAG, request.get(Constants.F_HASHTAG)));

            if (res.first() != null) {
                response.put(Constants.F_RESPONSE, Constants.RS_SUCCESSFUL_HASHTAG_SEARCH);
                response.put(Constants.F_MID, res.first().get(Constants.F_MID));
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return true;
            } else {
                response.put(Constants.F_RESPONSE, Constants.RS_UNSUCCESSFUL_HASHTAG_SEARCH);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean nameSearch() {
        try {

            JSONObject response = new JSONObject();
            ArrayList<Document> people = new ArrayList<>();
            ArrayList<Document> groups = new ArrayList<>();
            ArrayList<Document> channels = new ArrayList<>();
            boolean flag = false;
            FindIterable<Document> res = db.getCollection(Constants.C_USERS).find(new Document()
                    .append(Constants.F_USERNAME, request.get(Constants.F_NAME)));
            FindIterable<Document> res2 = db.getCollection(Constants.C_GROUPS).find(new Document()
                    .append(Constants.F_GROUP_NAME, request.get(Constants.F_NAME)));
            FindIterable<Document> res3 = db.getCollection(Constants.C_CHANNELS).find(new Document()
                    .append(Constants.F_CHANNEL_NAME, request.get(Constants.F_NAME)));
            if (res.first() != null) {
                flag = true;
                people.add(new Document().append(Constants.F_USERNAME, res.first().get(Constants.F_USERNAME)));
            }
            if (res2.first() != null) {
                flag = true;
                groups.add(new Document().append(Constants.F_GROUP_NAME, res2.first().get(Constants.F_GROUP_NAME)));
            }
            if (res3.first() != null) {
                flag = true;
                channels.add(new Document().append(Constants.F_CHANNEL_NAME, res.first().get(Constants.F_CHANNEL_NAME)));
            }

            if (flag) {
                response.put(Constants.F_RESPONSE, Constants.RS_SUCCESSFUL_NAME_SEARCH);
                response.put(Constants.F_USERNAME, people);
                response.put(Constants.F_GROUP_NAME, groups);
                response.put(Constants.F_CHANNEL_NAME, channels);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return true;
            } else {
                response.put(Constants.F_RESPONSE, Constants.RS_UNSUCCESSFUL_NAME_SEARCH);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean wis() {
        try {

            JSONObject response = new JSONObject();

            boolean flag = false;
            FindIterable<Document> res = db.getCollection(Constants.C_USERS).find(new Document()
                    .append(Constants.F_USERNAME, request.get(Constants.F_NAME)));
            FindIterable<Document> res2 = db.getCollection(Constants.C_GROUPS).find(new Document()
                    .append(Constants.F_GROUP_NAME, request.get(Constants.F_NAME)));
            FindIterable<Document> res3 = db.getCollection(Constants.C_CHANNELS).find(new Document()
                    .append(Constants.F_CHANNEL_NAME, request.get(Constants.F_NAME)));
            if (res.first() != null) {
                flag = true;
                response.put(Constants.F_USERNAME, request.get(Constants.F_NAME));
            }
            if (res2.first() != null) {
                flag = true;
                response.put(Constants.F_GROUP_NAME, request.get(Constants.F_NAME));
            }
            if (res3.first() != null) {
                flag = true;
                response.put(Constants.F_CHANNEL_NAME, request.get(Constants.F_NAME));
            }

            if (flag) {
                response.put(Constants.F_RESPONSE, Constants.RS_SUCCESSFUL_WTF);

                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return true;
            } else {
                response.put(Constants.F_RESPONSE, Constants.RS_UNSUCCESSFUL_WTF);
                System.out.println(response.toJSONString());
                dos.writeUTF(response.toJSONString());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}