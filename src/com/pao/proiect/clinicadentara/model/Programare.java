package com.pao.proiect.clinicadentara.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Programare implements Comparable<Programare> {
    private int idProgramare;
    private Pacient pacient;
    private Medic medic;
    private Cabinet cabinet;
    private LocalDateTime dataOra;
    private String status;

    public Programare(int idProgramare, Pacient pacient, Medic medic, Cabinet cabinet, LocalDateTime dataOra) {
        this.idProgramare = idProgramare;
        this.pacient = pacient;
        this.medic = medic;
        this.cabinet = cabinet;
        this.dataOra = dataOra;
        this.status = "in_asteptare";
    }

    public int getIdProgramare() {
        return idProgramare;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public Medic getMedic() {
        return medic;
    }

    public Cabinet getCabinet() {
        return cabinet;
    }

    public LocalDateTime getDataOra() {
        return dataOra;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int compareTo(Programare alta) {
        return this.dataOra.compareTo(alta.dataOra);
    }

    @Override
    public String toString() {
        return "Programare{" +
                "idProgramare=" + idProgramare +
                ", pacient=" + pacient.getNume() + " " + pacient.getPrenume() +
                ", medic=" + medic.getNume() + " " + medic.getPrenume() +
                ", cabinet=" + cabinet.getNumarCabinet() +
                ", dataOra=" + dataOra +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Programare)) {
            return false;
        }

        Programare programare = (Programare) obj;
        return idProgramare == programare.idProgramare;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProgramare);
    }
}