package com.github.lbovolini.app.estabelecimento.consulta;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.lbovolini.app.estabelecimento.compartilhado.Cliente;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

class DadosClienteResponse {

    private final UUID uuid;
    private final String nome;

    @JsonCreator(mode = PROPERTIES)
    DadosClienteResponse(UUID uuid, String nome) {
        this.uuid = uuid;
        this.nome = nome;
    }

    DadosClienteResponse(Cliente cliente) {
        this.uuid = cliente.getUuid();
        this.nome = cliente.getNome();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNome() {
        return nome;
    }
}
