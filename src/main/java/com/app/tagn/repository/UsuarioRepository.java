package com.app.tagn.repository;

import com.app.tagn.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository // Indica ao Spring que esta é a classe responsável pelo acesso ao banco
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método customizado: o Spring entende o nome "findByEmail"
    // e cria o SQL automaticamente: SELECT * FROM usuarios WHERE email = ?
    Optional<Usuario> findByEmail(String email);

}