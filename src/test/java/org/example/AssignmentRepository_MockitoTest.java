package org.example;

import org.example.domain.Tema;
import org.example.repository.TemaXMLRepository;
import org.example.validation.Validator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class AssignmentRepository_MockitoTest {

    @Mock
    private Validator<Tema> validator;

    private DocumentBuilder documentBuilder;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        documentBuilder = dbf.newDocumentBuilder();
    }

    @Test
    public void testGetEntityFromNode() throws Exception {
        TemaXMLRepository repository = new TemaXMLRepository(validator, "test.xml");
        Element element = getElementFromString("<tema ID=\"1\"><Descriere>desc</Descriere><Deadline>10</Deadline><Startline>2</Startline></tema>");
        Tema tema = repository.getEntityFromNode(element);

        assert tema.getID().equals("1");
        assert tema.getDescriere().equals("desc");
        assert tema.getDeadline() == 10;
        assert tema.getStartline() == 2;
    }

    @Test
    public void testGetElementFromEntity() throws Exception {
        TemaXMLRepository repository = new TemaXMLRepository(validator, "test.xml");
        Tema tema = new Tema("1", "desc", 10, 2);

        Element element = repository.getElementFromEntity(tema, documentBuilder.newDocument());

        assert element.getAttribute("ID").equals("1");
        assert element.getElementsByTagName("Descriere").item(0).getTextContent().equals("desc");
        assert element.getElementsByTagName("Deadline").item(0).getTextContent().equals("10");
        assert element.getElementsByTagName("Startline").item(0).getTextContent().equals("2");
    }

    private Element getElementFromString(String xml) throws Exception {
        InputStream stream = new ByteArrayInputStream(xml.getBytes());
        Document document = documentBuilder.parse(stream);
        return document.getDocumentElement();
    }
}
