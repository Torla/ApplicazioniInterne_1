package com.test.demo.db;

import com.test.demo.ViewModels.UserDataVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UsersMap {

	private final UserDataRepository repository;

	@Autowired
	public UsersMap(UserDataRepository repository) {
		this.repository = repository;
	}

	public class EmailAlreadyExist extends Exception {
  }

  public class EmailDoesntExist extends Exception {
  }

  public class WrongPassword extends Exception {
  }


  static private String pswDigest (String psw) {

    MessageDigest digest = null;

    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    byte[] hash = digest.digest(psw.getBytes());

    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < hash.length; i++) {
      String hex = Integer.toHexString(0xff & hash[i]);
      if (hex.length() == 1) hexString.append('0');
      hexString.append(hex);
    }
    return hexString.toString();
  }

  public void addUser (String name, String surname, String email, String psw) throws EmailAlreadyExist {
    if (repository.countByEmail(email) != 0) {
      throw new EmailAlreadyExist();
    }

    UserDataVM userData = new UserDataVM();
    userData.setEmail(email);
    userData.setName(name);
    userData.setPassword(pswDigest(psw));
    userData.setSurname(surname);
    repository.save(userData);
  }

  public UserDataVM getUserData (String email) throws EmailDoesntExist {
	  UserDataVM userDataVM = repository.findByEmail(email);
	  if(userDataVM == null) throw new EmailDoesntExist();
	  return userDataVM;
  }

  public void checkLogin (String email, String psw) throws EmailDoesntExist, WrongPassword {

  	UserDataVM userData = repository.findByEmail(email);
  	if(userData == null) throw new EmailDoesntExist();
    if (!userData.getPassword().equals(pswDigest(psw))) throw new WrongPassword();
  }

}
