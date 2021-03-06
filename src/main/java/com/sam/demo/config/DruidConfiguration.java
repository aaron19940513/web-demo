package com.sam.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

// @Configuration
// @ConditionalOnClass({DruidDataSource.class})
// @ConditionalOnProperty(
//         name = {"spring.datasource.type"},
//         havingValue = "com.alibaba.druid.pool.DruidDataSource",
//         matchIfMissing = true
// )
// @EnableConfigurationProperties(DruidDataSourceProperties.class)
public class DruidConfiguration {

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

    @Primary
    @Bean(name = "dataSource")
    public DruidDataSource dataSource(DataSourceProperties dataSourceProperties, DruidDataSourceProperties druidDataSourceProperties) {
        DruidDataSource druidDataSource = dataSourceProperties.initializeDataSourceBuilder().type(DruidDataSource.class).build();
        druidDataSource.configFromPropety(druidDataSourceProperties.toProperties());
        druidDataSource.setInitialSize(druidDataSourceProperties.getInitialSize());
        druidDataSource.setMinIdle(druidDataSourceProperties.getMinIdle());
        druidDataSource.setMaxActive(druidDataSourceProperties.getMaxActive());
        druidDataSource.setMaxWait(druidDataSourceProperties.getMaxWait());
        druidDataSource.setConnectProperties(druidDataSourceProperties.getConnectionProperties());
        return druidDataSource;
    }

}

