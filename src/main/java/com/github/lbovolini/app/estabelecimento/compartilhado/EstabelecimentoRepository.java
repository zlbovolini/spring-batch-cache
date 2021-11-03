package com.github.lbovolini.app.estabelecimento.compartilhado;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface EstabelecimentoRepository extends CrudRepository<Estabelecimento, Long> {

    @Cacheable(cacheNames = "estabelecimento", key="#uuid")
    Optional<Estabelecimento> findByUuid(UUID uuid);
}
