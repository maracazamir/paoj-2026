package com.pao.proiect.clinicadentara.service;

import com.pao.proiect.clinicadentara.exception.ProgramareNegasitaException;
import com.pao.proiect.clinicadentara.model.Programare;

import java.util.Set;
import java.util.TreeSet;

public class ProgramareService {
    private static ProgramareService instance;

    private Set<Programare> programari;

    private ProgramareService() {
        programari = new TreeSet<>();
    }

    public static ProgramareService getInstance() {
        if (instance == null) {
            instance = new ProgramareService();
        }
        return instance;
    }

    public void adaugaProgramare(Programare programare) {
        if (programare == null) {
            return;
        }

        programari.add(programare);
    }

    public Programare cautaProgramareDupaId(int id) throws ProgramareNegasitaException {
        for (Programare programare : programari) {
            if (programare.getIdProgramare() == id) {
                return programare;
            }
        }

        throw new ProgramareNegasitaException("Programarea nu exista!");
    }

    public void anuleazaProgramare(int id) throws ProgramareNegasitaException {
        Programare programare = cautaProgramareDupaId(id);
        programare.setStatus("anulata");
    }

    public void stergeProgramare(int id) throws ProgramareNegasitaException {
        Programare programare = cautaProgramareDupaId(id);
        programari.remove(programare);
    }

    public Set<Programare> getToateProgramarile() {
        return programari;
    }

    public void afiseazaProgramari() {
        for (Programare programare : programari) {
            System.out.println(programare);
        }
    }
}