package com.pao.proiect.clinicadentara.repository;

import com.pao.proiect.clinicadentara.model.Pacient;
import com.pao.proiect.clinicadentara.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PacientRepository implements Repository<Pacient, Integer> {

    private final Connection connection;

    public PacientRepository() throws SQLException {
        try {
            this.connection = DatabaseConnection
                    .getInstance()
                    .getConnection();
        } catch (Exception e) {
            throw new SQLException("Eroare la conectarea cu baza de date.");
        }
    }

    @Override
    public void save(Pacient pacient) throws SQLException {

        String sql = """
                INSERT INTO pacient
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, pacient.getIdPacient());
            statement.setString(2, pacient.getNume());
            statement.setString(3, pacient.getPrenume());
            statement.setString(4, pacient.getTelefon());
            statement.setString(5, pacient.getEmail());
            statement.setString(6, pacient.getDataNasterii().toString());
            statement.setString(7, pacient.getAdresa());
            statement.setString(8, pacient.getContactUrgenta());
            statement.setString(9, pacient.getGen());

            statement.executeUpdate();
        }
    }

    @Override
    public Optional<Pacient> findById(Integer id) throws SQLException {

        String sql = """
                SELECT * FROM pacient
                WHERE id_pacient = ?
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Pacient pacient = new Pacient(
                            resultSet.getInt("id_pacient"),
                            resultSet.getString("nume"),
                            resultSet.getString("prenume"),
                            resultSet.getString("telefon"),
                            resultSet.getString("email"),
                            LocalDate.parse(resultSet.getString("data_nasterii")),
                            resultSet.getString("adresa"),
                            resultSet.getString("contact_urgenta"),
                            resultSet.getString("gen")
                    );

                    return Optional.of(pacient);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Pacient> findAll() throws SQLException {

        List<Pacient> pacienti = new ArrayList<>();

        String sql = """
                SELECT * FROM pacient
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Pacient pacient = new Pacient(
                        resultSet.getInt("id_pacient"),
                        resultSet.getString("nume"),
                        resultSet.getString("prenume"),
                        resultSet.getString("telefon"),
                        resultSet.getString("email"),
                        LocalDate.parse(resultSet.getString("data_nasterii")),
                        resultSet.getString("adresa"),
                        resultSet.getString("contact_urgenta"),
                        resultSet.getString("gen")
                );

                pacienti.add(pacient);
            }
        }

        return pacienti;
    }

    @Override
    public void update(Pacient pacient) throws SQLException {

        String sql = """
                UPDATE pacient
                SET nume = ?,
                    prenume = ?,
                    telefon = ?,
                    email = ?,
                    data_nasterii = ?,
                    adresa = ?,
                    contact_urgenta = ?,
                    gen = ?
                WHERE id_pacient = ?
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, pacient.getNume());
            statement.setString(2, pacient.getPrenume());
            statement.setString(3, pacient.getTelefon());
            statement.setString(4, pacient.getEmail());
            statement.setString(5, pacient.getDataNasterii().toString());
            statement.setString(6, pacient.getAdresa());
            statement.setString(7, pacient.getContactUrgenta());
            statement.setString(8, pacient.getGen());
            statement.setInt(9, pacient.getIdPacient());

            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {

        String sql = """
                DELETE FROM pacient
                WHERE id_pacient = ?
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }
}