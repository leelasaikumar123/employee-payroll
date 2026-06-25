package com.bridgelabzs;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
public int updateAnSqlERecord() {
	int n=-1;
   try {
	Connection con=DBConnection.getConnection();
	Statement st=con.createStatement();
	n=st.executeUpdate("update employee_payroll set salary=30000 where name='Tanuja' ");
} catch (EmployeePayRollException | SQLException e) {
	
	e.printStackTrace();
}
 return n;  
}
public int updateAnSqlERecordUsingPreparedStatement() {
	int n=-1;
   try {
	Connection con=DBConnection.getConnection();
	PreparedStatement ps=con.prepareStatement("update employee_payroll set salary=30000 where name='Tanuja' ");
	n=ps.executeUpdate();
} catch (EmployeePayRollException | SQLException e) {
	
	e.printStackTrace();
}
 return n;  
}
public Employee getEmployeePayrollDataByName() {
	Employee emp=null;
	String retriveEmployeeByNameQuery="select * from employee_payroll where name = ?";
   try {
	Connection con=DBConnection.getConnection();
	PreparedStatement ps=con.prepareStatement(retriveEmployeeByNameQuery);
	ps.setString(1,"Tanuja");
	ResultSet res=ps.executeQuery();
	res.next();
	emp=new Employee(res.getInt(1), res.getString(2),res.getString(3).charAt(0), res.getInt(4),res.getDate(5));
	
} catch (EmployeePayRollException | SQLException e) {
	
	e.printStackTrace();
}
 return emp;  
}
public List<Employee> getEmployeesBetweenDates(Date from,Date to){
	List<Employee> emplist=new ArrayList<>();
	String getEmployeesBetweenDatesVariable="select * from employee_payroll where start_Date between ? and ?";
	try {
		Connection con=DBConnection.getConnection();
		PreparedStatement ps=con.prepareStatement(getEmployeesBetweenDatesVariable);
		ps.setDate(1, from);
		ps.setDate(2, to);
		ResultSet res=ps.executeQuery();
		while(res.next()) {
			emplist.add(new Employee(res.getInt(1), res.getString(2),res.getString(3).charAt(0), res.getInt(4),res.getDate(5)));
		}
		
	} catch (EmployeePayRollException | SQLException e) {
		
		e.printStackTrace();
	}
	
	
	return emplist;
}
public Map<Character,Integer> getSumOfTheSalariesOfFemaleEmployees(){
	Map<Character,Integer> employeemap=new LinkedHashMap<>();
	String getSumOfTheSalariesOfFemaleEmployeesVariable="select gender,sum(salary) from employee_payroll where gender='F' GROUP BY gender";
	try {
		Connection con=DBConnection.getConnection();
		PreparedStatement ps=con.prepareStatement(getSumOfTheSalariesOfFemaleEmployeesVariable);
		ResultSet set=ps.executeQuery();
		if(set.next()) {
			employeemap.put(set.getString(1).charAt(0),set.getInt(2));
		}

		
	} catch (EmployeePayRollException | SQLException e) {
		
		e.printStackTrace();
	}
	
	
	return employeemap;
}
public Employee addEmployeeToPayroll(String name,char gender,int salary,Date start_Date) {
	Employee emp=null;
	String addEmployeeToPayrollVariable="insert into employee_payroll(name,gender,salary,start_Date) values(?,?,?,?)";
	
	try {
		Connection con=DBConnection.getConnection();
		PreparedStatement pr=con.prepareStatement(addEmployeeToPayrollVariable,PreparedStatement.RETURN_GENERATED_KEYS);
		pr.setString(1, name);
		pr.setString(2,String.valueOf(gender));
		pr.setInt(3, salary);
		pr.setDate(4, start_Date);
		int n=pr.executeUpdate();
		if(n>0) {
			ResultSet res=pr.getGeneratedKeys();
			res.next();
			int id = res.getInt(1);
			emp=new Employee(id,name,gender,salary,start_Date);
		}
		con.close();
		pr.close();
	} catch (EmployeePayRollException | SQLException e) {		
		e.printStackTrace();
	}
	
	return emp;
}
public int addEmployeeToPayrollAndPayrollDetails(String name,char gender,int salary,Date start_Date) {
int n=-1;
Connection con=null;
String addEmployeeToPayrollVariable="insert into employee_payroll(name,gender,salary,start_Date) values(?,?,?,?)";
try {
	con=DBConnection.getConnection();
	 con=DBConnection.getConnection();
	PreparedStatement pr=con.prepareStatement(addEmployeeToPayrollVariable,PreparedStatement.RETURN_GENERATED_KEYS);
	pr.setString(1, name);
	pr.setString(2,String.valueOf(gender));
	pr.setInt(3, salary);
	pr.setDate(4, start_Date);
	 if(pr.executeUpdate()>0) {
			ResultSet res=pr.getGeneratedKeys();
			res.next();
			int id = res.getInt(1);
			double deductions=salary*0.2;
			double taxablePay=salary-deductions;
			double tax=taxablePay*0.1;
			double netPay=salary-tax;
			PreparedStatement pr2=con.prepareStatement("insert into payroll_details values(?,?,?,?,?,?)");
			pr2.setInt(1, id);
			pr2.setDouble(2, salary);
			pr2.setDouble(3, deductions);
			pr2.setDouble(4, taxablePay);
			pr2.setDouble(5, tax);
			pr2.setDouble(6, netPay);
			n=pr2.executeUpdate();
	 }
} catch (EmployeePayRollException | SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
return n;
}
}
