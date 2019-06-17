package com.sam.demo.mysql.dao.base;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * The type Invoice base repository.
 *
 * @param <T> the type parameter
 * @param <K> the type parameter
 */
//@NoRepositoryBean
public class PersistBaseRepositoryImpl<T, K extends Serializable> extends SimpleJpaRepository<T, K> implements PersistBaseRepository<T, K> {
    private final EntityManager em;
    private final JpaEntityInformation<T, ?> entityInformation;
    private static final Integer BATCH_FLUSH_SIZE = 200;


    /**
     * Instantiates a new Price base repository.
     *
     * @param entityInformation the entity information
     * @param em                the em
     */
    public PersistBaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.entityInformation = entityInformation;
        this.em = em;
    }

    @Override
    @Transactional
    public <S extends T> void persist(S entity) {
        em.persist(entity);
    }

    @Override
    @Transactional
    public <S extends T> void persistAll(List<S> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }
        for (int i = 0; i < entities.size(); i++) {
            S entity = entities.get(i);
            em.persist(entity);
            if (i % BATCH_FLUSH_SIZE == 0) {
                em.flush();
                em.clear();
            }
        }
        em.flush();
        em.clear();
    }
}
