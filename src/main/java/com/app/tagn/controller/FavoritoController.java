package com.app.tagn.controller;

import com.app.tagn.domain.Favorito;
import com.app.tagn.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Favorito>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(favoritoService.listarPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Favorito> adicionar(@RequestBody Favorito favorito) {
        return ResponseEntity.status(HttpStatus.CREATED).body(favoritoService.salvar(favorito));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        favoritoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
