package com.clphub.myapp.service;

import com.clphub.myapp.model.Role;
import com.clphub.myapp.model.User;
import com.clphub.myapp.repository.RoleRepository;
import com.clphub.myapp.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String username) {
        return userRepository.findByUserName(username);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        List<User> result = (List<User>) userRepository.findAll();

        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<User>();
        }
    }

    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }

    public User createOrUpdateUser(User user) {
        if (user.getId() == 0) {
            user = userRepository.save(user);
            return user;
        } else {
            Optional<User> userUpdate = userRepository.findById(user.getId());

            if (userUpdate.isPresent()) {
                User newUser = userUpdate.get();
                newUser.setFirstName(user.getFirstName());
                newUser.setLastName(user.getLastName());
                newUser.setMiddleName(user.getMiddleName());
                newUser.setAddress(user.getAddress());
                newUser.setBirthDate(user.getBirthDate());
                newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                newUser.setPid(user.getPid());
                newUser.setUserName(user.getUserName());


                newUser = userRepository.save(newUser);

                return newUser;
            } else {
                user = userRepository.save(user);
                return user;
            }
        }
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
