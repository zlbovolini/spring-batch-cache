package com.github.lbovolini.app.estabelecimento.batch;

import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

// !TODO implementar ItemStream para permitir reiniciar a partir de um checkpoint
@Component
class NovoEstabelecimentoItemPollingCleaner extends AbstractItemStreamItemWriter<Estabelecimento> {

    private final Logger log = LoggerFactory.getLogger(NovoEstabelecimentoItemPollingCleaner.class);

    private final NovoEstabelecimentoRepository novoEstabelecimentoRepository;
    private final NovoEstabelecimentoItemIdListener readerListener;

    NovoEstabelecimentoItemPollingCleaner(NovoEstabelecimentoRepository novoEstabelecimentoRepository,
                                          NovoEstabelecimentoItemIdListener readerListener) {
        this.novoEstabelecimentoRepository = novoEstabelecimentoRepository;
        this.readerListener = readerListener;
    }

    @Override
    public void write(List<? extends Estabelecimento> items) throws Exception {
        var ids = readerListener.getIds();

        log.info("Removendo n={} estabelecimentos da tabela de polling", ids.size());
        novoEstabelecimentoRepository.deleteAllById(ids);

        log.info("Removido com sucesso n={} estabelecimentos da tabela de polling", ids.size());
    }
}
