package org.jesus.appmockito.ejemplos.app_mockito.repositories;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jesus.appmockito.ejemplos.app_mockito.ejemplos.models.Examen;

public class ExamenRepositoryOtro implements ExamenRepository{

    @Override
    public List<Examen> findAll() {
        try {
            System.out.println("ExamenRepositoryOtro");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
