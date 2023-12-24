package hexlet.code.component;

import hexlet.code.mapper.UserMapper;
import hexlet.code.model.Label;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
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
    private final TaskStatusRepository taskStatusRepository;

    @Autowired
    private final LabelRepository labelRepository;

    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private final UserService userService;


    @Override
    public void run(ApplicationArguments args) throws Exception {

      //  if (userRepository.findByEmail("hexlet@example.com").isEmpty()) {
            var email = "hexlet@example.com";
            var userData = new User();
            userData.setEmail(email);
            userData.setPassword(passwordEncoder.encode("qwerty"));
            userRepository.save(userData);

            generatedTaskStatus("Draft", "draft");
            generatedTaskStatus("ToReview", "to_review");
            generatedTaskStatus("ToBeFixed", "to_be_fixed");
            generatedTaskStatus("ToPublish", "to_publish");
            generatedTaskStatus("Published", "published");

            generateLabels("feature");
            generateLabels("bug");

       // }

        /*
        var email = "hexlet@example.com";
        var userData = new User();
        userData.setEmail(email);
        userData.setPassword(passwordEncoder.encode("qwerty"));
        userRepository.save(userData);

        generatedTaskStatus("Draft", "draft");
        generatedTaskStatus("ToReview", "to_review");
        generatedTaskStatus("ToBeFixed", "to_be_fixed");
        generatedTaskStatus("ToPublish", "to_publish");
        generatedTaskStatus("Published", "published");

        generateLabels("feature");
        generateLabels("bug");

         */

    }

    private void generateLabels(String name) {
        var label = new Label();
        label.setName(name);
        labelRepository.save(label);

    }

    private void generatedTaskStatus(String name, String slug) {
        var taskStatus = new TaskStatus();
        taskStatus.setName(name);
        taskStatus.setSlug(slug);
        taskStatusRepository.save(taskStatus);
    }
}