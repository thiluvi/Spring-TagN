package com.app.tagn.service;

import com.app.tagn.domain.ItemPedido;
import com.app.tagn.domain.Pedido;
import com.app.tagn.domain.Produto;
import com.app.tagn.repository.PedidoRepository;
import com.app.tagn.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemSacolaService itemSacolaService;

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        // Assegurar que os itens apontem corretamente de volta para o Pedido
        if (pedido.getItens() != null) {
            for (ItemPedido item : pedido.getItens()) {
                item.setPedido(pedido);
            }
        }

        // Validar estoque disponível e decrementar para cada item do pedido
        if (pedido.getItens() != null) {
            for (ItemPedido item : pedido.getItens()) {
                Produto produto = produtoRepository.findById(item.getProduto().getId())
                        .orElseThrow(() -> new RuntimeException(
                                "Produto não encontrado: " + item.getProduto().getId()));

                if (produto.getQuantidade() < item.getQuantidade()) {
                    throw new RuntimeException(
                            "Estoque insuficiente para o produto: " + produto.getNome()
                            + ". Disponível: " + produto.getQuantidade()
                            + ", Solicitado: " + item.getQuantidade());
                }

                // Decrementar o estoque
                produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
                produtoRepository.save(produto);
            }
        }

        // Salvar o pedido (com cascade ALL para salvar os itens do pedido também)
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // Limpar o carrinho (sacola de compras) do usuário após concluir a compra
        itemSacolaService.limparSacola(pedido.getUsuario().getId());

        return pedidoSalvo;
    }

    public List<Pedido> listarPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioIdOrderByDataDesc(usuarioId);
    }
}
