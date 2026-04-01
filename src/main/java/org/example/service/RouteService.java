package org.example.service;

import org.example.model.Route;
import org.example.model.RoutePoint;
import org.example.repository.RouteRepository;

import java.time.LocalTime;
import java.util.List;

public class RouteService {
    private final RouteRepository repository;

    public RouteService(RouteRepository repository) {
        this.repository = repository;
    }

    // --- Маршруты ---

    public Route createRoute(int trainNumber, String routeName) {
        if (repository.existsByTrainNumber(trainNumber)) {
            throw new IllegalArgumentException("Маршрут с номером поезда " + trainNumber + " уже существует.");
        }
        Route route = new Route(trainNumber, routeName);
        repository.save(route);
        return route;
    }

    public List<Route> getAllRoutes() {
        return repository.findAll();
    }

    public Route getRoute(int trainNumber) {
        Route route = repository.findByTrainNumber(trainNumber);
        if (route == null) {
            throw new IllegalArgumentException("Маршрут с номером поезда " + trainNumber + " не найден.");
        }
        return route;
    }

    public void deleteRoute(int trainNumber) {
        if (!repository.deleteByTrainNumber(trainNumber)) {
            throw new IllegalArgumentException("Маршрут с номером поезда " + trainNumber + " не найден.");
        }
    }

    // --- Пункты маршрута ---

    public void setDeparturePoint(int trainNumber, String stationName, LocalTime departureTime) {
        Route route = getRoute(trainNumber);
        RoutePoint point = new RoutePoint(stationName, null, departureTime);
        List<RoutePoint> points = route.getPoints();
        if (points.isEmpty()) {
            points.add(point);
        } else {
            points.set(0, point);
        }
    }

    public void setArrivalPoint(int trainNumber, String stationName, LocalTime arrivalTime) {
        Route route = getRoute(trainNumber);
        RoutePoint point = new RoutePoint(stationName, arrivalTime, null);
        List<RoutePoint> points = route.getPoints();
        if (points.isEmpty()) {
            points.add(point);
        } else if (points.size() == 1) {
            points.add(point);
        } else {
            points.set(points.size() - 1, point);
        }
    }

    public void addIntermediatePoint(int trainNumber, String stationName,
                                     LocalTime arrivalTime, LocalTime departureTime) {
        Route route = getRoute(trainNumber);
        List<RoutePoint> points = route.getPoints();
        if (arrivalTime == null || departureTime == null) {
            throw new IllegalArgumentException("Для промежуточного пункта необходимо указать время прибытия и отправления.");
        }
        RoutePoint point = new RoutePoint(stationName, arrivalTime, departureTime);
        if (points.size() < 2) {
            points.add(point);
        } else {
            points.add(points.size() - 1, point);
        }
    }

    public void removePoint(int trainNumber, int index) {
        Route route = getRoute(trainNumber);
        List<RoutePoint> points = route.getPoints();
        if (index < 0 || index >= points.size()) {
            throw new IllegalArgumentException("Неверный индекс пункта: " + (index + 1));
        }
        points.remove(index);
    }

    public void editPoint(int trainNumber, int index, String stationName,
                          LocalTime arrivalTime, LocalTime departureTime) {
        Route route = getRoute(trainNumber);
        List<RoutePoint> points = route.getPoints();
        if (index < 0 || index >= points.size()) {
            throw new IllegalArgumentException("Неверный индекс пункта: " + (index + 1));
        }
        RoutePoint point = points.get(index);
        point.setStationName(stationName);
        point.setArrivalTime(arrivalTime);
        point.setDepartureTime(departureTime);
    }
}
