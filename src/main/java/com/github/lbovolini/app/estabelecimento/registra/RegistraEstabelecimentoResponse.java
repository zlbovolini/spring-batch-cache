package com.github.lbovolini.app.estabelecimento.registra;

import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;

import java.util.UUID;

class RegistraEstabelecimentoResponse {

    private final UUID uuid;

    RegistraEstabelecimentoResponse(Estabelecimento estabelecimento) {
        this.uuid = estabelecimento.getUuid();
    }

    public UUID getUuid() {
        return uuid;
    }
}
