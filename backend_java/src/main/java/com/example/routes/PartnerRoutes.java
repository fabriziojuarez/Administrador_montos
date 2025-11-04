package com.example.routes;

import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.example.controllers.PartnerController;
import com.sun.net.httpserver.HttpExchange;

public class PartnerRoutes {
    public static void Routes(HttpServer server){
        PartnerController partnerController = new PartnerController();

        server.createContext("/partners", (HttpExchange exchange) -> {
            String method = exchange.getRequestMethod();

            Integer status = 200;
            String res_body = "";

            switch (method) {
                case "GET":
                    res_body = partnerController.index();
                    break;
                case "POST":
                    res_body = "Crear un nuevo partner";
                    break;
                default:
                    status = 405; 
                    res_body = "Método no permitido";
                    break;
            }
            enviar(exchange, status, res_body);
        });
    }

    public static void enviar(HttpExchange exchange, int status, String res_body) {
        try{
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
            byte[] bytes = res_body.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(status, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
