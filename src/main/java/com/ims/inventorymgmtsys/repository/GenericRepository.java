package com.ims.inventorymgmtsys.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    List<T> selectAll();
    T selectById(ID id);
    void insert(T entity);
    void update(T entity);
    void deleteById(ID id);

}
