package com.bridgelabzs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

}
