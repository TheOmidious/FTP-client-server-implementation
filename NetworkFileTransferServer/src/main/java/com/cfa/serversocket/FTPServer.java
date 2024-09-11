//package com.cfa.serversocket;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class FTPServer {
//
//    private ServerSocket serverSocket;
//    private int port;
//    private String serverRoot;
//
//    public FTPServer(int port, String serverRoot) {
//        this.port = port;
//        this.serverRoot = serverRoot;
//    }
//
//    public void start() {
//        try {
//            serverSocket = new ServerSocket(port);
//            System.out.println("Server started. Listening on port " + port);
//        } catch (IOException e) {
//            System.err.println("Error starting server: " + e.getMessage());
//            return;
//        }
//
//        try {
//            while (true) {
//                Socket clientSocket = serverSocket.accept();
//                System.out.println("Client connected: " + clientSocket.getInetAddress());
//
//                // Create a new thread for each client
//                Thread clientThread = new Thread(String.valueOf(new ClientHandler(clientSocket, serverRoot)));
//                clientThread.start();
//            }
//        } catch (IOException e) {
//            System.err.println("Error accepting client connection: " + e.getMessage());
//        } finally {
//            try {
//                serverSocket.close();
//            } catch (IOException e) {
//                System.err.println("Error closing server socket: " + e.getMessage());
//            }
//        }
//    }
//}
