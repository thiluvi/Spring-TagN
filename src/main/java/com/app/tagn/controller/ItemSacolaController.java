package com.app.tagn.controller;

import com.app.tagn.domain.ItemSacola;
import com.app.tagn.domain.Produto;
import com.app.tagn.service.ItemSacolaService;
import com.app.tagn.service.ProdutoService;
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

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ItemSacola>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(itemSacolaService.listarPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody ItemSacola itemSacola) {
        // Validar estoque disponível antes de adicionar à sacola
        Optional<Produto> produtoOpt = produtoService.buscarPorId(itemSacola.getProduto().getId());
        if (produtoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Produto não encontrado.");
        }

        Produto produto = produtoOpt.get();
        if (produto.getQuantidade() < itemSacola.getQuantidade()) {
            return ResponseEntity.badRequest().body(
                    "Estoque insuficiente para " + produto.getNome()
                    + ". Disponível: " + produto.getQuantidade());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(itemSacolaService.salvar(itemSacola));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarQuantidade(@PathVariable Long id, @RequestBody ItemSacola itemAtualizado) {
        Optional<ItemSacola> itemExistente = itemSacolaService.buscarPorId(id);
        
        if (itemExistente.isPresent()) {
            ItemSacola item = itemExistente.get();

            // Validar estoque disponível antes de atualizar a quantidade
            Optional<Produto> produtoOpt = produtoService.buscarPorId(item.getProduto().getId());
            if (produtoOpt.isPresent()) {
                Produto produto = produtoOpt.get();
                if (produto.getQuantidade() < itemAtualizado.getQuantidade()) {
                    return ResponseEntity.badRequest().body(
                            "Estoque insuficiente para " + produto.getNome()
                            + ". Disponível: " + produto.getQuantidade());
                }
            }

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

