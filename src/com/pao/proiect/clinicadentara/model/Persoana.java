package com.pao.proiect.clinicadentara.model;

import java.time.LocalDate;

public abstract class Persoana {
    protected String nume;
    protected String prenume;
    protected String telefon;
    protected String email;
    protected LocalDate dataNasterii;
    protected String adresa;

    public Persoana(String nume, String prenume, String telefon, String email, LocalDate dataNasterii, String adresa) {
        this.nume = nume;
        this.prenume = prenume;
        this.telefon = telefon;
        this.email = email;
        this.dataNasterii = dataNasterii;
        this.adresa = adresa;
    }

    public String getNume() { return nume; }
    public String getPrenume() { return prenume; }
    public String getTelefon() { return telefon; }
    public String getEmail() { return email; }
    public LocalDate getDataNasterii() { return dataNasterii; }
    public String getAdresa() { return adresa; }
    public int getVarsta() {
        return java.time.Period.between(dataNasterii, LocalDate.now()).getYears();
    }

    public abstract String getRol();

    @Override
    public String toString() {
        return nume + " " + prenume + ", tel: " + telefon;
    }
}