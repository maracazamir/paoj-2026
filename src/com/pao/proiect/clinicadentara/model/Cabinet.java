package com.pao.proiect.clinicadentara.model;

public class Cabinet {
    private int idCabinet;
    private int numarCabinet;
    private int etaj;
    private int capacitate;

    public Cabinet(int idCabinet, int numarCabinet, int etaj, int capacitate) {
        this.idCabinet = idCabinet;
        this.numarCabinet = numarCabinet;
        this.etaj = etaj;
        this.capacitate = capacitate;
    }

    public int getIdCabinet() {
        return idCabinet;
    }

    public int getNumarCabinet() {
        return numarCabinet;
    }

    public int getEtaj() {
        return etaj;
    }

    public int getCapacitate() {
        return capacitate;
    }

    @Override
    public String toString() {
        return "Cabinet{" +
                "idCabinet=" + idCabinet +
                ", numarCabinet=" + numarCabinet +
                ", etaj=" + etaj +
                ", capacitate=" + capacitate +
                '}';
    }
}