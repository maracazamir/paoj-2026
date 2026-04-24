package com.pao.laboratory07.exercise3;

public final class ComandaStandard extends Comanda {
    public ComandaStandard(String nume, String client, double pret) {
        super(nume, client, pret);
    }

    @Override
    public double pretFinal() {
        return pret;
    }

    @Override
    public String descriere() {
        return String.format("STANDARD: %s, pret: %.2f lei [%s] - client: %s",
                nume, pretFinal(), stare, client);
    }
}