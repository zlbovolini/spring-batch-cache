package com.github.lbovolini.app.estabelecimento.registra;

import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;

import java.time.Instant;
import java.util.UUID;

class RegistraEstabelecimentoResponse {

    private final UUID uuid;
    private final String nome;
    private final String cnpj;
    private final Instant criadoEm;

    RegistraEstabelecimentoResponse(Estabelecimento estabelecimento) {
        this.uuid = estabelecimento.getUuid();
        this.nome = estabelecimento.getNome();
        this.cnpj = estabelecimento.getCnpj();
        this.criadoEm = estabelecimento.getCriadoEm();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }
}
