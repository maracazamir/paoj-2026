package com.pao.proiect.clinicadentara.repository;

import com.pao.proiect.clinicadentara.model.Medic;
import com.pao.proiect.clinicadentara.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicRepository implements Repository<Medic, Integer> {

    private final Connection connection;

    public MedicRepository() throws SQLException {
        try {
            this.connection = DatabaseConnection
                    .getInstance()
                    .getConnection();
        } catch (Exception e) {
            throw new SQLException("Eroare la conectarea cu baza de date.");
        }
    }

    @Override
    public void save(Medic medic) throws SQLException {
        String sql = """
                INSERT INTO medic
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, medic.getIdAngajat());
            statement.setString(2, medic.getNume());
            statement.setString(3, medic.getPrenume());
            statement.setString(4, medic.getTelefon());
            statement.setString(5, medic.getEmail());
            statement.setString(6, medic.getDataNasterii().toString());
            statement.setString(7, medic.getAdresa());
            statement.setDouble(8, medic.getSalariu());
            statement.setString(9, medic.getSpecializare());

            statement.executeUpdate();
        }
    }

    @Override
    public Optional<Medic> findById(Integer id) throws SQLException {
        String sql = """
                SELECT * FROM medic
                WHERE id_medic = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Medic medic = new Medic(
                            resultSet.getInt("id_medic"),
                            resultSet.getString("nume"),
                            resultSet.getString("prenume"),
                            resultSet.getString("telefon"),
                            resultSet.getString("email"),
                            LocalDate.parse(resultSet.getString("data_nasterii")),
                            resultSet.getString("adresa"),
                            resultSet.getDouble("salariu"),
                            resultSet.getString("specializare")
                    );

                    return Optional.of(medic);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Medic> findAll() throws SQLException {
        List<Medic> medici = new ArrayList<>();

        String sql = """
                SELECT * FROM medic
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Medic medic = new Medic(
                        resultSet.getInt("id_medic"),
                        resultSet.getString("nume"),
                        resultSet.getString("prenume"),
                        resultSet.getString("telefon"),
                        resultSet.getString("email"),
                        LocalDate.parse(resultSet.getString("data_nasterii")),
                        resultSet.getString("adresa"),
                        resultSet.getDouble("salariu"),
                        resultSet.getString("specializare")
                );

                medici.add(medic);
            }
        }

        return medici;
    }

    @Override
    public void update(Medic medic) throws SQLException {
        String sql = """
                UPDATE medic
                SET nume = ?,
                    prenume = ?,
                    telefon = ?,
                    email = ?,
                    data_nasterii = ?,
                    adresa = ?,
                    salariu = ?,
                    specializare = ?
                WHERE id_medic = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, medic.getNume());
            statement.setString(2, medic.getPrenume());
            statement.setString(3, medic.getTelefon());
            statement.setString(4, medic.getEmail());
            statement.setString(5, medic.getDataNasterii().toString());
            statement.setString(6, medic.getAdresa());
            statement.setDouble(7, medic.getSalariu());
            statement.setString(8, medic.getSpecializare());
            statement.setInt(9, medic.getIdAngajat());

            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = """
                DELETE FROM medic
                WHERE id_medic = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}