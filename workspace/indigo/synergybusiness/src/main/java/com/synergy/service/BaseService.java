package com.synergy.service;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

public interface BaseService<T> extends Serializable {
	@Transactional
	void save(T t);

	@Transactional
	void delete(T t);

	T find(Long id);

}
