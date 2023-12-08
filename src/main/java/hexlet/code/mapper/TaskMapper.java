package hexlet.code.mapper;

import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.dto.task.TaskDTO;
import hexlet.code.dto.task.TaskUpdateDTO;
import hexlet.code.model.Task;
import lombok.Getter;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Getter
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {

    private Collectors collectors;

    @Mapping(target = "taskStatus.slug", source = "status")
   // @Mapping(target = "assignee.id", source = "assigneeId")
    @Mapping(target = "assignee.id", source = "assigneeId")
  //  @Mapping(target = "labels", source = "taskLabelIds")
    public abstract Task map(TaskCreateDTO dto);

    @Mapping(target = "status", source = "taskStatus.slug")
    @Mapping(target = "assigneeId", source = "assignee.id")
    //  @Mapping(target = "taskLabelIds",
      //      expression = "java(getJsonNullable().of(model.getLabels().stream()"
        //            + ".map(Label::getId).collect(getCollectors().toSet())))")
    public abstract TaskDTO map(Task model);

    public abstract void update(TaskUpdateDTO dto, @MappingTarget Task model);

    @Mapping(target = "status", source = "taskStatus.slug")
    @Mapping(target = "assigneeId", source = "assignee.id")
   // @Mapping(target = "taskLabelIds",
     //       expression = "java(model.getLabels().stream().map(i -> i.getId()).collect(getCollectors().toSet()))")
    public abstract TaskCreateDTO mapToCreateDTO(Task model);




}
