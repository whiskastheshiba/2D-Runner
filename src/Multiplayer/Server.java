package Multiplayer;


import Enviroment.mySqlDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *Server class used for clients to connect for multiplayer game
 */
public class Server implements Runnable{
    /**
     * List of connected clients
     */
    private ArrayList<ConnectionHandler> connections;
    /**
     * Server socket
     */
    private ServerSocket server;
    /**
     * List of threds
     */
    private ExecutorService thredpool;
    /**
     * Represent the state of the server, if true server is closed
     */
    private boolean done;

    /**
     * Create empty list of connected clients and set server state done to false
     */
    public Server(){
        connections = new ArrayList<>();
        done = false;
    }

    /**
     * Create a server socket and listen for new connections
     */
    @Override
    public void run() {
        try {
            server = new ServerSocket(9999);
            thredpool = Executors.newCachedThreadPool();
            while(!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                thredpool.execute(handler);
            }
        } catch (IOException e) {
            shutDown();
        }
    }

    /**
     * Shutdown server closing all connections
     */
    public  void shutDown(){
        if(!server.isClosed()){
            try {
                done = true;
                for (ConnectionHandler ch : connections){
                    ch.shutdown();
                    mySqlDatabase.deleteLobby(ch.lobby.player1);
                }
                server.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Class for handling connection between server and client
     */
    class ConnectionHandler implements Runnable{

        /**
         * Client socket
         */
        private Socket client;
        /**
         * Input information to server from client
         */
        private BufferedReader read;
        /**
         * Output information to client from server
         */
        private PrintWriter write;
        /**
         * Client player username
         */
        private String clientName;
        /**
         * Information about client player lobby
         */
        public Lobby lobby = new Lobby();


        public ConnectionHandler(Socket client){
            this.client = client;
        }


        /**
         * Perform necessary operations based on received messages and send reply if needed
         */
        @Override
        public void run() {
            try {
                write = new PrintWriter(client.getOutputStream(), true);
                read = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String message;
                while((message = read.readLine()) != null){
                    if(message.startsWith("CreateLobby")){
                        String[] messageSplit = message.split(" ",3);
                        if(messageSplit.length == 3){
                            mySqlDatabase.createLobby(messageSplit[1],messageSplit[2]);
                            clientName = messageSplit[1];
                            lobby.player1 = messageSplit[1];
                            lobby.level = messageSplit[2];
                        }
                    }else if(message.startsWith("JoinLobby")){
                        String[] messageSplit = message.split(" ",3);
                        if(messageSplit.length == 3){
                            mySqlDatabase.joinLobby(messageSplit[1],messageSplit[2]);
                            clientName = messageSplit[2];
                            lobby.player2 = messageSplit[2];
                            String player = mySqlDatabase.getSpecificPlayer1(messageSplit[1]);
                            lobby.player1 = player;
                            for (ConnectionHandler ch : connections){
                                if(player.equals(ch.lobby.player1) && player.equals((ch.clientName))){
                                    ch.write.println("JoinedLobby");
                                    ch.lobby.player1 = player;
                                    ch.lobby.player2 = clientName;
                                    lobby.level = ch.lobby.level;
                                    break;
                                }
                            }
                        }
                    }else if(message.startsWith("Finished")){
                        String[] messageSplit = message.split(" ",2);
                        for (ConnectionHandler ch : connections){
                            if(ch.lobby.player1.equals(messageSplit[1])) {
                                ch.lobby.player1Finished = true;
                                ch.write.println("Finished" + ' ' + messageSplit[1]);
                            }
                            else if(ch.lobby.player2.equals(messageSplit[1])){
                                ch.lobby.player2Finished = true;
                                ch.write.println("Finished" + ' ' + messageSplit[1]);
                            }

                            if(ch.lobby.player1Finished && ch.lobby.player2Finished){
                                ch.lobby.finished = true;
                                ch.write.println("GameFinished");
                            }
                        }
                    }else if(message.startsWith("Score")) {
                        String[] messageSplit = message.split(" ", 3);
                        for(ConnectionHandler ch : connections){
                            if(messageSplit[2].equals(ch.lobby.player1)){
                                ch.lobby.player1Score = Integer.parseInt(messageSplit[1]);
                            }else if(messageSplit[2].equals(ch.lobby.player2)){
                                ch.lobby.player2Score = Integer.parseInt(messageSplit[1]);
                            }
                            if(ch.lobby.finished){
                                if(ch.lobby.player1Score >= ch.lobby.player2Score){
                                    ch.write.println("Won" + ' ' + ch.lobby.player1);
                                }else {
                                    ch.write.println("Won" + ' ' + ch.lobby.player2);
                                }
                            }
                        }
                    }
                    else if(message.startsWith("LeaveLobby")){
                        String[] messageSplit = message.split(" ",2);
                        for (ConnectionHandler ch : connections){
                            if(ch.lobby.player2 != null && ch.clientName != null ){
                                if(ch.lobby.player2.equals(messageSplit[1]) && ch.clientName.equals(messageSplit[1])){
                                    ch.lobby.player2 = null;
                                    this.lobby.player1 = null;
                                    this.lobby.player2 = null;
                                    mySqlDatabase.leaveLobby(messageSplit[1]);
                                }else if(ch.lobby.player2.equals(messageSplit[1]) && !ch.clientName.equals(messageSplit[1])){
                                    ch.write.println("LeftLobby");
                                }
                            }
                            if(ch.lobby.player1 != null && ch.clientName != null){
                                if(ch.lobby.player1.equals(messageSplit[1])){
                                    mySqlDatabase.deleteLobby(messageSplit[1]);
                                    if(!ch.clientName.equals(messageSplit[1])){
                                        ch.write.println("LobbyDeleted");
                                    }
                                }
                            }
                        }
                    }else if(message.startsWith("LeftGame")){
                        System.out.println(message);
                        String[] messageSplit = message.split(" ",2);
                        for (ConnectionHandler ch : connections){
                            if(messageSplit[1].equals(ch.lobby.player1)){
                                ch.lobby.finished = true;
                                ch.lobby.playerLeft = true;
                                ch.write.println("playerLeft" + ' ' + ch.lobby.player1);
                            }else if(messageSplit[1].equals(ch.lobby.player2)){
                                ch.lobby.finished = true;
                                ch.lobby.playerLeft = true;
                                ch.write.println("playerLeft" + ' ' + ch.lobby.player2);
                            }
                        }
                    }else if(message.startsWith("StartGame")){
                        String[] messageSplit = message.split(" ",2);
                        if(clientName != null && lobby.player1 != null && lobby.player2 != null){
                            write.println("StartGame");
                            lobby.running = true;
                        }
                        for (ConnectionHandler ch : connections){
                            if(ch.clientName != null && ch.lobby.player1 != null && ch.lobby.player2 != null){
                                if((ch.lobby.player1.equals(messageSplit[1]) || ch.lobby.player2.equals(messageSplit[1])) && !ch.clientName.equals(messageSplit[1])){
                                    ch.write.println("StartGame");
                                    ch.lobby.running = true;
                                }
                            }
                        }
                    }else if(message.startsWith("SendCoordinates")){
                        String[] messageSplit = message.split(" ",6);
                        for (ConnectionHandler ch : connections){
                            if(!ch.clientName.equals(messageSplit[1]) && (ch.lobby.player1.equals(messageSplit[1]) || ch.lobby.player2.equals(messageSplit[1]))){
                                ch.write.println("Coordinates" + ' ' + messageSplit[2] + ' ' + messageSplit[3] + ' ' + messageSplit[4] + ' ' + messageSplit[5]);
                            }
                        }
                    }else if(message.startsWith("DeleteLobby")){
                        String[] messageSplit = message.split(" ",2);
                        for (ConnectionHandler ch : connections){
                            if(ch.lobby.player1.equals(messageSplit[1]) || ch.lobby.player2.equals(messageSplit[1])){
                                mySqlDatabase.deleteLobby(ch.lobby.player1);
                                //ch.resetLobby();
                            }
                        }
                    }else if(message.startsWith("ShutDown")){
                        shutdown();
                    }
                }
            } catch (Exception e) {
                try {
                    shutdown();
                    System.out.println("Shutdown");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        /**
         * Close the connection between server and client
         * @throws SQLException
         */
        public void shutdown() throws SQLException {
            System.out.println(clientName);
            print();
            for (ConnectionHandler ch : connections){
                if(ch.lobby.player1 != null &&  ch.lobby.running && !ch.clientName.equals(clientName) && (ch.lobby.player1.equals(clientName) || ch.lobby.player2.equals(clientName))){
                    mySqlDatabase.deleteLobby(ch.lobby.player1);
                    ch.write.println("playerLeft" + ' ' + clientName);
                    System.out.println("PlayerLeft " + clientName + " send to " + ch.clientName);
                }
                if(ch.lobby.player2 != null && ch.lobby.player2.equals(clientName) && !ch.clientName.equals(clientName) && !ch.lobby.running){
                    ch.lobby.player2 = null;
                    this.lobby.player1 = null;
                    this.lobby.player2 = null;
                    mySqlDatabase.leaveLobby(clientName);
                    System.out.println(clientName + " left lobby");
                    ch.write.println("leftLobby" + ' ' + clientName);
                    break;
                }
                if(ch.lobby.player1 != null &&  ch.lobby.player1.equals(clientName) && !ch.lobby.running){
                    mySqlDatabase.deleteLobby(clientName);
                    if(!ch.clientName.equals(clientName)){
                        System.out.println(clientName + " left lobby");
                        ch.write.println("LobbyDeleted");
                    }
                }
            }
            if(!client.isClosed()){
                try {
                    read.close();
                    write.close();
                    client.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            connections.remove(this);
        }
        public void print(){
            System.out.println("Print:");
            for(ConnectionHandler ch : connections){
                System.out.println(ch.clientName);
                System.out.println(ch.lobby.player1);
                System.out.println(ch.lobby.player2);
                System.out.println();
            }
        }
        public void resetLobby(){
            lobby.running = false;
            lobby.player1 = null;
            lobby.player2 = null;
            lobby.player1Score = 0;
            lobby.player2Score = 0;
            lobby.level = null;
            lobby.player1Finished =false;
            lobby.player2Finished = false;
            lobby.finished = false;
            lobby.playerLeft = false;
        }
    }
    /**
     * Launch server
     * @param args
     */
    public static void main(String[] args){
        Server server = new Server();
        server.run();
    }
}
