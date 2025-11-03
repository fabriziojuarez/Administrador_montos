package com.example.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import io.github.cdimascio.dotenv.Dotenv;

public class FastFortexService{
    private static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("FASTFOREX_API_KEY");

    private static final String urlbase = "https://api.fastforex.io";

    public String fetch_one(String moneda_registro, String moneda_base){
        try{
            String apiUrl = urlbase+"/fetch-one?from="+moneda_registro+"&to="+moneda_base;
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("accept", "application/json")
                .header("X-API-KEY", API_KEY)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            return response.body();
        }catch(Exception e){
            System.out.println("Error al obtener la tasa de cambio: " + e.getMessage());
            return "{\"error\": \"No se pudo obtener la tasa de cambio\"}";
        }
    }
}