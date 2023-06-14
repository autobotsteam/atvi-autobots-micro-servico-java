package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.modelo.EnderecoAdicionador;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.modelo.EnderecoExclusor;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private EnderecoRepositorio enderecoRepositorio;
    @Autowired
    private ClienteSelecionador selecionador;

    @PostMapping("/create/{id}")
    public void create(@RequestBody Endereco endereco, @PathVariable Long id) {
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        EnderecoAdicionador adicionador = new EnderecoAdicionador();
        adicionador.adicionar(endereco, cliente);
        clienteRepositorio.save(cliente);
    }

    @GetMapping("/read/{id}")
    public Endereco read(@PathVariable Long id) {
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        Endereco enderecos = cliente.getEndereco();
        return enderecos;
    }

    @GetMapping("/read")
    public List<Endereco> read() {
        List<Endereco> enderecos = enderecoRepositorio.findAll();
        return enderecos;
    }

    @PutMapping("/update/{id}")
    public void update(@RequestBody Endereco endereco, @PathVariable Long id) {
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        Endereco enderecoAtual = cliente.getEndereco();
        EnderecoAtualizador atualizador = new EnderecoAtualizador();
        atualizador.atualizar(endereco, enderecoAtual);
        cliente.setEndereco(enderecoAtual);
        clienteRepositorio.save(cliente);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        EnderecoExclusor exclusor = new EnderecoExclusor();
        exclusor.excluir(cliente);
        clienteRepositorio.save(cliente);
    }
}
