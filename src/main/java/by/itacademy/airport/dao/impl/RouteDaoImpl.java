package by.itacademy.airport.dao.impl;

import by.itacademy.airport.dao.ConnectionDataBase;
import by.itacademy.airport.dao.RouteDao;
import by.itacademy.airport.entity.City;
import by.itacademy.airport.entity.Plane;
import by.itacademy.airport.entity.Route;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RouteDaoImpl implements RouteDao {
    private final Connection connectionDataBase;
    private Properties properties = new Properties();
    private InputStream inputStream;
    private final String SQL_QUERY_INSERT_ROUTE;
    private final String SQL_QUERY_REMOTE_ROUTE;
    private final String SQL_QUERY_GET_BY_ID_ROUTE;
    private final String SQL_QUERY_UPDATE_ROUTE;
    private final String SQL_QUERY_GET_ALL_ROUTE;
    private static RouteDaoImpl instance;

    private RouteDaoImpl() {
        try {
            connectionDataBase = ConnectionDataBase.getInstance().getConnection();
            inputStream = new FileInputStream("src/main/resources/ConfigDao.properties");
            properties.load(inputStream);
            SQL_QUERY_INSERT_ROUTE = properties.getProperty("SQL_QUERY_INSERT_ROUTE");
            SQL_QUERY_REMOTE_ROUTE = properties.getProperty("SQL_QUERY_REMOTE_ROUTE");
            SQL_QUERY_GET_BY_ID_ROUTE = properties.getProperty("SQL_QUERY_GET_BY_ID_ROUTE");
            SQL_QUERY_UPDATE_ROUTE = properties.getProperty("SQL_QUERY_UPDATE_ROUTE");
            SQL_QUERY_GET_ALL_ROUTE = properties.getProperty("SQL_QUERY_GET_ALL_ROUTE");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static RouteDao getInstance() {
        if (instance == null) {
            instance = new RouteDaoImpl();
        }
        return instance;
    }

    @Override
    public void add(Route route) throws SQLException {
        PreparedStatement statement = connectionDataBase.prepareStatement(SQL_QUERY_UPDATE_ROUTE);
        statement.setInt(1, route.getId());
        statement.setInt(2, route.getArrivalCity().getId());
        statement.setInt(3, route.getDepartureCity().getId());
        statement.setInt(4, route.getPlane().getId());
        statement.setString(5, route.getNumber());
        statement.setTimestamp(6, Timestamp.valueOf(route.getArrivalDate()));
        statement.setTimestamp(7, Timestamp.valueOf(route.getDepartureDate()));
        statement.setDouble(8, route.getRouteTime());
        Integer result = statement.executeUpdate();
    }

    @Override
    public void remote(Integer entityId) throws SQLException {
        PreparedStatement statement = connectionDataBase.prepareStatement(SQL_QUERY_REMOTE_ROUTE);
        statement.setInt(1, entityId);
        Integer result = statement.executeUpdate();
    }

    @Override
    public Route getEntityById(Integer entityId) throws SQLException {
        PreparedStatement statement = connectionDataBase.prepareStatement(SQL_QUERY_GET_BY_ID_ROUTE);
        statement.setInt(1, entityId);
        ResultSet rs = statement.executeQuery();
        Route route = new Route();
        if (rs.next()) {
            City cityArrival = new City();
            City cityDeparture = new City();
            Plane plane = new Plane();
            route.setId(rs.getInt(1));
            cityArrival.setId(rs.getInt(2));
            cityArrival.setName(rs.getString(3));
            cityDeparture.setId(rs.getInt(4));
            cityDeparture.setName(rs.getString(5));
            plane.setId(rs.getInt(6));
            plane.setNumber(rs.getString(7));
            route.setNumber(rs.getString(8));
            route.setArrivalDate(rs.getTimestamp(9).toLocalDateTime());
            route.setDepartureDate(rs.getTimestamp(10).toLocalDateTime());
            route.setRouteTime(rs.getDouble(11));
            route.setPlane(plane);
            route.setArrivalCity(cityArrival);
            route.setDepartureCity(cityDeparture);
        }
        return route;
    }

    @Override
    public List<Route> getAllEntity() throws SQLException {
        List<Route> routes = new ArrayList<>();
        Route route = new Route();
        PreparedStatement statement = connectionDataBase.prepareStatement(SQL_QUERY_GET_ALL_ROUTE);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            City cityArrival = new City();
            City cityDeparture = new City();
            Plane plane = new Plane();
            route.setId(rs.getInt(1));
            cityArrival.setId(rs.getInt(2));
            cityArrival.setName(rs.getString(3));
            cityDeparture.setId(rs.getInt(4));
            cityDeparture.setName(rs.getString(5));
            plane.setId(rs.getInt(6));
            plane.setNumber(rs.getString(7));
            route.setNumber(rs.getString(8));
            route.setArrivalDate(rs.getTimestamp(9).toLocalDateTime());
            route.setDepartureDate(rs.getTimestamp(10).toLocalDateTime());
            route.setRouteTime(rs.getDouble(11));
            route.setPlane(plane);
            route.setArrivalCity(cityArrival);
            route.setDepartureCity(cityDeparture);
            routes.add(route);
        }
        return routes;
    }

    @Override
    public void updateRecord(Route route) throws SQLException {
        PreparedStatement statement = connectionDataBase.prepareStatement(SQL_QUERY_UPDATE_ROUTE);

        statement.setInt(1, route.getArrivalCity().getId());
        statement.setInt(2, route.getDepartureCity().getId());
        statement.setInt(3, route.getPlane().getId());
        statement.setString(4, route.getNumber());
        statement.setTimestamp(5, Timestamp.valueOf(route.getArrivalDate()));
        statement.setTimestamp(6, Timestamp.valueOf(route.getDepartureDate()));
        statement.setDouble(7, route.getRouteTime());
        statement.setInt(8, route.getId());
        Integer result = statement.executeUpdate();
    }
}
