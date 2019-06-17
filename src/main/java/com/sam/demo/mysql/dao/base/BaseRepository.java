package com.sam.demo.mysql.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * The interface Base repository.
 *
 * @param <T> the type parameter
 * @param <D> the type parameter
 */
@NoRepositoryBean
public interface BaseRepository<T, D extends Serializable> extends JpaRepository<T, D>, JpaSpecificationExecutor<T> {

}
