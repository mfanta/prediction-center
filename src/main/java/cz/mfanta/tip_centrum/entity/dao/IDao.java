package cz.mfanta.tip_centrum.entity.dao;

import java.io.Serializable;
import java.util.List;

public interface IDao<T extends Serializable> {

    void save(T obj);

    void update(T obj);

    T getById(Object id);

    void delete(Object id);

    List<T> getAll();
}
