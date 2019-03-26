package com.test.demo.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class LoginRecord {
	@Id
	@GeneratedValue
	private Long id;
	private String email;
	private String cookie;
	private LocalDateTime localDateTime;
}
