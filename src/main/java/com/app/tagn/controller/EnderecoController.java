package com.app.tagn.controller;

import com.app.tagn.domain.Endereco;
import com.app.tagn.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Endereco>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(enderecoService.listarPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Endereco> salvar(@RequestBody Endereco endereco) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.salvar(endereco));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        enderecoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
