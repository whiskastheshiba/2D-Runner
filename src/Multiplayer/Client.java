package Multiplayer;

import Enviroment.mySqlDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

/**
 * Class responsible for player connection with the server for multiplayer game
 */

public class Client implements Runnable{
    /**
     * Client socket
     */
    private Socket client;
    /**
     * Represent the state of client, if equals true the client is shuted down
     */
    private boolean done = false;
    /**
     * Message received from server
     */
    private BufferedReader read;
    /**
     * Message written to the server
     */
    private PrintWriter write;
    /**
     * Player username
     */
    private String userName;
    /**
     * Show if another player left or joined lobby
     */
    public boolean isAction = false;
    /**
     * Show if game has started
     */
    public boolean isGameStarted = false;

    /**
     * Show if game has finished
     */
    public boolean gameFinished = false;

    /**
     * Show if player finished the game
     */
    public boolean finished;

    /**
     * Show is player won
     */
    public boolean isWinner = false;
    /**
     * Show is player loose
     */
    public boolean isLooser = false;
    /**
     * Represent the state of lobby deletion
     */
    public boolean isLobbyDeleted = false;
    /**
     * Represent second player x axis coordinates
     */
    public int secondPlayerX;
    /**
     * Represent second player y axis coordinates
     */
    public int secondPlayerY;
    /**
     * Represent second player x axis velocity
     */
    public float secondPlayerVelX;
    /**
     * Represent second player y axis velocity
     */
    public float secondPlayerVelY;

    /**
     * Boolean value which determines if the second player has left
     */
    public boolean secondPlayerLeft = false;

    /**
     * Boolean value which determines if the second player has finished the level
     */
    public boolean secondPlayerFinished;

    /**
     * Sets the client's username
     * @param userName clients username
     */

    public Client(String userName) {
        this.userName = userName;
    }

    /**
     * Connects client to the server and listen for output and input
     */
    @Override
    public void run() {
        try {
            client = new Socket("127.0.0.1",9999);
            write = new PrintWriter(client.getOutputStream(),true);
            read = new BufferedReader(new InputStreamReader(client.getInputStream()));
            InHandler inHandler = new InHandler();
            Thread t = new Thread(inHandler);
            t.start();


        } catch (Exception e) {
            try {
                shutdown();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

    /**
     * Send message to the server that user created a lobby
     * @param level specifies the name of the level selected
     */
    public void createLobby(String level){
        write.println( "CreateLobby" + ' ' + userName + ' ' + level );
    }

    /**
     *Send message to the server that user joined a lobby
     * @param id specifies lobby id in database
     */
    public void joinLobby(int id){
        write.println("JoinLobby" + ' ' + Integer.toString(id) + ' ' + userName);
    }

    /**
     * Send message to the server that user left a lobby
     */
    public void leaveLobby(){write.println("LeaveLobby" + ' ' + userName);}

    /**
     * Send message to the server that user started the game
     */
    public void startGame(){write.println("StartGame" + ' ' + userName);}

    /**
     * Send message to the server that user finished the game
     */
    public void finishedGame(){
        write.println("Finished" + ' ' +userName);
    }

    /**
     * Send users score to the server
     * @param score specifies the user score
     */
    public void sendScore(String score){

        write.println("Score" + ' ' + score + ' ' + userName);
    }

    /**
     * Send user coordinates to the server
     * @param x specifies the user x axis coordinates
     * @param y specifies the user y axis coordinates
     * @param velX specifies the user x axis velocity
     * @param velY specifies the user y axis velocity
     */
    public void sendCoordinates(int x,int y,int velX,int velY){
        write.println("SendCoordinates" + ' ' + userName + ' '+ x + ' '+ y + ' ' + velX + ' ' + velY );
    }

    /**
     * Send message to the server that user left the ongoing game
     */
    public void leaveGame(){
        write.println("LeftGame" + ' ' + userName);
        System.out.println("LeftGame" + ' ' + userName);
    }

    public void deleteLobby(){
        write.println("DeleteLobby" + ' ' + userName);
    }
    /**
     * shutdown the connection with server
     * @throws IOException
     */
    public void sendShutDown(){
        write.println("ShutDown");
    }
    public void shutdown() throws IOException {
        done = true;
        try {
            sendShutDown();
            if(read != null){
                read.close();
            }
            if(write != null){
                write.close();
            }
            mySqlDatabase.leaveLobby(userName);
            mySqlDatabase.deleteLobby(userName);

            if(client != null){
                if(!client.isClosed()){
                    client.close();
                }
            }
            System.out.println("shutdown done");
        }catch  (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Class handle the input from server
     */
        class InHandler implements Runnable{

        /**
         * Listen to the server output and perform necessary operations based on received messages
         */
        @Override
        public void run() {
            while (!done){
                try {
                    String message;
                    while ((message = read.readLine()) != null){
                        if (message.equals("JoinedLobby")) {
                            isAction = true;
                        }
                        else if(message.equals("LeftLobby")){
                            isAction = true;
                        }
                        else if(message.equals("LobbyDeleted")){
                            isLobbyDeleted = true;
                        }else if(message.equals("StartGame")){
                            isGameStarted = true;
                        }else if(message.startsWith("Finished")){
                            String[] messageSplit = message.split(" ",2);
                            if (messageSplit[1].equals(userName)) {
                                finished = true;
                            }else{
                                secondPlayerFinished = true;
                            }
                        }else if(message.startsWith("Won")){
                            String[] messageSplit = message.split(" ",2);
                            if(messageSplit[1].equals(userName)){
                                isWinner = true;
                                isLooser = false;
                                gameFinished = true;
                            }else{
                                isWinner = false;
                                isLooser = true;
                                gameFinished = true;
                            }
                        }else if(message.startsWith("GameFinished")){
                            gameFinished = true;
                        }else if(message.startsWith("Coordinates")){
                            String[] messageSplit = message.split(" ",5);
                            if(messageSplit.length == 5){
                                secondPlayerX = Integer.parseInt(messageSplit[1]);
                                secondPlayerY = Integer.parseInt(messageSplit[2]);
                                secondPlayerVelX = Float.parseFloat(messageSplit[3]);
                                secondPlayerVelY = Float.parseFloat(messageSplit[4]);
                            }
                        }else if(message.startsWith("playerLeft")){
                            String[] messageSplit = message.split(" ",2);
                            if(!messageSplit[1].equals(userName)){
                                System.out.println("I won " + userName);
                                isWinner = true;
                                isLooser = false;
                                gameFinished = true;
                                secondPlayerLeft = true;
                            }

                        }
                    }
                } catch (IOException e) {
                    try {
                        read.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
