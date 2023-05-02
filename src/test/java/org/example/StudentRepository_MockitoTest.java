package org.example;

import org.example.domain.Student;
import org.example.repository.StudentXMLRepository;
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

public class StudentRepository_MockitoTest {

    @Mock
    private Validator<Student> validator;

    private DocumentBuilder documentBuilder;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        documentBuilder = dbf.newDocumentBuilder();
    }

    @Test
    public void testGetEntityFromNode() throws Exception {
        StudentXMLRepository repository = new StudentXMLRepository(validator, "test.xml");
        Element element = getElementFromString("<student ID=\"1\"><Nume>John</Nume><Grupa>123</Grupa></student>");

        Student student = repository.getEntityFromNode(element);

        assert student.getID().equals("1");
        assert student.getNume().equals("John");
        assert student.getGrupa() == 123;
    }

    @Test
    public void testGetElementFromEntity() throws Exception {
        StudentXMLRepository repository = new StudentXMLRepository(validator, "test.xml");
        Student student = new Student("1", "John", 123);

        Element element = repository.getElementFromEntity(student, documentBuilder.newDocument());

        assert element.getAttribute("ID").equals("1");
        assert element.getElementsByTagName("Nume").item(0).getTextContent().equals("John");
        assert element.getElementsByTagName("Grupa").item(0).getTextContent().equals("123");
    }

    private Element getElementFromString(String xml) throws Exception {
        InputStream stream = new ByteArrayInputStream(xml.getBytes());
        Document document = documentBuilder.parse(stream);
        return document.getDocumentElement();
    }
}
