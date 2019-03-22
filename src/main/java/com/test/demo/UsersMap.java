package com.test.demo;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class UsersMap {
	private ConcurrentHashMap<String,UserData> map = new ConcurrentHashMap<>();

	public class EmailAlreadyExist extends Exception{}
	public class EmailDoesntExist extends Exception{}

	public void addUser(String name,String surname,String email,String psw) throws EmailAlreadyExist {
		if(map.containsKey(email)){
			throw new EmailAlreadyExist();
		}
		UserData userData = UserData.builder()
				.name(name)
				.surname(surname)
				.email(email)
				.password(psw)
				.build();
		map.put(userData.getEmail(),userData);
		System.out.println(map);
	}

	public UserData getUserData(String email) throws EmailDoesntExist {
		if(!map.containsKey(email)) throw new EmailDoesntExist();
		return map.get(email);
	}

}
