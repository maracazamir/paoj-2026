package com.pao.proiect.clinicadentara.repository;

import com.pao.proiect.clinicadentara.model.Cabinet;
import com.pao.proiect.clinicadentara.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CabinetRepository implements Repository<Cabinet, Integer> {

    private final Connection connection;

    public CabinetRepository() throws SQLException {
        try {
            this.connection = DatabaseConnection
                    .getInstance()
                    .getConnection();
        } catch (Exception e) {
            throw new SQLException("Eroare la conectarea cu baza de date.");
        }
    }

    @Override
    public void save(Cabinet cabinet) throws SQLException {
        String sql = """
                INSERT INTO cabinet
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cabinet.getIdCabinet());
            statement.setInt(2, cabinet.getNumarCabinet());
            statement.setInt(3, cabinet.getEtaj());
            statement.setInt(4, cabinet.getCapacitate());

            statement.executeUpdate();
        }
    }

    @Override
    public Optional<Cabinet> findById(Integer id) throws SQLException {
        String sql = """
                SELECT * FROM cabinet
                WHERE id_cabinet = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Cabinet cabinet = new Cabinet(
                            resultSet.getInt("id_cabinet"),
                            resultSet.getInt("numar_cabinet"),
                            resultSet.getInt("etaj"),
                            resultSet.getInt("capacitate")
                    );

                    return Optional.of(cabinet);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Cabinet> findAll() throws SQLException {
        List<Cabinet> cabinete = new ArrayList<>();

        String sql = """
                SELECT * FROM cabinet
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Cabinet cabinet = new Cabinet(
                        resultSet.getInt("id_cabinet"),
                        resultSet.getInt("numar_cabinet"),
                        resultSet.getInt("etaj"),
                        resultSet.getInt("capacitate")
                );

                cabinete.add(cabinet);
            }
        }

        return cabinete;
    }

    @Override
    public void update(Cabinet cabinet) throws SQLException {
        String sql = """
                UPDATE cabinet
                SET numar_cabinet = ?,
                    etaj = ?,
                    capacitate = ?
                WHERE id_cabinet = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, cabinet.getNumarCabinet());
            statement.setInt(2, cabinet.getEtaj());
            statement.setInt(3, cabinet.getCapacitate());
            statement.setInt(4, cabinet.getIdCabinet());

            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = """
                DELETE FROM cabinet
                WHERE id_cabinet = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}