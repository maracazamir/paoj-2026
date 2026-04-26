package com.pao.proiect.clinicadentara.model;

import java.time.LocalDate;

public class Angajat extends Persoana {
    protected int idAngajat;
    protected double salariu;

    public Angajat(int idAngajat, String nume, String prenume, String telefon,
                   String email, LocalDate dataNasterii, String adresa, double salariu) {
        super(nume, prenume, telefon, email, dataNasterii, adresa);
        this.idAngajat = idAngajat;
        this.salariu = salariu;
    }

    public int getIdAngajat() {
        return idAngajat;
    }

    public double getSalariu() {
        return salariu;
    }

    @Override
    public String getRol() {
        return "Angajat";
    }
}