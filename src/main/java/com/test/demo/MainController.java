package com.test.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import java.time.LocalTime;

@Controller
public class MainController {

	@Autowired
	private Users users;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/register")
	public String home(){
		return "register";
	}
	@PostMapping("/register")
	public String index(@Valid RegistrationVM vm, BindingResult res, Model m){
		logger.info(vm.toString());
		return "ciao";
	}
}
