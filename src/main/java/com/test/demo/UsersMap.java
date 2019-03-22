package com.test.demo;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class UsersMap {
	private ConcurrentHashMap<String,UserData> map = new ConcurrentHashMap<>();

	public class EmailAlreadyExist extends Exception{}
	public class EmailDoesntExist extends Exception{}
	public class WrongPassword extends Exception{}


	static private String pswDigest(String psw) {

		MessageDigest digest=null;

		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		byte[] hash = digest.digest(psw.getBytes());

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if(hex.length() == 1) hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public void addUser(String name,String surname,String email,String psw) throws EmailAlreadyExist {
		if(map.containsKey(email)){
			throw new EmailAlreadyExist();
		}


		UserData userData = new  UserData();
		userData.setEmail(email);
		userData.setName(name);
		userData.setPassword(pswDigest(psw));
		userData.setSurname(surname);
		map.put(userData.getEmail(),userData);
		System.out.println(map);
	}

	public UserData getUserData(String email) throws EmailDoesntExist {
		if(!map.containsKey(email)) throw new EmailDoesntExist();
		return map.get(email);
	}

	public void checkLogin(String email,String psw) throws EmailDoesntExist, WrongPassword {
		UserData userData = getUserData(email);
		if(!userData.getPassword().equals(pswDigest(psw))) throw new WrongPassword();

	}

}
