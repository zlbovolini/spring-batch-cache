package com.github.lbovolini.app.estabelecimento.batch;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class NovoEstabelecimentoItemReader implements ItemReader<NovoEstabelecimento> {

    private StepExecution stepExecution;

    private final DataSource dataSource;
    JdbcCursorItemReader<NovoEstabelecimento> cursorItemReader;

    public NovoEstabelecimentoItemReader(DataSource dataSource) {
        this.dataSource = dataSource;
        cursorItemReader = new JdbcCursorItemReaderBuilder<NovoEstabelecimento>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql("SELECT * FROM estabelecimentos.busca_estabelecimentos()")
                .rowMapper(new BeanPropertyRowMapper<>(NovoEstabelecimento.class))
                .build();
    }

    @Override
    public NovoEstabelecimento read() throws Exception {

        NovoEstabelecimento novoEstabelecimento = cursorItemReader.read();

        return novoEstabelecimento;
    }

}
