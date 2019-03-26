package com.test.demo.controllers;

import com.test.demo.ViewModels.LoginVM;
import com.test.demo.ViewModels.RegistrationVM;
import com.test.demo.ViewModels.UserDataVM;
import com.test.demo.db.UsersMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Random;

@Controller
public class MainController {

  private final UsersMap users;

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  public MainController (UsersMap users) {
    this.users = users;
  }

  @GetMapping("/register")
  public String register () {
    return "register";
  }

  @PostMapping("/register")
  public String registerForm (@Valid @ModelAttribute("registrationVM") RegistrationVM vm, BindingResult res) {
    logger.info(vm.toString());

    if (res.hasErrors()) {
      return "register";
    }

    if (!vm.password.equals(vm.passwordConf)) {
      res.addError(new FieldError("registrationVM", "password", "Password not matching"));
      return "register";
    }

    try {
      users.addUser(vm.name, vm.surname, vm.email, vm.password);
    } catch (UsersMap.EmailAlreadyExist emailAlreadyExist) {
      res.addError(
          new FieldError(
              "registrationVM",
              "email",
              vm.email,
              false,
              null,
              null,
              vm.email + " already exists")
      );
      return "register";
    }

    return "redirect:/login";
  }

  @GetMapping("/login")
  public String login () {
    return "login";
  }

  @PostMapping("/login")
  public String loginForm (@Valid @ModelAttribute("loginVM") LoginVM vm, BindingResult res,Model m,HttpServletResponse response) {
    logger.info(vm.toString());

    if (res.hasErrors()) {
      return "login";
    }

    Cookie cookie = new Cookie("login",Long.toHexString(new Random().nextLong()));

    try {
      users.checkLogin(vm.email, vm.password,cookie);
      m.addAttribute("name", users.getUserData(vm.email).getName());
      String imageNumber = String.valueOf(new Random().nextInt(7));
      m.addAttribute("imageNumber", imageNumber);
    } catch (UsersMap.EmailDoesntExist emailDoesntExist) {
      res.addError(
          new FieldError(
              "registrationVM",
              "email",
              vm.email,
              false,
              null,
              null,
              vm.email + " doesnt exists")
      );
      return "login";
    } catch (UsersMap.WrongPassword wrongPassword) {
      res.addError(new FieldError("loginVM", "password", "Wrong password"));
      return "login";
    }

    response.addCookie(cookie);
    return "privatePage";
  }

  @GetMapping("/privatePage")
  public String privatePage(@CookieValue(value = "login", defaultValue = "0") String cookie,Model m){
		UserDataVM  userDataVM;
		try {
		  userDataVM = users.checkCookie(cookie);
		} catch (UsersMap.NotLogged notLogged) {
		  return "login";
		}
		m.addAttribute("name", userDataVM.getName());
		String imageNumber = String.valueOf(new Random().nextInt(7));
		m.addAttribute("imageNumber", imageNumber);
		return "privatePage";
  }

  @ModelAttribute("registrationVM")
  public RegistrationVM getRegistrationVM () {
    return new RegistrationVM();
  }

  @ModelAttribute("loginVM")
  public LoginVM getLoginVM () {
    return new LoginVM();
  }
}
