package com.app.tagn.repository;

import com.app.tagn.domain.ItemSacola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemSacolaRepository extends JpaRepository<ItemSacola, Long> {
    List<ItemSacola> findByUsuarioId(Long usuarioId);
}
