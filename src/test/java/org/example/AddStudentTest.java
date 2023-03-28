package org.example;

import org.example.domain.Student;
import org.example.repository.StudentXMLRepository;
import org.example.service.Service;
import org.example.validation.StudentValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddStudentTest {
    private Service service;

    @BeforeAll
    static void createXML() throws IOException {
        File xml = new File("studentiTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
        }
    }

    @AfterAll
    static void removeXML() {
        new File("fisiere/studentiTest.xml").delete();
    }

    @BeforeEach
    void setup() {
        StudentValidator studentValidator = new StudentValidator();
        StudentXMLRepository studentFileRepository = new StudentXMLRepository(studentValidator, "studentiTest.xml");
        this.service = new Service(studentFileRepository, null, null);
    }

    @Test
    void testAddStudent_ValidStudent_StudentAddedCorrectly() {
        Student newStudent = new Student("1", "Alex", 931);
        service.saveStudent("1", "Alex", 931);
        assertEquals(newStudent, service.findAllStudents().iterator().next());
    }

    @Test
    void testAddStudent_InvalidStudent_StudentGroupTooSmall() {
        assertEquals(1, service.saveStudent("2", "Alex", -2));
    }

    @Test
    public void testAddStudent_InvalidStudent_StudentGroupTooLarge() {
        assertEquals(1, service.saveStudent("3", "a", 100000));
    }

    @Test
    public void testAddStudent_InvalidStudent_EmptyStudentName() {
        assertEquals(1, service.saveStudent("4", "", 931));
    }

    @Test
    public void testAddStudent_InvalidStudent_NullStudentName() {
        assertEquals(1, service.saveStudent("5", null, 931));
    }

    @Test
    public void testAddStudent_InvalidStudent_EmptyStudentID() {
        assertEquals(1, service.saveStudent("", "a", 931));

    }

    @Test
    public void testAddStudent_InvalidStudent_NullStudentID() {
        assertEquals(1, service.saveStudent(null, "a", 931));
    }

    @Test
    void testAddStudent_InvalidStudent_GroupBelowLowerBoundary() {
        assertEquals(1, service.saveStudent("6", "Alex", 109));
    }

    @Test
    void testAddStudent_ValidStudent_GroupEqualToLowerBoundary() {
        Student newStudent = new Student("7", "Alex", 110);
        service.saveStudent("7", "Alex", 931);
        assertEquals(newStudent, service.findAllStudents().iterator().next());
    }

    @Test
    void testAddStudent_InvalidStudent_GroupAboveHigherBoundary() {
        assertEquals(1, service.saveStudent("8", "Alex", 939));
    }

    @Test
    void testAddStudent_ValidStudent_GroupEqualToHigherBoundary() {
        Student newStudent = new Student("9", "Alex", 938);
        service.saveStudent("7", "Alex", 931);
        assertEquals(newStudent, service.findAllStudents().iterator().next());
    }
}