package com.test.demo.Entity;

import com.test.demo.ViewModels.UserDataVM;
import lombok.Data;
import org.apache.catalina.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Entity
public class LoginRecord {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	private UserDataVM user;
	private String cookie;
	private ZonedDateTime zonedDateTime;
}
