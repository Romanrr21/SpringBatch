package com.rom.employee.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class Employee {

	private Integer id;
	private String firstName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String lastName;

	public Employee( String firstName, String lastName) {
		super();

		
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Employee() {
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {

		return "firstName:" + firstName + "lastName:" + lastName;
	}

}
