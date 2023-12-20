package hexlet.code.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import hexlet.code.model.Label;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.Date;
import java.util.Set;

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
    private Set<Long> taskLabelIds;

   // private JsonNullable<Set<Label>> taskLabelIds;
}
