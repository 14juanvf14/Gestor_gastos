package com.gasto.gasto.Service;

import com.gasto.gasto.Modelo.Gasto;

import java.util.List;

public interface GastoService {
    List<Gasto> findAll();
    Gasto findById(Long id);
    Gasto save(Gasto gasto);
    void deleteById(Long id);
    Gasto updateGasto(Gasto gasto);
}
