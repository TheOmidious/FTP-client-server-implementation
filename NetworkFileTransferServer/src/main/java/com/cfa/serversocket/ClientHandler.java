package com.cfa.serversocket;

import java.io.*;
import java.net.Socket;
import java.nio.file.*;
import java.util.stream.Collectors;

public class ClientHandler {
    private Socket clientSocket;
    private String serverRoot;

    public ClientHandler(Socket clientSocket, String serverRoot) {
        this.clientSocket = clientSocket;
        this.serverRoot = serverRoot;
    }


    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                String[] tokens = inputLine.split("\\s+");
                String command = tokens[0];

                switch (command) {
                    case "BYE":
                    case "DISCONNECT":
                    case "QUIT":
                        out.println("Goodbye!");
                        return; // Terminate the connection
                    case "HELP":
                        out.println("Available commands: BYE, DISCONNECT, QUIT, HELP, LS, PUT, GET, MKDIR");
                        break;
                    case "LS":
                        listFiles(out);
                        break;
                    case "PUT":
                        receiveFile(in, tokens[1]);
                        break;
                    case "GET":
                        sendFile(out, tokens[1]);
                        break;
                    case "MKDIR":
                        createDirectory(tokens[1]);
                        break;
                    default:
                        out.println("Unknown command. Type HELP for available commands.");
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    private void listFiles(PrintWriter out) {
        try {
            String fileList = Files.list(Paths.get(serverRoot))
                    .map(Path::toString)
                    .collect(Collectors.joining("\n"));
            out.println(fileList);
        } catch (IOException e) {
            System.err.println("Error listing files: " + e.getMessage());
        }
    }

    private void receiveFile(BufferedReader in, String filename) {
        try {
            String filePath = Paths.get(serverRoot, filename).toString();
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.equals("END")) {
                    break;
                }
                fileWriter.write(line);
                fileWriter.newLine();
            }
            fileWriter.close();
            System.out.println("File received: " + filePath);
        } catch (IOException e) {
            System.err.println("Error receiving file: " + e.getMessage());
        }
    }

    private void sendFile(PrintWriter out, String filename) {
        try {
            String filePath = Paths.get(serverRoot, filename).toString();
            BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = fileReader.readLine()) != null) {
                out.println(line);
            }
            out.println("END"); // Signal end of file
            fileReader.close();
            System.out.println("File sent: " + filePath);
        } catch (IOException e) {
            System.err.println("Error sending file: " + e.getMessage());
        }
    }

    private void createDirectory(String directoryName) {
        try {
            Path directoryPath = Paths.get(serverRoot, directoryName);
            Files.createDirectory(directoryPath);
            System.out.println("Directory created: " + directoryPath);
        } catch (IOException e) {
            System.err.println("Error creating directory: " + e.getMessage());
        }
    }
}
