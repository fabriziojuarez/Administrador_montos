package com.example.routes;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.example.controllers.RegistroController;

public class RegistroRoutes
{

    public static void Routes(HttpServer server) {
        RegistroController registroController = new RegistroController();

        server.createContext("/registros", (HttpExchange exchange) -> {
            String method = exchange.getRequestMethod();
            Integer status = 200;
            String body = "";

            switch (method) {
                case "GET":
                    body = registroController.index();
                    break;
                case "POST":
                    body = "POST registros";
                    break;
                default:
                    status = 400;
                    body = "Método no soportado";
                    break;
            }   
            enviar(exchange, status, body);
        });

        server.createContext("/registros/", (HttpExchange exchange) -> {
            String method = exchange.getRequestMethod();
            Integer status = 200;
            String body = "";

            String path = exchange.getRequestURI().getPath();
            String[] partes = path.split("/");                 
            Integer id = null;
            
            if (partes.length == 3) {
                try {
                    id = Integer.parseInt(partes[2]); 
                    switch (method) {
                        case "GET":
                            body = "GET registro por ID" + id;
                            break;
                        case "PUT":
                            body = "PUT actualizar registro por ID";
                            break;
                        case "DELETE":
                            body = "DELETE eliminar registro por ID" + id;
                            break;
                        default:
                            body = "Método no soportado";
                            break;
                    }  
                } catch (NumberFormatException e) {
                    status = 444; 
                    body = e.getMessage();
                }
            }else{
                status = 400; 
                body = "Falta el ID en la URL";
            }
            enviar(exchange, status, body);
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
