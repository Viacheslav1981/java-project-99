package hexlet.code.component;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private final UserService userService;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        var email = "hexlet@example.com";
        var userData = new User();
        userData.setEmail(email);
        userData.setPassword(passwordEncoder.encode("qwerty"));
        userRepository.save(userData);


    }
}