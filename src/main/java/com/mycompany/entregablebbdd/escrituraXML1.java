/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entregablebbdd;

import static com.mycompany.entregablebbdd.Config.pass;
import static com.mycompany.entregablebbdd.Config.url;
import static com.mycompany.entregablebbdd.Config.user;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author jacqueline
 */
public class escrituraXML1 {

    public static void main(String[] args) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        escribir(con);

    }

    public static void escribir(Connection con) {
        String sql = "SELECT nombre,edad,direccion, c.NombreCurso FROM estudiantes e "
                + " JOIN inscripciones i ON e.ID=i.IDEstudiante "
                + "JOIN cursos c ON i.IDCurso=c.ID";

        ResultSet rs = null;
        File f = new File("escrituraSelect.xml");

        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
        try (PreparedStatement pstm = con.prepareStatement(sql);) {
            rs = pstm.executeQuery();
            XMLStreamWriter xmlWriter = xmlOutputFactory.createXMLStreamWriter(new FileWriter(f, false));
            xmlWriter.writeStartDocument();
            xmlWriter.writeCharacters("\n");
            xmlWriter.writeStartElement("alumnos");
            while (rs.next()) {
//                System.out.println(rs.getString(1) + "\t" + rs.getInt(2) + "\t\t"
//                        + rs.getString(3) + "\t\t" + rs.getString(4));

                xmlWriter.writeStartElement("alumno");
                xmlWriter.writeStartElement("nombre");
                xmlWriter.writeCharacters(rs.getString(1));
                xmlWriter.writeEndElement();
                xmlWriter.writeStartElement("edad");
                xmlWriter.writeCharacters(rs.getInt(2) + "");
                xmlWriter.writeEndElement();
                xmlWriter.writeStartElement("direccion");
                xmlWriter.writeCharacters(rs.getString(3));
                xmlWriter.writeEndElement();
                xmlWriter.writeStartElement("nombreCurso");
                xmlWriter.writeCharacters(rs.getString(4));
                xmlWriter.writeEndElement();
                xmlWriter.writeEndElement();

            }
            xmlWriter.writeEndElement();
            xmlWriter.writeEndDocument();
            xmlWriter.close();
        } catch (SQLException | IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
    /*
    SELECT nombre,edad,direccion, c.NombreCurso FROM estudiantes e
 JOIN inscripciones i ON e.ID=i.IDEstudiante
JOIN cursos c ON i.IDCurso=c.ID
     */
}
