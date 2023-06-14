package com.autobots.automanager.modelo;

import com.autobots.automanager.entidades.Cliente;

public class EnderecoExclusor {
    public void excluir(Cliente cliente) {
            cliente.setEndereco(null);
    }
}
