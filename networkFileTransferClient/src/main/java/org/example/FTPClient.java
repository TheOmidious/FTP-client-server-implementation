package org.example;

import java.io.*;
import java.net.Socket;

public class FTPClient {

    private static String SERVER_ADDRESS = "127.0.0.1"; // Server IP address
    private static int SERVER_PORT = 1234; // Server port


    public void start() {
        try (
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        ) {
            System.out.println("Connected to server.");

            String userInputLine;
            while ((userInputLine = userInput.readLine()) != null) {
                out.println(userInputLine);
                System.out.println("Sent to server: " + userInputLine);
                String serverResponse = in.readLine();
                System.out.println("Server response: " + serverResponse);
            }
        } catch (IOException e) {
            System.out.println("Could not stablish connection to the server");
        }
    }

}

