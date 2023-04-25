package org.example;

import org.example.domain.*;
import org.example.repository.*;
import org.example.service.Service;
import org.example.validation.*;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class BigBangTest {
    Validator<Student> studentValidator = new StudentValidator();
    Validator<Tema> temaValidator = new TemaValidator();
    Validator<Nota> notaValidator = new NotaValidator();

    StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
    TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
    NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

    Service service = new Service(fileRepository1, fileRepository2, fileRepository3);

    @Test
    void saveStudent() {
        assertEquals(0, service.saveStudent("10", "aa", 222));
    }

    @Test
    void saveTema() {
        assertEquals(0, service.saveTema("23", "aa", 13,10));
    }

    @Test
    void saveNota() {
        assertEquals(0, service.saveNota("10","23",10,13,"Perfect!"));
    }

    @Test
    void saveAll() {
        assertEquals(0, service.saveStudent("10", "aa", 222));
        assertEquals(0, service.saveTema("23", "aa", 13,12));
        assertEquals(1, service.saveNota("10","23",10,1,"Perfect!"));
    }
}
