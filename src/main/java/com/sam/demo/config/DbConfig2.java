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
//@Configuration
// @EnableJpaRepositories(basePackages = {"com.sam.demo.mysql.dao.secondary"},
//                        entityManagerFactoryRef = "secondaryEntityManagerFactory",
//                        transactionManagerRef = "secondaryTransactionManager",
//                        repositoryBaseClass = PersistBaseRepositoryImpl.class)
public class DbConfig2 {

    /**
     * manager factory local container entity manager factory bean.
     *
     * @param dataSource      the invoice data source
     * @param hibernateConfig the hibernate config
     * @return the local container entity manager factory bean
     */
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(@Autowired @Qualifier("secondaryDataSource") DataSource dataSource,
                                                                                @Autowired HibernateConfig hibernateConfig) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPersistenceUnitName("secondaryPersistentUnit");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        em.setJpaVendorAdapter(vendorAdapter);
        em.setPackagesToScan("com.sam.demo.mysql.entity.secondary");
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

    @Bean(name = "secondaryDataSource")
    @ConditionalOnProperty(prefix = "spring.datasource.secondary", name = "driverClassName")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondaryDataSource(@Autowired @Qualifier("secondaryDataSourceProperties") DataSourceProperties dataSourceProperties) {
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
    @Bean(name = "secondaryDataSourceProperties")
    @ConfigurationProperties("spring.datasource.secondary")
    public DataSourceProperties secondaryDataSourceProperties() {
        return new DataSourceProperties();
    }


    /**
     * secondary transaction manager platform transaction manager.
     *
     * @param secondaryEntityManagerFactory the hyve entity manager factory
     * @return the platform transaction manager
     */

    @Bean
    public PlatformTransactionManager secondaryTransactionManager(
        @Autowired @Qualifier("secondaryEntityManagerFactory") EntityManagerFactory secondaryEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(secondaryEntityManagerFactory);
        return transactionManager;
    }

    @Bean(name = "secondaryEntityManager")
    public EntityManager entityManager(@Qualifier("secondaryEntityManagerFactory") EntityManagerFactory secondaryEntityManagerFactory) {
        return secondaryEntityManagerFactory.createEntityManager();
    }

}