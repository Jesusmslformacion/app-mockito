package org.jesus.appmockito.ejemplos.app_mockito.repositories;

import java.util.List;

public interface PreguntaRepository {

    List<String> findPreguntasPorExamenId(Long id);

}
