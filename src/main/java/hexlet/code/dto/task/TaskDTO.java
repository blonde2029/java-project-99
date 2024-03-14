package hexlet.code.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskDTO {
    private Long id;
    private Integer index;
    @JsonProperty("title")
    private String name;
    @JsonProperty("content")
    private String description;
    private String status;
    @JsonProperty("assignee_id")
    private Long assigneeId;
    private LocalDate createdAt;
}
