package com.github.lbovolini.app.estabelecimento.registra;

import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

class RegistraEstabelecimentoResponse {

    private final UUID uuid;
    private final String nome;
    private final String cnpj;
    private final Instant criadoEm;
    private final List<DadosClienteResponse> clientes;

    RegistraEstabelecimentoResponse(Estabelecimento estabelecimento) {
        this.uuid = estabelecimento.getUuid();
        this.nome = estabelecimento.getNome();
        this.cnpj = estabelecimento.getCnpj();
        this.criadoEm = estabelecimento.getCriadoEm();
        this.clientes = estabelecimento.getClientes()
                .stream()
                .map(DadosClienteResponse::new)
                .collect(Collectors.toUnmodifiableList());
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

    public List<DadosClienteResponse> getClientes() {
        return clientes;
    }
}
