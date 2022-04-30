package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JournalComptableTest {

    private static JournalComptable vJournal;
    private static List<JournalComptable> vList;

    @BeforeEach
    void init() {
        vJournal = new JournalComptable();
        vJournal.setCode("AC");
        vJournal.setLibelle("Achat");
        vList = new ArrayList<>(0);
        vList.add(vJournal);
        vList.add(new JournalComptable("BQ", "Banque"));
    }

    @AfterAll
    static void tearDownAll() {
        vJournal = null;
        vList.clear();
    }

    @Test
    void getByCode() {
        assertEquals(JournalComptable.getByCode(vList, "AC"), vJournal);
    }
}