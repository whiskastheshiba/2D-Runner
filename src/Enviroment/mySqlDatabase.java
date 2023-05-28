package Enviroment;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import Game.*;
import java.util.Scanner;


/**
 * The class for handling all requests related to databases.
 */
public  class mySqlDatabase {


    /**
     * Connection to the database. Static so it can be used without having to create a mySqlDatabase class instance
     */
    private static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://sql8.freesqldatabase.com:3306/sql8620831", "sql8620831", "MdrBWFxCet");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A method to close the game's connection to the database when the user quits the game.
     */
    public static void closeConn(){
        conn = null;
        System.out.println("Conn closed");
    }


    /**
     * Saves the level result in the database.
     * If the user has never completed this level, the method creates a new insert.
     * If the user has previously completed this level, the method checks if this score is greater than his previous high score.
     * If it is, the high score table is updated with the new high score.
     * @param level which level the user completed
     * @param score what score he got
     * @param userID what is the usersID in the database
     * @throws SQLException
     */
    public static void saveLevelResult(String level, int score, String userID) throws SQLException {            // Sends the result to database which is hosted in free hosting service

        Statement statement = conn.createStatement();
        String sqlInsert = "INSERT INTO HighScores (levelID, score,userID) VALUES (?, ?, ?) ";

        PreparedStatement selectStatement = conn.prepareStatement("SELECT * FROM HighScores WHERE levelID = ? AND userID = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        selectStatement.setString(1, level);
        selectStatement.setString(2, userID);

        ResultSet playerRun = selectStatement.executeQuery();
        playerRun.last();
        int rowCount = playerRun.getRow();
        System.out.println(rowCount);
        if(rowCount == 0){
            PreparedStatement thisStatement = conn.prepareStatement(sqlInsert);
            thisStatement.setString(1, level);
            thisStatement.setString(2, Integer.toString(score));
            thisStatement.setString(3, userID);

            thisStatement.executeUpdate();

            ResultSet resultSet = statement.executeQuery("select * from HighScores");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("userID") +" "+ resultSet.getString("levelID")+" "+ resultSet.getString("score"));
            }
            System.out.println("Saved players first score in this level");
        }else{
            System.out.println(score);
            int runID = Integer.parseInt(playerRun.getString("runID"));
            if(Integer.parseInt(playerRun.getString("score")) < score){
                PreparedStatement updateBestRun = conn.prepareStatement("Update HighScores SET score = ? WHERE runID = ?");
                updateBestRun.setString(1, Integer.toString(score));
                updateBestRun.setString(2, Integer.toString(runID));
                updateBestRun.executeUpdate();
                ResultSet resultSet = statement.executeQuery("select * from HighScores");

                while (resultSet.next()) {
                    System.out.println(resultSet.getString("userID") +" "+ resultSet.getString("levelID")+" "+ resultSet.getString("score"));
                }
                System.out.println("Updated the score");
            }
        }
    }


    /**
     * Gets users ID from his username
     * @return
     * @throws SQLException
     */
    public static int getUserID(String userName) throws SQLException {
        int id;
        if(!doesUserExist(userName)){
            insertUser(userName);
        }
        Statement statement = conn.createStatement();
        String sqlSelect = "SELECT * FROM Users WHERE userName = ? ";
        PreparedStatement thisStatement = conn.prepareStatement(sqlSelect, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        thisStatement.setString(1, userName);

        ResultSet rs = thisStatement.executeQuery();
        rs.next();
        id = Integer.parseInt(rs.getString("id"));
        return id;
    }


    /**
     * Checks if user already exists in the users table.
     * @return true if user exists, false if user does not exist
     * @throws SQLException
     */
    public static boolean doesUserExist(String userName) throws SQLException {
        Statement statement = conn.createStatement();
        String sqlSelect = "SELECT * FROM Users WHERE userName = ? ";
        PreparedStatement thisStatement = conn.prepareStatement(sqlSelect, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        thisStatement.setString(1, userName);

        ResultSet rs = thisStatement.executeQuery();
        rs.last();
        if(rs.getRow() == 0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Checks whether a user is online
     * @param userName user name
     * @return true if is online, false if is not online
     * @throws SQLException
     */
    public static boolean isUserOnline(String userName) throws SQLException {
        Statement statement = conn.createStatement();
        String sqlSelect = "SELECT * FROM Users WHERE userName = ? and Online = 1";
        PreparedStatement thisStatement = conn.prepareStatement(sqlSelect, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        thisStatement.setString(1, userName);
        ResultSet rs = thisStatement.executeQuery();
        rs.last();
        if(rs.getRow() == 0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Sets the user to be online
     * @param userName username
     * @throws SQLException
     */
    public static void setUserOnline(String userName) throws SQLException {
        PreparedStatement updateUser = conn.prepareStatement("Update Users SET Online = 1 WHERE username = ?");

        updateUser.setString(1,userName);

        updateUser.executeUpdate();
    }

    /**
     * Sets the user to be offline
     * @param userName
     * @throws SQLException
     */
    public static void setUserOffline(String userName) throws SQLException {
        PreparedStatement updateUser = conn.prepareStatement("Update Users SET Online = 0 WHERE username = ?");

        updateUser.setString(1,userName);

        updateUser.executeUpdate();
    }
    /**
     * Inserts a new user in the users table.
     * @throws SQLException
     */
    public static void insertUser(String userName) throws SQLException {
        Statement statement = conn.createStatement();
        String sqlSelect = "INSERT INTO Users (userName,Online) VALUES (?,?)";
        PreparedStatement thisStatement = conn.prepareStatement(sqlSelect, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        thisStatement.setString(1, userName);
        thisStatement.setString(2, "0");
        thisStatement.executeUpdate();
    }


    /**
     * Gets all the ids of available lobbies.
     * @return an array of integers
     * @throws SQLException
     */
    public static int[] getLobbyIds() throws SQLException {
        Statement statement = conn.createStatement();
        String select = "SELECT * FROM Lobbies";
        PreparedStatement thisStatement = conn.prepareStatement(select, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = thisStatement.executeQuery();
        int[] lobbyIDs = new int[100];
        int i = 0;
        while(rs.next()){
            lobbyIDs[i] = Integer.parseInt(rs.getString("id"));
            i++;
        }
        return lobbyIDs;
    }

    /**
     * Returns a string of both player names, the level name and lobby id seperated by space.
     * @param id id of lobby
     * @return string of both player names, the level name and lobby id seperated by space.
     * @throws SQLException
     */

    public static String getLobbyInfo(int id) throws SQLException {
        Statement statement = conn.createStatement();
        String select = "SELECT * FROM Lobbies WHERE ID = ?";
        PreparedStatement thisStatement = conn.prepareStatement(select, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        thisStatement.setString(1, Integer.toString(id));

        ResultSet rs = thisStatement.executeQuery();
        String returnStr = "";
        while(rs.next()){
            returnStr = returnStr + rs.getString("ID") + " " + rs.getString("Player_1") + " " +  rs.getString("Level") + " " + rs.getString("Player_2");
        }
        return returnStr;
    }

    /**
     * Gets the id of the new lobby a player has just created.
     * @param userName players userName
     * @return id of lobby the player is currently in
     * @throws SQLException
     */

    public static int getSpecificLobbyID(String userName) throws SQLException {
        Statement statement = conn.createStatement();
        String select = "SELECT * FROM Lobbies WHERE Player_1 = ?";
        PreparedStatement thisStatement = conn.prepareStatement(select, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        thisStatement.setString(1, userName);

        ResultSet rs = thisStatement.executeQuery();
        int id = 0;
        while(rs.next()){
            id = Integer.parseInt(rs.getString("ID"));
        }
        return id;
    }

    /**
     * Gets player1 of specific lobby by lobby ID.
     * @param ID lobby id
     * @return player 1 username
     * @throws SQLException
     */

    public static String getSpecificPlayer1(String ID) throws SQLException {
        String select = "SELECT * FROM Lobbies WHERE ID = ?";
        PreparedStatement thisStatement = conn.prepareStatement(select, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        thisStatement.setString(1, ID);

        ResultSet rs = thisStatement.executeQuery();
        String player = "";
        while(rs.next()){
            player = rs.getString("Player_1");
        }
        return player;
    }
    /**
     * Creates a new lobby. Sets the player_1 to the current username and the level to the level which the user chose.
     * @param player1 player 1 username
     * @param level level name
     * @throws SQLException
     */
    public static void createLobby(String player1,String level) throws SQLException {
        Statement statement = conn.createStatement();
        String sqlInsert = "INSERT INTO Lobbies (Player_1,Running,Level) VALUES (?, ?, ?) ";
        PreparedStatement thisStatement = conn.prepareStatement(sqlInsert);
        thisStatement.setString(1, player1);
        thisStatement.setString(2, Integer.toString(0));
        thisStatement.setString(3, level);

        thisStatement.executeUpdate();
    }

    /**
     * Lets a player join a lobby.
     * @param id lobby ID
     * @param player_2 username of player who wants to join
     * @throws SQLException
     */
    public static void joinLobby(String id, String player_2) throws SQLException {
        PreparedStatement updateLobby = conn.prepareStatement("Update Lobbies SET Player_2 = ?,Running = ? WHERE ID = ?");

        updateLobby.setString(1,player_2);
        updateLobby.setString(2,"1");
        updateLobby.setString(3,id);

        updateLobby.executeUpdate();
    }


    /**
     * Deletes a specific lobby.
     * @param player player username
     * @throws SQLException
     */
    public static void deleteLobby(String player) throws SQLException {
        PreparedStatement deleteLobby = conn.prepareStatement("Delete FROM Lobbies WHERE Player_1 = ?");
        deleteLobby.setString(1,player);

        deleteLobby.execute();
    }

    /**
     * Updates the lobby and sets player 2 username to null
     * @param player player2 username
     * @throws SQLException
     */
    public static void leaveLobby(String player) throws SQLException {
        PreparedStatement updateLobby = conn.prepareStatement("Update Lobbies SET Player_2 = NULL,Running = 0 WHERE Player_2 = ?");

        updateLobby.setString(1,player);

        updateLobby.executeUpdate();
    }

    /**
     * Gets the highest score of all time for a specific level.
     * @param level level
     * @return String in which the information is kept
     * @throws SQLException
     */
   public static String getHighestScoreOfAllTime(String level) throws SQLException {

       PreparedStatement selectStatement = conn.prepareStatement("SELECT score FROM HighScores  WHERE levelID = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
       selectStatement.setString(1, level);

       ResultSet playerRun = selectStatement.executeQuery();
       String highestScore = "0";
       while(playerRun.next()){
           highestScore = playerRun.getString("score");
       }
       return highestScore;
   }

    /**
     * Gets the highest score of all time for a specific user in a specific level
     * @param level level
     * @param userID user ID
     * @return String in which the information is kept
     * @throws SQLException
     */
    public static String getUsersHighestScoreOfAllTime(String level, String userID) throws SQLException {
        PreparedStatement selectStatement = conn.prepareStatement("SELECT score FROM HighScores WHERE levelID = ? AND userID = ? ORDER BY score ASC", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        selectStatement.setString(1, level);
        selectStatement.setString(2, userID);

        ResultSet playerRun = selectStatement.executeQuery();
        String highestScore = "0";
        while(playerRun.next()){
            highestScore = playerRun.getString("score");
        }
        return highestScore;
    }
}