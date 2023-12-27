package hexlet.code.service;


import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.dto.user.UserDTO;
import hexlet.code.dto.user.UserUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.repository.UserRepository;
import hexlet.code.utils.UserUtils;
import hexlet.code.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserUtils userUtils;

    public UserDTO create(UserCreateDTO userData) {
        var user = userMapper.map(userData);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userMapper.map(user);
    }

    public List<UserDTO> getAll() {
        var users = userRepository.findAll();
        return users.stream()
                .map(p -> userMapper.map(p))
                .toList();
    }

    public UserDTO findById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id %s not found", id)));
        return userMapper.map(user);
    }

    public UserDTO update(UserUpdateDTO userData, Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id %s not found", id)));

        var userDataEmail = userData.getEmail();
        if (userDataEmail != null) {
            var findUser = userRepository.findByEmail(userDataEmail);
            if (findUser.isPresent() && id != user.getId()) {
                throw new ConstraintViolationException(
                        String.format("User with email %s already exists", userDataEmail));
            }
        }
        userMapper.update(userData, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userMapper.map(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}
