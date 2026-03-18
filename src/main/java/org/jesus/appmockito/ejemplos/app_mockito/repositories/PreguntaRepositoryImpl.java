package org.jesus.appmockito.ejemplos.app_mockito.repositories;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jesus.appmockito.ejemplos.app_mockito.ejemplos.Datos;

public class PreguntaRepositoryImpl implements PreguntaRepository{

    @Override
    public List<String> findPreguntasPorExamenId(Long id) {
        System.out.println("PreguntaRepositoryImpl.findPreguntasPorExamenId()");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Datos.PREGUNTAS;
    }

    @Override
    public void guardarVarias(List<String> preguntas) {
        System.out.println("PreguntaRepositoryImpl.guardarVarias()");
    }

}
