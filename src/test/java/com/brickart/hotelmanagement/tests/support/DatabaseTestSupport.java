package com.brickart.hotelmanagement.tests.support;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import java.util.stream.Collectors;

public final class DatabaseTestSupport {

    private static boolean schemaInitialized = false;

  private DatabaseTestSupport() {
  }

    public static synchronized void initializeSchema() {
        if (schemaInitialized) {
            return;
        }

        try (InputStream input = DatabaseTestSupport.class.getResourceAsStream("/config.properties")) {
            if (input == null) {
                throw new RuntimeException("Test config.properties not found on classpath");
            }

            Properties props = new Properties();
            props.load(input);
            Class.forName(props.getProperty("db.driver"));

            try (Connection connection = DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.username"),
                    props.getProperty("db.password"))) {
                String sql = readSchemaSql();
                try (Statement statement = connection.createStatement()) {
                    for (String chunk : sql.split(";")) {
                        String trimmed = chunk.trim();
                        if (!trimmed.isEmpty()) {
                            statement.execute(trimmed);
                        }
                    }
                }
            }

            schemaInitialized = true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize H2 test schema.", e);
        }
    }

    public static Connection openConnection() {
        try (InputStream input = DatabaseTestSupport.class.getResourceAsStream("/config.properties")) {
            if (input == null) {
                throw new RuntimeException("Test config.properties not found on classpath");
            }

            Properties props = new Properties();
            props.load(input);
            Class.forName(props.getProperty("db.driver"));
            return DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.username"),
                    props.getProperty("db.password")
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to open test database connection.", e);
        }
    }

    public static void executeUpdate(String sql) {
        try (Connection connection = openConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute SQL update: " + sql, e);
        }
    }

    private static String readSchemaSql() {
        try (InputStream input = DatabaseTestSupport.class.getResourceAsStream("/schema-h2.sql")) {
            if (input == null) {
                throw new RuntimeException("schema-h2.sql not found on classpath");
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(input, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read schema-h2.sql", e);
        }
    }
}
