package com.example.whywhiteee.Repositories;

import com.example.whywhiteee.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}