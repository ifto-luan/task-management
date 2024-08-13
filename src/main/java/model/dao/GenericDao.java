package model.dao;

import java.util.List;

public interface GenericDao<T> {

	T getById(int id);
	List<T> getAll();
	void insert(T reg);
	void update(T reg);
	void delete(T reg);
	
}
