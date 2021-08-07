package com.chukwu.MyStreamSite.data;

import com.chukwu.MyStreamSite.model.Channel;
import com.chukwu.MyStreamSite.model.User;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@Component
public class UserRepo {
  final private static int number = 1;
  private static List<User> mods;
  private PreparedStatement preparedStatement;

  public static Connection connect() {
    Connection conn = null;
    try {
      // db parameters
      String url = "jdbc:sqlite:C:/sqlite/StreamDB.db";
      // create a connection to the database
      conn = DriverManager.getConnection(url);

      System.out.println("Connection to SQLite has been established.");

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return conn;
  }


  private static List<User> selectMods() throws SQLException {

    mods = new ArrayList<>();
    String
        sql =
        "SELECT users.* from users,channel_mods WHERE channel_mods.user_id = users.id AND channel_mods.channel_id = ?;";

    try {
      Connection conn = connect();
      PreparedStatement preparedStatement =
          conn.prepareStatement(sql);
      preparedStatement.setInt(1, number);
      ResultSet rs = preparedStatement.executeQuery();

      // loop through the result set
      while (rs.next()) {

        mods.add(new User(rs.getString("name")));


      }
      System.out.println(mods.get(0).getName());
      System.out.println(mods.get(1).getName());
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return mods;
  }

  //This method collects the Strings of all the channels a user is a mod for.
  public static List<Channel> getUserMods(String userToken) throws SQLException {
    List<Channel> channelsModFor = new ArrayList<>();
    String
        sql =
        "SELECT channels.* from channels,channel_mods WHERE user_id = channel_mods.user_id AND user_id = ?;";

    try {
      Connection conn = connect();
      PreparedStatement preparedStatement =
          conn.prepareStatement(sql);
      preparedStatement.setInt(1, number);
      ResultSet rs = preparedStatement.executeQuery();

      // loop through the result set
      while (rs.next()) {

        channelsModFor.add(new Channel(rs.getString("name")));


      }
      System.out.println(channelsModFor.get(0).getName());

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return channelsModFor;
  }


  public static List<User> getMods() throws SQLException {
    selectMods();
    return mods;
  }

  public boolean addUser(){
    
  }


  public static void main(String args[]) throws SQLException {
getUserMods("temp");

  }


}
