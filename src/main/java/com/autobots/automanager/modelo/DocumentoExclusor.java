package com.autobots.automanager.modelo;

import java.util.List;
import java.util.Objects;

import com.autobots.automanager.entidades.Documento;

public class DocumentoExclusor {
    public int excluir(Documento documento, List<Documento> documentos) {
		int index = documentos.indexOf(documento);
		return index;
	}

	public int excluir(List<Documento> documentos, List<Documento> atualizacoes) {
		for (Documento atualizacao : atualizacoes) {
			for (Documento documento : documentos) {
				if (atualizacao.getId() != null &&  (Objects.equals(atualizacao.getId(), documento.getId()))) {
						int index = excluir(documento, documentos);
						return index;
					
				}
			}
		}
		return -1;
	}
}
