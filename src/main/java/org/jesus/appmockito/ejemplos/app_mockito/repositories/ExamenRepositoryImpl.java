package org.jesus.appmockito.ejemplos.app_mockito.repositories;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jesus.appmockito.ejemplos.app_mockito.ejemplos.Datos;
import org.jesus.appmockito.ejemplos.app_mockito.ejemplos.models.Examen;

public class ExamenRepositoryImpl implements ExamenRepository{

    @Override
    public Examen guardar(Examen examen) {
        System.out.println("ExamenRepositoryImpl.guardar()");
        return Datos.EXAMEN;
    }
    @Override
    public List<Examen> findAll() {
        System.out.println("ExamenRepositoryImpl.findAll()");
        try {
            System.out.println("ExamenRepositoryOtro");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Datos.EXAMENES;
    }


}
