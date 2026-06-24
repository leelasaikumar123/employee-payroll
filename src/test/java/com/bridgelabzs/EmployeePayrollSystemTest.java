package com.bridgelabzs;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;

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
	
}
