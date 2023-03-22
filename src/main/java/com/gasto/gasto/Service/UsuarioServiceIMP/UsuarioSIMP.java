package com.gasto.gasto.Service.UsuarioServiceIMP;

import com.gasto.gasto.Modelo.Usuario;
import com.gasto.gasto.Repository.UsuarioRepository;
import com.gasto.gasto.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioSIMP implements UsuarioService {

    @Autowired
    private UsuarioRepository repo;
    @Override
    public Usuario agregarUsuario(Usuario usuario) {
        usuario.setCorreo(usuario.getCorreo());
        return this.repo.save(usuario);
    }

    @Override
    public List<Usuario> verUsuarios() {
        return (List<Usuario>) this.repo.findAll();
    }

    @Override
    public Usuario buscarUsuarioID(int id) {
        if (this.repo.findById(id).isPresent()) {
            return this.repo.findById(id).get();
        } else {
            return null; // o return new Usuario(); si quieres devolver un objeto Usuario vacío
        }
    }

    @Override
    public Usuario modificarUsuarioID(Usuario usuario) {
        return this.repo.save(usuario);
    }

    @Override
    public void eliminarUsuario(int id) {

    }


}
