package org.jesus.appmockito.ejemplos.app_mockito;

import java.util.Arrays;
import java.util.List;

import org.jesus.appmockito.ejemplos.app_mockito.ejemplos.models.Examen;

public class Datos {

    public final static List<Examen> EXAMENES = Arrays.asList(new Examen(5L, "Matematicas"), new Examen(6L, "Lenguaje"),
                new Examen(7L, "Historia"));

    public final static List<String> PREGUNTAS = Arrays.asList("Aritmetica", "Integrales",
        "Derivadas", "Trigonometria", "Geometria");
}
