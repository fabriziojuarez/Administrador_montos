package com.example.routes;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import com.example.controllers.RegistroController;

public class RegistroRoutes
{
    public static void Routes(HttpServer server) {
        RegistroController registroController = new RegistroController();
        //Gson gson = new Gson();


        server.createContext("/registros", (HttpExchange exchange) -> {
            String method = exchange.getRequestMethod();

            Integer status = 200;
            String res_body = "";

            switch (method) {
                case "GET":
                    res_body = registroController.index();
                    break;
                case "POST":
                    InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder body = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        body.append(line).append("\n");
                    }
                    br.close();

                    String rawBody = body.toString();

                    // Extraer los valores del multipart (búsqueda manual)
                    String montoStr = null;
                    String moneda = null;

                    // Separar por líneas
                    String[] parts = rawBody.split("Content-Disposition:");
                    for (String part : parts) {
                        if (part.contains("name=\"monto_registro\"")) {
                            montoStr = part.split("\n")[2].trim();
                        } else if (part.contains("name=\"moneda_registro\"")) {
                            moneda = part.split("\n")[2].trim();
                        }
                    }

                    if (montoStr == null || moneda == null) {
                        System.out.println("Error: no se pudieron obtener los campos");
                    } 

                    res_body = registroController.store(new BigDecimal(montoStr), moneda);
                    break;
                default:
                    status = 400;
                    res_body = "Método no soportado";
                    break;
            }
            enviar(exchange, status, res_body);
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
                            // body = registroController.show(id);
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

    public static void enviar(HttpExchange exchange, int status, String res_body) {
        try{
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
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
