package com.example.routes;

import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.example.controllers.FastFortexController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public class FastFortexRoutes {
    public static void Routes(HttpServer server){
        server.createContext("/fastfortex", (HttpExchange exchange)->{
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
            String method = exchange.getRequestMethod();
            Integer status = 200;
            String res_body = "";

            switch (method) {
                case "POST": {
                    InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder body = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        body.append(line).append("\n");
                    }
                    br.close();

                    String rawBody = body.toString();

                    Gson gson = new Gson();
                    JsonObject json = gson.fromJson(rawBody, JsonObject.class);

                    String moneda_base = json.has("moneda_base") && !json.get("moneda_base").isJsonNull()
                        ? json.get("moneda_base").getAsString() : null;
                    String mes = json.has("mes") && !json.get("mes").isJsonNull()
                        ? json.get("mes").getAsString() : "-";
                    
                    res_body = FastFortexController.getTotal(moneda_base, mes);
                    break;
                }
                case "OPTIONS":{
                    exchange.sendResponseHeaders(204, -1);
                    break;
                }
                default:{
                    status = 405;
                    res_body = "Método no soportado";
                    break;
                }
            }
            enviar(exchange, status, res_body);
        });
    }

    public static void enviar(HttpExchange exchange, int status, String res_body) {
        try{
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
