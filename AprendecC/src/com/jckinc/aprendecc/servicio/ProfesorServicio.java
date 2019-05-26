/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jckinc.aprendecc.servicio;

import com.jckinc.aprendecc.dao.ProfesorDao;
import com.jckinc.aprendecc.dominio.Profesor;
import java.util.List;

/**
 * Clase para realizar operaciones CRUD con objetos de tipo Profesor
 * @author talos
 */
public class ProfesorServicio {
    // se declara una referencia para apuntar a un objeto de tipo ProfesorDao
    private ProfesorDao profesorDao;

    public ProfesorServicio() {
        // se crea un objeto de tipo ProfesorDao
        profesorDao = new ProfesorDao();
    }
    
    /*
    * Función que retorna un objeto de tipo Profesor a partir de su email y password
     */
    public Profesor consultar(String email, String password) {

        // obtiene una conexión a la base
        profesorDao.connect();
        
        // se invoca al método consultar del objeto dao
        Profesor profesor = profesorDao.consultar(email, password);

        // se desconecta de la base
        profesorDao.disconnect();
        
        return profesor;
    }

    /*
    * Función que retorna un objeto de tipo Profesor a partir de su id
     */
    public Profesor consultar(Long id) {

        // obtiene una conexión a la base
        profesorDao.connect();
        
        // se invoca al método consultar del objeto dao
        Profesor profesor = profesorDao.consultar(id);

        // se desconecta de la base
        profesorDao.disconnect();
        
        return profesor;
    }

    /*
    * Función que retorna una lista de objetos de tipo Profesor
     */
    public List<Profesor> consultar() {
        
        // asigna la conexión al objeto dao
        profesorDao.connect();

        // se invoca al método consultar del objeto dao
        List<Profesor> profesors = profesorDao.consultar();

        // se desconecta de la base
        profesorDao.disconnect();
        
        return profesors;
    }

    /*
    * Función que guarda en la base un objeto de tipo Profesor
     */
    public boolean crear(Profesor profesor) {
       
        // asigna la conexión al objeto dao
        profesorDao.connect();

        // se invoca al método crear del objeto dao
        boolean resultado = profesorDao.crear(profesor);

        // se desconecta de la base
        profesorDao.disconnect();
        return resultado;

    }

    /*
    * Función que actualiza en la base un objeto de tipo Profesor
     */
    public boolean actualizar(Profesor profesor) {
        // asigna la conexión al objeto dao
        profesorDao.connect();

        // se invoca al método actualizar del objeto dao
        boolean resultado = profesorDao.actualizar(profesor);

        // se desconecta de la base
        profesorDao.disconnect();
        return resultado;
    }

    /*
    * Función que elimina en la base un objeto de tipo Profesor
     */
    public boolean eliminar(Long id) {
        
        // asigna la conexión al objeto dao
        profesorDao.connect();

        // se invoca al método eliminar del objeto dao
        boolean resultado = profesorDao.eliminar(id);

        // se desconecta de la base
        profesorDao.disconnect();
        return resultado;

    }
}
