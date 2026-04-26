package com.pao.proiect.clinicadentara.model;

public final class CodProgramare {
    private final String cod;

    public CodProgramare(String cod) {
        if (cod == null || cod.isEmpty()) {
            throw new IllegalArgumentException("Cod invalid");
        }
        this.cod = cod;
    }

    public String getCod() {
        return cod;
    }

    @Override
    public String toString() {
        return cod;
    }
}