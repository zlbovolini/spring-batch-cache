package com.github.lbovolini.app.estabelecimento.batch;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface NovoEstabelecimentoRepository extends PagingAndSortingRepository<NovoEstabelecimento, Long> {

    @Procedure("estabelecimentos.busca_estabelecimentos")
    List<NovoEstabelecimento> findAllNovoEstabelecimento(Pageable pageable);
}
