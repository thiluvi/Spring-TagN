package com.app.tagn.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity // Avisa ao Spring: "Ei, transforme essa classe em uma tabela no banco!"
@Table(name = "usuarios") // Define o nome da tabela no MySQL
public class Usuario {

    @Id // Diz que este campo é a Chave Primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Faz o ID ser Auto-Incremento (1, 2, 3...)
    private Long id;

    @Column(nullable = false) // Impede que o nome seja salvo em branco
    private String nome;

    @Column(nullable = false, unique = true) // Impede email em branco e garante que não existam dois iguais
    private String email;

    @Column(nullable = false)
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;

    // Construtor vazio (É obrigatório ter um para o banco de dados funcionar)
    public Usuario() {
    }

    // --- Daqui para baixo são apenas os Getters e Setters padrão ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}