package com.sam.demo.config;


import com.sam.demo.mysql.dao.base.PersistBaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJndi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * The type Hyve invoice data source configuration.
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.sam.demo.mysql"},
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager",
        repositoryBaseClass = PersistBaseRepositoryImpl.class)
public class DbConfig {


    /**
     * Plm data source properties data source properties.
     *
     * @return the data source properties
     */
    @Primary
    @Bean(name = "dataSourceProperties")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }


    /**
     * data source data source.
     *
     * @param dataSourceProperties the invoice data source properties
     * @return the data source
     * @throws NamingException the naming exception
     */
    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource(@Autowired @Qualifier("dataSourceProperties") DataSourceProperties dataSourceProperties) {
        //dataSourceLookup = new JndiDataSourceLookup();
        //DataSource dataSource = dataSourceLookup.getDataSource(invoiceDataSourceProperties.getJndiName());
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName(dataSourceProperties.getDriverClassName())
                .url(dataSourceProperties.getUrl())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword()).build();
        return dataSource;
    }

    /**
     * Plm entity manager factory local container entity manager factory bean.
     *
     * @param dataSource      the invoice data source
     * @param hibernateConfig the hibernate config
     * @return the local container entity manager factory bean
     */
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired @Qualifier("dataSource") DataSource dataSource,
                                                                       @Autowired HibernateConfig hibernateConfig) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPersistenceUnitName("persistentUnit");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        em.setJpaVendorAdapter(vendorAdapter);
        em.setPackagesToScan("com.sam.demo.mysql.entity");
        Properties properties = new Properties();
        properties.setProperty("hibernate.jdbc.batch_size", hibernateConfig.getBatchValueSize());
        properties.setProperty("hibernate.order_inserts", hibernateConfig.getOrderInserts());
        properties.setProperty("hibernate.order_updates", hibernateConfig.getOrderUpdates());
        properties.setProperty("hibernate.jdbc.batch_versioned_data", hibernateConfig.getBatchVersionData());
        em.setJpaProperties(properties);
        return em;
    }

    /**
     * invoice transaction manager platform transaction manager.
     *
     * @param entityManagerFactory the invoice entity manager factory
     * @return the platform transaction manager
     */
    @Primary
    @Bean
    public PlatformTransactionManager transactionManager(
            @Autowired @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

}