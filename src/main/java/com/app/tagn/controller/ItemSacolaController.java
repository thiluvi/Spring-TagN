package com.app.tagn.controller;

import com.app.tagn.domain.ItemSacola;
import com.app.tagn.service.ItemSacolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sacola")
public class ItemSacolaController {

    @Autowired
    private ItemSacolaService itemSacolaService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ItemSacola>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(itemSacolaService.listarPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<ItemSacola> adicionar(@RequestBody ItemSacola itemSacola) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemSacolaService.salvar(itemSacola));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemSacola> atualizarQuantidade(@PathVariable Long id, @RequestBody ItemSacola itemAtualizado) {
        Optional<ItemSacola> itemExistente = itemSacolaService.buscarPorId(id);
        
        if (itemExistente.isPresent()) {
            ItemSacola item = itemExistente.get();
            item.setQuantidade(itemAtualizado.getQuantidade());
            return ResponseEntity.ok(itemSacolaService.salvar(item));
        }
        
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        itemSacolaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
