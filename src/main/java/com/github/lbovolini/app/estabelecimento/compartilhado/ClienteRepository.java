package com.github.lbovolini.app.estabelecimento.compartilhado;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

    List<Cliente> findAllByUuidIn(Set<UUID> uuids);
}
