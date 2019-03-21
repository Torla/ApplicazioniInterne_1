package com.test.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class MainController {

	@Autowired
	private UsersMap users;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	@GetMapping("/register")
	public String home(){
		return "register";
	}
	@PostMapping("/register")
	public String index(@Valid RegistrationVM vm, BindingResult res, Model m){
		logger.info(vm.toString());
		if(!vm.password.equals(vm.passwordConf)){
			m.addAttribute("errorMsg","passwords not matching");
			return "register";
		}
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(vm.email);
		if(!matcher.find()) {
			m.addAttribute("errorMsg","email not valid");
			return "register";
		}
		try {
			users.addUser(vm.user,vm.surname,vm.email,vm.password);
		} catch (UsersMap.EmailAlreadyExist emailAlreadyExist) {
			m.addAttribute("errorMsg","emailAlreadyExist");
		}
		return "register";
	}
}
