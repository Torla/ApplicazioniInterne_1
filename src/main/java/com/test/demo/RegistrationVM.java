package com.test.demo;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class RegistrationVM {
	@Size(min=1, max=255)
	String user;
}
