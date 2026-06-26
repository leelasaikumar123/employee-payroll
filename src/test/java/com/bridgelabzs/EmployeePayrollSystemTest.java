package com.bridgelabzs;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class EmployeePayrollSystemTest {

	@Test
	void printEmployeeDataTest() {
		EmployeePayrollSystem eps=new EmployeePayrollSystem();
		List<Employee> emplist=eps.readEmployeeData();
		assertEquals(3,emplist.size());
	}

	@Test
	void updateSqlRecordTest() {
		EmployeePayrollSystem eps=new EmployeePayrollSystem();
		int actual=eps.updateAnSqlERecord();
		assertEquals(1,actual);
	}
	
	@Test
	void updateSqlRecordUsingPreparedStatementTest() {
		EmployeePayrollSystem eps=new EmployeePayrollSystem();
		int actual=eps.updateAnSqlERecordUsingPreparedStatement();
		assertEquals(1,actual);
	}
	@Test
	void getEmployeePayrollDataByNameTest() {
		EmployeePayrollSystem eps=new EmployeePayrollSystem();
		Employee actual=eps.getEmployeePayrollDataByName();
		assertEquals(new Employee(3,"Tanuja",'F',30000,Date.valueOf("2025-09-17")),actual);
	}
	
	@Test
	void getEmployeesBetweenDatesTest() {
		List<Employee> expected = Arrays.asList(
				new Employee(1,"Nagur",'M',50000,Date.valueOf("2025-09-17")),
				new Employee(2,"Leela",'M',50000,Date.valueOf("2025-09-17")),
				new Employee(3,"Tanuja",'F',30000,Date.valueOf("2025-09-17"))
			);
		EmployeePayrollSystem eps=new EmployeePayrollSystem();
		List<Employee> actaul=eps.getEmployeesBetweenDates(Date.valueOf("1970-01-01"),Date.valueOf("2027-01-01"));
		assertEquals(expected,actaul);
	}
	@Test
	void getSumOfTheSalariesOfFemaleEmployeesTest() {
		Map<Character,Integer> expected=new LinkedHashMap<>();
		expected.put('F', 30000);
		EmployeePayrollSystem eps=new EmployeePayrollSystem();
		assertEquals(expected, eps.getSumOfTheSalariesOfFemaleEmployees());
	}
//	@Test
//	void addEmployeeToPayrollTest() {
//		EmployeePayrollSystem eps=new EmployeePayrollSystem();
//		Employee expected=new Employee(12,"Vinod",'M',40000,Date.valueOf("2025-09-19"));
//		Employee actual=eps.addEmployeeToPayroll("Vinod",'M',40000,Date.valueOf("2025-09-19"));
//		assertEquals(expected,actual);
//	}	
//	@Test
//	void addEmployeeToPayrollAndPayrollDetailsTest() {
//		EmployeePayrollSystem eps=new EmployeePayrollSystem();
//		int actual=eps.addEmployeeToPayrollAndPayrollDetails("Nagur",'M',50000,Date.valueOf("2025-09-17"));
//		int expected=1;
//		assertEquals(expected,actual);
//	}
	// @Test
	// void addEmployeeTest() {
	// 	EmployeePayrollSystem eps=new EmployeePayrollSystem();
	// 	List<String> departments=Arrays.asList("HR","Sales");
	// 	int actual=eps.addEmployee("Tanuja",'F',"7288858691","Guntur",Date.valueOf("2025-09-17"),50000,departments);
	// 	int expected=1;
	// 	assertEquals(expected,actual);
	// }
	@Test
	void getAverageSalaryTest() {
		Map<Character,Double> expected=new LinkedHashMap<>();
		expected.put('F',30000.0);
	    expected.put('M',50000.0); 
		EmployeePayrollSystem eps=new EmployeePayrollSystem();
		assertEquals(expected,eps.getAverageSalary());
	}
	@Test
	void getMinimumSalaryTest() {
		Map<Character,Integer> expected=new LinkedHashMap<>();
		expected.put('F',30000);
	    expected.put('M',50000);
		EmployeePayrollSystem eps=new EmployeePayrollSystem();
		assertEquals(expected,eps.getMinimumSalary());
	}
@Test
void addEmployeeTest() {
	EmployeePayrollSystem eps=new EmployeePayrollSystem();
	List<String> departments=Arrays.asList("HR","Sales");
	Employee expected=new Employee(6,"Vinod",'M',50000,Date.valueOf("2025-09-17"));
	Employee actual=eps.addEmployeeUC11("Vinod",'M',"9876543210","Guntur",Date.valueOf("2025-09-17"),50000,departments);
	assertEquals(expected,actual);
}	
}
