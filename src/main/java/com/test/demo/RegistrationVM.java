package com.test.demo;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class RegistrationVM {
	@Size(min=1, max=255)
	String user;
	@Size(min=1, max=255)
	String surname;
	@Size(min=1, max=255)
	String email;
	@Size(min=5, max=15)
	String password;
	@Size(min=5, max=15)
	String passwordConf;
}
