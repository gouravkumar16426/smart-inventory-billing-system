package interfaces;

import java.util.List;

public interface IService<T> {
    void add(T item);
    void update(T item);
    void delete(int id);
    List<T> viewAll();
    T search(int id);
}
