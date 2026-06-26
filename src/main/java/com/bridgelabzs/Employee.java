package com.bridgelabzs;

import java.sql.Date;

public class Employee {
private int id;
private String name;
private char gender;
private int salary;
private Date start_Date;
private boolean isActive;
public Employee() {
	super();
}
public Employee(int id, String name, char gender, int salary, Date start_Date) {
	super();
	this.id = id;
	this.name = name;
	this.gender = gender;
	this.salary = salary;
	this.start_Date = start_Date;
	this.isActive=true;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public char getGender() {
	return gender;
}

public void setGender(char gender) {
	this.gender = gender;
}

public int getSalary() {
	return salary;
}

public void setSalary(int salary) {
	this.salary = salary;
}

public Date getStart_Date() {
	return start_Date;
}

public void setStart_Date(Date start_Date) {
	this.start_Date = start_Date;
}
public boolean isActive() {
	return isActive;
}

public void setActive(boolean isActive) {
	this.isActive=isActive;
}

@Override
public String toString() {
	return "Employee [id=" + id + ", name=" + name + ", gender=" + gender + ", salary=" + salary + ", start_Date="
			+ start_Date + ",isActive=" + isActive + "]";
}
	@Override
		public boolean equals(Object obj) {
			Employee emp2=(Employee) obj;
			return emp2.getId()==id && emp2.getGender()==gender && start_Date.equals(emp2.start_Date) && emp2.getSalary()==salary && emp2.isActive()==isActive;
		}
	
	}
