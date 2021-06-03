package com.ufpb.ajude.servicos;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.ufpb.ajude.repositorios.CampanhaRepositorio;
import com.ufpb.ajude.entidades.Campanha;

@Service
public class campanhaServico {
	@Autowired
	private CampanhaRepositorio campanhaRepositorio;
	
	public campanhaServico(CampanhaRepositorio campanhaRepositorio) {
		this.campanhaRepositorio = campanhaRepositorio;
	}
	
	public Campanha criaCampanha(Campanha campanha) {
		if(campanha.getDeadline().after(new Date())) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		
		if(!campanha.getStatus().equals("Ativa")) {
			campanha.setStatus("Ativa");
		}
		
		Campanha campanhaCriada = this.campanhaRepositorio.save(campanha);
		
		return campanhaCriada;
	}
	
	public Campanha encerraCampanha(Long id) {
		Optional<Campanha> buscaCampanha = this.campanhaRepositorio.findById(id);
		
		if(!buscaCampanha.isPresent()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		
		Campanha campanha = buscaCampanha.get();
		
		if(!campanha.getStatus().equals("Encerrada") || campanha.getStatus().equals("Concluída")) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		
		campanha.setStatus("Encerrada");
		
		return campanha;
	}
	
	public Campanha concluiCampanha(Long id) {
		Optional<Campanha> buscaCampanha = this.campanhaRepositorio.findById(id);
		
		if(!buscaCampanha.isPresent()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		
		Campanha campanha = buscaCampanha.get();
		
		if(!campanha.getStatus().equals("Encerrada") || campanha.getStatus().equals("Concluída")) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		}
		
		campanha.setStatus("Concluída");
		
		return campanha;
	}
	
	private void atualizaStatusVencido(Campanha campanha) {
		if(campanha.getStatus().equals("Ativa")) {
			if(campanha.getDeadline().after(new Date())) {
				campanha.setStatus("Vencida");
			}
		}
	}
}
