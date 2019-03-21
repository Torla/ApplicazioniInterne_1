package com.test.demo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class RegistrationVM {
	@Size(min=1, max=255, message = "name has to be between 1 and 255")
	String user;
	@Size(min=1, max=255, message = "surname has to be between 1 and 255")
	String surname;
	@Email(message = "Email not valid")
	String email;
	@Size(min=5, max=15, message = "password has to be between 5 and 15")
	String password;
	@Size(min=5, max=15)
	String passwordConf;
}
