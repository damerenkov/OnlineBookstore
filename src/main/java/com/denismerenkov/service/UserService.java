package com.denismerenkov.service;



import com.denismerenkov.model.user.User;

import java.util.List;

public interface UserService {

    void register(User user);

    List<User> getAll();

    User findByUsername(String username);
    User findByHash(String hash);

    User findById(Long id);

    User update(User user);

    User delete(Long id);
}
