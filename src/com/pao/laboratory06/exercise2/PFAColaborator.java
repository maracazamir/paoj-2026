package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends PersoanaFizica {
    private static final double SALARIU_MINIM_ANUAL = 4050 * 12;
    private double cheltuieliLunare;

    public PFAColaborator() {
        this.tip = TipColaborator.PFA;
    }

    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();
        this.cheltuieliLunare = in.nextDouble();
    }

    @Override
    public void afiseaza() {
        System.out.printf("PFA: %s %s, venit net anual: %.2f lei%n",
                nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return "PFA";
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venitNetInainteTaxe = (venitBrutLunar - cheltuieliLunare) * 12;

        double impozit = 0.10 * venitNetInainteTaxe;

        double cass;
        if (venitNetInainteTaxe < 6 * SALARIU_MINIM_ANUAL) {
            cass = 0.10 * (6 * SALARIU_MINIM_ANUAL);
        } else if (venitNetInainteTaxe <= 72 * SALARIU_MINIM_ANUAL) {
            cass = 0.10 * venitNetInainteTaxe;
        } else {
            cass = 0.10 * (72 * SALARIU_MINIM_ANUAL);
        }

        double cas;
        if (venitNetInainteTaxe < 12 * SALARIU_MINIM_ANUAL) {
            cas = 0;
        } else if (venitNetInainteTaxe <= 24 * SALARIU_MINIM_ANUAL) {
            cas = 0.25 * (12 * SALARIU_MINIM_ANUAL);
        } else {
            cas = 0.25 * (24 * SALARIU_MINIM_ANUAL);
        }

        return venitNetInainteTaxe - impozit - cass - cas;
    }
}
