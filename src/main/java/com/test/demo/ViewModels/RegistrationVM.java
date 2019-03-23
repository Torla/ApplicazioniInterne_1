package com.test.demo.ViewModels;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class RegistrationVM {
  @Size(min = 1, max = 255, message = "name has to be between 1 and 255")
  public String user;
  @Size(min = 1, max = 255, message = "surname has to be between 1 and 255")
  public String surname;
  @Email(message = "Email not valid")
  public String email;
  @Size(min = 5, max = 15, message = "password has to be between 5 and 15")
  public String password;
  @Size(min = 5, max = 15)
  public String passwordConf;
}
