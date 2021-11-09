package com.github.lbovolini.app.estabelecimento.atualiza;

import com.github.lbovolini.app.estabelecimento.compartilhado.Cliente;

import java.util.UUID;

class DadosClienteResponse {

    private final UUID uuid;
    private final String nome;

    DadosClienteResponse(Cliente cliente) {
        this.uuid = cliente.getUuid();
        this.nome = cliente.getNome();
    }

    public String getNome() {
        return nome;
    }
}
