package com.sam.demo.config;


import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.sam.demo.mysql.dao.base.PersistBaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

/**
 * The type local data source configuration.
 */
@Configuration
@EnableJpaRepositories(basePackages = {"com.sam.demo.mysql.dao.primary"},
                       entityManagerFactoryRef = "localEntityManagerFactory",
                       transactionManagerRef = "localTransactionManager",
                       repositoryBaseClass = PersistBaseRepositoryImpl.class)
public class DbConfigOriginal {

    /**
     * manager factory local container entity manager factory bean.
     *
     * @param localDataSource      the invoice data source
     * @param hibernateConfig the hibernate config
     * @return the local container entity manager factory bean
     */
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean localEntityManagerFactory(@Autowired @Qualifier("localDataSource") DataSource localDataSource,
                                                                            @Autowired HibernateConfig hibernateConfig) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(localDataSource);
        em.setPersistenceUnitName("localPersistentUnit");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        em.setJpaVendorAdapter(vendorAdapter);
        em.setPackagesToScan("com.sam.demo.mysql.entity.primary");
        Properties properties = new Properties();
        properties.setProperty("hibernate.jdbc.batch_size", hibernateConfig.getBatchValueSize());
        properties.setProperty("hibernate.order_inserts", hibernateConfig.getOrderInserts());
        properties.setProperty("hibernate.order_updates", hibernateConfig.getOrderUpdates());
        properties.setProperty("hibernate.jdbc.batch_versioned_data", hibernateConfig.getBatchVersionData());
        //自动建表
        // properties.setProperty("hibernate.hbm2ddl.auto", "update");
        em.setJpaProperties(properties);
        return em;
    }

    /**
     * local data source data source.
     *
     * @return the data source
     */
    @Primary
    @Bean(name = "localDataSource")
    // @ConditionalOnProperty(prefix = "spring.datasource", name = "driverClassName")
    // @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource localDataSource(@Autowired @Qualifier("localDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return DataSourceBuilder.create()
                                .driverClassName(dataSourceProperties.getDriverClassName())
                                .url(dataSourceProperties.getUrl())
                                .username(dataSourceProperties.getUsername())
                                .password(dataSourceProperties.getPassword()).build();
    }

    /**
     * local data source properties data source properties.
     *
     * @return the data source properties
     */
    @Primary
    @Bean(name = "localDataSourceProperties")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties localDataSourceProperties() {
        return new DataSourceProperties();
    }


    /**
     * Hyve transaction manager platform transaction manager.
     *
     * @param localEntityManagerFactory the hyve entity manager factory
     * @return the platform transaction manager
     */
    @Primary
    @Bean
    public PlatformTransactionManager localTransactionManager(
        @Autowired @Qualifier("localEntityManagerFactory") EntityManagerFactory localEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(localEntityManagerFactory);
        return transactionManager;
    }

    @Primary
    @Bean(name = "localEntityManager")
    public EntityManager entityManager(@Qualifier("localEntityManagerFactory") EntityManagerFactory localEntityManagerFactory) {
        return localEntityManagerFactory.createEntityManager();
    }

}