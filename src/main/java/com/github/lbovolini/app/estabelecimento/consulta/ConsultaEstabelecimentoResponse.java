package com.github.lbovolini.vendas.estabelecimento.consulta;

import com.github.lbovolini.vendas.estabelecimento.compartilhado.Estabelecimento;

import java.time.Instant;
import java.util.UUID;

class ConsultaEstabelecimentoResponse {

    private final UUID uuid;
    private final String nome;
    private final String cnpj;
    private final Instant criadoEm;

    ConsultaEstabelecimentoResponse(Estabelecimento estabelecimento) {
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
