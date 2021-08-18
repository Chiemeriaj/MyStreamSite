package com.chukwu.MyStreamSite.controller;


import com.chukwu.MyStreamSite.Exceptions.TakenUsernameException;
import com.chukwu.MyStreamSite.data.UserRepo;
import com.chukwu.MyStreamSite.model.Channel;
import com.chukwu.MyStreamSite.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.sql.SQLException;
import java.util.List;

@Controller
public class UserController {

  @Autowired
  private UserRepo userRepo;

  @RequestMapping("/")

  public String listGifs(ModelMap modelMap) throws SQLException {
    List<User> allMods = userRepo.getMods();
    modelMap.put("allMods", allMods);
    System.out.println(allMods.get(0).getName());
    return "index";

  }

  @RequestMapping("/user/info")

  public String listofMod(ModelMap modelMap) throws SQLException {

    List<Channel> userChannelMods = userRepo.getUserMods("1");
    modelMap.put("listofChannels", userChannelMods);
    return "index";

  }

  @RequestMapping(value = "/signup", method = RequestMethod.GET)
  public String user(@ModelAttribute User user, BindingResult errors, ModelMap model)
      throws TakenUsernameException {

    model.put("User", user);

    return "signup";


  }


  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public String saveStudent(@ModelAttribute User user, BindingResult errors, ModelMap model)
      throws TakenUsernameException, SQLException {

    userRepo.addUser(user.getName(), user.getId());
    System.out.println(user.getId() + " " + user.getName());
    model.put("User", user);

    return "signup";


  }
}
