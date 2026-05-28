package com.app.tagn.controller;

import com.app.tagn.domain.Pedido;
import com.app.tagn.repository.PedidoRepository;
import com.app.tagn.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * GET /admin/dashboard
     * Retorna estatísticas gerais para o painel do administrador:
     * - totalPedidos: quantidade total de pedidos realizados
     * - receitaTotal: soma dos totais de todos os pedidos
     * - totalProdutos: quantidade de produtos cadastrados
     * - produtosEstoqueBaixo: produtos com estoque <= 5
     * - pedidosRecentes: últimos 10 pedidos (com dados do usuário e itens)
     */
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        Map<String, Object> dashboard = new HashMap<>();

        // Estatísticas gerais
        long totalPedidos = pedidoRepository.count();
        BigDecimal receitaTotal = pedidoRepository.sumTotalReceita();
        long totalProdutos = produtoRepository.count();
        long produtosEstoqueBaixo = produtoRepository.countByQuantidadeLessThanEqual(5);

        // Últimos 10 pedidos
        List<Pedido> pedidosRecentes = pedidoRepository.findTop10ByOrderByDataDesc();

        dashboard.put("totalPedidos", totalPedidos);
        dashboard.put("receitaTotal", receitaTotal);
        dashboard.put("totalProdutos", totalProdutos);
        dashboard.put("produtosEstoqueBaixo", produtosEstoqueBaixo);
        dashboard.put("pedidosRecentes", pedidosRecentes);

        return ResponseEntity.ok(dashboard);
    }
}
