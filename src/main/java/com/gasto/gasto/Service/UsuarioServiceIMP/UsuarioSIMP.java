package com.gasto.gasto.Service.UsuarioServiceIMP;

import com.gasto.gasto.Modelo.Usuario;
import com.gasto.gasto.Repository.UsuarioRepository;
import com.gasto.gasto.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioSIMP implements UsuarioService {

    @Autowired
    private UsuarioRepository repo;
    @Override
    public Usuario agregarUsuario(Usuario usuario) {
        usuario.setCorreo(usuario.getCorreo());
        return this.repo.save(usuario);
    }
}
