package com.github.lbovolini.app.estabelecimento.cria;

import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

class CriaEstabelecimentoRequest {

    @NotNull
    @Size(min = 3, max = 100)
    private final String nome;

    @NotNull
    @CNPJ
    private final String cnpj;

    CriaEstabelecimentoRequest(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
    }

    String getCnpj() {
        return cnpj;
    }

    Estabelecimento toEstabelecimento() {
        return new Estabelecimento(nome, cnpj);
    }
}
