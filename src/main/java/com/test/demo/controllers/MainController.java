package com.test.demo.controllers;

import com.test.demo.ViewModels.LoginVM;
import com.test.demo.ViewModels.RegistrationVM;
import com.test.demo.db.UsersMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MainController {

  private final UsersMap users;

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  public MainController (UsersMap users) {
    this.users = users;
  }

  @GetMapping("/register")
  public String register (@ModelAttribute("vm") RegistrationVM vm) {
    return "register";
  }

  @PostMapping("/register")
  public String registerForm (@Valid @ModelAttribute("vm") RegistrationVM vm, BindingResult res) {
    logger.info(vm.toString());

    if (res.hasErrors()) {
      return "register";
    }

    if (!vm.password.equals(vm.passwordConf)) {
      res.addError(new FieldError("vm", "password", "Password not matching"));
      return "register";
    }

    try {
      users.addUser(vm.name, vm.surname, vm.email, vm.password);
    } catch (UsersMap.EmailAlreadyExist emailAlreadyExist) {
      res.addError(new FieldError("vm", "email", vm.email + " already exists"));
      return "register";
    }

    return "redirect:/login";
  }

  @GetMapping("/login")
  public String login (@ModelAttribute("vm") LoginVM vm) {
    return "login";
  }

  @PostMapping("/login")
  public String loginForm (@Valid @ModelAttribute("vm") LoginVM vm, BindingResult res, Model m) {
    logger.info(vm.toString());

    if (res.hasErrors()) {
      return "login";
    }

    try {
      users.checkLogin(vm.email, vm.password);
      m.addAttribute("name", users.getUserData(vm.email).getName());
    } catch (UsersMap.EmailDoesntExist emailDoesntExist) {
      res.addError(new FieldError("vm", "email", vm.email + " doesn't exist"));
      return "login";
    } catch (UsersMap.WrongPassword wrongPassword) {
      res.addError(new FieldError("vm", "password", "Wrong password"));
      return "login";
    }

    return "privatePage";
  }
}
