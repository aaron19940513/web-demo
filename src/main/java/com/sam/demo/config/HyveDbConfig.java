package com.sam.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * The type Hyve data source configuration.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.synnex.hyve.invoice.service.dao.mysql.repository.hyve",
                       entityManagerFactoryRef = "hyveEntityManagerFactory",
                       transactionManagerRef = "hyveTransactionManager")
public class HyveDbConfig {
    /**
     * get EntityManagerFactory.
     *
     * @param hyveDataSource the hyve data source
     * @return the local container entity manager factory bean
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean hyveEntityManagerFactory(@Autowired @Qualifier("hyveDataSource") DataSource hyveDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(hyveDataSource);
        em.setPersistenceUnitName("hyvePersistentUnit");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        em.setJpaVendorAdapter(vendorAdapter);
        em.setPackagesToScan("com.synnex.hyve.invoice.service.dao.mysql.entity.hyve");
        return em;
    }

    /**
     * Property placeholder configurer property placeholder configurer.
     *
     * @return the property placeholder configurer
     */
    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertyPlaceholderConfigurer();
    }


    /**
     * Hyve data source data source.
     *
     * @param dataSourceProperties the data source properties
     * @return the data source
     */
    @Bean(name = "hyveDataSource")
    @ConditionalOnProperty(prefix = "spring.datasource.hyve", name = "driverClassName")
    @ConfigurationProperties(prefix = "spring.datasource.hyve")
    public DataSource hyveDataSource(@Autowired @Qualifier("hyveDataSourceProperties") DataSourceProperties dataSourceProperties) {

        return DataSourceBuilder.create()
                                .driverClassName(dataSourceProperties.getDriverClassName())
                                .url(dataSourceProperties.getUrl())
                                .username(dataSourceProperties.getUsername())
                                .password(dataSourceProperties.getPassword()).build();
    }

    /**
     * Hyve data source properties data source properties.
     *
     * @return the data source properties
     */
    @Bean(name = "hyveDataSourceProperties")
    @ConfigurationProperties("spring.datasource.hyve")
    public DataSourceProperties hyveDataSourceProperties() {
        return new DataSourceProperties();
    }


    /**
     * Hyve jndi data source data source.
     *
     * @param hyveDataSourceProperties the hyve data source properties
     * @return the data source
     * @throws NamingException the naming exception
     */
    @Bean(name = "hyveDataSource")
    @ConditionalOnProperty(prefix = "spring.datasource.hyve", name = "jndi-name")
    public DataSource hyveJndiDataSource(@Autowired @Qualifier("hyveDataSourceProperties") DataSourceProperties hyveDataSourceProperties) {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        DataSource dataSource = dataSourceLookup.getDataSource(hyveDataSourceProperties.getJndiName());
        return dataSource;
    }

    /**
     * Hyve transaction manager platform transaction manager.
     *
     * @param hyveEntityManagerFactory the hyve entity manager factory
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager hyveTransactionManager(@Autowired @Qualifier("hyveEntityManagerFactory") EntityManagerFactory hyveEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(hyveEntityManagerFactory);
        return transactionManager;
    }

    /**
     * Entity manager entity manager.
     *
     * @param hyveEntityManagerFactory the hyve entity manager factory
     * @return the entity manager
     */
    @Bean(name = "hyveEntityManager")
    public EntityManager entityManager(@Qualifier("hyveEntityManagerFactory") EntityManagerFactory hyveEntityManagerFactory) {
        return hyveEntityManagerFactory.createEntityManager();
    }
}