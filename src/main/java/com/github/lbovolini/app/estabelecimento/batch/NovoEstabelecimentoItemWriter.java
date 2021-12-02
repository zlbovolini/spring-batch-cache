package com.github.lbovolini.app.estabelecimento.batch;

import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;
import com.github.lbovolini.app.estabelecimento.compartilhado.EstabelecimentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
class NovoEstabelecimentoItemWriter implements ItemWriter<Estabelecimento> {

    private final Logger log = LoggerFactory.getLogger(NovoEstabelecimentoItemWriter.class);

    private final EstabelecimentoRepository estabelecimentoRepository;

    NovoEstabelecimentoItemWriter(EstabelecimentoRepository estabelecimentoRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    // !TODO se cnpj ja cadastrado? -> erro
    @Transactional
    @Override
    public void write(List<? extends Estabelecimento> estabelecimentos) throws Exception {
        log.info("Salvando n={} novos estabelecimentos", estabelecimentos.size());
        estabelecimentoRepository.saveAll(estabelecimentos);
    }
}
