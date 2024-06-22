package com.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dto.UserDTO;
import com.project.entities.Users;
import com.project.repositories.UsersRepository;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UsersRepository userRepository; 

		@Override
		public UserDTO logIn(String userName, String password) {
			List<Users> users= listUsers();
					UserDTO userModel=new UserDTO();
					for(Users user:users) {
						if(user.getUserName().equals(userName) && user.getPassword().equals(password)) {
							userModel.setUserName(user.getUserName());
							userModel.setPassword(user.getPassword());
							userModel.setRole(user.getRole());
							break;
						}
					}
					return userModel;		}

		@Override
		public List<Users> listUsers() {
			return (List<Users>) userRepository.findAll();
		}

		
	 
	
	 
	

}
