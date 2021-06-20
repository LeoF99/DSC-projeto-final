package com.ufpb.ajude.servicos;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.ufpb.ajude.repositorios.CampanhaRepositorio;
import com.ufpb.ajude.repositorios.DoacaoRepositorio;
import com.ufpb.ajude.dtos.FazDoacaoRespostaDTO;
import com.ufpb.ajude.entidades.Campanha;
import com.ufpb.ajude.entidades.Doacao;

@Service
public class DoacaoServico {
	@Autowired
	DoacaoRepositorio doacaoRepositorio;
	
	@Autowired
	private CampanhaRepositorio campanhaRepositorio;
	
	@Autowired
	private UsuarioServico usuarioServico;
	
	public FazDoacaoRespostaDTO realizaDoacao(long idCampanha, double valor, String email, String header) throws ServletException {
		if(!this.usuarioServico.usuarioTemPermissaoRota(header, email)) {
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
		}
		
		Optional<Campanha> buscaCampanha = this.campanhaRepositorio.findById(idCampanha);
		
		if(buscaCampanha.isEmpty()) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		
		Campanha campanha = buscaCampanha.get();
		
		Doacao doacao = this.criaDoacao(valor, email, idCampanha);
		
		List<Doacao> doacoes = this.salvaDoacaoEmCampanha(doacao, campanha);
		
		return this.calculaRestanteDaMeta(doacoes, campanha.getMeta());
	}
	
	private FazDoacaoRespostaDTO calculaRestanteDaMeta(List<Doacao> doacoes, double meta) {
		double restante = meta;
		
		for( Doacao d: doacoes ) {
			restante = restante - d.getValor();
		}
		
		return new FazDoacaoRespostaDTO(restante);
	}
	
	private List<Doacao> salvaDoacaoEmCampanha(Doacao doacao, Campanha campanha) {
		List<Doacao> doacoes = campanha.getDoacoes();
		doacoes.add(doacao);
		
		campanha.setDoacoes(doacoes);
		
		this.campanhaRepositorio.save(campanha);
		
		return doacoes;
	}
	
	private Doacao criaDoacao(double valor, String email, long idCampanha) {
		Doacao doacao = new Doacao(
			valor,
			email,
			idCampanha,
			new Date()
		);
		
		return doacao;
	}
}
