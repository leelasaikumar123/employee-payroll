package com.bridgelabzs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection2 {
public static  Connection getConnection() throws EmployeePayRollException {
	Connection con=null;
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("Driver class loaded successfully");
		 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_service","root","Leela@123");
		System.out.println("connection established successfully");
	} catch (ClassNotFoundException | SQLException e) {		
		throw new EmployeePayRollException("unable to fetch employee Data");
	}
	
	return con;
}
}
