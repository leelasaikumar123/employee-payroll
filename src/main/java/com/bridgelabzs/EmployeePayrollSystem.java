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
		String readEmployeeDataVariable="select e.id,e.name,e.gender,p.basic_pay,e.start_Date from employee e join payroll p on e.id=p.employee_id where e.is_active=true;";
		try {
			Connection con=DBConnection2.getConnection();
			PreparedStatement pr=con.prepareStatement(readEmployeeDataVariable);
			ResultSet res=pr.executeQuery();
			while(res.next()) {
				emplist.add(new Employee(res.getInt(1),res.getString(2),res.getString(3).charAt(0),res.getInt(4),res.getDate(5)));
			}
		}
		catch(EmployeePayRollException | SQLException e) {
			e.printStackTrace();
		}
		return emplist;
	}


public int updateAnSqlERecord() {
	int n=-1;
	String updateSalaryVariable="update payroll p join employee e on p.employee_id=e.id set p.basic_pay=30000 where e.name='Tanuja' and e.is_active=true;";
	try {
		Connection con=DBConnection2.getConnection();
		Statement st=con.createStatement();
		n=st.executeUpdate(updateSalaryVariable);
	}
	catch(EmployeePayRollException | SQLException e) {
		e.printStackTrace();
	}
	return n;
}

public int updateAnSqlERecordUsingPreparedStatement() {
	int n=-1;
	String updateSalaryVariable="update payroll p join employee e on p.employee_id=e.id set p.basic_pay=? where e.name=? and e.is_active=true;";
	try {
		Connection con=DBConnection2.getConnection();
		PreparedStatement pr=con.prepareStatement(updateSalaryVariable);
		pr.setInt(1,30000);
		pr.setString(2,"Tanuja");
		n=pr.executeUpdate();
	}
	catch(EmployeePayRollException | SQLException e) {
		e.printStackTrace();
	}
	return n;
}

public Employee getEmployeePayrollDataByName() {
	Employee emp=null;
	String getEmployeePayrollDataByNameVariable="select e.id,e.name,e.gender,p.basic_pay,e.start_Date from employee e join payroll p on e.id=p.employee_id where e.name=? and e.is_active=true;";
	try {
		Connection con=DBConnection2.getConnection();
		PreparedStatement pr=con.prepareStatement(getEmployeePayrollDataByNameVariable);
		pr.setString(1,"Tanuja");
		ResultSet res=pr.executeQuery();
		if(res.next()) {
			emp=new Employee(res.getInt(1),res.getString(2),res.getString(3).charAt(0),res.getInt(4),res.getDate(5));
		}
	}
	catch(EmployeePayRollException | SQLException e) {
		e.printStackTrace();
	}
	return emp;
}

public List<Employee> getEmployeesBetweenDates(Date from,Date to){
	List<Employee> emplist=new ArrayList<>();
	String getEmployeesBetweenDatesVariable="select e.id,e.name,e.gender,p.basic_pay,e.start_Date from employee e join payroll p on e.id=p.employee_id where e.start_Date between ? and ? and e.is_active=true;";
	try {
		Connection con=DBConnection2.getConnection();
		PreparedStatement pr=con.prepareStatement(getEmployeesBetweenDatesVariable);
		pr.setDate(1,from);
		pr.setDate(2,to);
		ResultSet res=pr.executeQuery();
		while(res.next()) {
			emplist.add(new Employee(res.getInt(1),res.getString(2),res.getString(3).charAt(0),res.getInt(4),res.getDate(5)));
		}
	}
	catch(EmployeePayRollException | SQLException e) {
		e.printStackTrace();
	}
	return emplist;
}

public Map<Character,Integer> getSumOfTheSalariesOfFemaleEmployees(){
	Map<Character,Integer> employeemap=new LinkedHashMap<>();
	String getSumVariable="select e.gender,sum(p.basic_pay) from employee e join payroll p on e.id=p.employee_id where e.gender='F' and e.is_active=true group by e.gender;";
	try {
		Connection con=DBConnection2.getConnection();
		PreparedStatement pr=con.prepareStatement(getSumVariable);
		ResultSet res=pr.executeQuery();
		if(res.next()) {
			employeemap.put(res.getString(1).charAt(0),res.getInt(2));
		}
	}
	catch(EmployeePayRollException | SQLException e) {
		e.printStackTrace();
	}
	return employeemap;
}

public Map<Character,Double> getAverageSalary(){
	Map<Character,Double> employeemap=new LinkedHashMap<>();
	String getAverageSalaryVariable="select e.gender,avg(p.basic_pay) from employee e join payroll p on e.id=p.employee_id where e.is_active=true group by e.gender;";
	try {
		Connection con=DBConnection2.getConnection();
		PreparedStatement pr=con.prepareStatement(getAverageSalaryVariable);
		ResultSet res=pr.executeQuery();
		while(res.next()) {
			employeemap.put(res.getString(1).charAt(0),res.getDouble(2));
		}
	}
	catch(EmployeePayRollException | SQLException e) {
		e.printStackTrace();
	}
	return employeemap;
}

public Map<Character,Integer> getMinimumSalary(){
	Map<Character,Integer> employeemap=new LinkedHashMap<>();
	String getMinimumSalaryVariable="select e.gender,min(p.basic_pay) from employee e join payroll p on e.id=p.employee_id where e.is_active=true group by e.gender;";
	try {
		Connection con=DBConnection2.getConnection();
		PreparedStatement pr=con.prepareStatement(getMinimumSalaryVariable);
		ResultSet res=pr.executeQuery();
		while(res.next()) {
			employeemap.put(res.getString(1).charAt(0),res.getInt(2));
		}
	}
	catch(EmployeePayRollException | SQLException e) {
		e.printStackTrace();
	}
	return employeemap;
}
public Employee addEmployeeToPayroll(String name,char gender,int salary,Date start_Date) {
	Employee emp=null;
	String addEmployeeToPayrollVariable="insert into employee_payroll(name,gender,salary,start_Date) values(?,?,?,?)";
	
	try {
		Connection con=DBConnection2.getConnection();
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
public Employee addEmployeeUC11(String name,char gender,String phone,String address,Date start_Date,int basicPay,List<String> departments) {
	Employee emp=null;
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
		emp=new Employee(employeeId,name,gender,basicPay,start_Date);

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

	return emp;
}
public int removeEmployee(String name) {
	int n=-1;
	String removeEmployeeVariable="update employee set is_active=false where name=?";
	try {
		Connection con=DBConnection2.getConnection();
		PreparedStatement pr=con.prepareStatement(removeEmployeeVariable);
		pr.setString(1,name);
		n=pr.executeUpdate();
	}
	catch(EmployeePayRollException | SQLException e) {
		e.printStackTrace();
	}
	return n;
}
}
