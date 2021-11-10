package com.github.lbovolini.app.estabelecimento.consulta;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

class ConsultaEstabelecimentoResponse {

    private final UUID uuid;
    private final String nome;
    private final String cnpj;
    private final Instant criadoEm;
    private final List<DadosClienteResponse> clientes;

    @JsonCreator(mode = PROPERTIES)
    ConsultaEstabelecimentoResponse(UUID uuid, String nome, String cnpj, Instant criadoEm, List<DadosClienteResponse> clientes) {
        this.uuid = uuid;
        this.nome = nome;
        this.cnpj = cnpj;
        this.criadoEm = criadoEm;
        this.clientes = clientes;
    }

    ConsultaEstabelecimentoResponse(Estabelecimento estabelecimento) {
        this.uuid = estabelecimento.getUuid();
        this.nome = estabelecimento.getNome();
        this.cnpj = estabelecimento.getCnpj();
        this.criadoEm = estabelecimento.getCriadoEm();
        this.clientes = estabelecimento.getClientes()
                .stream()
                .map(DadosClienteResponse::new)
                .collect(Collectors.toList());
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
