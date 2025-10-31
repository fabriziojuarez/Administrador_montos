package com.example;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App 
{
    public static void main( String[] args )
    {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            // Registrar rutas
            

            server.start();
            System.out.println("Servidor corriendo en http://localhost:8080");
        } catch (IOException e) {
            System.err.println("❌ Error al iniciar el servidor: " + e.getMessage());
        }
    }
}
