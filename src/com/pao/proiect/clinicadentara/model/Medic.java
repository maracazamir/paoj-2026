package com.pao.proiect.clinicadentara.model;

import java.time.LocalDate;

public class Medic extends Angajat {
    private String specializare;

    public Medic(int idAngajat, String nume, String prenume, String telefon,
                 String email, LocalDate dataNasterii, String adresa,
                 double salariu, String specializare) {
        super(idAngajat, nume, prenume, telefon, email, dataNasterii, adresa, salariu);
        this.specializare = specializare;
    }

    public String getSpecializare() {
        return specializare;
    }

    @Override
    public String getRol() {
        return "Medic";
    }

    @Override
    public String toString() {
        return "Medic{" +
                "id=" + idAngajat +
                ", nume=" + nume +
                ", specializare='" + specializare + '\'' +
                '}';
    }
}