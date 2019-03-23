package com.test.demo.ViewModels;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class LoginVM {
	@Email(message = "Not valid Email")
	public String email;
	public String password;
}
