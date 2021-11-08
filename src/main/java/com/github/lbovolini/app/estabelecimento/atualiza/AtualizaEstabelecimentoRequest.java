package com.github.lbovolini.app.estabelecimento.atualiza;

import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

class AtualizaEstabelecimentoRequest {

    @NotNull
    @Size(min = 3, max = 100)
    private final String nome;

    @NotNull
    @CNPJ
    private final String cnpj;

    AtualizaEstabelecimentoRequest(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
    }

    String getNome() {
        return nome;
    }

    String getCnpj() {
        return cnpj;
    }

}
