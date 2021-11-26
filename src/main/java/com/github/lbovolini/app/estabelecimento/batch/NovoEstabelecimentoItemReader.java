package com.github.lbovolini.app.estabelecimento.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
class NovoEstabelecimentoItemReader {

    private final DataSource dataSource;

    public NovoEstabelecimentoItemReader(@Qualifier("estabelecimentoLegadoDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
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

}
