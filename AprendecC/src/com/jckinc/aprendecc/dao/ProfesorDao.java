/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jckinc.aprendecc.dao;

import com.jckinc.aprendecc.dominio.Profesor;
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
 * @author talos
 */
public class ProfesorDao extends ConceptoDao{
    /*
    * Función que retorna un objeto Profesor desde la base de datos por su correo y contraseña
    */
    public Profesor consultar(String email, String password) {
        Profesor profesor = null;
        try {
            // se crea la consulta
            PreparedStatement stmtConsulta = connection.prepareStatement(
                    "SELECT ID, NAME, EMPLOYEE_CODE, EMAIL, PASSWORD FROM ROOT.Profesor WHERE email=? AND password=?");
            // se inyectan los parámetros email y password
            stmtConsulta.setString(1, email);
            stmtConsulta.setString(1, password);
            // se ejecuta la consulta
            ResultSet rs = stmtConsulta.executeQuery();
            // si se obtiene un registro se crea el objeto Profesor y se inyevtan los valores
            if(rs.next()) {
                profesor = new Profesor();
                profesor.setId(rs.getLong("id"));
                profesor.setName(rs.getString("name"));
                profesor.setEmployeeCode(rs.getString("employee_code"));
                profesor.setEmail(rs.getString("email"));
                profesor.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return profesor;
    }
    
    /**
     * Función que retorna un objeto Profesor desde la base de datos por su id 
     */
    public Profesor consultar(Long id) {
        Profesor profesor = null;
        try {
            // se crea la consulta
            PreparedStatement stmtConsulta = connection.prepareStatement(
                    "SELECT ID, NAME, EMPLOYEE_CODE, EMAIL, PASSWORD FROM ROOT.PROFESOR WHERE id=?");
            // se inyecta el parámetro id
            stmtConsulta.setLong(1, id);
            // se ejecuta la consulta
            System.out.println(id);
            ResultSet rs = stmtConsulta.executeQuery();
            // si se obtiene un registro se crea el objeto Profesor y se inyectan los valores
            if (rs.next()) {
                profesor = new Profesor();
                profesor.setId(rs.getLong("id"));
                profesor.setName(rs.getString("name"));
                profesor.setEmployeeCode(rs.getString("employee_code"));
                profesor.setEmail(rs.getString("email"));
                profesor.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return profesor;
    }

    /*
    * Función que retorna una lista de objetos profesor consultados desde la base de datos
    */
     public List<Profesor> consultar() {
        List<Profesor> profesores = new ArrayList<>();
        try {
              // se crea la consulta
            PreparedStatement stmtConsulta = connection.prepareStatement(
                    "SELECT ID, NAME, EMPLOYEE_CODE, EMAIL, PASSWORD FROM ROOT.Profesor");
            // se ejecuta la consulta
            ResultSet rs = stmtConsulta.executeQuery();
            Profesor profesor;
            // se recorren los registros del resultado retorna y se crea un alista de profesors
            while (rs.next()) {
                profesor = new Profesor();
                profesor.setId(rs.getLong("id"));
                profesor.setName(rs.getString("name"));
                profesor.setEmployeeCode(rs.getString("employee_code"));
                profesor.setEmail(rs.getString("email"));
                profesor.setPassword("password");
                
                profesores.add(profesor);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return profesores;
    }
    
    /*
     * Función que elimina un registro de Profesor en la base de datos
     */
    public boolean eliminar(Long id) {
        boolean bDeleted = false;

        try {
            // se crea la consulta
            PreparedStatement stmtDeleteAddress = connection.
                    prepareStatement("DELETE FROM ROOT.PROFESOR WHERE ID = ?");

            // se inyecta el parámetro id en la consulta
            stmtDeleteAddress.clearParameters();
            stmtDeleteAddress.setLong(1, id);
            // se ejecuta la consulta
            stmtDeleteAddress.executeUpdate();
            bDeleted = true;

        } catch (SQLException ex) {
            Logger.getLogger(ProfesorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bDeleted;

    }

    /*
    * Función para guardar en la base de datos un objeto de tipo Profesor
    */
    public boolean crear(Profesor profesor) {
        boolean bCreated = false;
        try {
              // se crea la consulta
            PreparedStatement stmtCreate = connection.prepareStatement(
                    "INSERT INTO ROOT.PROFESOR "
                    + "   (NAME, EMPLOYEE_CODE, EMAIL, PASSWORD) "
                    + "VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            // se inyectan los parámetros
            stmtCreate.clearParameters();
            stmtCreate.setString(1, profesor.getName());
            stmtCreate.setString(2, profesor.getEmployeeCode());
            stmtCreate.setString(3, profesor.getEmail());
            stmtCreate.setString(4, profesor.getPassword());
            
            // se ejecuta la consulta
            stmtCreate.executeUpdate();
            ResultSet results = stmtCreate.getGeneratedKeys();
            if (results.next()) {
                profesor.setId(new Long(results.getLong(1)));
            }
            bCreated = true;
        } catch (SQLException ex) {
            Logger.getLogger(ProfesorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bCreated;
    }

    /*
    * Función para actualizar los datos en la base de un objeto de tipo Profesor
    */
    public boolean actualizar(Profesor profesor) {
        boolean bEdited = false;
        try {
              // se crea la consulta
            PreparedStatement stmtUpdate = connection.prepareStatement(
                    "UPDATE ROOT.PROFESOR "
                    + " SET NAME=?, "
                    + " EMPLOYEE_CODE=?, "
                    + " EMAIL=?, "
                    + " PASSWORD=? "
                    + " WHERE ID=?",
                    Statement.RETURN_GENERATED_KEYS);

            // se inyectan los parámetros
            stmtUpdate.clearParameters();
            stmtUpdate.setString(1, profesor.getName());
            stmtUpdate.setString(2, profesor.getEmployeeCode());
            stmtUpdate.setString(3, profesor.getEmail());
            stmtUpdate.setString(4, profesor.getPassword());
            stmtUpdate.setLong(5, profesor.getId());
            
            // se ejecuta la consulta
            stmtUpdate.executeUpdate();
            bEdited = true;
        } catch (SQLException ex) {
            Logger.getLogger(ProfesorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bEdited;

    }
}
