package com.ufpb.ajude.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufpb.ajude.entidades.Campanha;

public interface CampanhaRepositorio extends JpaRepository<Campanha, Long> {
}
