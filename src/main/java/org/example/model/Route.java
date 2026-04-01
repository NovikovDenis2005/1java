package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private int trainNumber;
    private String routeName;
    private List<RoutePoint> points;

    public Route(int trainNumber, String routeName) {
        this.trainNumber = trainNumber;
        this.routeName = routeName;
        this.points = new ArrayList<>();
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public List<RoutePoint> getPoints() {
        return points;
    }

    public void setPoints(List<RoutePoint> points) {
        this.points = points;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Поезд №")
                .append(trainNumber)
                .append(" — ")
                .append(routeName)
                .append("\n");
        if (points.isEmpty()) {
            sb.append("  Пункты маршрута не заданы\n");
        } else {
            for (int i = 0; i < points.size(); i++) {
                sb.append(" ")
                        .append(i + 1)
                        .append(". ")
                        .append(points.get(i))
                        .append("\n");
            }
        }
        return sb.toString();
    }
}
