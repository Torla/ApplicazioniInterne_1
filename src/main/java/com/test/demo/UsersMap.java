package com.test.demo;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class UsersMap {
	private Map<String,UserData> map = new HashMap<>();

	public void addUser(String name,String surname,String email,String psw){
		UserData userData = UserData.builder()
				.name(name)
				.surname(surname)
				.email(email)
				.password(psw)
				.build();
		map.put(userData.getEmail(),userData);
		System.out.println(map);
	}

}
