package com.gasto.gasto.Service;

import com.gasto.gasto.Modelo.Gestor;

import java.util.List;
import java.util.Optional;

public interface GestorService {
    List<Gestor> getAll();
    Optional<Gestor> findById(Long id);
    Gestor save(Gestor gestor);
    void deleteById(Long id);
    Gestor updategestor(Gestor gestorActualizado);


}
