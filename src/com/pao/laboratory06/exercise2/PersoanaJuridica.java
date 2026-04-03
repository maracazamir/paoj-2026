package com.pao.laboratory06.exercise2;

public abstract class PersoanaJuridica extends Colaborator {
    public PersoanaJuridica() {
        super();
    }

    public PersoanaJuridica(String nume, String prenume, double venitBrutLunar, TipColaborator tip) {
        super(nume, prenume, venitBrutLunar, tip);
    }
}
