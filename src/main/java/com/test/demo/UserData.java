package com.test.demo;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
public class UserData {
	private String name;
	private String surname;
	private String email;
	private String password;

}
