package common;

import client.Client;
import server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Saeid Dadkhah on 2016-06-28 4:48 PM.
 * Project: DBFinalProject
 */
public class SCStarter {

    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("Server is running...");
                    ServerSocket serverSocket = new ServerSocket(Constants.SN_PORT);
                    serverSocket.setSoTimeout(1000000);

                    //noinspection InfiniteLoopStatement
                    while (true) {
                        System.out.println("Waiting...");
                        Socket socket = serverSocket.accept();
                        System.out.print("Connecting to: ");
                        System.out.println(socket.getRemoteSocketAddress());
                        new Server(socket);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        new Client();
    }

}
