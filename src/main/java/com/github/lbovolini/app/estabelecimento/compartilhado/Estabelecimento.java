package com.github.lbovolini.app.estabelecimento.compartilhado;

import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.*;

@Entity
public class Estabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID uuid = UUID.randomUUID();

    @NotNull
    @Size(min = 3, max = 100)
    private String nome;

    @NotNull
    @CNPJ
    @Column(unique = true, nullable = false)
    private String cnpj;

    private Instant criadoEm = Instant.now();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estabelecimento", cascade = CascadeType.ALL)
    private List<Cliente> clientes = new ArrayList<>();

    /**
     * Requisito do Hibernate
     */
    @Deprecated
    Estabelecimento() {}

    public Estabelecimento(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public List<Cliente> getClientes() {
        return Collections.unmodifiableList(clientes);
    }

    public void adicionaCliente(Cliente cliente) {
        Assert.isTrue(equals(cliente.getEstabelecimento()), "Cliente deve estar associado ao mesmo estabelecimento");
        this.clientes.add(cliente);
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) { return true; }

        if (object == null || getClass() != object.getClass()) { return false; }

        Estabelecimento that = (Estabelecimento) object;

        return Objects.equals(nome, that.nome)
                && Objects.equals(cnpj, that.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cnpj);
    }
}
