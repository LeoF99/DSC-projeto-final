package com.ufpb.ajude.controladores;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.ufpb.ajude.dtos.FazDoacaoRequisicaoDTO;
import com.ufpb.ajude.dtos.FazDoacaoRespostaDTO;
import com.ufpb.ajude.servicos.DoacaoServico;

@RestController
public class DoacoesControlador {
	@Autowired
	private DoacaoServico doacaoServico;
	
	@PostMapping("/auth/campanhas/{id}/doacao")
	public ResponseEntity<FazDoacaoRespostaDTO> realizaDoacao(@PathVariable Long id, @RequestBody FazDoacaoRequisicaoDTO valor, @RequestParam String email, @RequestHeader("Authorization") String header) throws ServletException {
		try {
			FazDoacaoRespostaDTO restante = this.doacaoServico.realizaDoacao(id, valor.getValor(), email, header);
			
			return new ResponseEntity<FazDoacaoRespostaDTO>(restante, HttpStatus.OK);
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<FazDoacaoRespostaDTO>(e.getStatusCode());
		}
	}
}
