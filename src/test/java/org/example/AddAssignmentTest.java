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
    void testAddTema_InvalidTema_StartlineLargerThanDeadline() {
        assertEquals(1, service.saveTema("2", "Alex", 10, 11));
    }

    @Test
    void testAddTema_ValidTema_StartlineBeforeDeadline() {
        Tema newTema = new Tema("21", "aa", 4, 3);
        service.saveTema("21", "aa", 4,3);
        assertEquals(newTema, service.findAllTeme().iterator().next());
        service.deleteTema("21");
    }

    @Test
    void testAddTema_InvalidTema_Startline_0() {
        assertEquals(1, service.saveTema("22", "aa", 2, 0));
    }

    @Test
    void testAddTema_ValidTema_Deadline_13() {
        Tema newTema = new Tema("23", "aa", 13,12);
        service.saveTema("23", "aa", 13,12);
        assertEquals(newTema, service.findAllTeme().iterator().next());
        service.deleteTema("23");
    }

    @Test
    void testAddTema_InvalidTema_Deadline_15() {
        assertEquals(1, service.saveTema("24", "aa", 15, 12));
    }

    @Test
    void testAddTema_InvalidTema_Startline_and_Deadline_negative() {
        assertEquals(1, service.saveTema("25", "aa", -2,-1));
    }

    @Test
    void testAddTema_InvalidTema_Startline_20_and_Deadline_25() {
        assertEquals(1, service.saveTema("26", "aa", 25, 20));
    }

    @Test
    void testAddTema_InvalidTema_Id_emptyString() {
        assertEquals(1, service.saveTema("", "aa", 6,5));
    }

    @Test
    void testAddTema_Descriere_emptyString() {
        assertEquals(1, service.saveTema("27", "", 6, 5));
    }

}
