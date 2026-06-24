package com.bridgelabzs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollSystem {

	public static void main(String[] args) {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("Driver class loaded successfully");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_services","root","Leela@123");
		System.out.println("connection established successfully");
	} catch (ClassNotFoundException | SQLException e) {		
		e.printStackTrace();
	}

	}

public List<Employee> readEmployeeData(){
	List<Employee> emplist=new ArrayList<>();
	try {
		Connection con=DBConnection.getConnection();
		Statement st=con.createStatement();
		ResultSet res=st.executeQuery("select * from employee_payroll");
		while(res.next()) {
			emplist.add(new Employee(res.getInt(1), res.getString(2),res.getString(3).charAt(0), res.getInt(4),res.getDate(5)));
		}
	} catch (EmployeePayRollException | SQLException e) {
		
		e.printStackTrace();
	}
	for(Employee emp:emplist) {
		System.out.println(emp);
	}
	return emplist;
	
}
}
