package com.test.demo.db;

import com.test.demo.Entity.LoginRecord;
import com.test.demo.ViewModels.UserDataVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;

@Component
public class UsersMap {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final UserDataRepository UserDataRepository;
  private final LoginRecordRepository loginRecordRepository;

  @Autowired
  public UsersMap(UserDataRepository repository, LoginRecordRepository loginRecordRepository) {
    this.UserDataRepository = repository;
    this.loginRecordRepository = loginRecordRepository;
  }

  public class EmailAlreadyExist extends Exception {
  }

  public class EmailDoesntExist extends Exception {
  }

  public class WrongPassword extends Exception {

  }

  public class NotLogged extends Exception {

  }


  static private String pswDigest(String psw) {

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

  @Transactional
  public void addUser(String name, String surname, String email, String psw) throws EmailAlreadyExist {
    if (UserDataRepository.countByEmail(email) != 0) {
      throw new EmailAlreadyExist();
    }

    UserDataVM userData = new UserDataVM();
    userData.setEmail(email);
    userData.setName(name);
    userData.setPassword(pswDigest(psw));
    userData.setSurname(surname);
    UserDataRepository.save(userData);
  }

  public UserDataVM getUserData(String email) throws EmailDoesntExist {
    UserDataVM userDataVM = UserDataRepository.findByEmail(email);
    if (userDataVM == null) throw new EmailDoesntExist();
    return userDataVM;
  }

  @Transactional
  public void checkLogin(String email, String psw, Cookie cookie) throws EmailDoesntExist, WrongPassword {

    UserDataVM userData = UserDataRepository.findByEmail(email);
    if (userData == null) throw new EmailDoesntExist();
    if (!userData.getPassword().equals(pswDigest(psw))) throw new WrongPassword();
    LoginRecord loginRecord = new LoginRecord();
    loginRecord.setUser(UserDataRepository.findByEmail(email));
    loginRecord.setCookie(cookie.getValue());
    loginRecord.setZonedDateTime(ZonedDateTime.now());
    logger.info(ZonedDateTime.now().toString());
    loginRecordRepository.save(loginRecord);
  }

  public UserDataVM checkCookie(String cookie) throws NotLogged {
    LoginRecord loginRecord = loginRecordRepository.findByCookie(cookie);
    if (loginRecord == null) throw new NotLogged();
    return UserDataRepository.findById(loginRecord.getId()).get();
  }
}
