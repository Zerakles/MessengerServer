package de.zerakles.server;

import org.bukkit.Bukkit;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Server {

    private int port;
    private String address;
    private String name;

    public boolean running = true;

    public ServerSocket serverSocket;

    public ArrayList<Socket> sockets = new ArrayList<>();

    public Server(int port, String address, String name){
        this.port = port;
        this.address = address;
        this.name = name;
    }

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            this.serverSocket = serverSocket;
            while (running){
                System.out.println("Waiting for a message!");
                try{
                    Socket socket = serverSocket.accept();
                    handle(socket);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handle(Socket socket) {
        if(sockets.contains(socket)){
            InputStream input = null;
            try {
                input = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(input!=null){
                InputStreamReader inputStreamReader = new InputStreamReader(input);
                Stream<String> streamOfString= new BufferedReader(inputStreamReader).lines();
                String streamToString = streamOfString.collect(Collectors.joining());
                String[] args;
                Pattern ptr = Pattern.compile(" ");
                args = ptr.split(streamToString);
                if(args[2].equalsIgnoreCase("message")){
                    sendMessageAll(args);
                } else if(args[2].equalsIgnoreCase("private")){
                    sendMessageTo(args);
                } else {
                    sendCMD(args);
                }
            }
        } else {
            sockets.add(socket);
            InputStream input = null;
            try {
                input = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(input!=null){
                InputStreamReader inputStreamReader = new InputStreamReader(input);
                Stream<String> streamOfString= new BufferedReader(inputStreamReader).lines();
                String streamToString = streamOfString.collect(Collectors.joining());
                String[] args;
                Pattern ptr = Pattern.compile(" ");
                args = ptr.split(streamToString);
                if(args[2].equalsIgnoreCase("message")){
                    sendMessageAll(args);
                } else if(args[2].equalsIgnoreCase("private")){
                    sendMessageTo(args);
                } else {
                    sendCMD(args);
                }
            }
        }
    }

    private void sendCMD(String[] args) {
    }

    private void sendMessageTo(String[] args) {
    }

    private void sendMessageAll(String[] args) {
    }

}
