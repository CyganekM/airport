package by.itacademy.airport.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDataBase {


    private static ConnectionDataBase instance;
    private final String connectionUrl;
    private final String userName;

    private final String password;
    private Connection connection;

    private ConnectionDataBase() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream;
        inputStream = new FileInputStream("src/main/resources/ConfigDao.properties");
        properties.load(inputStream);
        connectionUrl = properties.getProperty("ConfigDao.url");
        userName = properties.getProperty("ConfigDao.userName");
        password = properties.getProperty("ConfigDao.password");
        createConn();
    }


    public static ConnectionDataBase getInstance() throws IOException {
        if (instance == null) {
            instance = new ConnectionDataBase();
            ;
        }
        return instance;
    }

    private void createConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка при подключении к базе данных");
        }
        try {
            connection = DriverManager.getConnection(connectionUrl, userName, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if (!connection.isClosed()) {
                return connection;
            } else {
                createConn();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
}
