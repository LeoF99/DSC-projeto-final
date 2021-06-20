package com.ufpb.ajude.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufpb.ajude.entidades.Doacao;

public interface DoacaoRepositorio extends JpaRepository<Doacao, Long>{

}
