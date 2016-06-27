package server;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import common.Constants;
import org.bson.Document;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Saeid Dadkhah on 2016-06-20 6:11 AM.
 * Project: Server
 */
public class Server implements Runnable {

    private MongoDatabase db;

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(Constants.SN_PORT);
            serverSocket.setSoTimeout(1000000);

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

        MongoClient client = new MongoClient();
        db = client.getDatabase(Constants.DB_NAME);

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            int requestNumber;
            do {
                requestNumber = dis.readInt();
                System.out.println("Request Number: " + requestNumber);
                switch (requestNumber) {
                    case Constants.R_SIGNUP:
                        signUp();
                        break;
                    case Constants.R_LOGIN:
                        logIn();
                        break;
                    case Constants.R_DISCONNECT:
                        socket.close();
                        break;
                    default:
                        System.err.println("Invalid request. Request number: " + requestNumber);
                }
            } while (requestNumber != Constants.R_DISCONNECT);
            System.out.println("Disconnecting from: " + socket.getRemoteSocketAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean signUp() {
        String str = null;
        try {
            str = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert str != null;
        FindIterable<Document> res = db.getCollection(Constants.C_USERS)
                .find(new Document()
                        .append(Constants.F_USERNAME, str.split(" ")[0]));
        if (res.first() == null) {
            db.getCollection(Constants.C_USERS).insertOne(new Document()
                    .append(Constants.F_USERNAME, str.split(" ")[0])
                    .append(Constants.F_PASSWORD, str.split(" ")[1]));

            try {
                dos.writeInt(Constants.RS_SUCCESSFUL_SIGNUP);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {

            try {
                dos.writeInt(Constants.RS_UNSUCCESSFUL_SIGNUP);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean logIn() {
        String str = null;
        try {
            str = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert str != null;
        FindIterable<Document> res = db.getCollection(Constants.C_USERS)
                .find(new Document()
                        .append(Constants.F_USERNAME, str.split(" ")[0])
                        .append(Constants.F_PASSWORD, str.split(" ")[1]));

        if (res.first() != null) {
            try {
                dos.writeInt(Constants.RS_SUCCESSFUL_LOGIN);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            try {
                dos.writeInt(Constants.RS_UNSUCCESSFUL_LOGIN);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

}
