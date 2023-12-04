/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entregablebbdd;

import static com.mycompany.entregablebbdd.Config.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Tabla "Estudiantes"
 *
 * ID (Clave primaria) Nombre Edad Dirección Correo electrónico
 *
 * ++insert ++update ++delete
 *
 * @author jacqueline
 */
public class Estudiantes implements AutoCloseable {

    Connection con = null;

    private int id;
    private String nombre;
    private int edad;
    private String direccion;
    private String email;

    @Override
    public String toString() {
        return "Estudiantes{" + "nombre=" + nombre + ", edad=" + edad + ", direccion=" + direccion + '}';
    }

    public Estudiantes() {
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void mostrarEstudiantes() throws SQLException {
        String sql = "select * from estudiantes";

        ResultSet rs = null;
        try (PreparedStatement pstm = con.prepareStatement(sql);) {
            rs = pstm.executeQuery();

            System.out.println("ID\tNombre\t\tEdad\t\tDireccion\t\tEmail");
            System.out.println("----------------------------------------------");

            while (rs.next()) {

                System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t\t"
                        + rs.getInt(3) + "\t\t" + rs.getString(4) + "\t\t" + rs.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Estudiantes> consultarEstudiantes(String nombre) throws SQLException {
        ArrayList<Estudiantes> alE = new ArrayList<Estudiantes>();
        Estudiantes e = null;

        String sql = "select * from estudiantes where Nombre like ? ;";
        ResultSet rs = null;
        try (PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.setString(1, nombre + "%");
            rs = pstm.executeQuery();

            System.out.println("ID\tNombre\t\tEdad\t\tDireccion\t\tEmail");
            System.out.println("----------------------------------------------");

            while (rs.next()) {
                e = new Estudiantes();
//                e.setId(rs.getInt(1));
                e.setNombre(rs.getString(2));
                e.setEdad(rs.getInt(3));
                e.setDireccion(rs.getString(4));
                e.setEmail(rs.getString(5));
                alE.add(e);

                System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t\t"
                        + rs.getInt(3) + "\t\t" + rs.getString(4) + "\t\t" + rs.getString(5));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return alE;
    }

    public int insertEstudiante(Estudiantes a) throws SQLException {
        int r;
        String sql = "Insert into estudiantes (Nombre, Edad, Direccion, Email) VALUES(?,?,?,?)";

        try (PreparedStatement pstm = con.prepareStatement(sql);) {

            pstm.setString(1, a.getNombre());
            pstm.setInt(2, a.getEdad());
            pstm.setString(3, a.getDireccion());
            pstm.setString(4, a.getEmail());
            r = pstm.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        return r;
    }

    public int updateEstudiante(Estudiantes a, int id) throws SQLException {

        int r;
        String sql = "update estudiantes set nombre=?,edad=?,direccion=?,email=? where ID=?;";

        try (PreparedStatement pstm = con.prepareStatement(sql);) {

            pstm.setString(1, a.getNombre());
            pstm.setInt(2, a.getEdad());
            pstm.setString(3, a.getDireccion());
            pstm.setString(4, a.getEmail());
            pstm.setInt(5, id);
            r = pstm.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        return r;
    }

    public int deleteEstudiante(int idE) throws SQLException {

        int r;
        String sql = "delete from estudiantes where ID = ? ;";

        try (PreparedStatement pstm = con.prepareStatement(sql);) {
            pstm.setInt(1, idE);

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
