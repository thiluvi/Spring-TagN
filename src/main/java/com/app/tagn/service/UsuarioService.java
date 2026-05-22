package com.app.tagn.service; // Ajuste para o seu pacote

import com.app.tagn.domain.Usuario;
import com.app.tagn.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // Avisa ao Spring que aqui ficam as regras de negócio
public class UsuarioService {

    @Autowired // Puxa o Repository que criamos na etapa anterior
    private UsuarioRepository usuarioRepository;

    @Autowired // Puxa a ferramenta de criptografia que configuramos acima
    private PasswordEncoder passwordEncoder;

    public Usuario cadastrarUsuario(Usuario novoUsuario) {

        // 1. Verifica se o e-mail já existe no banco de dados da Aiven
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(novoUsuario.getEmail());

        if (usuarioExistente.isPresent()) {
            // Se já existir, interrompe tudo e lança um erro
            throw new RuntimeException("Este e-mail já está em uso!");
        }

        // 2. Criptografa a senha (transforma "123456" em algo como "$2a$10$wY...")
        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);

        // 3. Salva no banco de dados
        return usuarioRepository.save(novoUsuario);
    }

    // Login
    public Usuario login(String email, String senhaPura) {
        // 1. Procura o utilizador pelo e-mail
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilizador não encontrado!"));

        // 2. Compara a senha digitada com a senha encriptada da base de dados
        if (passwordEncoder.matches(senhaPura, usuario.getSenha())) {
            return usuario; // Sucesso!
        } else {
            throw new RuntimeException("E-mail ou palavra-passe incorretos!");
        }
    }

    public Usuario atualizarUsuario(Long id, String nome, String senhaAtual, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if (nome != null && !nome.trim().isEmpty()) {
            usuario.setNome(nome);
        }

        if (novaSenha != null && !novaSenha.trim().isEmpty()) {
            if (senhaAtual == null || senhaAtual.trim().isEmpty()) {
                throw new RuntimeException("A senha atual é necessária para alterar a senha.");
            }
            if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
                throw new RuntimeException("Senha atual incorreta.");
            }
            if (novaSenha.length() < 6) {
                throw new RuntimeException("A nova senha deve ter no mínimo 6 caracteres.");
            }
            usuario.setSenha(passwordEncoder.encode(novaSenha));
        }

        return usuarioRepository.save(usuario);
    }
}
