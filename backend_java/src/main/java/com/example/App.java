package com.example;

import com.example.controllers.RegistroController;
import com.example.database.Conexion;
import java.sql.Connection;

public class App 
{
    public static void main( String[] args )
    {
        Connection conexion = Conexion.getConnection();
        if (conexion != null) {
            System.out.println("Hola, contectado a la base de datos correctamente!");
        }

        RegistroController controller = new RegistroController();

        String registrosJson = controller.index();
        System.out.println("Registros en formato JSON:");   
        System.out.println(registrosJson);
    }
}
