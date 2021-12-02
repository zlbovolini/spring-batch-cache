package com.github.lbovolini.app.estabelecimento.batch;

import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
class NovoEstabelecimentoJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ItemReader<NovoEstabelecimento> reader;
    private final ItemProcessor<NovoEstabelecimento, Estabelecimento> processor;
    private final CompositeItemWriter<Estabelecimento> compositeWriter;
    private final NovoEstabelecimentoItemIdListener readListener;

    NovoEstabelecimentoJobConfiguration(JobBuilderFactory jobBuilderFactory,
                                        StepBuilderFactory stepBuilderFactory,
                                        ItemReader<NovoEstabelecimento> reader,
                                        ItemProcessor<NovoEstabelecimento, Estabelecimento> processor,
                                        CompositeItemWriter<Estabelecimento> compositeWriter,
                                        NovoEstabelecimentoItemIdListener readListener) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.reader = reader;
        this.processor = processor;
        this.compositeWriter = compositeWriter;
        this.readListener = readListener;
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
                .processor(processor)
                .writer(compositeWriter)
                .listener(readListener)
                .build();
    }
}
