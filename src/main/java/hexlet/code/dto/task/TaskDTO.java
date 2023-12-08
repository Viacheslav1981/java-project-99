package hexlet.code.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskDTO {
    private Long id;

    private int index;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdAt;

    @JsonProperty("assignee_id")
    private Long assigneeId;

    @JsonProperty("title")
    private String name;

    @JsonProperty("content")
    private String description;

    private String status;

   // private JsonNullable<Set<Long>> taskLabelIds;
}
