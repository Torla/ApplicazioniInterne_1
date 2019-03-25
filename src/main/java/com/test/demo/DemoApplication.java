package com.test.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.TimeZone;

@SpringBootApplication
public class DemoApplication {

  public static void main (String[] args) {
    TimeZone.setDefault(TimeZone.getTimeZone("IST"));
    ConfigurableApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
  }

}
