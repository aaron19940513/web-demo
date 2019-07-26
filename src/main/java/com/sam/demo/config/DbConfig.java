package com.sam.demo.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.sam.demo.mysql.dao.base.PersistBaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
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
     * Plm entity manager factory local container entity manager factory bean.
     *
     * @param dataSource      the invoice data source
     * @param hibernateConfig the hibernate config
     * @return the local container entity manager factory bean
     */
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired DruidDataSource dataSource,
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