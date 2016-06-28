package server;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import common.Constants;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Saeid Dadkhah on 2016-06-20 6:11 AM.
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

    private boolean signUp() {
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

    private boolean logIn() {
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

    private boolean deleteAccount() {
        try {
            FindIterable<Document> res = db.getCollection(Constants.C_USERS)
                    .find(new Document()
                            .append(Constants.F_USERNAME, request.get(Constants.F_USERNAME)));
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

}
