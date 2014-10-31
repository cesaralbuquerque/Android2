package com.synergy.dao;

public interface BaseDao<T> {
	T find(Long id);

	void save(T t);

	void delete(T t);
	
	void merge(T t);
	
	void refresh(T t);
}
