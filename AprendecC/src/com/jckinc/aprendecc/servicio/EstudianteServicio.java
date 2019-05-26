/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jckinc.aprendecc.servicio;

import com.jckinc.aprendecc.dao.EstudianteDao;
import com.jckinc.aprendecc.dominio.Estudiante;
import java.util.List;

/**
 *Clase para realizar operaciones CRUD con objetos de tipo Estudiante
 * @author d3xt3
 */
public class EstudianteServicio {
    // se declara una referencia para apuntar a un objeto de tipo EstudianteDao
    private EstudianteDao estudianteDao;

    public EstudianteServicio() {
        // se crea un objeto de tipo EstudianteDao
        estudianteDao = new EstudianteDao();
    }
    
    /*
    * Función que retorna un objeto de tipo Estudiante a partir de su email y password
     */
    public Estudiante consultar(String email, String password) {

        // obtiene una conexión a la base
        estudianteDao.connect();
        
        // se invoca al método consultar del objeto dao
        Estudiante estudiante = estudianteDao.consultar(email, password);

        // se desconecta de la base
        estudianteDao.disconnect();
        
        return estudiante;
    }

    /*
    * Función que retorna un objeto de tipo Estudiante a partir de su id
     */
    public Estudiante consultar(Long id) {

        // obtiene una conexión a la base
        estudianteDao.connect();
        
        // se invoca al método consultar del objeto dao
        Estudiante estudiante = estudianteDao.consultar(id);

        // se desconecta de la base
        estudianteDao.disconnect();
        
        return estudiante;
    }

    /*
    * Función que retorna una lista de objetos de tipo Estudiante
     */
    public List<Estudiante> consultar() {
        
        // asigna la conexión al objeto dao
        estudianteDao.connect();

        // se invoca al método consultar del objeto dao
        List<Estudiante> estudiantes = estudianteDao.consultar();

        // se desconecta de la base
        estudianteDao.disconnect();
        
        return estudiantes;
    }

    /*
    * Función que guarda en la base un objeto de tipo Estudiante
     */
    public boolean crear(Estudiante estudiante) {
       
        // asigna la conexión al objeto dao
        estudianteDao.connect();

        // se invoca al método crear del objeto dao
        boolean resultado = estudianteDao.crear(estudiante);

        // se desconecta de la base
        estudianteDao.disconnect();
        return resultado;

    }

    /*
    * Función que actualiza en la base un objeto de tipo Estudiante
     */
    public boolean actualizar(Estudiante estudiante) {
        // asigna la conexión al objeto dao
        estudianteDao.connect();

        // se invoca al método actualizar del objeto dao
        boolean resultado = estudianteDao.actualizar(estudiante);

        // se desconecta de la base
        estudianteDao.disconnect();
        return resultado;
    }

    /*
    * Función que elimina en la base un objeto de tipo Estudiante
     */
    public boolean eliminar(Long id) {
        
        // asigna la conexión al objeto dao
        estudianteDao.connect();

        // se invoca al método eliminar del objeto dao
        boolean resultado = estudianteDao.eliminar(id);

        // se desconecta de la base
        estudianteDao.disconnect();
        return resultado;

    }
}
