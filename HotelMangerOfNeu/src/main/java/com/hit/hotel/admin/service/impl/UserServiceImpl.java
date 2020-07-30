package com.hit.hotel.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hit.hotel.admin.mapper.IUserMapper;
import com.hit.hotel.admin.model.UserModel;
import com.hit.hotel.admin.service.IUserService;

//管理员业务实现类，使用Spring集成MyBatis方式实现
@Service
@Transactional //环绕事务Advice的切入点
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserMapper userMapper = null;

	@Override
	public UserModel getById(String id) throws Exception {
		return userMapper.getById(id);
	}

	@Override
	public boolean validate(String id, String password) throws Exception {
		UserModel um = userMapper.getById(id);
		if (um != null && um.getPassword() != null && um.getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void changePassword(String id, String password) throws Exception {
		UserModel um = userMapper.getById(id);
		um.setPassword(password);
		userMapper.update(um);
	}

	@Override
	public void add(UserModel um) throws Exception {
		// TODO Auto-generated method stub
		userMapper.insert(um);
	}

}
