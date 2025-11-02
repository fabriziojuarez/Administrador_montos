package com.example.routes;

import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.example.controllers.PorcentController;
import com.sun.net.httpserver.HttpExchange;


public class PorcentRoutes{

    public static void Routes(HttpServer server) {
        PorcentController porcentController = new PorcentController();

        server.createContext("/porcentajes", (HttpExchange exchange)->{
            String method = exchange.getRequestMethod();
            if(!method.equals("GET")){
                enviar(exchange, 400, "Método no soportado");
                return;
            }
            Integer status = 200;
            String res_body = porcentController.porcentajeRegistros();
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