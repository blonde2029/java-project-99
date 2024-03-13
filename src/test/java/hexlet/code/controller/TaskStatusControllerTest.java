package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.taskStatus.TaskStatusUpdateDTO;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.util.ModelGenerator;
import hexlet.code.util.UserUtils;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskStatusControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskStatusMapper taskStatusMapper;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private UserUtils userUtils;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;

    private TaskStatus testTaskStatus;

    @BeforeEach
    public void setUp() {
        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));
        testTaskStatus = Instancio.of(modelGenerator.getTaskStatusModel())
                .create();
    }

    @Test
    public void testTaskStatusIndex() throws Exception {
        taskStatusRepository.save(testTaskStatus);
        var result = mockMvc.perform(MockMvcRequestBuilders.get("/api/task_statuses").with(token))
                .andExpect(status().isOk())
                .andReturn();
        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    @Test
    public void testTaskStatusCreate() throws Exception {
        var dto = taskStatusMapper.map(testTaskStatus);
        var request = MockMvcRequestBuilders.post("/api/task_statuses")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));
        mockMvc.perform(request)
                .andExpect(status().isCreated());

        var taskStatus = taskStatusRepository.findBySlug(testTaskStatus.getSlug()).get();
        assertNotNull(taskStatus);
        assertThat(taskStatus.getSlug()).isEqualTo(testTaskStatus.getSlug());
    }

    @Test
    public void testTaskStatusUpdate() throws Exception {
        taskStatusRepository.save(testTaskStatus);

        var data = new TaskStatusUpdateDTO();
        data.setName(JsonNullable.of("blablabla"));

        var request = MockMvcRequestBuilders.put("/api/task_statuses/" + testTaskStatus.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        testTaskStatus = taskStatusRepository.findById(testTaskStatus.getId()).get();
        assertThat(testTaskStatus.getName()).isEqualTo(data.getName().get());
    }

    @Test
    public void testTaskStatusShow() throws Exception {
        taskStatusRepository.save(testTaskStatus);

        var request = MockMvcRequestBuilders.get("/api/task_statuses/" + testTaskStatus.getId()).with(jwt());
        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        var body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                v -> v.node("name").isEqualTo(testTaskStatus.getName()),
                v -> v.node("slug").isEqualTo(testTaskStatus.getSlug()));
    }

    @Test
    public void testTaskStatusDestroy() throws Exception {
        taskStatusRepository.save(testTaskStatus);
        var request = MockMvcRequestBuilders.delete("/api/task_statuses/" + testTaskStatus.getId()).with(token);
        mockMvc.perform(request)
                .andExpect(status().isNoContent());
        assertThat(taskStatusRepository.existsById(testTaskStatus.getId())).isEqualTo(false);
    }
}
