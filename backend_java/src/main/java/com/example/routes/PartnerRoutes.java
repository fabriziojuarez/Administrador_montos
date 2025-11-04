package com.example.routes;

import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.example.controllers.PartnerController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public class PartnerRoutes {
    public static void Routes(HttpServer server){
        PartnerController partnerController = new PartnerController();

        server.createContext("/partners", (HttpExchange exchange) -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
            exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");
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

        server.createContext("/partners/", (HttpExchange exchange) -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
            exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");
            String method = exchange.getRequestMethod();
            Integer status = 200;
            String body = "";

            String path = exchange.getRequestURI().getPath();
            String[] partes = path.split("/");                 
            Integer id = null;

            if(partes.length !=3){
                status = 400; 
                body = "Falta el ID en la URL";
            }

            try {
                id = Integer.parseInt(partes[2]); 
                switch (method) {
                    case "PUT": {
                        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
                        BufferedReader br = new BufferedReader(isr);
                        StringBuilder reqBody = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            reqBody.append(line);
                        }
                        br.close();

                        String rawBody = reqBody.toString().trim();
                        System.out.println("JSON recibido: " + rawBody);

                        Gson gson = new Gson();
                        JsonObject json = gson.fromJson(rawBody, JsonObject.class);

                        String name_partner = json.has("name_partner") && !json.get("name_partner").isJsonNull()
                            ? json.get("name_partner").getAsString() : null;
                        Integer id_horario = json.has("id_horario") && !json.get("id_horario").isJsonNull()
                            ? json.get("id_horario").getAsInt() : null;
                        Integer status_partner = json.has("status_partner") && !json.get("status_partner").isJsonNull()
                            ? json.get("status_partner").getAsInt() : null;
                        String observation_partner = json.has("observation_partner") && !json.get("observation_partner").isJsonNull()
                            ? json.get("observation_partner").getAsString() : "-";

                        if (name_partner == null || id_horario == null || status_partner == null) {
                            status = 400;
                            body = "Faltan datos obligatorios";
                        }

                        body = partnerController.update(id, name_partner, id_horario, status_partner, observation_partner);
                        break;
                    }
                    case "OPTIONS":{
                        // Angular envia una peticion con metodo OPTIONS de manera
                        // previa antes de la peticion real
                        // esto es para ver si el servicio acepta ciertas peticiones
                        exchange.sendResponseHeaders(204, -1);
                        break;
                    }
                    default:{
                        status = 405;
                        body = "Método no soportado";
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                status = 400;
                body = "ID inválido";
            }
            enviar(exchange, status, body);
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
