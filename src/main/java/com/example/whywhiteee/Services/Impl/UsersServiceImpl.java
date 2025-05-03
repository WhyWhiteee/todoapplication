package com.example.whywhiteee.Services.Impl;

import com.example.whywhiteee.Models.Users;
import com.example.whywhiteee.Repositories.UsersRepository;
import com.example.whywhiteee.Services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }
}
