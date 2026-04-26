package com.pao.proiect.clinicadentara.model;

import java.time.LocalDate;

public class Procedura {
    private int idProcedura;
    private Pacient pacient;
    private Medic medic;
    private Tratament tratament;
    private LocalDate data;

    public Procedura(int idProcedura, Pacient pacient, Medic medic,
                     Tratament tratament, LocalDate data) {
        this.idProcedura = idProcedura;
        this.pacient = pacient;
        this.medic = medic;
        this.tratament = tratament;
        this.data = data;
    }

    public int getIdProcedura() {
        return idProcedura;
    }

    public Tratament getTratament() {
        return tratament;
    }

    @Override
    public String toString() {
        return "Procedura{" +
                "id=" + idProcedura +
                ", pacient=" + pacient.getNume() +
                ", medic=" + medic.getNume() +
                ", tratament=" + tratament.getDenumire() +
                ", data=" + data +
                '}';
    }
}