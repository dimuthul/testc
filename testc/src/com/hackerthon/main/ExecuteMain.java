package com.hackerthon.main;

import com.hackerthon.common.UtilTransform;
import com.hackerthon.service.GetEmployeeService;

public class ExecuteMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		GetEmployeeService employeeService = new GetEmployeeService();
		try {
			UtilTransform.requestTransform();
			employeeService.employeesFromXml();
			employeeService.empTableCreate();
			employeeService.addEmployees();
//			employeeService.eMPLOYEEGETBYID("EMP10004");
//			employeeService.EMPLOYEEDELETE("EMP10001");
			employeeService.displayEmployee();
		} catch (Exception e) {
		}

	}

}
