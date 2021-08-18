package com.chukwu.MyStreamSite.data;

import com.chukwu.MyStreamSite.Exceptions.TakenUsernameException;
import com.chukwu.MyStreamSite.model.Channel;
import com.chukwu.MyStreamSite.model.User;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


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
    connect().close();
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

    connect().close();
    return channelsModFor;
  }


  public static List<User> getMods() throws SQLException {
    selectMods();
    return mods;
  }

  public static void addUser(String name, int id) throws TakenUsernameException, SQLException {

    if (isUser(name)) {
      throw new TakenUsernameException("That user already exist!!!");
    } else {

      String sql = "INSERT INTO users(id,name) VALUES(?,?)";

      try {
        Connection conn = connect();
        PreparedStatement preparedStatement =
                conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, name.toLowerCase());
        preparedStatement.executeUpdate();


      } catch (SQLException e) {
        System.out.println(e);
      }

      connect().close();
    }
  }

  public static boolean isUser(String username) throws SQLException {
    List<User> allUser = new ArrayList<>();
    Boolean result = false;
    String sql =
            "SELECT users.* from users WHERE ? = users.name;";

    try {
      Connection conn = connect();
      PreparedStatement preparedStatement =
              conn.prepareStatement(sql);
      preparedStatement.setString(1, username.toLowerCase());
      ResultSet rs = preparedStatement.executeQuery();
      String match = rs.getString("name");

      result = match.toLowerCase(Locale.ROOT).equals(username.toLowerCase(Locale.ROOT));

      System.out.println(match);
      System.out.println(match + "hmmm" + username);

      System.out.println(result);


    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    connect().close();
    return result;
  }


  public static void main(String args[]) throws SQLException {

    isUser("Chuka");

  }


}
