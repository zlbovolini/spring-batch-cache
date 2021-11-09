package com.github.lbovolini.app.estabelecimento.registra;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

class DadosClienteRequest {

    @NotBlank
    private final String nome;

    @JsonCreator(mode = PROPERTIES)
    DadosClienteRequest(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
