package com.zmater.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zmater.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>
{

  public User findByPhoneNumber(String phoneNumber);

}
