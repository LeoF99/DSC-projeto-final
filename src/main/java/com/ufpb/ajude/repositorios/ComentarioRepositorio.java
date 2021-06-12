package com.ufpb.ajude.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufpb.ajude.entidades.Comentario;

public interface ComentarioRepositorio extends JpaRepository<Comentario, Long> {

}
