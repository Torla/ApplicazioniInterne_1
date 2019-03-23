package com.test.demo.ViewModels;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class RegistrationVM {
  @Size(min = 1, max = 255, message = "Name length must be between 1 and 255")
  public String name;
  @Size(min = 1, max = 255, message = "Surname length must be between 1 and 255")
  public String surname;
  @Email(message = "Email field must contains a valid email")
  @Size(min = 1, max = 255, message = "Email length must be between 1 and 255")
  public String email;
  @Size(min = 5, max = 15, message = "Password length must be between 5 and 15")
  public String password;
  @Size(min = 5, max = 15)
  public String passwordConf;
}
