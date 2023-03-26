package com.gasto.gasto.Service;

import com.gasto.gasto.Modelo.Gasto;

import java.util.List;

public interface GastoService {
    public Gasto agregarGasto(Gasto gasto);
    public List<Gasto> verGastos();
    public Gasto buscarGastoID(int id);
}
