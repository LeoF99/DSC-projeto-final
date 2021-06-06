package com.ufpb.ajude.controladores;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.ufpb.ajude.entidades.Campanha;
import com.ufpb.ajude.servicos.campanhaServico;
import com.ufpb.ajude.dtos.CriaCampanhaDTO;

@RestController
public class CampanhasControlador {
	@Autowired
	campanhaServico campanhaServico;
	
	public CampanhasControlador(campanhaServico campanhaServico) {
		this.campanhaServico = campanhaServico;
	}
	
	@PostMapping("/auth/campanhas")
	public ResponseEntity<Campanha> criaCampanha(@RequestBody CriaCampanhaDTO campanha) {
		try {
			Campanha campanhaCriada = this.campanhaServico.criaCampanha(campanha);
			
			return new ResponseEntity<Campanha>(campanhaCriada, HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<Campanha>(e.getStatusCode());
		}
	}
	
	@PutMapping("/auth/campanhas/{id}/encerra")
	public ResponseEntity<Campanha> encerraCampanha(@PathVariable Long id) {
		try {
			Campanha campanha = this.campanhaServico.encerraCampanha(id);
			
			return new ResponseEntity<Campanha>(campanha, HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<Campanha>(e.getStatusCode());
		}
	}
	
	@PutMapping("/auth/campanhas/{id}/conclui")
	public ResponseEntity<Campanha> concluiCampanha(@PathVariable Long id) {
		try {
			Campanha campanha = this.campanhaServico.encerraCampanha(id);
			
			return new ResponseEntity<Campanha>(campanha, HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<Campanha>(e.getStatusCode());
		}
	}
}
