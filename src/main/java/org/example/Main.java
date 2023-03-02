package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            ServerWorker worker = new ServerWorker(clientSocket);
            worker.start();
            System.out.println("Client connected");
        }
    }

    private static void broadcastNewConnection(Socket newClient) {
        clients.forEach((client) -> {
            try {
                client.getOutputStream().write(("New person joined: " + newClient.toString() + "\n").getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}