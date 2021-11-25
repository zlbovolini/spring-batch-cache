package com.github.lbovolini.app.estabelecimento.configuration.database;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
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
        basePackages = "com.github.lbovolini.app.estabelecimento.batch",
        entityManagerFactoryRef = "estabelecimentoLegadoEntityManagerFactory",
        transactionManagerRef= "estabelecimentoLegadoTransactionManager")
public class LegadoDatasourceConfiguration {

    @Bean("estabelecimentoLegadoDataSourceProperties")
    @ConfigurationProperties("estabelecimento-legado.datasource")
    DataSourceProperties estabelecimentoLegadoDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("estabelecimentoLegadoDataSource")
    @ConfigurationProperties("estabelecimento-legado.datasource.configuration")
    DataSource estabelecimentoLegadoDataSource() {
        return estabelecimentoLegadoDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean("estabelecimentoLegadoEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean estabelecimentoLegadoEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        Map<String, String> properties = Map.of("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");

        return builder.dataSource(estabelecimentoLegadoDataSource())
                .packages("com.github.lbovolini.app.estabelecimento.batch")
                .persistenceUnit("legado")
                .properties(properties)
                .build();
    }

    @Bean("estabelecimentoLegadoTransactionManager")
    PlatformTransactionManager estabelecimentoLegadoTransactionManager(
            @Qualifier("estabelecimentoLegadoEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }
}
