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
		assertEquals(5,emplist.size());
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
		assertEquals(new Employee(1,"Tanuja",'F',30000,Date.valueOf("2026-01-01")),actual);
	}
	
	@Test
	void getEmployeesBetweenDatesTest() {
		List<Employee> expected = Arrays.asList(
			    new Employee(1, "Tanuja", 'F', 30000, Date.valueOf("2026-01-01")),
			    new Employee(2, "Gopi Chand", 'M', 20000, Date.valueOf("2026-01-05")),
			    new Employee(3, "Leela Sai Kumar", 'M', 10000, Date.valueOf("2025-09-29")),
			    new Employee(4, "Usha Kiran", 'F', 90000, Date.valueOf("2026-01-15")),
			    new Employee(5, "Anand Sai", 'M', 100000, Date.valueOf("2025-09-19"))
			);
		EmployeePayrollSystem eps=new EmployeePayrollSystem();
		List<Employee> actaul=eps.getEmployeesBetweenDates(Date.valueOf("1970-01-01"),Date.valueOf("2027-01-01"));
		assertEquals(expected,actaul);
	}
	@Test
	void getSumOfTheSalariesOfFemaleEmployeesTest() {
		Map<Character,Integer> expected=new LinkedHashMap<>();
		expected.put('F', 120000);
		EmployeePayrollSystem eps=new EmployeePayrollSystem();
		assertEquals(expected, eps.getSumOfTheSalariesOfFemaleEmployees());
	}
	@Test
	void addEmployeeToPayrollTest() {
		EmployeePayrollSystem eps=new EmployeePayrollSystem();
		Employee expected=new Employee(12,"Vinod",'M',40000,Date.valueOf("2025-09-19"));
		Employee actual=eps.addEmployeeToPayroll("Vinod",'M',40000,Date.valueOf("2025-09-19"));
		assertEquals(expected,actual);
	}	
}
