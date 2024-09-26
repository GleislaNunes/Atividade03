package org.example.dao;

import org.example.model.Area;
import org.example.model.Cursos;

import java.util.List;
import java.util.Optional;

public interface ICursoDAO {
    Cursos create(Cursos cursos);
    void update(Cursos cursos);
    void delete(Cursos cursos);
    List<Cursos> findAll();
    Optional<Cursos> findById(Long id);
    List<Cursos> findByArea(Area area);
    Optional<Cursos> findBySigla(String sigla);
}
