/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entregablebbdd;

import static com.mycompany.entregablebbdd.Config.pass;
import static com.mycompany.entregablebbdd.Config.url;
import static com.mycompany.entregablebbdd.Config.user;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Tabla "Cursos"
 *
 * ID (Clave primaria) Nombre del curso Descripción Créditos
 *
 * ++insert ++update ++delete
 *
 * @author jacqueline
 */
public class Cursos implements AutoCloseable {

    Connection con = null;

    private int id;
    private String nombreCurso;
    private String descripcion;
    private int creditos;

    public Cursos() {
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public void mostrarCursos() throws SQLException {
        String sql = "select * from cursos";

        ResultSet rs = null;
        try (PreparedStatement pstm = con.prepareStatement(sql);) {
            rs = pstm.executeQuery();

            System.out.println("ID\tNombreCurso\t\tDescripcion\t\tCreditos");
            System.out.println("----------------------------------------------------");

            while (rs.next()) {

                System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t\t"
                        + rs.getString(3) + "\t\t" + rs.getInt(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Cursos> consultarCursos(String nombreC) throws SQLException {
        ArrayList<Cursos> alC = new ArrayList<Cursos>();
        Cursos c = null;

        String sql = "select * from cursos where NombreCurso like ? ;";
        ResultSet rs = null;
        try (PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.setString(1, nombreC + "%");
            rs = pstm.executeQuery();

            System.out.println("ID\tNombreCurso\t\tDescripcion\t\tCreditos");
            System.out.println("----------------------------------------------");

            while (rs.next()) {
                c = new Cursos();
                c.setNombreCurso(rs.getString(2));
                c.setDescripcion(rs.getString(3));
                c.setCreditos(rs.getInt(4));
                alC.add(c);
                System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t\t"
                        + rs.getString(3) + "\t\t" + rs.getInt(4));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return alC;
    }

    public void consultarCursos() throws SQLException {
        try (Statement statement = con.createStatement()) {

            String sql = "select * from cursos";

            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {

                    String nombreC = resultSet.getString("NombreCurso");
                    String descripcion = resultSet.getString("Descripcion");
                    int creditos = resultSet.getInt("Creditos");
                    System.out.println("ID: " + id + ", Nombre del curso: " + nombreC + ", Descripcion: " + descripcion + ", Creditos: " + creditos);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insertCurso(Cursos a) throws SQLException {

        int r;
        String sql = "Insert into cursos (NombreCurso, Descripcion, Creditos) VALUES(?,?,?)";

        try (PreparedStatement pstm = con.prepareStatement(sql);) {

            pstm.setString(1, a.getNombreCurso());
            pstm.setString(2, a.getDescripcion());
            pstm.setInt(3, a.getCreditos());
            r = pstm.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        return r;
    }

    public int updateCurso(Cursos c, int id) throws SQLException {

        int r;
        String sql = "update cursos set NombreCurso=?,descripcion=?,creditos=? where ID=? ;";

        try (PreparedStatement pstm = con.prepareStatement(sql);) {

            pstm.setString(1, c.getNombreCurso());
            pstm.setString(2, c.getDescripcion());
            pstm.setInt(3, c.getCreditos());
            pstm.setInt(4, id);

            r = pstm.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        return r;
    }

    public int deleteCurso(int idC) throws SQLException {

        int r;
        String sql = "delete from cursos where ID = ? ;";

        try (PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.setInt(1, idC);

            r = pstm.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        return r;
    }

    public void close() throws Exception {
        if (con != null) {
            con.close();
        }
    }

}
