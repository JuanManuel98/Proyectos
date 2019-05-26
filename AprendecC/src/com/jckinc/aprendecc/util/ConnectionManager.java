/*
 * Clase con las funciones para conectarse a la base de datos
 */
package com.jckinc.aprendecc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author talos
 */
public class ConnectionManager {
    /*
    * Constantes con los parámetros de la base de datos
    */
    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DATABASE_URL = "jdbc:derby://localhost:1527/AprendecC_bd";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    /*
    * Función que retorna una conexión a la base de datos
    */
    public static Connection connect() {
        Connection con = null;
        try {
            // se carga el controlador jdbc para java db
            Class.forName(JDBC_DRIVER);
            // se obtiene un objeto de conexión para la base de datos con el usuario y contraseña
            con = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            // se configura la conexión para que no aplique un commit después de cada operación sql en la base de datos
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
}

/*
    * Función que recibe una conexión a la base de datos.
    * Cierra la conexión y libera los recursos
    */
    public static void disconnect(Connection con){
        
        try {
            // aplica los cambios pendientes a la base de datos
            // cierra la conexión.
            con.commit();
            con.close();
        } catch (SQLException ex) {
            try {
                // en caso de falla, deshace todas las operaciones realizadas
                con.rollback();
                //cierra la conexión
                con.close();
            } catch (SQLException ex1) {
                Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 

}