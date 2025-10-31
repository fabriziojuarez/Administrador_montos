package com.example.routes;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

// import com.example.controllers.RegistroController;

public class RegistroRoutes
{

    public static void Routes(HttpServer server) {
        // RegistroController registroController = new RegistroController();

        server.createContext("/registros", (HttpExchange exchange) -> {
            String method = exchange.getRequestMethod();
            enviar(exchange, 200, method);
        });
    }

    public static void enviar(HttpExchange exchange, int status, String body) {
        try{
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(status, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
