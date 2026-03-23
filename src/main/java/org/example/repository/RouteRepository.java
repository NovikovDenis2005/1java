package org.example.repository;

import org.example.model.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteRepository {
    private final Map<Integer, Route> routes = new HashMap<>();

    public void save(Route route) {
        routes.put(route.getTrainNumber(), route);
    }

    public Route findByTrainNumber(int trainNumber) {
        return routes.get(trainNumber);
    }

    public List<Route> findAll() {
        return new ArrayList<>(routes.values());
    }

    public boolean existsByTrainNumber(int trainNumber) {
        return routes.containsKey(trainNumber);
    }

    public boolean deleteByTrainNumber(int trainNumber) {
        return routes.remove(trainNumber) != null;
    }
}
