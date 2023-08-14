package com.sahil.blog.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sahil.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
Optional<User>findByEmail(String email);

Optional<User> findByEmailAndPassword(String email,String password);
List <User> findBynameContaining(String keywords);

}
