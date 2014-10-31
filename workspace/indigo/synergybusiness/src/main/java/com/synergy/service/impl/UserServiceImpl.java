package com.synergy.service.impl;

import com.synergy.dao.UserDao;
import com.synergy.model.Group;
import com.synergy.model.User;
import com.synergy.service.UserService;

public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public User find(Long id) {
		return userDao.find(id);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(final UserDao dao) {
		this.userDao = dao;
	}

	public void delete(Group t) {
	}

	public void delete(User t) {
		// TODO Auto-generated method stub

	}

	public void save(User t) {
		// TODO Auto-generated method stub

	}

}
