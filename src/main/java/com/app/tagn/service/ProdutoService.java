package com.app.tagn.service;

import com.app.tagn.domain.Produto;
import com.app.tagn.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Indica que esta classe contém as regras de negócio
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    // Retorna todos os produtos cadastrados
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    // Busca um produto pelo ID
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    // Salva um novo produto ou atualiza um existente
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    // Salva uma lista de produtos
    public List<Produto> salvarTodos(List<Produto> produtos) {
        return produtoRepository.saveAll(produtos);
    }

    // Deleta um produto pelo ID
    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }
}
