package com.github.lbovolini.app.estabelecimento.compartilhado;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface EstabelecimentoRepository extends CrudRepository<Estabelecimento, Long> {

    Optional<Estabelecimento> findByUuid(UUID uuid);

    Optional<Estabelecimento> findByCnpj(String cnpj);
}
