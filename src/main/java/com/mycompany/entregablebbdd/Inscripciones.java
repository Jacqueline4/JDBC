/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entregablebbdd;

import static com.mycompany.entregablebbdd.Config.pass;
import static com.mycompany.entregablebbdd.Config.url;
import static com.mycompany.entregablebbdd.Config.user;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Además, crea una tercera tabla llamada "Inscripciones" que represente la
 * relación muchos a muchos entre estudiantes y cursos. Esta tabla debe contener
 * al menos los siguientes atributos: Tabla "Inscripciones"
 *
 * ID (Clave primaria) ID del estudiante (clave foránea) ID del curso (clave
 * foránea) Fecha de inscripción
 *
 * ++get ++delete
 *
 * @author jacqueline
 */
public class Inscripciones implements AutoCloseable {

    Connection con = null;
    private int id;
    private int id_estudiante;
    private int id_curso;
    private LocalDate fecha;

    public Inscripciones() {
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public int getId() {
        return id;
    }

    public int getId_estudiante() {
        return id_estudiante;
    }

    public int getId_curso() {
        return id_curso;
    }

    public LocalDate getFecha() {
        return fecha;
    }
        public void setId_estudiante(int id_estudiante) {
        this.id_estudiante = id_estudiante;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    

    public int registrarInscripcion(int idEstudiante, int idCurso, LocalDate fechaInscripcion) throws SQLException {
        int r;
        String sql = "Insert into inscripciones (IDEstudiante, IDCurso, FechaInscripcion) VALUES (?, ?, ?)";

        try (PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.setInt(1, idEstudiante);
            pstm.setInt(2, idCurso);
            Date sqlDate = Date.valueOf(fechaInscripcion);
            pstm.setDate(3, sqlDate);

            r = pstm.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }
        return r;
    }

    public void mostrarInscripciones() throws SQLException {
        String sql = "select * from inscripciones";
        ResultSet rs = null;
        try (PreparedStatement pstm = con.prepareStatement(sql);) {
            rs = pstm.executeQuery();

            System.out.println("ID\tIDEstudiante\tIDCurso\tFechaInscripcion");
            System.out.println("----------------------------------------------");

            while (rs.next()) {

                System.out.println(rs.getInt(1) + "\t" + rs.getInt(2) + "\t\t"
                        + rs.getInt(3) + "\t" + rs.getDate(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int deleteInscripcion(int id) throws SQLException {

        int r;
        String sql = "delete from inscripciones where ID = ? ;";

        try (PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.setInt(1, id);
            r = pstm.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        return r;
    }

    public ArrayList<String> getCursos(String nombre) throws SQLException {
        ResultSet rs = null;
        ArrayList<String> al = new ArrayList<String>();
        String NombreCurso = null;
        String sql = "select NombreCurso from cursos as c "
                + "join inscripciones as i "
                + "on c.ID=IDCurso "
                + "join estudiantes as e "
                + "on e.ID= IDEstudiante "
                + "where Nombre like ? ;";

        try (PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.setString(1, nombre + "%");
            rs = pstm.executeQuery();
            while (rs.next()) {
                NombreCurso = rs.getString(1);
                al.add(NombreCurso);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return al;
    }

    public ArrayList<String> getEstudiantes(String nombreCurso) throws SQLException {
        ResultSet rs = null;
        ArrayList<String> al = new ArrayList<String>();
        String NombreEstudiante = null;

        String sql = "select Nombre from estudiantes as e "
                + "join inscripciones as i "
                + "on e.ID=IDEstudiante "
                + "join cursos as c "
                + "on c.ID= IDCurso "
                + "where NombreCurso like ? ;";

        try (PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.setString(1, nombreCurso + "%");
            rs = pstm.executeQuery();
            while (rs.next()) {
                NombreEstudiante = rs.getString(1);
                al.add(NombreEstudiante);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return al;
    }

    public void close() throws Exception {
        if (con != null) {
            con.close();
        }
    }


}
