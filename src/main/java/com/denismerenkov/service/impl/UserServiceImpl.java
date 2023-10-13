package com.denismerenkov.service.impl;


import com.denismerenkov.model.user.Role;
import com.denismerenkov.model.user.User;
import com.denismerenkov.repository.RoleRepository;
import com.denismerenkov.repository.UserRepository;
import com.denismerenkov.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            List<Role> roles = roleRepository.findByNameIn(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
            if(roles.isEmpty()){
                throw new IllegalArgumentException("Roles does not exists");
            }
            user.setRoles(roles);

            userRepository.save(user);

            log.info("IN register - user: {} successfully registered", user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("User is already exists");
        }
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUserName(username)
                .orElseThrow(()->new UsernameNotFoundException("User does not exists"));
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findByHash(String hash) {
        User result = userRepository.findByHash(hash)
                .orElseThrow(()->new UsernameNotFoundException("User does not exists"));
        log.info("IN findByHash - user: {} found by hash: {}", result, hash);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository
                .findById(id).orElseThrow(
                        ()->new IllegalArgumentException("User does not exists"));

        //log.warn("IN findById - no user found by id: {}", id);
        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public User update(User user) {
        User base = this.findById(user.getId());
        base.setUserName(user.getUserName());
        base.setPassword(user.getPassword());
        base.setFirstName(user.getFirstName());
        base.setLastName(user.getLastName());
        base.setEmail(user.getEmail());
        base.setHash(user.getHash());
        base.setRoles(user.getRoles());
        log.info("IN update - user with id: {} successfully updated", base.getId());
        return this.userRepository.save(base);
    }

    @Override
    public User delete(Long id) {
        User user = this.findById(id);
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);
        return user;
    }
}
