package com.test.demo;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class UsersMap {
	private ConcurrentHashMap<String,UserData> map = new ConcurrentHashMap<>();

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
