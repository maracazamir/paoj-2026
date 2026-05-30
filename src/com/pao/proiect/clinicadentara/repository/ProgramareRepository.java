package com.pao.proiect.clinicadentara.repository;

import com.pao.proiect.clinicadentara.model.Cabinet;
import com.pao.proiect.clinicadentara.model.Medic;
import com.pao.proiect.clinicadentara.model.Pacient;
import com.pao.proiect.clinicadentara.model.Programare;
import com.pao.proiect.clinicadentara.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProgramareRepository implements Repository<Programare, Integer> {

    private final Connection connection;

    public ProgramareRepository() throws SQLException {
        try {
            this.connection = DatabaseConnection
                    .getInstance()
                    .getConnection();
        } catch (Exception e) {
            throw new SQLException("Eroare la conectarea cu baza de date.");
        }
    }

    @Override
    public void save(Programare programare) throws SQLException {
        String sql = """
                INSERT INTO programare
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, programare.getIdProgramare());
            statement.setInt(2, programare.getPacient().getIdPacient());
            statement.setInt(3, programare.getMedic().getIdAngajat());
            statement.setInt(4, programare.getCabinet().getIdCabinet());
            statement.setString(5, programare.getDataOra().toString());
            statement.setString(6, programare.getStatus());

            statement.executeUpdate();
        }
    }

    @Override
    public Optional<Programare> findById(Integer id) throws SQLException {
        String sql = """
                SELECT p.id_programare,
                       p.data_ora,
                       p.status,

                       pac.id_pacient,
                       pac.nume AS pacient_nume,
                       pac.prenume AS pacient_prenume,
                       pac.telefon AS pacient_telefon,
                       pac.email AS pacient_email,
                       pac.data_nasterii AS pacient_data_nasterii,
                       pac.adresa AS pacient_adresa,
                       pac.contact_urgenta,
                       pac.gen,

                       m.id_medic,
                       m.nume AS medic_nume,
                       m.prenume AS medic_prenume,
                       m.telefon AS medic_telefon,
                       m.email AS medic_email,
                       m.data_nasterii AS medic_data_nasterii,
                       m.adresa AS medic_adresa,
                       m.salariu,
                       m.specializare,

                       c.id_cabinet,
                       c.numar_cabinet,
                       c.etaj,
                       c.capacitate
                FROM programare p
                JOIN pacient pac ON p.id_pacient = pac.id_pacient
                JOIN medic m ON p.id_medic = m.id_medic
                JOIN cabinet c ON p.id_cabinet = c.id_cabinet
                WHERE p.id_programare = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapProgramare(resultSet));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Programare> findAll() throws SQLException {
        List<Programare> programari = new ArrayList<>();

        String sql = """
                SELECT p.id_programare,
                       p.data_ora,
                       p.status,

                       pac.id_pacient,
                       pac.nume AS pacient_nume,
                       pac.prenume AS pacient_prenume,
                       pac.telefon AS pacient_telefon,
                       pac.email AS pacient_email,
                       pac.data_nasterii AS pacient_data_nasterii,
                       pac.adresa AS pacient_adresa,
                       pac.contact_urgenta,
                       pac.gen,

                       m.id_medic,
                       m.nume AS medic_nume,
                       m.prenume AS medic_prenume,
                       m.telefon AS medic_telefon,
                       m.email AS medic_email,
                       m.data_nasterii AS medic_data_nasterii,
                       m.adresa AS medic_adresa,
                       m.salariu,
                       m.specializare,

                       c.id_cabinet,
                       c.numar_cabinet,
                       c.etaj,
                       c.capacitate
                FROM programare p
                JOIN pacient pac ON p.id_pacient = pac.id_pacient
                JOIN medic m ON p.id_medic = m.id_medic
                JOIN cabinet c ON p.id_cabinet = c.id_cabinet
                ORDER BY p.data_ora
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                programari.add(mapProgramare(resultSet));
            }
        }

        return programari;
    }

    @Override
    public void update(Programare programare) throws SQLException {
        String sql = """
                UPDATE programare
                SET id_pacient = ?,
                    id_medic = ?,
                    id_cabinet = ?,
                    data_ora = ?,
                    status = ?
                WHERE id_programare = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, programare.getPacient().getIdPacient());
            statement.setInt(2, programare.getMedic().getIdAngajat());
            statement.setInt(3, programare.getCabinet().getIdCabinet());
            statement.setString(4, programare.getDataOra().toString());
            statement.setString(5, programare.getStatus());
            statement.setInt(6, programare.getIdProgramare());

            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = """
                DELETE FROM programare
                WHERE id_programare = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private Programare mapProgramare(ResultSet resultSet) throws SQLException {
        Pacient pacient = new Pacient(
                resultSet.getInt("id_pacient"),
                resultSet.getString("pacient_nume"),
                resultSet.getString("pacient_prenume"),
                resultSet.getString("pacient_telefon"),
                resultSet.getString("pacient_email"),
                LocalDate.parse(resultSet.getString("pacient_data_nasterii")),
                resultSet.getString("pacient_adresa"),
                resultSet.getString("contact_urgenta"),
                resultSet.getString("gen")
        );

        Medic medic = new Medic(
                resultSet.getInt("id_medic"),
                resultSet.getString("medic_nume"),
                resultSet.getString("medic_prenume"),
                resultSet.getString("medic_telefon"),
                resultSet.getString("medic_email"),
                LocalDate.parse(resultSet.getString("medic_data_nasterii")),
                resultSet.getString("medic_adresa"),
                resultSet.getDouble("salariu"),
                resultSet.getString("specializare")
        );

        Cabinet cabinet = new Cabinet(
                resultSet.getInt("id_cabinet"),
                resultSet.getInt("numar_cabinet"),
                resultSet.getInt("etaj"),
                resultSet.getInt("capacitate")
        );

        Programare programare = new Programare(
                resultSet.getInt("id_programare"),
                pacient,
                medic,
                cabinet,
                LocalDateTime.parse(resultSet.getString("data_ora"))
        );

        programare.setStatus(resultSet.getString("status"));

        return programare;
    }
}