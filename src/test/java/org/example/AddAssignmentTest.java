package org.example;

import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.StudentXMLRepository;
import org.example.repository.TemaXMLRepository;
import org.example.service.Service;
import org.example.validation.StudentValidator;
import org.example.validation.TemaValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddAssignmentTest {
    private Service service;

    @BeforeAll
    static void createXML() throws IOException {
        File xml = new File("temaTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
        }
    }

    @AfterAll
    static void removeXML() {
        new File("fisiere/temaTest.xml").delete();
    }

    @BeforeEach
    void setup() {
        TemaValidator temaValidator = new TemaValidator();
        TemaXMLRepository temaFileRepository = new TemaXMLRepository(temaValidator, "temaTest.xml");
        this.service = new Service(null, temaFileRepository, null);
    }

    @Test
    void testAddTema_ValidTema() {
        Tema newTema = new Tema("1", "Alex", 10, 6);
        service.saveTema("1", "Alex", 10, 6);
        assertEquals(newTema, service.findAllTeme().iterator().next());
        service.deleteTema("1");
    }

    @Test
    void testAddTema_InvalidTema_NullId() {
        assertEquals(1, service.saveTema(null, "Alex", 10, 6));
    }

    @Test
    void testAddTema_InvalidTema_StartLineLargerThanDeadline() {
        assertEquals(1, service.saveTema("2", "Alex", 10, 11));
    }
}
