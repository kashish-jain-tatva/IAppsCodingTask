package com.demo.iapps.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

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
		Document doc = newDocumentBuilder.parse(xmlFile);
		EPaperRequest request = new EPaperRequest();
		NamedNodeMap attributes2 = doc.getElementsByTagName("screenInfo").item(0).getAttributes();
		request.setWidth(Integer.parseInt(attributes2.getNamedItem("width").getTextContent()));
		request.setHeight(Integer.parseInt(attributes2.getNamedItem("height").getTextContent()));
		request.setDpi(Integer.parseInt(attributes2.getNamedItem("dpi").getTextContent()));
		request.setNewspaperName(doc.getElementsByTagName("newspaperName").item(0).getTextContent());
		request.setFileName(xmlFile.getName());
		request.setUploadTime(LocalDateTime.now());
		return request;
	}

}
