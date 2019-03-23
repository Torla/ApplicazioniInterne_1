package com.test.demo.ViewModels;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class LoginVM {
  @NotNull
  @Email(message = "Not valid Email")
  public String email;
  @NotNull
  public String password;
}
