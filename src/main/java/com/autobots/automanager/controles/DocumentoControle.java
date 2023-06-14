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
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.modelo.DocumentoAdicionador;
import com.autobots.automanager.modelo.DocumentoAtualizador;
import com.autobots.automanager.modelo.DocumentoExclusor;
import com.autobots.automanager.modelo.DocumentoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
    @Autowired
    private ClienteRepositorio cliente;
    @Autowired
    private DocumentoRepositorio documento;
    @Autowired
    private ClienteSelecionador selecionador;
    @Autowired
    private DocumentoSelecionador selecionadorDoc;

        
    @PostMapping("/create")
    public void create(@RequestBody Cliente doc) {
        List<Cliente> target = cliente.findAll();
        Cliente cli = selecionador.selecionar(target, doc.getId());
        DocumentoAdicionador adicionador = new DocumentoAdicionador();
        adicionador.adicionar(cli, doc.getDocumentos());
        cliente.save(cli);
    }

    @GetMapping("/read")
    public List<Documento> read() {
        List<Documento> documentos = documento.findAll();
        return documentos;
    }

    @GetMapping("/read/{id}")
    public List<Documento> read(@PathVariable Long id) {
        List<Cliente> target = cliente.findAll();
        Cliente cli = selecionador.selecionar(target, id);
        List<Documento> documentos = cli.getDocumentos();
        return documentos;
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable Long id, @RequestBody Documento doc) {
        List<Cliente> target = cliente.findAll();
        Cliente cli = selecionador.selecionar(target, id);
        List<Documento> documentos = cli.getDocumentos();
        Documento documentoAtl = selecionadorDoc.selecionar(documentos, doc.getId());
        DocumentoAtualizador atualizador = new DocumentoAtualizador();
        atualizador.atualizar(documentoAtl, doc);
        DocumentoExclusor exclusor = new DocumentoExclusor();
        int index = exclusor.excluir(doc, documentos);
        documentos.remove(index);
        documentos.add(documentoAtl);
        cli.setDocumentos(documentos);
        cliente.save(cli);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id, @RequestBody Documento doc) {
        List<Cliente> target = cliente.findAll();
        Cliente cli = selecionador.selecionar(target, id);
        List<Documento> documentos = cli.getDocumentos();
        DocumentoExclusor exclusor = new DocumentoExclusor();
        int index = exclusor.excluir(doc, documentos);
        documentos.remove(index);
        cli.setDocumentos(documentos);
        cliente.save(cli);
    }
}
