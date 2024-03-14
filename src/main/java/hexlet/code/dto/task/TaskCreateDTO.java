package hexlet.code.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import hexlet.code.model.TaskStatus;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateDTO {
    @Size(min = 1)
    @NotBlank
    @JsonProperty("title")
    private String name;

    private Integer index;

    @JsonProperty("content")
    private String description;

    @NotNull
    @JsonProperty("status")
    private TaskStatus taskStatus;

    @JsonProperty("assignee_id")
    private Long assigneeId;
}
