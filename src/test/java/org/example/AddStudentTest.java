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
        Student newStudent = new Student("1111", "Matei", 931);
        service.saveStudent("1111", "Matei", 931);
        assertEquals(newStudent, service.findAllStudents().iterator().next());
    }

    @Test
    void testAddStudent_StudentGroupTooSmall() {
        assertEquals(1, service.saveStudent("113", "a", 10));
    }

    @Test
    public void testAddStudent_StudentGroupTooLarge() {
        assertEquals(1, service.saveStudent("114", "a", 100000));
    }

    @Test
    public void testAddStudent_EmptyStudentName() {
        assertEquals(1, service.saveStudent("115", "", 931));
    }

    @Test
    public void testAddStudent_NullStudentName() {
        assertEquals(1, service.saveStudent("116", null, 931));
    }

    @Test
    public void testAddStudent_EmptyStudentID() {
        assertEquals(1, service.saveStudent("", "a", 931));

    }

    @Test
    public void testAddStudent_NullStudentID() {
        assertEquals(1, service.saveStudent(null, "a", 931));
    }
}