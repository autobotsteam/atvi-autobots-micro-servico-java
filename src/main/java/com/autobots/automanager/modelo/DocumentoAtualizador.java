package com.autobots.automanager.modelo;

import java.util.List;
import java.util.Objects;

import com.autobots.automanager.entidades.Documento;

public class DocumentoAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void atualizar(Documento documento, Documento atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getTipo())) {
				documento.setTipo(atualizacao.getTipo());
			}
			if (!verificador.verificar(atualizacao.getNumero())) {
				documento.setNumero(atualizacao.getNumero());
			}
		}
	}

	public void atualizar(List<Documento> documentos, List<Documento> atualizacoes) {
		for (Documento atualizacao : atualizacoes) {
			for (Documento documento : documentos) {
				if (atualizacao.getId() != null &&  (Objects.equals(atualizacao.getId(), documento.getId()))) {
						atualizar(documento, atualizacao);
					
				}
			}
		}
	}
}