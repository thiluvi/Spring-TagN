package com.app.tagn.service;

import com.app.tagn.domain.ItemSacola;
import com.app.tagn.repository.ItemSacolaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemSacolaService {

    @Autowired
    private ItemSacolaRepository itemSacolaRepository;

    public List<ItemSacola> listarPorUsuario(Long usuarioId) {
        return itemSacolaRepository.findByUsuarioId(usuarioId);
    }

    public ItemSacola salvar(ItemSacola itemSacola) {
        return itemSacolaRepository.save(itemSacola);
    }
    
    public Optional<ItemSacola> buscarPorId(Long id) {
        return itemSacolaRepository.findById(id);
    }

    public void deletar(Long id) {
        itemSacolaRepository.deleteById(id);
    }

    public void limparSacola(Long usuarioId) {
        List<ItemSacola> itens = itemSacolaRepository.findByUsuarioId(usuarioId);
        itemSacolaRepository.deleteAll(itens);
    }
}
