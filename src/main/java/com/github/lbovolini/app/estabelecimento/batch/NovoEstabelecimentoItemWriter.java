package com.github.lbovolini.app.estabelecimento.batch;

import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;
import com.github.lbovolini.app.estabelecimento.compartilhado.EstabelecimentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
class NovoEstabelecimentoItemWriter implements ItemWriter<Estabelecimento> {

    private final Logger log = LoggerFactory.getLogger(NovoEstabelecimentoItemWriter.class);

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final NovoEstabelecimentoRepository novoEstabelecimentoRepository;

    private final List<Long> ids = new ArrayList<>();

    NovoEstabelecimentoItemWriter(EstabelecimentoRepository estabelecimentoRepository,
                                  NovoEstabelecimentoRepository novoEstabelecimentoRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.novoEstabelecimentoRepository = novoEstabelecimentoRepository;
    }

    @Transactional
    @Override
    public void write(List<? extends Estabelecimento> estabelecimentos) throws Exception {

        log.info("Salvando n={} novos estabelecimentos", estabelecimentos.size());
        estabelecimentoRepository.saveAll(estabelecimentos);

        log.info("Removendo n={} estabelecimentos da tabela de polling", ids.size());
        novoEstabelecimentoRepository.deleteAllById(ids);

        log.info("Removido com sucesso estabelecimentos da tabela de polling");
    }

    @AfterRead
    public void afterRead(NovoEstabelecimento novoEstabelecimento) {

        log.info("Salvando id={} de novo estabelecimento após leitura", novoEstabelecimento.getId());
        ids.add(novoEstabelecimento.getId());
    }

}
