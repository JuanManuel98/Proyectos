/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jckinc.aprendecc.dao;

import com.jckinc.aprendecc.dominio.Ejercicio;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.serial.SerialBlob;

/**
 * Clase que implementa operaciones CRUD con la base por medio de consultas SQL
 * @author d3xt3
 */
public class EjercicioDao extends ConceptoDao{
    /**
     * Función que retorna un objeto Ejercicio desde la base de datos por su id 
     */
    public Ejercicio consultar(Long id) {
        Ejercicio estudiante = null;
        try {
            // se crea la consulta
            PreparedStatement stmtConsulta = connection.prepareStatement(
                    "SELECT ID, SUBJECT, EXCERCISE_NAME, LEVEL, CONTENT, ANSWERS FROM ROOT.Ejercicio WHERE id=?");
            // se inyecta el parámetro id
            stmtConsulta.setLong(1, id);
            // se ejecuta la consulta
            ResultSet rs = stmtConsulta.executeQuery();
            // si se obtiene un registro se crea el objeto Ejercicio y se inyectan los valores
            if (rs.next()) {
                estudiante = new Ejercicio();
                estudiante.setId(rs.getLong("id"));
                estudiante.setSubject(rs.getString("subject"));
                estudiante.setExcerciseName(rs.getString("excercise_name"));
                estudiante.setLevel(rs.getInt("level"));
                Blob content = rs.getBlob("content");
                if (content != null) {
                    estudiante.setContent(content.getBytes(1, (int) content.length()));
                }
                Blob answers = rs.getBlob("answers");
                if (answers != null) {
                    estudiante.setAnswers(answers.getBytes(1, (int) answers.length()));
                }
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return estudiante;
    }

    /*
    * Función que retorna una lista de objetos estudiante consultados desde la base de datos
    */
     public List<Ejercicio> consultar() {
        List<Ejercicio> ejercicios = new ArrayList<>();
        try {
              // se crea la consulta
            PreparedStatement stmtConsulta = connection.prepareStatement(
                    "SELECT ID, SUBJECT, EXCERCISE_NAME, LEVEL, CONTENT, ANSWERS FROM ROOT.Ejercicio");
            // se ejecuta la consulta
            ResultSet rs = stmtConsulta.executeQuery();
            Ejercicio ejercicio;
            // se recorren los registros del resultado retorna y se crea un alista de ejercicios
            while (rs.next()) {
                ejercicio = new Ejercicio();
                ejercicio.setId(rs.getLong("id"));
                ejercicio.setSubject(rs.getString("subject"));
                ejercicio.setExcerciseName(rs.getString("excercise_name"));
                ejercicio.setLevel(rs.getInt("level"));
                Blob content = rs.getBlob("content");
                if(content != null) {
                    ejercicio.setContent(content.getBytes(1, (int) content.length()));
                }
                Blob answers = rs.getBlob("answers");
                if(answers != null) {
                    ejercicio.setAnswers(answers.getBytes(1, (int) answers.length()));
                }
                ejercicios.add(ejercicio);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return ejercicios;
    }
    
    /*
     * Función que elimina un registro de Ejercicio en la base de datos
     */
    public boolean eliminar(Long id) {
        boolean bDeleted = false;

        try {
            // se crea la consulta
            PreparedStatement stmtDeleteAddress = connection.
                    prepareStatement("DELETE FROM ROOT.EJERCICIO WHERE ID = ?");

            // se inyecta el parámetro id en la consulta
            stmtDeleteAddress.clearParameters();
            stmtDeleteAddress.setLong(1, id);
            // se ejecuta la consulta
            stmtDeleteAddress.executeUpdate();
            bDeleted = true;

        } catch (SQLException ex) {
            Logger.getLogger(EjercicioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bDeleted;

    }

    /*
    * Función para guardar en la base de datos un objeto de tipo Ejercicio
    */
    public boolean crear(Ejercicio ejercicio) {
        boolean bCreated = false;
        try {
              // se crea la consulta
            PreparedStatement stmtCreate = connection.prepareStatement(
                    "INSERT INTO ROOT.EJERCICIO "
                    + "   (SUBJECT, EXCERCISE_NAME, "
                    + "    LEVEL, CONTENT, ANSWERS)"
                    + "VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            // se inyectan los parámetros
            stmtCreate.clearParameters();
            stmtCreate.setString(1, ejercicio.getSubject());
            stmtCreate.setString(2, ejercicio.getExcerciseName());
            stmtCreate.setInt(3, ejercicio.getLevel());
            Blob content = new SerialBlob(ejercicio.getContent());
            stmtCreate.setBlob(4, content);
            Blob answers = new SerialBlob(ejercicio.getAnswers());
            stmtCreate.setBlob(5, answers);
            // se ejecuta la consulta
            stmtCreate.executeUpdate();
            ResultSet results = stmtCreate.getGeneratedKeys();
            if (results.next()) {
                ejercicio.setId(results.getLong(1));
            }
            bCreated = true;
        } catch (SQLException ex) {
            Logger.getLogger(EjercicioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bCreated;
    }

    /*
    * Función para actualizar los datos en la base de un objeto de tipo Ejercicio
    */
    public boolean actualizar(Ejercicio ejercicio) {
        boolean bEdited = false;
        try {
              // se crea la consulta
            PreparedStatement stmtUpdate = connection.prepareStatement(
                    "UPDATE ROOT.EJERCICIO "
                    + " SET SUBJECT=?, "
                    + " EXCERCISE_NAME=?,"
                    + " LEVEL=?,"
                    + " CONTENT=?,"
                    + " ANSWERS=?"
                    + " WHERE ID=?",
                    Statement.RETURN_GENERATED_KEYS);

            // se inyectan los parámetros
            stmtUpdate.clearParameters();
            stmtUpdate.setString(1, ejercicio.getSubject());
            stmtUpdate.setString(2, ejercicio.getExcerciseName());
            stmtUpdate.setInt(3, ejercicio.getLevel());
            Blob content = new SerialBlob(ejercicio.getContent());
            stmtUpdate.setBlob(4, content);
            Blob answers = new SerialBlob(ejercicio.getAnswers());
            stmtUpdate.setBlob(5, answers);
            stmtUpdate.setLong(6, ejercicio.getId());
            
            // se ejecuta la consulta
            stmtUpdate.executeUpdate();
            bEdited = true;
        } catch (SQLException ex) {
            Logger.getLogger(EjercicioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bEdited;

    }
}
