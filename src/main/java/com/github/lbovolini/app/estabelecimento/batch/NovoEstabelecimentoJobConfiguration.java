package com.github.lbovolini.app.estabelecimento.batch;

import com.github.lbovolini.app.estabelecimento.compartilhado.Cliente;
import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
class NovoEstabelecimentoJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ItemReader<NovoEstabelecimento> reader;
    private final ItemWriter<Estabelecimento> writer;

    NovoEstabelecimentoJobConfiguration(JobBuilderFactory jobBuilderFactory,
                                        StepBuilderFactory stepBuilderFactory,
                                        ItemReader<NovoEstabelecimento> reader,
                                        ItemWriter<Estabelecimento> writer) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.reader = reader;
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
                .reader(reader)
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
