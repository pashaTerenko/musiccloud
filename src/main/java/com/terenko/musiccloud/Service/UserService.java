package com.terenko.musiccloud.Service;

import com.terenko.musiccloud.DTO.AccountDTO;
import com.terenko.musiccloud.Repository.UserRepository;
import com.terenko.musiccloud.model.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void addAccount(AccountDTO dto) {
        userRepository.save(CustomUser.fromDTO(dto));
    }


    @Transactional(readOnly = true)
    public CustomUser getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }


    @Transactional(readOnly = true)
    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }


    @Transactional
    public void addUser(CustomUser customUser) {
        userRepository.save(customUser);
    }
}
