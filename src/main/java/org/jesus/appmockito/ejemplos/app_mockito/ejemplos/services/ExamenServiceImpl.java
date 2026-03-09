package org.jesus.appmockito.ejemplos.app_mockito.ejemplos.services;

import java.util.Optional;

import org.jesus.appmockito.ejemplos.app_mockito.ejemplos.models.Examen;
import org.jesus.appmockito.ejemplos.app_mockito.repositories.ExamenRepository;

public class ExamenServiceImpl implements ExamenService{

    private ExamenRepository examenRepository;
    
    public ExamenServiceImpl(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    @Override
    public Optional<Examen> findExamenPorNombre(String nombre) {
        return examenRepository.findAll()
            .stream()
            .filter(e -> e.getNombre().contains(nombre))
            .findFirst();
        
    }

}
