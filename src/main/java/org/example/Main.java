package org.example;

import org.example.repository.RouteRepository;
import org.example.service.RouteService;
import org.example.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        RouteRepository repository = new RouteRepository();
        RouteService service = new RouteService(repository);
        ConsoleUI ui = new ConsoleUI(service);
        ui.start();
    }
}