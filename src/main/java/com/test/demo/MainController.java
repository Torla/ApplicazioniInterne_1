package com.test.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.*;
import java.util.Set;
import java.util.concurrent.locks.StampedLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class MainController {

	private final UsersMap users;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public MainController(UsersMap users) {
		this.users = users;
	}

	@GetMapping("/register")
	public String register(){
		return "register";
	}
	@PostMapping("/register")
	public String registerForm(@Valid RegistrationVM vm, BindingResult res, Model m){
		logger.info(vm.toString());
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<RegistrationVM>> violations = validator.validate(vm);
		if(!violations.isEmpty()){
			m.addAttribute("errorMsg",violations.iterator().next().getMessage());
			return "register";
		}
		if(!vm.password.equals(vm.passwordConf)){
			m.addAttribute("errorMsg","passwords not matching");
			return "register";
		}
		try {
			users.addUser(vm.user,vm.surname,vm.email,vm.password);
		} catch (UsersMap.EmailAlreadyExist emailAlreadyExist) {
			m.addAttribute("errorMsg","emailAlreadyExist");
		}
		return "register";
	} //todo redirect to login after registration

	@GetMapping("/login")
	public String login(){return "login";}
	@PostMapping("/login")
	public String loginForm(@Valid LoginVM vm, BindingResult res, Model m){
		logger.info(vm.toString());
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<LoginVM>> violations = validator.validate(vm);
		if(!violations.isEmpty()){
			m.addAttribute("errorMsg",violations.iterator().next().getMessage());
			return "login";
		}
		UserData user;
		try {
			user = users.getUserData(vm.email);
		} catch (UsersMap.EmailDoesntExist emailDoesntExist) {
			m.addAttribute("errorMsg","email doesn't exist");
			return "login";
		}
		if(!user.getPassword().equals(vm.password)){
			m.addAttribute("errorMsg","wrong password");
			return "login";
		}
		m.addAttribute("name",user.getName());
		return "privatePage";
	}
}
