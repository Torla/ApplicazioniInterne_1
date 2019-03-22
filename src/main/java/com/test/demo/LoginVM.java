package com.test.demo;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class LoginVM {
	@Email(message = "Not valid Email")
	String email;
	String password;
}
