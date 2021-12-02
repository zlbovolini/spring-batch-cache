package com.github.lbovolini.app.estabelecimento.batch;

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
        basePackageClasses = { NovoEstabelecimento.class },
        entityManagerFactoryRef = "estabelecimentoLegadoEntityManagerFactory",
        transactionManagerRef= "estabelecimentoLegadoTransactionManager")
class LegadoDatasourceConfiguration {

    @Bean("estabelecimentoLegadoDataSourceProperties")
    @ConfigurationProperties("estabelecimento-legado.datasource")
    DataSourceProperties estabelecimentoLegadoDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("estabelecimentoLegadoDataSource")
    DataSource estabelecimentoLegadoDataSource(@Qualifier("estabelecimentoLegadoDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean("estabelecimentoLegadoEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                @Qualifier("estabelecimentoLegadoDataSource") DataSource dataSource) {
        Map<String, String> properties = Map.of("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");

        return builder.dataSource(dataSource)
                .packages(NovoEstabelecimento.class)
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
