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
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.modelo.TelefoneAdicionador;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.modelo.TelefoneExclusor;
import com.autobots.automanager.modelo.TelefoneSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private TelefoneRepositorio telefoneRepositorio;
    @Autowired
    private ClienteSelecionador selecionador;
    @Autowired
    private TelefoneSelecionador selecionadorTel;

    @PostMapping("/create/{id}")
    public void create(@RequestBody Telefone telefone, @PathVariable Long id) {
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        TelefoneAdicionador adicionador = new TelefoneAdicionador();
        adicionador.adicionar(telefone, cliente);
        clienteRepositorio.save(cliente);
    }

    @GetMapping("/read/{id}")
    public List<Telefone> read(@PathVariable Long id) {
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        List<Telefone> telefones = cliente.getTelefones();
        return telefones;
    }

    @GetMapping("/read")
    public List<Telefone> read() {
        List<Telefone> telefones = telefoneRepositorio.findAll();
        return telefones;
    }

    @PutMapping("/update/{id}")
    public void update(@RequestBody Telefone telefone, @PathVariable Long id) {
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        List<Telefone> telefones = cliente.getTelefones();
        Telefone telefoneatl = selecionadorTel.selecionar(telefones, telefone.getId());
        TelefoneAtualizador atualizador = new TelefoneAtualizador();
        atualizador.atualizar(telefoneatl, telefone);
        TelefoneExclusor exclusor = new TelefoneExclusor();
        int index = exclusor.excluir(telefoneatl, telefones);
        telefones.remove(index);
        telefones.add(telefoneatl);
        cliente.setTelefones(telefones);
        clienteRepositorio.save(cliente);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id, @RequestBody Telefone telefone) {
        List<Cliente> clientes = clienteRepositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        List<Telefone> telefones = cliente.getTelefones();
        TelefoneExclusor exclusor = new TelefoneExclusor();
        int index = exclusor.excluir(telefone, telefones);
        telefones.remove(index);
        cliente.setTelefones(telefones);
        clienteRepositorio.save(cliente);
    }
}
