package com.ufpb.ajude.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ufpb.ajude.entidades.Campanha;

public interface CampanhaRepositorio extends JpaRepository<Campanha, Long> {
	@Query("SELECT c.nome FROM Campanha c where c.nome LIKE :nome")
	List<Campanha> buscaPorNome(@Param("nome") String nome);
	
	@Query("SELECT c.nome FROM Campanha c where c.nome LIKE :nome AND c.status='Ativa'")
	List<Campanha> buscaAtivasPorNome(@Param("nome") String nome);
}
