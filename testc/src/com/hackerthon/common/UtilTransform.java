package com.hackerthon.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class UtilTransform extends UtilC {

	private static final ArrayList<Map<String, String>> XMLPATHSLIST = new ArrayList<Map<String, String>>();

	private static Map<String, String> empDetailsMap = null;

	public static void requestTransform() throws Exception {

		Source x = new StreamSource(new File("src/com/hackerthon/config/EmployeeRequest.xml"));
		Source s = new StreamSource(new File("src/com/hackerthon/config/Employee-modified.xsl"));
		Result o = new StreamResult(new File("src/com/hackerthon/config/EmployeeResponse.xml"));
		TransformerFactory.newInstance().newTransformer(s).transform(x, o);
	}

	public static ArrayList<Map<String, String>> xmlXPaths() throws Exception {

		Document employeeResponseDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse("src/com/hackerthon/config/EmployeeResponse.xml");
		XPath path = XPathFactory.newInstance().newXPath();
		int employeeCount = Integer.parseInt((String) path.compile("count(//Employees/Employee)").evaluate(employeeResponseDoc, XPathConstants.STRING));
		for (int i = 1; i <= employeeCount; i++) {
			empDetailsMap = new HashMap<String, String>();
			empDetailsMap.put("XpathEmployeeIDKey", (String) path.compile("//Employees/Employee[" + i + "]/EmployeeID/text()")
					.evaluate(employeeResponseDoc, XPathConstants.STRING));
			empDetailsMap.put("XpathEmployeeNameKey", (String) path.compile("//Employees/Employee[" + i + "]/EmployeeFullName/text()")
					.evaluate(employeeResponseDoc, XPathConstants.STRING));
			empDetailsMap.put("XpathEmployeeAddressKey",
					(String) path.compile("//Employees/Employee[" + i + "]/EmployeeFullAddress/text()").evaluate(employeeResponseDoc,
							XPathConstants.STRING));
			empDetailsMap.put("XpathFacultyNameKey", (String) path.compile("//Employees/Employee[" + i + "]/FacultyName/text()")
					.evaluate(employeeResponseDoc, XPathConstants.STRING));
			empDetailsMap.put("XpathDepartmentKey", (String) path.compile("//Employees/Employee[" + i + "]/Department/text()")
					.evaluate(employeeResponseDoc, XPathConstants.STRING));
			empDetailsMap.put("XpathDesignationKey", (String) path.compile("//Employees/Employee[" + i + "]/Designation/text()")
					.evaluate(employeeResponseDoc, XPathConstants.STRING));
			XMLPATHSLIST.add(empDetailsMap);
		}
		return XMLPATHSLIST;
	}
}
