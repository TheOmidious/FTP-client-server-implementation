package org.example;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234); // Connect to server
//            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Connected to server.");
            System.out.println();

            socket.close(); // Close the connection
 //           serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}