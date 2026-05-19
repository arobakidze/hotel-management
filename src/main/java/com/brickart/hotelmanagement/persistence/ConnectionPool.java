package com.brickart.hotelmanagement.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private static final ConnectionPool INSTANCE = new ConnectionPool();
    private final BlockingQueue<Connection> connections = new ArrayBlockingQueue<>(10);

    private ConnectionPool() {
        try (InputStream input = ConnectionPool.class.getResourceAsStream("/config.properties")) {
            Properties props = new Properties();
            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }
            props.load(input);
            Class.forName(props.getProperty("db.driver"));
            for (int i = 0; i < 10; i++) {
                connections.add(DriverManager.getConnection(
                        props.getProperty("db.url"),
                        props.getProperty("db.username"),
                        props.getProperty("db.password")
                ));
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to initialize connection pool.", e);
        }
    }

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        try {
            return connections.take();
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to get connection.", e);
        }
    }

    public void releaseConnection(Connection connection) {
        connections.offer(connection);
    }
}