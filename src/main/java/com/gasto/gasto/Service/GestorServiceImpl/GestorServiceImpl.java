package com.gasto.gasto.Service.GestorServiceImpl;

import com.gasto.gasto.Modelo.Gestor;
import com.gasto.gasto.Repository.GestorRepository;
import com.gasto.gasto.Service.GestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GestorServiceImpl implements GestorService {

    @Autowired
    private GestorRepository gestorRepository;

    @Override
    public List<Gestor> getAll() {
        return (List<Gestor>) gestorRepository.findAll();
    }

    @Override
    public Optional<Gestor> findById(Long id) {
        return gestorRepository.findById(id);
    }

    @Override
    public Gestor save(Gestor gestor) {
        return gestorRepository.save(gestor);
    }

    @Override
    public void deleteById(Long id) {
        gestorRepository.deleteById(id);
    }

    @Override
    public Gestor updategestor(Gestor gestorActualizado) {
        return gestorRepository.save(gestorActualizado);
    }
}
