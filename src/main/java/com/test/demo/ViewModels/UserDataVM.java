package com.test.demo.ViewModels;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Data
@Entity
public class UserDataVM {
  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private String surname;
  private String email;
  private String password;
}
