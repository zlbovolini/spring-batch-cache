package com.github.lbovolini.app.estabelecimento.consulta;

import com.github.lbovolini.app.estabelecimento.compartilhado.Cliente;

import java.util.UUID;

class DadosClienteResponse {

    private final UUID uuid;
    private final String nome;

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
