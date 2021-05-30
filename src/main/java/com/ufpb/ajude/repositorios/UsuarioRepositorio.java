package com.ufpb.ajude.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufpb.ajude.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{

}
