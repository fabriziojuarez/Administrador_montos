package com.example.routes;

import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.example.controllers.FastFortexController;
import com.sun.net.httpserver.HttpExchange;

public class FastFortexRoutes {
    public static void Routes(HttpServer server){
        server.createContext("/fastfortex", (HttpExchange exchange)->{
            String method = exchange.getRequestMethod();

            if(!method.equals("POST")){
                enviar(exchange, 400, "Método no soportado");
                return;
            }
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                body.append(line).append("\n");
            }
            br.close();

            String rawBody = body.toString();
            String moneda_base = null;

            String[] parts = rawBody.split("Content-Disposition:");
            for (String part : parts) {
                if (part.contains("name=\"moneda_base\"")) {
                    moneda_base = part.split("\n")[2].trim();
                }
            }

            if (moneda_base == null) {
                System.out.println("Error: no se pudieron obtener los campos");
            } 

            String total = FastFortexController.getTotal(moneda_base);
            enviar(exchange, 200, total);

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
