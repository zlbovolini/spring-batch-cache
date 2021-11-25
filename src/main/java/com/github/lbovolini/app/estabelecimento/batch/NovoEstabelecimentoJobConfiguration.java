package com.github.lbovolini.app.estabelecimento.batch;

import com.github.lbovolini.app.estabelecimento.compartilhado.Cliente;
import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;
import com.github.lbovolini.app.estabelecimento.compartilhado.EstabelecimentoRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
class NovoEstabelecimentoJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final NovoEstabelecimentoRepository novoEstabelecimentoRepository;
    @Qualifier("estabelecimentoLegadoDataSource")
    private final DataSource dataSource;
    private final ItemWriter<Estabelecimento> writer;

    NovoEstabelecimentoJobConfiguration(JobBuilderFactory jobBuilderFactory,
                                        StepBuilderFactory stepBuilderFactory,
                                        EstabelecimentoRepository estabelecimentoRepository,
                                        NovoEstabelecimentoRepository novoEstabelecimentoRepository,
                                        @Qualifier("estabelecimentoLegadoDataSource") DataSource dataSource,
                                        ItemWriter<Estabelecimento> writer) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.novoEstabelecimentoRepository = novoEstabelecimentoRepository;
        this.dataSource = dataSource;
        this.writer = writer;
    }

    @Primary
    @Bean
    Job estabelecimentoJob() {
        return jobBuilderFactory.get("estabelecimentoJob")
                .start(chunkStep())
                .build();
    }

    @Bean
    Step chunkStep() {
        return stepBuilderFactory.get("estabelecimentoChunk")
                .<NovoEstabelecimento, Estabelecimento>chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

//    @Bean
//    RepositoryItemReader<NovoEstabelecimento> reader() {
//
//        RepositoryItemReader<NovoEstabelecimento> reader = new RepositoryItemReader<>();
//
//        reader.setRepository(novoEstabelecimentoRepository);
//        reader.setMethodName("findAllNovoEstabelecimento");
//        reader.setPageSize(100);
//        reader.setSort(Map.of("id", Sort.Direction.ASC));
//
//        return reader;
//    }

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

    @Bean
    ItemReader<NovoEstabelecimento> reader() {
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
}
