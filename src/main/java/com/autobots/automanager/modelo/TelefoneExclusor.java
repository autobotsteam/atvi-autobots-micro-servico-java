package com.autobots.automanager.modelo;

import java.util.List;
import java.util.Objects;

import com.autobots.automanager.entidades.Telefone;

public class TelefoneExclusor {
    public int excluir(Telefone telefone, List<Telefone> telefones) {
		int index = telefones.indexOf(telefone);
		return index;
	}

	public int excluir(List<Telefone> telefones, List<Telefone> atualizacoes) {
		for (Telefone atualizacao : atualizacoes) {
			for (Telefone telefone : telefones) {
				if (atualizacao.getId() != null &&  (Objects.equals(atualizacao.getId(), telefone.getId()))) {
						int index = excluir(telefone, telefones);
						return index;
					
				}
			}
		}
		return -1;
	}
}
