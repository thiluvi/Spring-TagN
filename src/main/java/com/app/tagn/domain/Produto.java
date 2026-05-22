package com.app.tagn.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity // Avisa ao Spring para transformar essa classe em uma tabela
@Table(name = "produtos") // Define o nome da tabela no banco de dados
public class Produto {

    @Id // Chave Primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-Incremento
    private Long id;

    @Column(nullable = false) // Nome do produto
    private String nome;

    @Column(nullable = false) // Descrição do produto (não pode ser nula)
    private String descricao;

    @Column // Campo para armazenar o caminho ou URL da imagem
    private String imagem;

    @Column(nullable = false, precision = 10, scale = 2) // Preço do produto com 2 casas decimais
    private BigDecimal preco;

    @Column(nullable = false) // Quantidade em estoque
    private Integer quantidade;

    @Column(nullable = false) // Categoria do produto
    private String categoria;

    // Construtor vazio exigido pelo JPA
    public Produto() {
    }

    // --- Getters e Setters ---

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
