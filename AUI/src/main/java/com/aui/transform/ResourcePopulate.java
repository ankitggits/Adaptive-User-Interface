package com.aui.transform;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.aui.pojo.Flight;
import com.aui.pojo.SecurityQuestion;

public class ResourcePopulate {
	
	public static List<Flight> getFlightsFromXML(){
		List<Flight> flights = new ArrayList<Flight>();
		NodeList nList = getNodeList("/data/Flights.xml","flight");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Flight flight = new Flight();
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				flight.setArrival(eElement.getElementsByTagName("arrival").item(0).getTextContent());
				flight.setBusinessfare(eElement.getElementsByTagName("businessfare").item(0).getTextContent());
				flight.setDeparture(eElement.getElementsByTagName("departure").item(0).getTextContent());
				flight.setDestination(eElement.getElementsByTagName("destination").item(0).getTextContent());
				flight.setDuration(eElement.getElementsByTagName("duration").item(0).getTextContent());
				flight.setEconomyfare(eElement.getElementsByTagName("economyfare").item(0).getTextContent());
				flight.setFlightCode(eElement.getAttribute("flightCode"));
				flight.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
				flight.setSource(eElement.getElementsByTagName("source").item(0).getTextContent());
				flights.add(flight);
			}
		}
		return flights;
	}
	
	public static List<SecurityQuestion> getQuestionsFromXML(){
		List<SecurityQuestion> securityQuestions = new ArrayList<SecurityQuestion>();
		try {
			NodeList nList = getNodeList("/data/SecurityQuestions.xml","securityQuestion");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				SecurityQuestion secQuestions = new SecurityQuestion();
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					secQuestions.setGroup(eElement.getAttribute("group"));
					secQuestions.setQuestion(eElement.getElementsByTagName("question").item(0).getTextContent());
					securityQuestions.add(secQuestions);
				}
			}
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return securityQuestions;
	}
	
	private static NodeList getNodeList(String pathOfResource, String nameOfNode){
		try{
			URL url = ResourcePopulate.class.getResource(pathOfResource);
			File fXmlFile = new File(url.toURI());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(nameOfNode);
			return nList;
		}catch (Exception e) {
		    e.printStackTrace();
		    return null;
		}
	}
	
}
