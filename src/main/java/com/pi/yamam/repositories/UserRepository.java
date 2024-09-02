package com.pi.yamam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.pi.yamam.domain.user.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long>{
    UserDetails findByEmail(String email);
}
