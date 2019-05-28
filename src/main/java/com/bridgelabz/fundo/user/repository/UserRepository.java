package com.bridgelabz.fundo.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundo.user.model.User;
@Service
public interface UserRepository extends JpaRepository<User,Long>
{
	public Optional<User> findByEmailId(String emailId);
	//public User findByEmail(String emailId);
	public Optional<User> findByUserId(long id);
}
