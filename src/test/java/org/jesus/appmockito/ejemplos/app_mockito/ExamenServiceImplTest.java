package org.jesus.appmockito.ejemplos.app_mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.jesus.appmockito.ejemplos.app_mockito.ejemplos.models.Examen;
import org.jesus.appmockito.ejemplos.app_mockito.ejemplos.services.ExamenService;
import org.jesus.appmockito.ejemplos.app_mockito.ejemplos.services.ExamenServiceImpl;
import org.jesus.appmockito.ejemplos.app_mockito.repositories.ExamenRepository;
import org.jesus.appmockito.ejemplos.app_mockito.repositories.ExamenRepositoryOtro;
import org.jesus.appmockito.ejemplos.app_mockito.repositories.PreguntaRepository;


class ExamenServiceImplTest {

    ExamenRepository repository;
    ExamenService service;
    PreguntaRepository preguntaRepository;

    @BeforeEach
    void setUp() {
        repository = mock(ExamenRepositoryOtro.class);
        preguntaRepository = mock(PreguntaRepository.class);
        service = new ExamenServiceImpl(repository, preguntaRepository);
        
    }

    @Test
    void findExamenPorNombre() {
        List<Examen> datos = Arrays.asList(new Examen(5L, "Matematicas"), new Examen(6L, "Lenguaje"),
                new Examen(7L, "Historia"));

        when(repository.findAll()).thenReturn(datos);
        Optional<Examen> examen = service.findExamenPorNombre("Matematicas");
        
        assertTrue(examen.isPresent());
        assertEquals(5L, examen.orElseThrow().getId());
        assertEquals("Matematicas", examen.get().getNombre());
    }

    @Test
    void findExamenPorNombreListaVacia() {

        List<Examen> datos = Collections.emptyList();

        when(repository.findAll()).thenReturn(datos);
        Optional<Examen> examen = service.findExamenPorNombre("Matematicas");
        
        assertFalse(examen.isPresent());
        
    }

    
}
