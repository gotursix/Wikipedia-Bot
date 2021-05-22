package dao;

import java.util.List;

public interface Dao<T> {
    T get(String id);

    List<T> getAll(int n);

    void save(T t);

    void update(T t, String[] params);

    void delete(T t);
}
