package com.cfa.serversocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDraft {
    private static final int SERVER_PORT = 1234;
    private static final String SERVER_ROOT = "serverRoot";

    public void start() {
        try {f
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server started. Listening on port " + SERVER_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try (
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received command from client: " + inputLine);
                String response = myCommand(inputLine);
                System.out.println("Sent response to client: " + response);
                out.write(response);

                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String myCommand(String command) {
        switch (command.toUpperCase()) {
            case "BYE":
                return "-> Connection terminated";
            case "DISCONNECT":
                return "-> Connection terminated";
            case "QUIT":
                return "-> Connection terminated";
            case "HELP":
                return "-> The available commands are:, \n"
                        + "BYE, DISCONNECT, QUIT, HELP, LS, PUT, GET and MKDIR";
            case "LS":
                return "-> Listing files on the server...";
            case "PUT":
                return "-> Uploading file";
            case "GET":
                return "-> Downloading file";
            case "MKDIR":
                return "-> Creating directory";
            default:
                return " -> Invalid command";
        }
    }
}