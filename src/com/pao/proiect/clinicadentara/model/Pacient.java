package com.pao.proiect.clinicadentara.model;

import java.time.LocalDate;
import java.util.Objects;

public class Pacient extends Persoana {
    private int idPacient;
    private String contactUrgenta;
    private String gen;

    public Pacient(int idPacient, String nume, String prenume, String telefon, String email,
                   LocalDate dataNasterii, String adresa, String contactUrgenta, String gen) {
        super(nume, prenume, telefon, email, dataNasterii, adresa);
        this.idPacient = idPacient;
        this.contactUrgenta = contactUrgenta;
        this.gen = gen;
    }

    public int getIdPacient() {
        return idPacient;
    }

    public String getContactUrgenta() {
        return contactUrgenta;
    }

    public void setContactUrgenta(String contactUrgenta) {
        this.contactUrgenta = contactUrgenta;
    }

    public String getGen() {
        return gen;
    }

    @Override
    public String getRol() {
        return "Pacient";
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "idPacient=" + idPacient +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", telefon='" + telefon + '\'' +
                ", email='" + email + '\'' +
                ", varsta=" + getVarsta() +
                ", contactUrgenta='" + contactUrgenta + '\'' +
                ", gen='" + gen + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Pacient)) {
            return false;
        }

        Pacient pacient = (Pacient) obj;
        return idPacient == pacient.idPacient;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPacient);
    }
}