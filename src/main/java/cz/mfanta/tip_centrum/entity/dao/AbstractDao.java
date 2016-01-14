package cz.mfanta.tip_centrum.entity.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractDao<T extends Serializable> implements IDao<T> {

	private final EntityManager entityManager;

    private Class<T> clazz;

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(T obj) {
        entityManager.persist(obj);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(T obj) {
        entityManager.merge(obj);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public T getById(Object id) {
        return entityManager.find(clazz, id);
    }

	@Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void delete(Object id) {
		final T obj = entityManager.find(clazz, id);
		entityManager.remove(obj);
	}

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<T> getAll() {
        return executeQuery("from " + clazz.getName());
    }

	protected List<T> executeQuery(String query) {
        //noinspection unchecked
        return entityManager.createQuery(query).getResultList();
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
