package org.jesus.appmockito.ejemplos.app_mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    @Mock
    ExamenRepositoryImpl repository;

    @Mock
    PreguntaRepositoryImpl preguntaRepository;

    @InjectMocks
    ExamenServiceImpl service;

    @Captor
    ArgumentCaptor<Long> captor;


    @BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this);
       //repository = mock(ExamenRepositoryImpl.class);
        //preguntaRepository = mock(PreguntaRepositoryImpl.class);
        //service = new ExamenServiceImpl(repository, preguntaRepository);
        
    }

    @Test
    void findExamenPorNombre() {
        

        when(repository.findAll()).thenReturn(Datos.EXAMENES);
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

    @Test
    void testPreguntasExamen() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(5L)).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Aritmetica"));
        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());

        
    }

    @Test
    void testNoExisteExamenVerify() {
        // Dado
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(5L)).thenReturn(Datos.PREGUNTAS);

        // Cuando 
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");

        // Entonces
        assertNull(examen);
        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(5L);

        
    }

    @Test
    void testGuardarExamen() {
        // Dado
        Examen newExamen = Datos.EXAMEN;
        newExamen.setPreguntas(Datos.PREGUNTAS);

        when(repository.guardar(any(Examen.class))).then(new Answer<Examen>() {

            Long secuencia = 8L;

            @Override
            public Examen answer(InvocationOnMock invocation) throws Throwable {
                Examen examen = invocation.getArgument(0);
                examen.setId(secuencia++);
                return examen;
            }
            
        });

        // Cuando
        Examen examen = service.guardar(newExamen);

        // Entonces
        assertNotNull(examen.getId());
        assertEquals(8L, examen.getId());
        assertEquals("Fisica", examen.getNombre());

        verify(repository).guardar(any(Examen.class));
        verify(preguntaRepository).guardarVarias(anyList());

    }

    @Test
    void testManejoException() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NULL);
        when(preguntaRepository.findPreguntasPorExamenId(isNull())).thenThrow(IllegalArgumentException.class);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.findExamenPorNombreConPreguntas("Matematicas");
        });
        assertEquals(IllegalArgumentException.class, exception.getClass());

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(isNull());
    }

    @Test
    void testArgumentMatchers() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamenPorNombreConPreguntas("Matematicas");

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(argThat(arg -> arg !=null && arg.equals(5L)));
    }

    @Test
    void testArgumentMatchers2() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVOS);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamenPorNombreConPreguntas("Matematicas");

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(argThat(new MiArgsMatchers()));
    }

    @Test
    void testArgumentMatchers3() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVOS);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamenPorNombreConPreguntas("Matematicas");

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(argThat( (argument) -> argument != null && argument > 0 ));
    }

    

    

    public static class MiArgsMatchers implements ArgumentMatcher<Long> {

        private Long argument;

        @Override
        public boolean matches(Long argument) {
            this.argument = argument;
            return argument != null && argument > 0;
        }

        @Override
        public String toString() {
            return "Es para un mensaje personalizado de error" + 
                    " que imprime mockito en caso de que falle el test " +
                     argument + " debe ser un entero positivo";
        }

        

    }

    @Test
    void testArgumentCaptor() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        //when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamenPorNombreConPreguntas("Matematicas");

        //ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(preguntaRepository).findPreguntasPorExamenId(captor.capture());

        assertEquals(5L, captor.getValue());
    }
    
    @Test 
    void testDoThrow() {
        Examen examen = Datos.EXAMEN;
        examen.setPreguntas(Datos.PREGUNTAS);
        doThrow(IllegalArgumentException.class).when(preguntaRepository).guardarVarias(anyList());
        assertThrows(IllegalArgumentException.class, () -> {
            service.guardar(examen);
        });
    }

    @Test 
    void testDoAnswer() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
       // when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
       doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            return id == 5L? Datos.PREGUNTAS: Collections.emptyList();
       }).when(preguntaRepository).findPreguntasPorExamenId(anyLong());

       Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
       assertEquals(5, examen.getPreguntas().size());
       assertTrue(examen.getPreguntas().contains("Geometria"));
       assertEquals(5L, examen.getId());
       assertEquals("Matematicas", examen.getNombre());

       verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
    }

    @Test
    void testDoAnswerGuardarExamen() {
        // Dado
        Examen newExamen = Datos.EXAMEN;
        newExamen.setPreguntas(Datos.PREGUNTAS);

        doAnswer(new Answer<Examen>() {

            Long secuencia = 8L;

            @Override
            public Examen answer(InvocationOnMock invocation) throws Throwable {
                Examen examen = invocation.getArgument(0);
                examen.setId(secuencia++);
                return examen;
            }
            
        }).when(repository).guardar(any(Examen.class));

        // Cuando
        Examen examen = service.guardar(newExamen);

        // Entonces
        assertNotNull(examen.getId());
        assertEquals(8L, examen.getId());
        assertEquals("Fisica", examen.getNombre());

        verify(repository).guardar(any(Examen.class));
        verify(preguntaRepository).guardarVarias(anyList());

    }

    @Test
    void testDoCallRealMethod() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        //when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        doCallRealMethod().when(preguntaRepository).findPreguntasPorExamenId(anyLong());
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(5L, examen.getId());
        assertEquals("Matematicas", examen.getNombre());
    }

    @Test
    void testSpy() {
        ExamenRepository examenRepository = spy(ExamenRepositoryImpl.class);
        PreguntaRepository preguntaRepository = spy(PreguntaRepositoryImpl.class);
        ExamenService examenService = new ExamenServiceImpl(examenRepository, preguntaRepository);

        List<String> preguntas = Arrays.asList("Aritmetica");
        //when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(preguntas);
        doReturn(preguntas).when(preguntaRepository).findPreguntasPorExamenId(anyLong());
        Examen examen = examenService.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(5, examen.getId());
        assertEquals("Matematicas", examen.getNombre());
        assertEquals(1, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Aritmetica"));

        verify(examenRepository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());

    }

    @Test
    void testOrdenDeInvocaciones() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);

        service.findExamenPorNombreConPreguntas("Matematicas");
        service.findExamenPorNombreConPreguntas("Lenguaje");

        InOrder inOrder = inOrder(preguntaRepository);
        inOrder.verify(preguntaRepository).findPreguntasPorExamenId(5L);
        inOrder.verify(preguntaRepository).findPreguntasPorExamenId(6L);

    }

    @Test
    void testOrdenDeInvocaciones2() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);

        service.findExamenPorNombreConPreguntas("Matematicas");
        service.findExamenPorNombreConPreguntas("Lenguaje");

        InOrder inOrder = inOrder(repository, preguntaRepository);
        inOrder.verify(repository).findAll();
        inOrder.verify(preguntaRepository).findPreguntasPorExamenId(5L);
        inOrder.verify(repository).findAll();
        inOrder.verify(preguntaRepository).findPreguntasPorExamenId(6L);

    }

    @Test
    void testNumeroInvocaciones() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        service.findExamenPorNombreConPreguntas("Matematicas");

        verify(preguntaRepository).findPreguntasPorExamenId(5L);
        verify(preguntaRepository, times(1)).findPreguntasPorExamenId(5L);
        verify(preguntaRepository, atLeast(1)).findPreguntasPorExamenId(5L);
        verify(preguntaRepository, atLeastOnce()).findPreguntasPorExamenId(5L);
        verify(preguntaRepository, atMost(10)).findPreguntasPorExamenId(5L);
        verify(preguntaRepository, atMostOnce()).findPreguntasPorExamenId(5L);
    }

    @Test
    void testNumeroInvocaciones2() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        service.findExamenPorNombreConPreguntas("Matematicas");

        //verify(preguntaRepository).findPreguntasPorExamenId(5L); FALLA
        verify(preguntaRepository, times(2)).findPreguntasPorExamenId(5L);
        verify(preguntaRepository, atLeast(2)).findPreguntasPorExamenId(5L);
        verify(preguntaRepository, atLeastOnce()).findPreguntasPorExamenId(5L);
        verify(preguntaRepository, atMost(2)).findPreguntasPorExamenId(5L);
        //verify(preguntaRepository, atMostOnce()).findPreguntasPorExamenId(5L); FALLA
    }

    @Test
    void testNumeroInvocaciones3() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        service.findExamenPorNombreConPreguntas("Matematicas");

        verify(preguntaRepository, never()).findPreguntasPorExamenId(5L);
        verifyNoInteractions(preguntaRepository);

        verify(repository).findAll();
        verify(repository, times(1)).findAll();
        verify(repository, atLeast(1)).findAll();
        verify(repository, atLeastOnce()).findAll();
        verify(repository, atMost(10)).findAll();
        verify(repository, atMostOnce()).findAll();
    }
}
