package com.github.lbovolini.app.estabelecimento.atualiza;

import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

class AtualizaEstabelecimentoRequest {

    @NotNull
    @Size(min = 3, max = 100)
    private final String nome;

    @NotNull
    @CNPJ
    private final String cnpj;

    @NotNull
    @Size(min = 1)
    private final Set<UUID> clientes;

    AtualizaEstabelecimentoRequest(String nome, String cnpj, Set<UUID> clientes) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.clientes = clientes;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Set<UUID> getClientes() {
        return clientes;
    }
}
