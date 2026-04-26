package com.pao.proiect.clinicadentara.service;

import com.pao.proiect.clinicadentara.exception.PacientNegasitException;
import com.pao.proiect.clinicadentara.model.Pacient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
public class PacientService {
    private static PacientService instance;

    private List<Pacient> pacienti;
    private Map<Integer, Pacient> pacientiDupaId;

    private PacientService() {
        pacienti = new ArrayList<>();
        pacientiDupaId = new HashMap<>();
    }

    public static PacientService getInstance() {
        if (instance == null) {
            instance = new PacientService();
        }
        return instance;
    }

    public void adaugaPacient(Pacient pacient) {
        if (pacient == null) {
            return;
        }

        pacienti.add(pacient);
        pacientiDupaId.put(pacient.getIdPacient(), pacient);
    }

    public Pacient cautaPacientDupaId(int id) throws PacientNegasitException {
        Pacient pacient = pacientiDupaId.get(id);

        if (pacient == null) {
            throw new PacientNegasitException("Pacientul nu exista!");
        }

        return pacient;
    }

    public Pacient cautaPacientDupaNume(String nume) {
        for (Pacient pacient : pacienti) {
            if (pacient.getNume().equalsIgnoreCase(nume)) {
                return pacient;
            }
        }

        return null;
    }

    public void stergePacient(int id) {
        Pacient pacient = pacientiDupaId.get(id);

        if (pacient != null) {
            pacienti.remove(pacient);
            pacientiDupaId.remove(id);
        }
    }

    public void stergePacientDupaNumeComplet(String nume, String prenume) {
        Pacient pacientGasit = null;

        for (Pacient pacient : pacienti) {
            if (pacient.getNume().equalsIgnoreCase(nume)
                    && pacient.getPrenume().equalsIgnoreCase(prenume)) {
                pacientGasit = pacient;
                break;
            }
        }

        if (pacientGasit != null) {
            pacienti.remove(pacientGasit);
            pacientiDupaId.remove(pacientGasit.getIdPacient());
            System.out.println("Pacientul a fost sters.");
        } else {
            System.out.println("Pacientul nu a fost gasit.");
        }
    }

    public List<Pacient> getTotiPacientii() {
        return pacienti;
    }

    public void afiseazaPacienti() {
        if (pacienti.isEmpty()) {
            System.out.println("Nu exista pacienti.");
            return;
        }

        for (Pacient pacient : pacienti) {
            System.out.println(pacient);
        }
    }
}