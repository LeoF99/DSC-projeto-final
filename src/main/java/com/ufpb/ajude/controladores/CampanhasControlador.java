package com.ufpb.ajude.controladores;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpStatus;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.ufpb.ajude.entidades.Campanha;
import com.ufpb.ajude.servicos.campanhaServico;
import com.ufpb.ajude.dtos.CriaCampanhaDTO;
import com.ufpb.ajude.dtos.BuscaCampanhaDTO;

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
	
	@GetMapping("/search/campanhas")
	public ResponseEntity<List<Campanha>> buscaCampanhas(@RequestBody BuscaCampanhaDTO corpoBusca) {
		try {
			List<Campanha> campanhas = this.campanhaServico.buscaCampanhas(corpoBusca.getBusca(), corpoBusca.isRetornarTodas());
			
			return new ResponseEntity<List<Campanha>>(campanhas, HttpStatus.FOUND);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<List<Campanha>>(e.getStatusCode());
		}
	}
	
	@PatchMapping("/auth/campanhas/{id}/encerra")
	public ResponseEntity<Campanha> encerraCampanha(@PathVariable Long id, @RequestParam String email, @RequestHeader("Authorization") String header) throws ServletException {
		try {
			Campanha campanha = this.campanhaServico.encerraCampanha(id, email, header);
			
			return new ResponseEntity<Campanha>(campanha, HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<Campanha>(e.getStatusCode());
		}
	}
	
	@PatchMapping("/auth/campanhas/{id}/conclui")
	public ResponseEntity<Campanha> concluiCampanha(@PathVariable Long id, @RequestParam String email, @RequestHeader("Authorization") String header) throws ServletException {
		try {
			Campanha campanha = this.campanhaServico.concluiCampanha(id, email, header);
			
			return new ResponseEntity<Campanha>(campanha, HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<Campanha>(e.getStatusCode());
		}
	}
}
