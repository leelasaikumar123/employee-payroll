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
	con.setAutoCommit(false);
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
			con.commit();
	 }
} catch (EmployeePayRollException | SQLException e) {
	// TODO Auto-generated catch block
  try {
	con.rollback();
} catch (SQLException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
	e.printStackTrace();
}
finally {
	try {
		con.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
return n;
}
public int addEmployee(String name,char gender,String phone,String address,Date start_Date,int basicPay,List<String> departments) {

	int n=-1;
	Connection con=null;
	try {
		con=DBConnection2.getConnection();
		con.setAutoCommit(false);
		String addEmployeeVariable="insert into employee(name,gender,phone_number,address,start_Date) values(?,?,?,?,?)";
		PreparedStatement pr=con.prepareStatement(addEmployeeVariable,PreparedStatement.RETURN_GENERATED_KEYS);
		pr.setString(1,name);
		pr.setString(2,String.valueOf(gender));
		pr.setString(3,phone);
		pr.setString(4,address);
		pr.setDate(5,start_Date);
		pr.executeUpdate();
		ResultSet res=pr.getGeneratedKeys();
		res.next();
		int employeeId=res.getInt(1);
		int deductions=(basicPay*20)/100;
		int taxablePay=basicPay-deductions;
		int tax=(taxablePay*10)/100;
		int netPay=basicPay-tax;
		PreparedStatement pr2=con.prepareStatement("insert into payroll values(?,?,?,?,?,?)");

		pr2.setInt(1,employeeId);
		pr2.setInt(2,basicPay);
		pr2.setInt(3,deductions);
		pr2.setInt(4,taxablePay);
		pr2.setInt(5,tax);
		pr2.setInt(6,netPay);
		pr2.executeUpdate();
		for(String department:departments) {
			int deptId=-1;
			PreparedStatement pr3=con.prepareStatement("select dept_id from department where dept_name=?");
			pr3.setString(1,department);
			ResultSet set=pr3.executeQuery();
			if(set.next()) {
				deptId=set.getInt(1);
			}
			else {

				PreparedStatement pr4=con.prepareStatement("insert into department(dept_name) values(?)",PreparedStatement.RETURN_GENERATED_KEYS);
				pr4.setString(1,department);
				pr4.executeUpdate();
				ResultSet set2=pr4.getGeneratedKeys();
				set2.next();
				deptId=set2.getInt(1);
			}
			PreparedStatement pr5=con.prepareStatement("insert into employee_department values(?,?)");
			pr5.setInt(1,employeeId);
			pr5.setInt(2,deptId);
			pr5.executeUpdate();
		}
		con.commit();
		n=1;
	}
	catch(EmployeePayRollException | SQLException e) {
		try {
			con.rollback();
		}
		catch(SQLException e1) {
			e1.printStackTrace();
		}
		e.printStackTrace();
	}
	finally {
		try {
			con.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	return n;
}
}
