package com.hackerthon.common;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class UtilQ extends UtilC {
	
	public static String Q(String id) throws Exception {
		NodeList n; Element e = null;
		n = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new File("src/com/hackerthon/config/EmployeeQuery.xml"))
				.getElementsByTagName("query");
		for (int x = 0; x < n.getLength(); x++) {
			e = (Element) n.item(x);
			if (e.getAttribute("id").equals(id))
				break;
		}
		return e.getTextContent().trim();
	}
}
