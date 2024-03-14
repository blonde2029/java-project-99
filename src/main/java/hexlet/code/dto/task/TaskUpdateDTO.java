package hexlet.code.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import hexlet.code.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TaskUpdateDTO {
    @Size(min = 1)
    @NotBlank
    @JsonProperty("title")
    private JsonNullable<String> name;

    private JsonNullable<Integer> index;

    @JsonProperty("content")
    private JsonNullable<String> description;

    @NotNull
    @JsonProperty("status")
    private JsonNullable<TaskStatus> taskStatus;

    @JsonProperty("assignee_id")
    private JsonNullable<Long> assigneeId;
}