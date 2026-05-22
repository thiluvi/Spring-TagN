package com.app.tagn.service;

import com.app.tagn.domain.ItemPedido;
import com.app.tagn.domain.Pedido;
import com.app.tagn.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

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
