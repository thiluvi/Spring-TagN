package com.app.tagn.repository;

import com.app.tagn.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioIdOrderByDataDesc(Long usuarioId);

    // Últimos 10 pedidos de todos os usuários
    List<Pedido> findTop10ByOrderByDataDesc();

    // Soma total da receita de todos os pedidos
    @Query("SELECT COALESCE(SUM(p.total), 0) FROM Pedido p")
    BigDecimal sumTotalReceita();
}
