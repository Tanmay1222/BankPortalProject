package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entities.Users;
@Repository
public interface UsersRepository extends JpaRepository<Users, String>{

}
