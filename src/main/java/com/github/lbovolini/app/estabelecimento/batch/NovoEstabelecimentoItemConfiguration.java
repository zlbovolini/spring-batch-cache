package com.github.lbovolini.app.estabelecimento.batch;

import com.github.lbovolini.app.estabelecimento.compartilhado.Cliente;
import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.List;

@Configuration
class NovoEstabelecimentoItemConfiguration {

    private final DataSource dataSource;
    private final NovoEstabelecimentoItemWriter writer;
    private final NovoEstabelecimentoItemPollingCleaner cleaner;

    NovoEstabelecimentoItemConfiguration(@Qualifier("estabelecimentoLegadoDataSource") DataSource dataSource,
                                         NovoEstabelecimentoItemWriter writer,
                                         NovoEstabelecimentoItemPollingCleaner cleaner) {
        this.dataSource = dataSource;
        this.writer = writer;
        this.cleaner = cleaner;
    }

    @Bean
    @StepScope
    JdbcCursorItemReader<NovoEstabelecimento> itemReader() {
        return new JdbcCursorItemReaderBuilder<NovoEstabelecimento>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql("SELECT * FROM estabelecimentos.busca_estabelecimentos()")
                .rowMapper(new BeanPropertyRowMapper<>(NovoEstabelecimento.class))
                .build();
    }

    @Bean
    ItemProcessor<NovoEstabelecimento, Estabelecimento> processor() {
        return (novoEstabelecimento) -> {
            String cnpj = String.format("%014d", Long.parseLong(novoEstabelecimento.getCnpj()));
            Estabelecimento estabelecimento = new Estabelecimento(novoEstabelecimento.getNome(), cnpj);
            Cliente cliente = new Cliente(novoEstabelecimento.getCliente(), estabelecimento);

            estabelecimento.adicionaCliente(cliente);

            return estabelecimento;
        };
    }

    @Bean
    CompositeItemWriter<Estabelecimento> compositeItemWriter() {
        List<ItemWriter<? super Estabelecimento>> writers = List.of(writer, cleaner);

        return new CompositeItemWriterBuilder<Estabelecimento>()
                .delegates(writers)
                .build();
    }

//    @Bean
//    StoredProcedureItemReader<NovoEstabelecimento> reader() {
//        return new StoredProcedureItemReaderBuilder<NovoEstabelecimento>().name("busca_estabelecimentos")
//                .procedureName("estabelecimentos.busca_estabelecimentos")
//                .dataSource(dataSource)
//                .rowMapper(new BeanPropertyRowMapper<>(NovoEstabelecimento.class))
//                .function()
//                .build();
//    }
//
}
