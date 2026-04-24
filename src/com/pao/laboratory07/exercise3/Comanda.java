package com.pao.laboratory07.exercise3;

import com.pao.laboratory07.exercise1.OrderState;

public abstract sealed class Comanda permits ComandaStandard, ComandaRedusa, ComandaGratuita {
    protected String nume;
    protected String client;
    protected double pret;
    protected OrderState stare;

    public Comanda(String nume, String client, double pret) {
        this.nume = nume;
        this.client = client;
        this.pret = pret;
        this.stare = OrderState.PLACED;
    }

    public String getNume() {
        return nume;
    }

    public String getClient() {
        return client;
    }

    public double getPret() {
        return pret;
    }

    public OrderState getStare() {
        return stare;
    }

    public abstract double pretFinal();
    public abstract String descriere();

    public String descriereScurta() {
        if (this instanceof ComandaRedusa cr) {
            return String.format("DISCOUNTED: %s, pret: %.2f lei - client: %s",
                    cr.getNume(), cr.pretFinal(), cr.getClient());
        } else if (this instanceof ComandaStandard cs) {
            return String.format("STANDARD: %s, pret: %.2f lei - client: %s",
                    cs.getNume(), cs.pretFinal(), cs.getClient());
        } else {
            return String.format("GIFT: %s, gratuit - client: %s",
                    this.getNume(), this.getClient());
        }
    }

    public String tipComanda() {
        if (this instanceof ComandaStandard) {
            return "STANDARD";
        } else if (this instanceof ComandaRedusa) {
            return "DISCOUNTED";
        } else {
            return "GIFT";
        }
    }
}