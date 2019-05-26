/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jckinc.aprendecc.servicio;

import com.jckinc.aprendecc.dao.EjercicioDao;
import com.jckinc.aprendecc.dominio.Ejercicio;
import java.util.List;

/**
 *Clase para realizar operaciones CRUD con objetos de tupo Ejercicio
 * @author d3xt3
 */
public class EjercicioServicio {
    // se declara una referencia para apuntar a un objeto de tipo Ejercicio
    private EjercicioDao ejercicioDao;
    
    public EjercicioServicio() {
        // se crea un objeto de tipo Ejercicio
        ejercicioDao = new EjercicioDao();
    }

    /*
    * Funcion que retorna un objeto de tipo Ejercicio a partir de su id
    */
    public Ejercicio consultar(long id) {
        // se obtiene una conexión a la base
        ejercicioDao.connect();
        
        // se invoca al método consultar del objeto dao
        Ejercicio ejercicio = ejercicioDao.consultar(id);
        
        // se desconecta de la base
        ejercicioDao.disconnect();
        
        return ejercicio;
    }
    
    /*
    * Función que retorna una lista de objetos de tipo Ejercicio
    */
    public List<Ejercicio> consultar() {
        // asina la conexión al objeto dao
        ejercicioDao.connect();
        
        // se invoca el método consultar del objeto dao
        List<Ejercicio> ejercicios = ejercicioDao.consultar();
        
        // se desconecta de l base
        ejercicioDao.disconnect();
        
        return ejercicios;
    }
    
    /*
    * Función que guarda en la base un objeto de tipo Ejercicio
    */
    public boolean crear(Ejercicio ejercicio) {
        // asigna la conexión al objeto dao
        ejercicioDao.connect();
        
        // se invoca al método crear del objeto dao
        boolean resultado = ejercicioDao.crear(ejercicio);
        
        // se deconecta de la base
        ejercicioDao.disconnect();
        
        return resultado;
    }
    
    /*
    * Función que actualiza en la base un objeto de tipo Ejercicio
     */
    public boolean actualizar(Ejercicio ejercicio) {
        // asigna la conexión al objeto dao
        ejercicioDao.connect();

        // se invoca al método actualizar del objeto dao
        boolean resultado = ejercicioDao.actualizar(ejercicio);

        // se desconecta de la base
        ejercicioDao.disconnect();
        return resultado;
    }

    /*
    * Función que elimina en la base un objeto de tipo Ejercicio
     */
    public boolean eliminar(Long id) {
        
        // asigna la conexión al objeto dao
        ejercicioDao.connect();

        // se invoca al método eliminar del objeto dao
        boolean resultado = ejercicioDao.eliminar(id);

        // se desconecta de la base
        ejercicioDao.disconnect();
        return resultado;

    }
}