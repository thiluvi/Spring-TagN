package com.app.tagn.controller;

import com.app.tagn.domain.Usuario;
import com.app.tagn.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Classe DTO interna estática para receber os dados de atualização de forma isolada
    public static class UpdateUserRequest {
        private String nome;
        private String senhaAtual;
        private String novaSenha;

        public UpdateUserRequest() {}

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getSenhaAtual() {
            return senhaAtual;
        }

        public void setSenhaAtual(String senhaAtual) {
            this.senhaAtual = senhaAtual;
        }

        public String getNovaSenha() {
            return novaSenha;
        }

        public void setNovaSenha(String novaSenha) {
            this.novaSenha = novaSenha;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizarUsuario(
                    id,
                    request.getNome(),
                    request.getSenhaAtual(),
                    request.getNovaSenha()
            );
            // Remover hash de senha do objeto de retorno por segurança
            usuarioAtualizado.setSenha(null);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar o usuário.");
        }
    }
}
