package com.pao.proiect.clinicadentara.service;

import com.pao.proiect.clinicadentara.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClinicaJdbcService {
    private static ClinicaJdbcService instance;

    private ClinicaJdbcService() {
    }

    public static ClinicaJdbcService getInstance() {
        if (instance == null) {
            instance = new ClinicaJdbcService();
        }

        return instance;
    }

    private Connection getConnection() throws Exception {
        return DatabaseConnection.getInstance().getConnection();
    }

    public void programeazaPacientCuTranzactie(
            int idProgramare,
            int idPacient,
            int idMedic,
            int idCabinet,
            String dataOra
    ) throws Exception {

        Connection connection = getConnection();
        connection.setAutoCommit(false);

        try {
            verificaExistenta("pacient", "id_pacient", idPacient);
            verificaExistenta("medic", "id_medic", idMedic);
            verificaExistenta("cabinet", "id_cabinet", idCabinet);

            String sql = """
                    INSERT INTO programare
                    VALUES (?, ?, ?, ?, ?, ?)
                    """;

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, idProgramare);
                statement.setInt(2, idPacient);
                statement.setInt(3, idMedic);
                statement.setInt(4, idCabinet);
                statement.setString(5, dataOra);
                statement.setString(6, "in_asteptare");

                statement.executeUpdate();
            }

            connection.commit();
            System.out.println("Tranzactie reusita: programarea a fost salvata.");

        } catch (Exception e) {
            connection.rollback();
            System.out.println("Tranzactie esuata. Rollback executat.");
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private void verificaExistenta(String tabel, String coloana, int id) throws Exception {
        String sql = "SELECT COUNT(*) FROM " + tabel + " WHERE " + coloana + " = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) == 0) {
                    throw new SQLException("Nu exista in tabela " + tabel + " id-ul " + id);
                }
            }
        }
    }

    public List<String> raportProgramariComplete() throws Exception {
        String sql = """
                SELECT p.id_programare,
                       pac.nume AS nume_pacient,
                       pac.prenume AS prenume_pacient,
                       m.nume AS nume_medic,
                       m.prenume AS prenume_medic,
                       c.numar_cabinet,
                       p.data_ora,
                       p.status
                FROM programare p
                JOIN pacient pac ON p.id_pacient = pac.id_pacient
                JOIN medic m ON p.id_medic = m.id_medic
                JOIN cabinet c ON p.id_cabinet = c.id_cabinet
                ORDER BY p.data_ora
                """;

        List<String> rezultate = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                rezultate.add(
                        "Programare #" + resultSet.getInt("id_programare")
                                + " | Pacient: " + resultSet.getString("nume_pacient")
                                + " " + resultSet.getString("prenume_pacient")
                                + " | Medic: " + resultSet.getString("nume_medic")
                                + " " + resultSet.getString("prenume_medic")
                                + " | Cabinet: " + resultSet.getInt("numar_cabinet")
                                + " | Data: " + resultSet.getString("data_ora")
                                + " | Status: " + resultSet.getString("status")
                );
            }
        }

        return rezultate;
    }

    public List<String> raportProgramariPentruPacient(int idPacient) throws Exception {
        String sql = """
                SELECT p.id_programare,
                       pac.nume AS nume_pacient,
                       pac.prenume AS prenume_pacient,
                       m.nume AS nume_medic,
                       m.prenume AS prenume_medic,
                       p.data_ora,
                       p.status
                FROM programare p
                JOIN pacient pac ON p.id_pacient = pac.id_pacient
                JOIN medic m ON p.id_medic = m.id_medic
                WHERE pac.id_pacient = ?
                ORDER BY p.data_ora
                """;

        List<String> rezultate = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setInt(1, idPacient);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    rezultate.add(
                            "Programare #" + resultSet.getInt("id_programare")
                                    + " | Pacient: " + resultSet.getString("nume_pacient")
                                    + " " + resultSet.getString("prenume_pacient")
                                    + " | Medic: " + resultSet.getString("nume_medic")
                                    + " " + resultSet.getString("prenume_medic")
                                    + " | Data: " + resultSet.getString("data_ora")
                                    + " | Status: " + resultSet.getString("status")
                    );
                }
            }
        }

        return rezultate;
    }

    public List<String> raportNumarProgramariPerMedic() throws Exception {
        String sql = """
                SELECT m.id_medic,
                       m.nume,
                       m.prenume,
                       COUNT(p.id_programare) AS numar_programari
                FROM medic m
                LEFT JOIN programare p ON m.id_medic = p.id_medic
                GROUP BY m.id_medic, m.nume, m.prenume
                ORDER BY numar_programari DESC
                """;

        List<String> rezultate = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                rezultate.add(
                        "Medic: " + resultSet.getString("nume")
                                + " " + resultSet.getString("prenume")
                                + " | Numar programari: "
                                + resultSet.getInt("numar_programari")
                );
            }
        }

        return rezultate;
    }
}