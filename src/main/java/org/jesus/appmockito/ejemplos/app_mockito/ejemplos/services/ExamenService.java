package org.jesus.appmockito.ejemplos.app_mockito.ejemplos.services;

import java.util.Optional;

import org.jesus.appmockito.ejemplos.app_mockito.ejemplos.models.Examen;

public interface ExamenService {
    Optional<Examen> findExamenPorNombre (String nombre);

}
