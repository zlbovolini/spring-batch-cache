package com.github.lbovolini.app.estabelecimento.compartilhado;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID uuid = UUID.randomUUID();

    @NotBlank
    private String nome;

    /**
     * Requisito do Hibernate
     */
    @Deprecated
    Cliente() {}

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Estabelecimento estabelecimento;

    public Cliente(String nome, Estabelecimento estabelecimento) {
        this.nome = nome;
        this.estabelecimento = estabelecimento;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNome() {
        return nome;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(nome, cliente.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
