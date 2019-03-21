package com.test.demo;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.time.LocalTime;

@Controller
public class MainController {
	@GetMapping("/")
	public String index(Model m){
		m.addAttribute("now", LocalTime.now());
		return "home";
	}
}
