package com.github.lbovolini.app.estabelecimento.registra;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

class RegistraEstabelecimentoRequest {

    @NotNull
    @Size(min = 3, max = 100)
    private final String nome;

    @NotNull
    @CNPJ
    private final String cnpj;

    @JsonCreator(mode = PROPERTIES)
    RegistraEstabelecimentoRequest(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    Estabelecimento toEstabelecimento() {
        return new Estabelecimento(nome, cnpj);
    }
}
