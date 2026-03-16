package org.jesus.appmockito.ejemplos.app_mockito.repositories;

import java.util.List;

import org.jesus.appmockito.ejemplos.app_mockito.ejemplos.models.Examen;

public interface ExamenRepository {
    Examen guardar(Examen examen);
    List<Examen> findAll();
}
