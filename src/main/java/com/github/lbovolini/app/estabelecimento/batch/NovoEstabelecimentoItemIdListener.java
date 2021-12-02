package com.github.lbovolini.app.estabelecimento.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class NovoEstabelecimentoItemIdListener {

    private final Logger log = LoggerFactory.getLogger(NovoEstabelecimentoItemIdListener.class);

    private final List<Long> ids = new ArrayList<>();

    @AfterRead
    void afterRead(NovoEstabelecimento novoEstabelecimento) {
        log.info("Salvando id={} de novo estabelecimento em lista após leitura", novoEstabelecimento.getId());
        ids.add(novoEstabelecimento.getId());
    }

    @AfterChunk
    void afterChunk() {
        if (ids.isEmpty()) {
            return;
        }
        ids.clear();
        log.info("Esvaziada lista de ids de NovoEstabelecimento");
    }

    public List<Long> getIds() {
        return ids;
    }
}
