package com.pao.proiect.clinicadentara.model;

import java.util.List;

public class Factura {
    private int idFactura;
    private Pacient pacient;
    private List<Procedura> proceduri;
    private double total;

    public Factura(int idFactura, Pacient pacient, List<Procedura> proceduri) {
        this.idFactura = idFactura;
        this.pacient = pacient;
        this.proceduri = proceduri;
        calculeazaTotal();
    }

    private void calculeazaTotal() {
        total = 0;
        for (Procedura procedura : proceduri) {
            total += procedura.getTratament().getCost();
        }
    }

    public int getIdFactura() {
        return idFactura;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public double getTotal() {
        return total;
    }

    public List<Procedura> getProceduri() {
        return proceduri;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "idFactura=" + idFactura +
                ", pacient=" + pacient.getNume() + " " + pacient.getPrenume() +
                ", total=" + total +
                " lei}";
    }
}