package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompteComptableTest {

    private static CompteComptable vCompte;
    private static List<CompteComptable> vList;

    @BeforeEach
    void init() {
        vCompte = new CompteComptable();
        vCompte.setNumero(401);
        vCompte.setLibelle("Fournisseurs");
        vList = new ArrayList<>(0);
        vList.add(vCompte);
        vList.add(new CompteComptable(411, "Clients"));
    }

    @AfterAll
    static void tearDownAll() {
        vCompte = null;
        vList.clear();
    }

    @Test
    void getByNumero() {
        assertEquals(CompteComptable.getByNumero(vList, 401), vCompte);
    }
}