package com.sam.demo.config;

import com.sam.demo.mysql.dao.base.BaseRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.sam.demo.mysql",repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class JpaDataConfig {

}