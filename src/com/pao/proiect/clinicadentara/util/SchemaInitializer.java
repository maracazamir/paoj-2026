package com.pao.proiect.clinicadentara.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInitializer {

    public static void initialize() throws IOException, SQLException {
        Connection connection = DatabaseConnection
                .getInstance()
                .getConnection();

        String sql = Files.readString(Paths.get("resources/schema.sql"));

        String[] statements = sql.split(";");

        for (String statementText : statements) {
            String trimmed = statementText.trim();

            if (!trimmed.isEmpty()) {
                try (Statement statement = connection.createStatement()) {
                    statement.execute(trimmed);
                }
            }
        }

        System.out.println("Schema bazei de date a fost initializata.");
    }
}