package com.github.lbovolini.vendas.estabelecimento.compartilhado;

import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;

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

    /**
     * Requisito do Hibernate
     */
    @Deprecated
    Estabelecimento() {}

    public Estabelecimento( String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
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
