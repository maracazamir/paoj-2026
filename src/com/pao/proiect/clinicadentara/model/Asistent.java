package com.pao.proiect.clinicadentara.model;

import java.time.LocalDate;

public class Asistent extends Angajat {
    private int aniExperienta;

    public Asistent(int idAngajat, String nume, String prenume, String telefon,
                    String email, LocalDate dataNasterii, String adresa,
                    double salariu, int aniExperienta) {
        super(idAngajat, nume, prenume, telefon, email, dataNasterii, adresa, salariu);
        this.aniExperienta = aniExperienta;
    }

    public int getAniExperienta() {
        return aniExperienta;
    }

    @Override
    public String getRol() {
        return "Asistent";
    }

    @Override
    public String toString() {
        return "Asistent{" +
                "id=" + idAngajat +
                ", nume=" + nume +
                ", experienta=" + aniExperienta +
                '}';
    }
}