package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.util.ModelGenerator;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;




@SpringBootTest
@AutoConfigureMockMvc
public class TaskStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskStatusRepository taskStatusRepository;


    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskStatusMapper mapper;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private Faker faker;

    private TaskStatus testTaskStatus;

    @Value("/api/task_statuses")
    @Autowired
    private String url;

    @BeforeEach
    public void setUp() {
        testTaskStatus = Instancio.of(modelGenerator.getTaskStatusModel()).create();
    }

    @Test
    public void testIndex() throws Exception {

        taskStatusRepository.save(testTaskStatus);

        var result = mockMvc.perform(get(url).with(jwt()))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    @Test
    public void testShow() throws Exception {

        taskStatusRepository.save(testTaskStatus);

        var request = get(url + "/{id}", testTaskStatus.getId()).with(jwt());
        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        var body = result.getResponse().getContentAsString();

        assertThatJson(body).and(
                v -> v.node("name").isEqualTo(testTaskStatus.getName()),
                v -> v.node("slug").isEqualTo(testTaskStatus.getSlug())
        );
    }

    @Test
    public void testCreate() throws Exception {

        var dto = mapper.mapToCreateDTO(testTaskStatus);

        var request = post(url).with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));

        mockMvc.perform(request)
                .andExpect(status().isCreated());

        var taskStatus = taskStatusRepository.findBySlug(
                testTaskStatus.getSlug()).orElseThrow();

        assertThat(taskStatus).isNotNull();
        assertThat(taskStatus.getName()).isEqualTo(testTaskStatus.getName());
        assertThat(taskStatus.getSlug()).isEqualTo(testTaskStatus.getSlug());
    }

    @Test
    public void testUpdate() throws Exception {
        taskStatusRepository.save(testTaskStatus);

        var newTaskStatusModel = Instancio.of(modelGenerator.getTaskStatusModel()).create();
        var dto = mapper.mapToCreateDTO(newTaskStatusModel);

        var request = put(url + "/{id}", testTaskStatus.getId()).with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var taskStatus = taskStatusRepository.findById(
                testTaskStatus.getId()).orElseThrow();

        assertThat(taskStatus).isNotNull();
        assertThat(taskStatus.getName()).isEqualTo(dto.getName());
        assertThat(taskStatus.getSlug()).isEqualTo(dto.getSlug());
    }

    @Test
    public void testPartialUpdate() throws Exception {

        taskStatusRepository.save(testTaskStatus);

        testTaskStatus.setSlug(faker.internet().slug());
        var dto = mapper.mapToCreateDTO(testTaskStatus);

        var request = put(url + "/{id}", testTaskStatus.getId()).with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var taskStatus = taskStatusRepository.findById(
                testTaskStatus.getId()).orElseThrow();

        assertThat(taskStatus).isNotNull();
        assertThat(taskStatus.getName()).isEqualTo(testTaskStatus.getName());
        assertThat(taskStatus.getSlug()).isEqualTo(dto.getSlug());
    }

    @Test
    public void testDelete() throws Exception {

        taskStatusRepository.save(testTaskStatus);

        var request = delete(url + "/{id}", testTaskStatus.getId()).with(jwt());
        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        var taskStatus = taskStatusRepository.findById(
                testTaskStatus.getId()).orElse(null);

        assertThat(taskStatus).isNull();
    }


}
