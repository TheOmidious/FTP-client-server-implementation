package com.cfa.serversocket;

public class Main {

    public static void main(String[] args) {
        int port = 12345;
        String serverRoot = "serverRoot";
//        FTPServer server = new FTPServer(port, serverRoot);
        ServerDraft serverDraft = new ServerDraft();
//        server.start();
        serverDraft.start();
    }
}
