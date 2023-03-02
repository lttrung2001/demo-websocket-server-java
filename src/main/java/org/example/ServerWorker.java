package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ServerWorker extends Thread {
    private Socket clientSocket;
    private OutputStream outputStream;

    public ServerWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleClientSocket() throws IOException, InterruptedException {
        InputStream inputStream = clientSocket.getInputStream();
        outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String[] tokens;
        String cmd;
        while ((line = reader.readLine()) != null) {
            tokens = line.split(" ");
            cmd = tokens[0];
            if ("quit".equalsIgnoreCase(cmd)) {
                break;
            } else if ("login".equalsIgnoreCase(cmd)) {
                handleLogin(tokens);
            } else {
                String msg = "unknown " + cmd;
                outputStream.write(msg.getBytes());
            }
        }
        clientSocket.close();
    }

    private void handleLogin(String[] tokens) throws IOException {
        if (tokens.length == 3) {
            String login = tokens[1];
            String password = tokens[2];
            if (login.equals("admin") && password.equals("admin")) {
                String msg = "ok login";
                outputStream.write(msg.getBytes());
            } else {
                String msg = "error login";
                outputStream.write(msg.getBytes());
            }
        }
    }
}
