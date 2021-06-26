package com.ufpb.ajude.controladores;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.ufpb.ajude.entidades.Campanha;
import com.ufpb.ajude.entidades.Comentario;
import com.ufpb.ajude.servicos.CampanhaServico;
import com.ufpb.ajude.dtos.CriaCampanhaDTO;
import com.ufpb.ajude.dtos.CriaComentarioDTO;
import com.ufpb.ajude.dtos.RecuperaCampanhaRespostaDTO;
import com.ufpb.ajude.dtos.BuscaCampanhaDTO;
import com.ufpb.ajude.dtos.AtualizaCampanhaDTO;

@RestController
public class CampanhasControlador {
	@Autowired
	CampanhaServico campanhaServico;
	
	public CampanhasControlador(CampanhaServico campanhaServico) {
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
	
	@PutMapping("/auth/campanhas/{id}")
	public ResponseEntity<Campanha> atualizaCampanha(@PathVariable Long id, @RequestBody AtualizaCampanhaDTO campanha, @RequestParam String email, @RequestHeader("Authorization") String header) throws ServletException, ParseException {
		try {
			Campanha campanhaAtualizada = this.campanhaServico.atualizaCampanha(
				id,
				campanha,
				email,
				header
			);
			
			return new ResponseEntity<Campanha>(campanhaAtualizada, HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<Campanha>(e.getStatusCode());
		}
	}
	
	@PostMapping("/auth/campanhas/comentarios")
	public ResponseEntity<Comentario> adicionaComentario(@RequestBody CriaComentarioDTO dados) {
		try {
			Comentario comentario = this.campanhaServico.adicionaComentario(dados);
			
			return new ResponseEntity<Comentario>(comentario, HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<Comentario>(e.getStatusCode());
		}
	}
	
	@PostMapping("/auth/campanhas/comentarios/{id}/respostas")
	public ResponseEntity<Comentario> respondeComentario(@RequestBody CriaComentarioDTO dados, @PathVariable Long id) {
		try {
			Comentario resposta = this.campanhaServico.respondeComentario(dados, id);
			
			return new ResponseEntity<Comentario>(resposta, HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<Comentario>(e.getStatusCode());
		}
	}
	
	@DeleteMapping("/auth/campanhas/comentarios/{id}")
	public ResponseEntity<Comentario> removeComentario(@PathVariable Long id, @RequestParam String email, @RequestHeader("Authorization") String header) throws ServletException {
		try {
			Comentario comentario = this.campanhaServico.removeComentario(id, header, email);
			
			return new ResponseEntity<Comentario>(comentario, HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<Comentario>(e.getStatusCode());
		}
	}
	
	@GetMapping("/campanhas")
	public ResponseEntity<List<RecuperaCampanhaRespostaDTO>> recuperarCampanhasAtivas(@RequestParam Integer estrategia) {
		return new ResponseEntity<List<RecuperaCampanhaRespostaDTO>>(this.campanhaServico.recuperarCampanhasAtivas(estrategia), HttpStatus.OK);
	}
}
