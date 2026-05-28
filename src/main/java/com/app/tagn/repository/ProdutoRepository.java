package com.app.tagn.repository;

import com.app.tagn.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indica que esta interface é um repositório gerenciado pelo Spring
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // O JpaRepository já nos fornece métodos como save(), findAll(), findById(), deleteById()

    // Conta quantos produtos têm estoque menor ou igual ao valor informado
    long countByQuantidadeLessThanEqual(Integer quantidade);
}
