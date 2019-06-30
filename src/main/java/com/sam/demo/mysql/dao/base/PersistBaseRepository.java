package com.sam.demo.mysql.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * The interface Base repository.
 *
 * @param <T> the type parameter
 * @param <D> the type parameter
 */
@NoRepositoryBean
public interface PersistBaseRepository<T, D extends Serializable> extends JpaRepository<T, D>, JpaSpecificationExecutor<T> {
    <S extends T> void  persist(S entity);
    <S extends T> void  persistAll(List<S> entities);
    <S extends T> void  merge(S entity);
    <S extends T> void  mergeAll(List<S> entities);
}
