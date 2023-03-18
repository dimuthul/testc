package com.hackerthon.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import com.hackerthon.common.UtilC;
import com.hackerthon.common.UtilQ;
import com.hackerthon.common.UtilTransform;
import com.hackerthon.model.Employee;

public class GetEmployeeService extends UtilC {

	private final ArrayList<Employee> employeeList = new ArrayList<Employee>();

	private static Connection conn;

	private static Statement stat;

	private PreparedStatement preparedStatement;

	public GetEmployeeService() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(p.getProperty("url"), p.getProperty("username"),
					p.getProperty("password"));
		} catch (Exception e) {
		}
	}

	public void employeesFromXml() {

		try {
			int size = UtilTransform.xmlXPaths().size();
			for (int i = 0; i < size; i++) {
				Map<String, String> xmlPathList = UtilTransform.xmlXPaths().get(i);
				Employee employee = new Employee();
				employee.setEmployeeId(xmlPathList.get("XpathEmployeeIDKey"));
				employee.setFullName(xmlPathList.get("XpathEmployeeNameKey"));
				employee.setAddress(xmlPathList.get("XpathEmployeeAddressKey"));
				employee.setFacultyName(xmlPathList.get("XpathFacultyNameKey"));
				employee.setDepartment(xmlPathList.get("XpathDepartmentKey"));
				employee.setDesignation(xmlPathList.get("XpathDesignationKey"));
				employeeList.add(employee);
				System.out.println(employee.toString() + "\n");
			}
		} catch (Exception e) {
		}
	}

	public void empTableCreate() {
		try {
			stat = conn.createStatement();
			stat.executeUpdate(UtilQ.Q("q2"));
			stat.executeUpdate(UtilQ.Q("q1"));
		} catch (Exception e) {
		}
	}

	public void addEmployees() {
		try {
			preparedStatement = conn.prepareStatement(UtilQ.Q("q3"));
			conn.setAutoCommit(false);
			for (int i = 0; i < employeeList.size(); i++) {
				Employee employee = employeeList.get(i);
				preparedStatement.setString(1, employee.getEmployeeId());
				preparedStatement.setString(2, employee.getFullName());
				preparedStatement.setString(3, employee.getAddress());
				preparedStatement.setString(4, employee.getFacultyName());
				preparedStatement.setString(5, employee.getDepartment());
				preparedStatement.setString(6, employee.getDesignation());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			conn.commit();
		} catch (Exception e) {
		}
	}

	public void getEmployeeById(String empId) {

		Employee employee = new Employee();
		try {
			preparedStatement = conn.prepareStatement(UtilQ.Q("q4"));
			preparedStatement.setString(1, empId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				employee.setEmployeeId(resultSet.getString(1));
				employee.setFullName(resultSet.getString(2));
				employee.setAddress(resultSet.getString(3));
				employee.setFacultyName(resultSet.getString(4));
				employee.setDepartment(resultSet.getString(5));
				employee.setDesignation(resultSet.getString(6));
			}
			ArrayList<Employee> employeeList = new ArrayList<Employee>();
			employeeList.add(employee);
			employeeOutput(employeeList);
		} catch (Exception e) {
		}
	}

	public void deleteEmployee(String empId) {

		try {
			preparedStatement = conn.prepareStatement(UtilQ.Q("q6"));
			preparedStatement.setString(1, empId);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayEmployee() {

		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		try {
			preparedStatement = conn.prepareStatement(UtilQ.Q("q5"));
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Employee employee = new Employee();
				employee.setEmployeeId(resultSet.getString(1));
				employee.setFullName(resultSet.getString(2));
				employee.setAddress(resultSet.getString(3));
				employee.setFacultyName(resultSet.getString(4));
				employee.setDepartment(resultSet.getString(5));
				employee.setDesignation(resultSet.getString(6));
				employeeList.add(employee);
			}
		} catch (Exception e) {
		}
		employeeOutput(employeeList);
	}

	public void employeeOutput(ArrayList<Employee> empList) {

		System.out.println("Employee ID" + "\t\t" + "Full Name" + "\t\t" + "Address" + "\t\t" + "Faculty Name" + "\t\t"
				+ "Department" + "\t\t" + "Designation" + "\n");
		System.out.println(
				"================================================================================================================");
		for (int i = 0; i < empList.size(); i++) {
			Employee employee = empList.get(i);
			System.out.println(employee.getEmployeeId() + "\t" + employee.getFullName() + "\t\t" + employee.getAddress()
					+ "\t" + employee.getFacultyName() + "\t" + employee.getDepartment() + "\t"
					+ employee.getDesignation() + "\n");
			System.out.println(
					"----------------------------------------------------------------------------------------------------------------");
		}

	}
}
