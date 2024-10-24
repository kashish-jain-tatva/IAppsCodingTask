package com.demo.iapps.service;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

import com.demo.iapps.entity.EPaperRequest;

@Service
public class XMLService {

	public void validateXml(File xmlFile, File xsdFile) throws SAXException, IOException {
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = factory.newSchema(xsdFile);
		Validator validator = schema.newValidator();
		validator.validate(new StreamSource(xmlFile));
	}

	public EPaperRequest parseXML(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder newDocumentBuilder = factory.newDocumentBuilder();
		Document document = newDocumentBuilder.parse(xmlFile);
		EPaperRequest request = new EPaperRequest();
		// getting the tag named screenInfo 
		NamedNodeMap nodeMap = document.getElementsByTagName("screenInfo").item(0).getAttributes();
		request.setWidth(Integer.parseInt(nodeMap.getNamedItem("width").getTextContent()));
		request.setHeight(Integer.parseInt(nodeMap.getNamedItem("height").getTextContent()));
		request.setDpi(Integer.parseInt(nodeMap.getNamedItem("dpi").getTextContent()));
		request.setNewspaperName(document.getElementsByTagName("newspaperName").item(0).getTextContent()); // getting the tag named newspaperName
		request.setFileName(xmlFile.getName());
		return request;
	}

}
