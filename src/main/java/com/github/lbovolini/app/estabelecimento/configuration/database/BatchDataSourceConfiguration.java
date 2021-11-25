package com.github.lbovolini.app.estabelecimento.configuration.database;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(enableDefaultTransactions = true,
        basePackages = "com.github.lbovolini.app.estabelecimento.configuration.database",
        entityManagerFactoryRef = "batchEntityManagerFactory",
        transactionManagerRef= "batchTransactionManager")
class BatchDataSourceConfiguration {

    @Bean("batchDataSourceProperties")
    @ConfigurationProperties("batch.datasource")
    DataSourceProperties batchDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("batchDataSource")
    DataSource batchDataSource(@Qualifier("batchDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean("batchEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean batchEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                     @Qualifier("batchDataSource") DataSource dataSource) {
        Map<String, String> properties = Map.of("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy",
               "hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

        return builder.dataSource(dataSource)
                .packages("com.github.lbovolini.app.estabelecimento.configuration.database")
                .persistenceUnit("batch")
                .properties(properties)
                .build();
    }

    @Bean("batchTransactionManager")
    PlatformTransactionManager estabelecimentoLegadoTransactionManager(
            @Qualifier("batchEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }

    @Bean
    BatchDataSourceScriptDatabaseInitializer scriptDatabaseInitializer(
            @Qualifier("batchDataSource") DataSource dataSource,
            @BatchDataSource ObjectProvider<DataSource> batchDataSource,
            BatchProperties properties) {
        return new BatchDataSourceScriptDatabaseInitializer(batchDataSource.getIfAvailable(() -> dataSource), properties.getJdbc());
    }
}
