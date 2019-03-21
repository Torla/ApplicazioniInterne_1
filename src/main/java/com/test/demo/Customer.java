package com.test.demo;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Value
public class Customer {
	private Person myPerson;

	@Autowired
	public Customer(Person person){
		this.myPerson=person;
	}
}

@Component
interface Person{}

@Component
class PersonImpl implements Person{}
