package com.test.demo;

import org.springframework.stereotype.Component;

import java.util.HashMap;


@Component
public class Users{
	HashMap<String,UserData> map;

	public void addUser(String name,String surname,String adress,String email,String psw){
		UserData userData = UserData.builder().name(name).build();
		map.put(userData.getEmail(),userData);
	}

}
