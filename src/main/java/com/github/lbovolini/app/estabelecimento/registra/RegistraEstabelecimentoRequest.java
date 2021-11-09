package com.github.lbovolini.app.estabelecimento.registra;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.lbovolini.app.estabelecimento.compartilhado.Cliente;
import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

class RegistraEstabelecimentoRequest {

    @NotNull
    @Size(min = 3, max = 100)
    private final String nome;

    @NotNull
    @CNPJ
    private final String cnpj;

    @NotNull
    @Size(min = 1)
    private final List<DadosClienteRequest> clientes;

    @JsonCreator(mode = PROPERTIES)
    RegistraEstabelecimentoRequest(String nome, String cnpj, List<DadosClienteRequest> clientes) {
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

    public List<DadosClienteRequest> getClientes() {
        return clientes;
    }

    Estabelecimento toEstabelecimento() {
        Estabelecimento estabelecimento = new Estabelecimento(nome, cnpj);
        clientes.stream()
                .map(dadosCliente -> new Cliente(dadosCliente.getNome(), estabelecimento))
                .forEach(estabelecimento::adicionaCliente);

        return estabelecimento;
    }
}
