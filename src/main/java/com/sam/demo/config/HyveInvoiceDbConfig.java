package com.sam.demo.config;

import com.synnex.hyve.invoice.service.dao.mysql.repository.InvoiceBaseRepositoryImpl;
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
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
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
@ConditionalOnJndi
@EnableJpaRepositories(basePackages = {"com.synnex.hyve.invoice.service.dao.mysql.repository.invoice"},
                       entityManagerFactoryRef = "invoiceEntityManagerFactory",
                       transactionManagerRef = "invoiceTransactionManager",
                       repositoryBaseClass = InvoiceBaseRepositoryImpl.class)
public class HyveInvoiceDbConfig {
    /**
     * Plm entity manager factory local container entity manager factory bean.
     *
     * @param invoiceDataSource the invoice data source
     * @param hibernateConfig   the hibernate config
     * @return the local container entity manager factory bean
     */
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean invoiceEntityManagerFactory(@Autowired @Qualifier("invoiceDataSource") DataSource invoiceDataSource,
                                                                              @Autowired HibernateConfig hibernateConfig) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(invoiceDataSource);
        em.setPersistenceUnitName("invoicePersistentUnit");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        em.setJpaVendorAdapter(vendorAdapter);
        em.setPackagesToScan("com.synnex.hyve.invoice.service.dao.mysql.entity.invoice");
        Properties properties = new Properties();
        properties.setProperty("hibernate.jdbc.batch_size", hibernateConfig.getBatchValueSize());
        properties.setProperty("hibernate.order_inserts", hibernateConfig.getOrderInserts());
        properties.setProperty("hibernate.order_updates", hibernateConfig.getOrderUpdates());
        properties.setProperty("hibernate.jdbc.batch_versioned_data", hibernateConfig.getBatchVersionData());
        em.setJpaProperties(properties);
        return em;
    }

    /**
     * Plm data source data source.
     *
     * @param dataSourceProperties the data source properties
     * @return the data source
     */
    @Primary
    @Bean(name = "invoiceDataSource")
    @ConditionalOnProperty(prefix = "spring.datasource.invoice", name = "driverClassName")
    @ConfigurationProperties(prefix = "spring.datasource.invoice")
    public DataSource invoiceDataSource(@Autowired @Qualifier("invoiceDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return DataSourceBuilder.create().driverClassName(dataSourceProperties.getDriverClassName())
                                .url(dataSourceProperties.getUrl())
                                .username(dataSourceProperties.getUsername())
                                .password(dataSourceProperties.getPassword()).build();
    }

    /**
     * Plm data source properties data source properties.
     *
     * @return the data source properties
     */
    @Primary
    @Bean(name = "invoiceDataSourceProperties")
    @ConfigurationProperties("spring.datasource.invoice")
    public DataSourceProperties invoiceDataSourceProperties() {
        return new DataSourceProperties();
    }


    /**
     * Hyve jndi data source data source.
     *
     * @param invoiceDataSourceProperties the invoice data source properties
     * @return the data source
     * @throws NamingException the naming exception
     */
    @Primary
    @Bean(name = "invoiceDataSource")
    @ConditionalOnProperty(prefix = "spring.datasource.invoice", name = "jndi-name")
    public DataSource hyveJndiDataSource(@Autowired @Qualifier("invoiceDataSourceProperties") DataSourceProperties invoiceDataSourceProperties) {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        DataSource dataSource = dataSourceLookup.getDataSource(invoiceDataSourceProperties.getJndiName());
        return dataSource;
    }

    /**
     * invoice transaction manager platform transaction manager.
     *
     * @param invoiceEntityManagerFactory the invoice entity manager factory
     * @return the platform transaction manager
     */
    @Primary
    @Bean
    public PlatformTransactionManager invoiceTransactionManager(
        @Autowired @Qualifier("invoiceEntityManagerFactory") EntityManagerFactory invoiceEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(invoiceEntityManagerFactory);
        return transactionManager;
    }

}