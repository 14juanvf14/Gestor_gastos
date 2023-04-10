package com.gasto.gasto.Service.UsuarioServiceImpl;

import com.gasto.gasto.Excepciones.ResourceNotFoundException;
import com.gasto.gasto.Modelo.Usuario;
import com.gasto.gasto.Repository.UsuarioRepository;
import com.gasto.gasto.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public Usuario saveUser(Usuario usuario) throws ResourceNotFoundException {
        Optional<Usuario> usuarioSaved = usuarioRepository.findByEmail(usuario.getEmail());
        if(usuarioSaved.isPresent()){
            throw new ResourceNotFoundException("El usuario con email "+usuario.getEmail()+" ya existe en el sistema");
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> getAllUsers() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> getUserById(long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario updateUser(Usuario usuarioActualizado) {
        return usuarioRepository.save(usuarioActualizado);
    }

    @Override
    public void eliminarUsuario(long id) {
        usuarioRepository.deleteById(id);
    }


}
