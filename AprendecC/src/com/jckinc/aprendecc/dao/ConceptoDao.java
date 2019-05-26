/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jckinc.aprendecc.dao;

import com.jckinc.aprendecc.util.ConnectionManager;
import java.sql.Connection;

/**
 * Clase padre de las clases Dao que mantiene un objeto de conexión con la base de datos
 * @author talos
 */
public class ConceptoDao {
    // Objeto que representa una conexión con la base de datos
    protected Connection connection;
    
    /**
     * Función para crear una conexión con la base de datos
     */
    public void connect() {
        connection = ConnectionManager.connect();
    }
    
    /**
     * Función para aplicar un commit a las operaciones realizadas
     * cerrar la conexión con la base de datos y liberar los recursos
     */
    public void disconnect() {
        ConnectionManager.disconnect(connection);
    }
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
