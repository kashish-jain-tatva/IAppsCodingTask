package com.demo.iapps.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.demo.iapps.entity.EPaperRequest;

@ExtendWith(MockitoExtension.class)
public class XMLServiceTest {

	@InjectMocks
	private XMLService service;

	@Test
	public void test_correctXML_validateXML() throws SAXException, IOException {
		assertDoesNotThrow(() -> service.validateXml(new File("src/main/resources/correct.xml"),
				new File("src/main/resources/schema.xsd")));
	}

	@Test
	public void test_incorrectXML_validateXML() throws SAXException, IOException {
		assertThrows(SAXParseException.class, () -> service.validateXml(new File("src/main/resources/incorrect.xml"),
				new File("src/main/resources/schema.xsd")));
	}

	@Test
	public void test_parseXML() throws SAXException, IOException, ParserConfigurationException {
		EPaperRequest request = new EPaperRequest();
		request.setDpi(160);
		request.setHeight(752);
		request.setNewspaperName("n");
		request.setWidth(1280);
		EPaperRequest ePaperRequest = service.parseXML(new File("src/main/resources/correct.xml"));
		assertEquals(request.getDpi(), ePaperRequest.getDpi());
		assertEquals(request.getHeight(), ePaperRequest.getHeight());
		assertEquals(request.getNewspaperName(), ePaperRequest.getNewspaperName());
		assertEquals(request.getWidth(), ePaperRequest.getWidth());

	}

}
