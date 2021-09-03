package com.chukwu.MyStreamSite.controller;


import com.chukwu.MyStreamSite.Exceptions.TakenUsernameException;
import com.chukwu.MyStreamSite.data.UserRepo;
import com.chukwu.MyStreamSite.data.UserTransporter;
import com.chukwu.MyStreamSite.model.Channel;
import com.chukwu.MyStreamSite.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

  @Autowired
  private UserRepo userRepo;

  @RequestMapping("/")

  public String getCookies(ModelMap modelMap, HttpServletRequest request) throws SQLException {

    Cookie[] cookies = request.getCookies();
    String value = "username";
    for (Cookie cookie : cookies) {
      if (value.equals(cookie.getName())) {
        modelMap.put("username", cookie.getValue());
      }
    }
    return "index";

  }

  @GetMapping("/setUsername")
  public String setCookie(HttpServletResponse response) {
    User user;
    if (UserTransporter.isUserAvailable()) {
      user = UserTransporter.getUser();
      Cookie cookie = new Cookie("username", user.getName());
      cookie.setPath("/");
      response.addCookie(cookie);

    }
    return "redirect:/";
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
          throws SQLException {

    String name = user.getName();
    int id = user.getId();
    boolean result = userRepo.addUser(name, id);

    model.put("result", result);
    System.out.println(user.getId() + " " + user.getName());
    model.put("User", user);
    if(result){
      return "redirect:/login";
    }

    return "signup";


  }
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String logUser(@ModelAttribute User user, BindingResult errors, ModelMap model)
          throws TakenUsernameException {

    model.put("User", user);

    return "login";


  }
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String logUserTask(@ModelAttribute User user, BindingResult errors, ModelMap model)
          throws SQLException {
    String name = user.getName();
    int id = user.getId();



    boolean result = userRepo.passwordCheck(name, id);

    model.put("result", result);


    if(result) {
      UserTransporter.setUser(user);

      return "redirect:/setUsername";
    }

    return "login";


  }
}
