package com.chukwu.MyStreamSite.controller;


import com.chukwu.MyStreamSite.data.UserRepo;
import com.chukwu.MyStreamSite.model.Channel;
import com.chukwu.MyStreamSite.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;


import java.sql.SQLException;
import java.util.List;

@Controller
public class UserController {

  @Autowired
  private UserRepo userRepo;

  @RequestMapping("/")

  public String listGifs(ModelMap modelMap) throws SQLException {
    List<User> allGifs = userRepo.getMods();
    modelMap.put("gifs", allGifs);
    return "index.html";

  }

  @RequestMapping("/user/info")

  public String listofMod(ModelMap modelMap) throws SQLException {


    List <Channel> userChannelMods = userRepo.getUserMods("1");
    modelMap.put("listofChannels", userChannelMods);
    return "index.html";

  }
}