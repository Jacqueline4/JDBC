/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entregablebbdd;

import static com.mycompany.entregablebbdd.Config.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jacqueline
 */
public class Tablas implements AutoCloseable {

    Connection con = null;

    public Tablas() {
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void crearTablas() throws SQLException {

        try (Statement stm = con.createStatement()) {
            stm.executeUpdate("CREATE TABLE IF NOT EXISTS estudiantes ("
                    + "ID INT PRIMARY KEY AUTO_INCREMENT,"
                    + "Nombre VARCHAR(50),"
                    + "Edad INT,"
                    + "Direccion VARCHAR(50),"
                    + "Email VARCHAR(50))");
            System.out.println("Creada la tabla estudiantes");

            stm.executeUpdate("CREATE TABLE IF NOT EXISTS cursos ("
                    + "ID INT PRIMARY KEY AUTO_INCREMENT,"
                    + "NombreCurso VARCHAR(50),"
                    + "Descripcion VARCHAR(50),"
                    + "Creditos INT)");
            System.out.println("Creada la tabla cursos");

            stm.executeUpdate("CREATE TABLE IF NOT EXISTS inscripciones ("
                    + "ID INT PRIMARY KEY AUTO_INCREMENT,"
                    + "IDEstudiante INT,"
                    + "IDCurso INT,"
                    + "FechaInscripcion DATE,"
                    + "FOREIGN KEY (IDEstudiante) REFERENCES estudiantes(ID)on delete cascade on update cascade,"
                    + "FOREIGN KEY (IDCurso) REFERENCES cursos(ID)on delete cascade on update cascade)");
            System.out.println("Creada la tabla inscripciones");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() throws Exception {
        if (con != null) {
            con.close();
        }
    }
}
