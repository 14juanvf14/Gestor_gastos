package com.gasto.gasto.Service;

import com.gasto.gasto.Modelo.Usuario;

import java.util.List;

public interface UsuarioService {
    public Usuario agregarUsuario(Usuario usuario);
    public List<Usuario> verUsuarios();
    public Usuario buscarUsuarioID(int id);
    public Usuario modificarUsuarioID(Usuario usuario);
    public void eliminarUsuario(int id);

}
