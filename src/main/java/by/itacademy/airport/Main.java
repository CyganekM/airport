package by.itacademy.airport;

import by.itacademy.airport.dao.ConnectionDataBase;
import by.itacademy.airport.dao.RouteDao;
import by.itacademy.airport.dao.impl.RouteDaoImpl;
import by.itacademy.airport.entity.Plane;
import by.itacademy.airport.entity.Route;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        RouteDao routeDao = RouteDaoImpl.getInstance();
        Route route = routeDao.getEntityById(1);
        Plane plane = route.getPlane();
        plane.setId(4);
        routeDao.updateRecord(route);
        System.out.println(routeDao.getAllEntity());
        ConnectionDataBase.getInstance().getConnection().close();

    }
}