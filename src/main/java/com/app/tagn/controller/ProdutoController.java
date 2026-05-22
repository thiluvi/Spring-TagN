package com.app.tagn.controller;

import com.app.tagn.domain.Produto;
import com.app.tagn.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Indica que esta classe é um controlador REST (retorna JSON, etc)
@RequestMapping("/produtos") // Define o caminho (URL) base para este controlador
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // GET /produtos - Retorna a lista de todos os produtos
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    // GET /produtos/{id} - Retorna um produto específico
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);
        return produto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // 404 se não achar
    }

    // POST /produtos - Cria um novo produto
    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
        Produto produtoSalvo = produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo); // 201 Created
    }

    // POST /produtos/lote - Cria vários produtos em lote
    @PostMapping("/lote")
    public ResponseEntity<List<Produto>> criarEmLote(@RequestBody List<Produto> produtos) {
        List<Produto> produtosSalvos = produtoService.salvarTodos(produtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtosSalvos); // 201 Created
    }

    // PUT /produtos/{id} - Atualiza um produto existente
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        Optional<Produto> produtoExistente = produtoService.buscarPorId(id);
        
        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();
            // Atualiza os campos
            produto.setNome(produtoAtualizado.getNome());
            produto.setDescricao(produtoAtualizado.getDescricao());
            produto.setImagem(produtoAtualizado.getImagem());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setQuantidade(produtoAtualizado.getQuantidade());
            produto.setCategoria(produtoAtualizado.getCategoria());
            
            return ResponseEntity.ok(produtoService.salvar(produto)); // 200 OK
        }
        
        return ResponseEntity.notFound().build(); // 404 se não existir
    }

    // DELETE /produtos/{id} - Deleta um produto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Optional<Produto> produtoExistente = produtoService.buscarPorId(id);
        
        if (produtoExistente.isPresent()) {
            produtoService.deletar(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        
        return ResponseEntity.notFound().build(); // 404 se não existir
    }
}
