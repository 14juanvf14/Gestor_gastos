package com.gasto.gasto.Service.GastoServiceImpl;

import com.gasto.gasto.Modelo.Gasto;
import com.gasto.gasto.Repository.GastoRepository;
import com.gasto.gasto.Service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class GastoServiceImpl implements GastoService {
    @Autowired
    private GastoRepository gastoRepository;

    @Override
    public List<Gasto> findAll() {
        return (List<Gasto>) gastoRepository.findAll();
    }

    @Override
    public Gasto findById(Long id) {
        return gastoRepository.findById(id).orElse(null);
    }

    @Override
    public Gasto save(Gasto gasto) {
        return gastoRepository.save(gasto);
    }

    @Override
    public void deleteById(Long id) {
        gastoRepository.deleteById(id);
    }

    @Override
    public Gasto updateGasto(Gasto gasto) {
        return gastoRepository.save(gasto);
    }

}
