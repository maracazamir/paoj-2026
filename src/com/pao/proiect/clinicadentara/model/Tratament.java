package com.pao.proiect.clinicadentara.model;

public class Tratament {
    private int idTratament;
    private String denumire;
    private double cost;
    private int durataZile;

    public Tratament(int idTratament, String denumire, double cost, int durataZile) {
        this.idTratament = idTratament;
        this.denumire = denumire;
        this.cost = cost;
        this.durataZile = durataZile;
    }

    public int getIdTratament() {
        return idTratament;
    }

    public String getDenumire() {
        return denumire;
    }

    public double getCost() {
        return cost;
    }

    public int getDurataZile() {
        return durataZile;
    }

    @Override
    public String toString() {
        return "Tratament{" +
                "id=" + idTratament +
                ", denumire='" + denumire + '\'' +
                ", cost=" + cost +
                ", durata=" + durataZile +
                '}';
    }
}