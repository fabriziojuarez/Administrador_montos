package com.example;

import com.example.routes.FastFortexRoutes;
import com.example.routes.PorcentRoutes;
import com.example.routes.RegistroRoutes;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App 
{
    public static void main( String[] args )
    {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            // Registrar rutas
            RegistroRoutes.Routes(server);
            PorcentRoutes.Routes(server);
            FastFortexRoutes.Routes(server);
            server.start();
            System.out.println("Servidor corriendo en http://localhost:8000");
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}
