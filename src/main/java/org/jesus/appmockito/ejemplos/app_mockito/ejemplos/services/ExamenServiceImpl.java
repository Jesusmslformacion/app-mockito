package org.jesus.appmockito.ejemplos.app_mockito.ejemplos.services;

import java.util.List;
import java.util.Optional;

import org.jesus.appmockito.ejemplos.app_mockito.ejemplos.models.Examen;
import org.jesus.appmockito.ejemplos.app_mockito.repositories.ExamenRepository;
import org.jesus.appmockito.ejemplos.app_mockito.repositories.PreguntaRepository;

public class ExamenServiceImpl implements ExamenService{

    private ExamenRepository examenRepository;
    private PreguntaRepository preguntaRepository;
    
    public ExamenServiceImpl(ExamenRepository examenRepository, PreguntaRepository preguntaRepository) {
        this.examenRepository = examenRepository;
        this.preguntaRepository = preguntaRepository;
    }

    @Override
    public Optional<Examen> findExamenPorNombre(String nombre) {
        return examenRepository.findAll()
            .stream()
            .filter(e -> e.getNombre().contains(nombre))
            .findFirst();
        
    }

    @Override
    public Examen findExamenPorNombreConPreguntas(String nombre) {
        Examen examen = null;
        Optional<Examen> exameOptional = findExamenPorNombre(nombre);
        if (exameOptional.isPresent()) {
            examen = exameOptional.orElseThrow();
            List<String> preguntas = preguntaRepository.findPreguntasPorExamenId(examen.getId());
             preguntaRepository.findPreguntasPorExamenId(examen.getId());
            examen.setPreguntas(preguntas);
            
        }
        return examen;
    }

    @Override
    public Examen guardar(Examen examen) {
        if (!examen.getPreguntas().isEmpty()) {
            preguntaRepository.guardarVarias(examen.getPreguntas());
        }
        return examenRepository.guardar(examen);
    }

}
