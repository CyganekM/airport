package by.itacademy.airport.dao.impl;

import by.itacademy.airport.dao.ConnectionDataBase;
import by.itacademy.airport.dao.CityDao;
import by.itacademy.airport.entity.City;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CityDaoImpl implements CityDao {
    private final Connection connectionDataBase;
    private final Properties properties;
    private final InputStream inputStream;
    private final String SQL_QUERY_INSERT_CITY;
    private final String SQL_QUERY_REMOTE_CITY;
    private final String SQL_QUERY_GET_BY_ID_CITY;
    private final String SQL_QUERY_UPDATE_CITY;
    private final String SQL_QUERY_GET_ALL_CITY;
    private static CityDao instance;

    private CityDaoImpl() {

        try {
            connectionDataBase = ConnectionDataBase.getInstance().getConnection();
            inputStream = new FileInputStream("src/main/resources/ConfigDao.properties");
            properties = new Properties();
            properties.load(inputStream);
            SQL_QUERY_INSERT_CITY = properties.getProperty("SQL_QUERY_INSERT_CITY");
            SQL_QUERY_REMOTE_CITY = properties.getProperty("SQL_QUERY_REMOTE_CITY");
            SQL_QUERY_GET_BY_ID_CITY = properties.getProperty("SQL_QUERY_GET_BY_ID_CITY");
            SQL_QUERY_UPDATE_CITY = properties.getProperty("SQL_QUERY_UPDATE_CITY");
            SQL_QUERY_GET_ALL_CITY = properties.getProperty("SQL_QUERY_GET_ALL_CITY");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CityDao getInstance() {
        if (instance == null) {
            instance = new CityDaoImpl();
        }
        return instance;
    }


    @Override
    public void add(City entity) throws SQLException {
        PreparedStatement statement = connectionDataBase.prepareStatement(SQL_QUERY_INSERT_CITY);
        statement.setInt(1, entity.getId());
        statement.setString(2, entity.getName());
        Integer result = statement.executeUpdate();
    }

    @Override
    public void remote(Integer entityId) throws SQLException {
        PreparedStatement statement = connectionDataBase.prepareStatement(SQL_QUERY_REMOTE_CITY);
        statement.setInt(1, entityId);
        Integer result = statement.executeUpdate();
    }

    @Override
    public City getEntityById(Integer entityId) throws SQLException {
        PreparedStatement statement = connectionDataBase.prepareStatement(SQL_QUERY_GET_BY_ID_CITY);
        statement.setInt(1, entityId);
        ResultSet rs = statement.executeQuery();

        City city = new City();
        if (rs.next()) {
            city.setId(rs.getInt("id"));
            city.setName(rs.getString("name"));
        }
        rs.close();
        statement.close();
        return city;
    }

    @Override
    public List<City> getAllEntity() throws SQLException {
        PreparedStatement statement = connectionDataBase.prepareStatement(SQL_QUERY_GET_ALL_CITY);
        ResultSet rs = statement.executeQuery();
        List<City> cityList = new ArrayList<>();
        while (rs.next()) {
            City city = new City();
            city.setId(rs.getInt("id"));
            city.setName(rs.getString("name"));
            cityList.add(city);
        }
        rs.close();
        statement.close();
        return cityList;
    }

    @Override
    public void updateRecord(City entity) throws SQLException {
        PreparedStatement statement = connectionDataBase.prepareStatement(SQL_QUERY_UPDATE_CITY);
        statement.setString(1, entity.getName());
        statement.setInt(2, entity.getId());
        Integer result = statement.executeUpdate();
    }
}
