package org.jesus.appmockito.ejemplos.app_mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.jesus.appmockito.ejemplos.app_mockito.ejemplos.Datos;
import org.jesus.appmockito.ejemplos.app_mockito.ejemplos.models.Examen;
import org.jesus.appmockito.ejemplos.app_mockito.ejemplos.services.ExamenService;
import org.jesus.appmockito.ejemplos.app_mockito.ejemplos.services.ExamenServiceImpl;
import org.jesus.appmockito.ejemplos.app_mockito.repositories.ExamenRepository;
import org.jesus.appmockito.ejemplos.app_mockito.repositories.ExamenRepositoryImpl;
import org.jesus.appmockito.ejemplos.app_mockito.repositories.PreguntaRepository;
import org.jesus.appmockito.ejemplos.app_mockito.repositories.PreguntaRepositoryImpl;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplTest {

    @Spy
    ExamenRepositoryImpl repository;

    @Spy
    PreguntaRepositoryImpl preguntaRepository;

    @InjectMocks
    ExamenServiceImpl service;


    @Test
    void testSpy() {

        List<String> preguntas = Arrays.asList("Aritmetica");
        //when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(preguntas);
        doReturn(preguntas).when(preguntaRepository).findPreguntasPorExamenId(anyLong());
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(5, examen.getId());
        assertEquals("Matematicas", examen.getNombre());
        assertEquals(1, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Aritmetica"));

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());

    }
}
