package com.adnan.user.service.service;

import com.adnan.user.service.exception.UserException;
import com.adnan.user.service.modal.User;
import com.adnan.user.service.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class userService {

    @Autowired
    private userRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User modifyUser(Long id, User userDetails) throws UserException {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserException("User not found with id: ");
        } else {
            User existingUser = user.get();
            if (userDetails.getFullName() != null) {
                existingUser.setFullName(userDetails.getFullName());
            }
            if (userDetails.getUsername() != null) {
                existingUser.setUsername(userDetails.getUsername());
            }
            if (userDetails.getEmail() != null) {
                existingUser.setEmail(userDetails.getEmail());
            }
            if (userDetails.getPhone() != null) {
                existingUser.setPhone(userDetails.getPhone());
            }
            if (userDetails.getPassword() != null) {
                existingUser.setPassword(userDetails.getPassword());
            }
            return userRepository.save(existingUser);
        }

    }

    public User findUserById(Long id) throws UserException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserException("User not found with id: " + id);
        }
        return user.get();
    }

    public String deleteUserById(Long id){
        try{
            Optional<User> user = userRepository.findById(id);
            if(user.isEmpty()){
                return "User not found with id: " + id;
            }
            userRepository.deleteById(id);
            return "User deleted with id: " + id;
        } catch(Exception e){
            return "Error deleting user with id: " + id + ". Error: " + e.getMessage();
        }
    }
}
