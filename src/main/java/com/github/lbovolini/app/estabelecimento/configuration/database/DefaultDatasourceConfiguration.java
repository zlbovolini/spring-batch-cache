package com.github.lbovolini.app.estabelecimento.configuration.database;

import com.github.lbovolini.app.estabelecimento.compartilhado.Estabelecimento;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
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
        basePackages = "com.github.lbovolini.app.estabelecimento",
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.github.lbovolini.app.estabelecimento.batch.*"),
        entityManagerFactoryRef = "estabelecimentoEntityManagerFactory",
        transactionManagerRef= "estabelecimentoTransactionManager")
class DefaultDatasourceConfiguration {

    @Primary
    @Bean("estabelecimentoDataSourceProperties")
    @ConfigurationProperties("estabelecimento.datasource")
    DataSourceProperties estabelecimentoDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean("estabelecimentoDataSource")
    DataSource estabelecimentoDataSource(@Qualifier("estabelecimentoDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean("estabelecimentoEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                @Qualifier("estabelecimentoDataSource") DataSource dataSource) {
        Map<String, String> properties = Map.of("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");

        return builder.dataSource(dataSource)
                .packages(Estabelecimento.class)
                .persistenceUnit("estabelecimento")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean("estabelecimentoTransactionManager")
    PlatformTransactionManager estabelecimentoTransactionManager(
            @Qualifier("estabelecimentoEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }

}
