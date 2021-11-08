package com.github.lbovolini.app.estabelecimento.consulta;

import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;
import com.github.lbovolini.app.estabelecimento.compartilhado.EstabelecimentoRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
class ConsultaEstabelecimentoCache {

    private final EstabelecimentoRepository estabelecimentoRepository;

    ConsultaEstabelecimentoCache(EstabelecimentoRepository estabelecimentoRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    @Cacheable(cacheNames = "estabelecimento", key = "#uuid", unless="#result == null")
    public Optional<Estabelecimento> consultaPorUuid(UUID uuid) {
        return estabelecimentoRepository.findByUuid(uuid);
    }
}
