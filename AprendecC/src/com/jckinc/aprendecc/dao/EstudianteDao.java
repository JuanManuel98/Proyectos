/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jckinc.aprendecc.dao;

import com.jckinc.aprendecc.dominio.Estudiante;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa operaciones CRUD con la base por medio de consultas SQL
 * @author d3xt3
 */
public class EstudianteDao extends ConceptoDao{
    /*
    * Función que retorna un objeto Estudiante desde la base de datos por su correo y contraseña
    */
    public Estudiante consultar(String email, String password) {
        Estudiante estudiante = null;
        try {
            // se crea la consulta
            PreparedStatement stmtConsulta = connection.prepareStatement(
                    "SELECT ID, NAME, BIRTHDATE, PLATE, EMAIL, PASSWORD, EXP_LEVEL FROM ROOT.Estudiante WHERE email=? AND password=?");
            // se inyectan los parámetros email y password
            stmtConsulta.setString(1, email);
            stmtConsulta.setString(2, password);
            // se ejecuta la consulta
            ResultSet rs = stmtConsulta.executeQuery();
            // si se obtiene un registro se crea el objeto Estudiante y se inyevtan los valores
            if(rs.next()) {
                estudiante = new Estudiante();
                estudiante.setId(rs.getLong("id"));
                estudiante.setName(rs.getString("name"));
                estudiante.setBirthdate(rs.getDate("birthdate"));
                estudiante.setPlate(rs.getString("plate"));
                estudiante.setEmail(rs.getString("email"));
                estudiante.setPassword(rs.getString("password"));
                estudiante.setExpLevel(rs.getInt("exp_level"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return estudiante;
    }
    
    /**
     * Función que retorna un objeto Estudiante desde la base de datos por su id 
     */
    public Estudiante consultar(Long id) {
        Estudiante estudiante = null;
        try {
            // se crea la consulta
            PreparedStatement stmtConsulta = connection.prepareStatement(
                    "SELECT ID, NAME, BIRTHDATE, PLATE, EMAIL, PASSWORD, EXP_LEVEL FROM ROOT.Estudiante WHERE id=?");
            // se inyecta el parámetro id
            stmtConsulta.setLong(1, id);
            // se ejecuta la consulta
            ResultSet rs = stmtConsulta.executeQuery();
            // si se obtiene un registro se crea el objeto Estudiante y se inyectan los valores
            if (rs.next()) {
                estudiante = new Estudiante();
                estudiante.setId(rs.getLong("id"));
                estudiante.setName(rs.getString("name"));
                estudiante.setBirthdate(rs.getDate("birthdate"));
                estudiante.setPlate(rs.getString("plate"));
                estudiante.setEmail(rs.getString("email"));
                estudiante.setPassword(rs.getString("password"));
                estudiante.setExpLevel(rs.getInt("exp_level"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return estudiante;
    }

    /*
    * Función que retorna una lista de objetos estudiante consultados desde la base de datos
    */
     public List<Estudiante> consultar() {
        List<Estudiante> estudiantes = new ArrayList<>();
        try {
              // se crea la consulta
            PreparedStatement stmtConsulta = connection.prepareStatement(
                    "SELECT ID, NAME, BIRTHDATE, PLATE, EMAIL, PASSWORD, EXP_LEVEL FROM ROOT.Estudiante");
            // se ejecuta la consulta
            ResultSet rs = stmtConsulta.executeQuery();
            Estudiante estudiante;
            // se recorren los registros del resultado retorna y se crea un alista de estudiantes
            while (rs.next()) {
                estudiante = new Estudiante();
                estudiante.setId(rs.getLong("id"));
                estudiante.setName(rs.getString("name"));
                estudiante.setBirthdate(rs.getDate("birthdate"));
                estudiante.setPlate(rs.getString("plate"));
                estudiante.setEmail(rs.getString("email"));
                estudiante.setPassword(rs.getString("password"));
                estudiante.setExpLevel(rs.getInt("exp_level"));
                estudiantes.add(estudiante);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return estudiantes;
    }
    
    /*
     * Función que elimina un registro de Estudiante en la base de datos
     */
    public boolean eliminar(Long id) {
        boolean bDeleted = false;

        try {
            // se crea la consulta
            PreparedStatement stmtDeleteAddress = connection.
                    prepareStatement("DELETE FROM ROOT.ESTUDIANTE WHERE ID = ?");

            // se inyecta el parámetro id en la consulta
            stmtDeleteAddress.clearParameters();
            stmtDeleteAddress.setLong(1, id);
            // se ejecuta la consulta
            stmtDeleteAddress.executeUpdate();
            bDeleted = true;

        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bDeleted;

    }

    /*
    * Función para guardar en la base de datos un objeto de tipo Estudiante
    */
    public boolean crear(Estudiante estudiante) {
        boolean bCreated = false;
        try {
              // se crea la consulta
            PreparedStatement stmtCreate = connection.prepareStatement(
                    "INSERT INTO ROOT.ESTUDIANTE "
                    + "   (NAME, BIRTHDATE, "
                    + "    PLATE, EMAIL, PASSWORD, EXP_LEVEL) "
                    + "VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            // se inyectan los parámetros
            stmtCreate.clearParameters();
            stmtCreate.setString(1, estudiante.getName());
            stmtCreate.setDate(2, new java.sql.Date(estudiante.getBirthdate().getTime()));
            stmtCreate.setString(3, estudiante.getPlate());
            stmtCreate.setString(4, estudiante.getEmail());
            stmtCreate.setString(5, estudiante.getPassword());
            stmtCreate.setInt(6, estudiante.getExpLevel());
            // se ejecuta la consulta
            stmtCreate.executeUpdate();
            ResultSet results = stmtCreate.getGeneratedKeys();
            if (results.next()) {
                estudiante.setId(results.getLong(1));
            }
            bCreated = true;
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bCreated;
    }

    /*
    * Función para actualizar los datos en la base de un objeto de tipo Estudiante
    */
    public boolean actualizar(Estudiante estudiante) {
        boolean bEdited = false;
        try {
              // se crea la consulta
            PreparedStatement stmtUpdate = connection.prepareStatement(
                    "UPDATE ROOT.ESTUDIANTE "
                    + " SET NAME=?, "
                    + " BIRTHDATE=?,"
                    + " PLATE=?,"
                    + " EMAIL=?, "
                    + " PASSWORD=?, "
                    + " EXP_LEVEL=? "
                    + " WHERE ID=?",
                    Statement.RETURN_GENERATED_KEYS);

            // se inyectan los parámetros
            stmtUpdate.clearParameters();
            stmtUpdate.setString(1, estudiante.getName());
            stmtUpdate.setDate(2, new java.sql.Date(estudiante.getBirthdate().getTime()));
            stmtUpdate.setString(3, estudiante.getPlate());
            stmtUpdate.setString(4, estudiante.getEmail());
            stmtUpdate.setString(5, estudiante.getPassword());
            stmtUpdate.setInt(6, estudiante.getExpLevel());
            stmtUpdate.setLong(7, estudiante.getId());
            
            // se ejecuta la consulta
            stmtUpdate.executeUpdate();
            bEdited = true;
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bEdited;

    }
}
