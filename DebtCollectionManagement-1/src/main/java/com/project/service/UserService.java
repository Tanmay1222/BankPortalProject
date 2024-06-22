package com.project.service;

import java.util.List;

import com.project.dto.UserDTO;
import com.project.entities.Users;

public interface UserService {
	public List<Users> listUsers();
	public UserDTO logIn(String userName, String password);
	

}
