package org.example.ui;

import org.example.model.Route;
import org.example.service.RouteService;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final RouteService service;
    private final Scanner scanner;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public ConsoleUI(RouteService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Выберите действие: ");
            switch (choice) {
                case 1 -> createRoute();
                case 2 -> showAllRoutes();
                case 3 -> viewRoute();
                case 4 -> editPointsMenu();
                case 5 -> deleteRoute();
                case 0 -> running = false;
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
        System.out.println("До свидания!");
    }

    private void printMainMenu() {
        System.out.println("\n=== Управление маршрутами поездов ===");
        System.out.println("1. Создать маршрут");
        System.out.println("2. Показать все маршруты");
        System.out.println("3. Просмотр маршрута");
        System.out.println("4. Редактировать пункты маршрута");
        System.out.println("5. Удалить маршрут");
        System.out.println("0. Выход");
    }

    private void createRoute() {
        int trainNumber = readInt("Номер поезда: ");
        System.out.print("Название маршрута: ");
        String name = scanner.nextLine();
        try {
            service.createRoute(trainNumber, name);
            System.out.println("Маршрут создан.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void showAllRoutes() {
        List<Route> routes = service.getAllRoutes();
        if (routes.isEmpty()) {
            System.out.println("Маршрутов пока нет.");
            return;
        }
        System.out.println("\n--- Все маршруты ---");
        for (Route route : routes) {
            System.out.print(route);
        }
    }

    private void viewRoute() {
        int trainNumber = readInt("Номер поезда: ");
        try {
            Route route = service.getRoute(trainNumber);
            System.out.println();
            System.out.print(route);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void deleteRoute() {
        int trainNumber = readInt("Номер поезда для удаления: ");
        try {
            service.deleteRoute(trainNumber);
            System.out.println("Маршрут удалён.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // --- Подменю редактирования пунктов ---

    private void editPointsMenu() {
        int trainNumber = readInt("Номер поезда: ");
        try {
            service.getRoute(trainNumber);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
            return;
        }

        boolean editing = true;
        while (editing) {
            printPointsMenu();
            int choice = readInt("Выберите действие: ");
            switch (choice) {
                case 1 -> setDeparturePoint(trainNumber);
                case 2 -> setArrivalPoint(trainNumber);
                case 3 -> addIntermediatePoint(trainNumber);
                case 4 -> removePoint(trainNumber);
                case 5 -> editPoint(trainNumber);
                case 0 -> editing = false;
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void printPointsMenu() {
        System.out.println("\n--- Редактирование пунктов ---");
        System.out.println("1. Задать пункт отправления");
        System.out.println("2. Задать пункт прибытия");
        System.out.println("3. Добавить промежуточный пункт");
        System.out.println("4. Удалить пункт");
        System.out.println("5. Изменить пункт");
        System.out.println("0. Назад");
    }

    private void setDeparturePoint(int trainNumber) {
        System.out.print("Название станции отправления: ");
        String station = scanner.nextLine();
        LocalTime departure = readTime("Время отправления (HH:mm): ");
        try {
            service.setDeparturePoint(trainNumber, station, departure);
            System.out.println("Пункт отправления задан.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void setArrivalPoint(int trainNumber) {
        System.out.print("Название станции прибытия: ");
        String station = scanner.nextLine();
        LocalTime arrival = readTime("Время прибытия (HH:mm): ");
        try {
            service.setArrivalPoint(trainNumber, station, arrival);
            System.out.println("Пункт прибытия задан.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void addIntermediatePoint(int trainNumber) {
        System.out.print("Название станции: ");
        String station = scanner.nextLine();
        LocalTime arrival = readTime("Время прибытия (HH:mm): ");
        LocalTime departure = readTime("Время отправления (HH:mm): ");
        try {
            service.addIntermediatePoint(trainNumber, station, arrival, departure);
            System.out.println("Промежуточный пункт добавлен.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void removePoint(int trainNumber) {
        try {
            Route route = service.getRoute(trainNumber);
            System.out.print(route);
            int index = readInt("Номер пункта для удаления: ") - 1;
            service.removePoint(trainNumber, index);
            System.out.println("Пункт удалён.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void editPoint(int trainNumber) {
        try {
            Route route = service.getRoute(trainNumber);
            System.out.print(route);
            int index = readInt("Номер пункта для изменения: ") - 1;
            System.out.print("Новое название станции: ");
            String station = scanner.nextLine();
            System.out.print("Есть время прибытия? (да/нет): ");
            String hasArrival = scanner.nextLine().trim().toLowerCase();
            LocalTime arrival = null;
            if (hasArrival.equals("да")) {
                arrival = readTime("Время прибытия (HH:mm): ");
            }
            System.out.print("Есть время отправления? (да/нет): ");
            String hasDeparture = scanner.nextLine().trim().toLowerCase();
            LocalTime departure = null;
            if (hasDeparture.equals("да")) {
                departure = readTime("Время отправления (HH:mm): ");
            }
            service.editPoint(trainNumber, index, station, arrival, departure);
            System.out.println("Пункт изменён.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // --- Вспомогательные методы ---

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
    }

    private LocalTime readTime(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return LocalTime.parse(line, timeFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат времени. Используйте HH:mm (например, 08:30).");
            }
        }
    }
}
