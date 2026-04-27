package com.app.tagn.controller; // Ajuste para o seu pacote

import com.app.tagn.domain.Usuario;
import com.app.tagn.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Define que esta classe é um controlador de API
@RequestMapping("/api/auth") // Todas as rotas aqui começam com /api/auth
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // Rota de Cadastro: Recebe um JSON e tenta cadastrar
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
        try {
            // Tenta executar a lógica do Service
            Usuario usuarioSalvo = usuarioService.cadastrarUsuario(usuario);
            // Se der certo, retorna o usuário com status 201 (Created)
            return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Se o Service lançar erro (ex: e-mail já existe), retorna a mensagem de erro
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Erros inesperados
            return new ResponseEntity<>("Erro interno no servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Rota de Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario dadosLogin) {
        try {
            // Chamamos o serviço para validar
            Usuario usuario = usuarioService.login(dadosLogin.getEmail(), dadosLogin.getSenha());

            // Por agora, devolvemos os dados do utilizador (exceto a senha por segurança)
            usuario.setSenha(null);
            return ResponseEntity.ok(usuario);

        } catch (RuntimeException e) {
            // Se falhar, devolvemos 401 (Não autorizado)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}