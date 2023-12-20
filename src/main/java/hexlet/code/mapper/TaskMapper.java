package hexlet.code.mapper;

import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.dto.task.TaskDTO;
import hexlet.code.dto.task.TaskUpdateDTO;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.repository.LabelRepository;
import lombok.Getter;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {

    @Autowired
    private LabelRepository labelRepository;

    @Mapping(target = "taskStatus.slug", source = "status")
    // @Mapping(target = "assignee.id", source = "assigneeId")
    @Mapping(target = "assignee.id", source = "assigneeId")
    @Mapping(target = "labels", source = "taskLabelIds")
    //  @Mapping(target = "labels.id",
    //  expression = "java(Task.getLabels().stream().map(i -> i.getId()).collect(getCollectors().toSet()))")
    public abstract Task map(TaskCreateDTO dto);


    /*
    public Set<Label> toSetLabels(Set<Long> labelIds) {
        if (labelIds == null) {
            return null;
        }
        return labelIds.stream()
                .map(labelId -> labelRepository.findById(labelId)
                        .orElseThrow())
                .collect(Collectors.toSet());
    }

     */

    public Set<Label> labelSet(Set<Long> labelIds) {
        return labelIds == null ? null :
                labelIds.stream()
                        .map(id -> labelRepository.findById(id)
                                .orElseThrow())
                        .collect(Collectors.toSet());
    }

    public Set<Long> labelLong(Set<Label> labels) {
        return labels == null ? null : labels.stream()
                .map(Label::getId)
                .collect(Collectors.toSet());

    }


    @Mapping(target = "status", source = "taskStatus.slug")
    @Mapping(target = "assigneeId", source = "assignee.id")
    // @Mapping(target = "labels", source = "taskLabelIds")
    @Mapping(target = "taskLabelIds", source = "labels")
    //  @Mapping(target = "labels", source = "taskLabelIds")
    //  @Mapping(target = "taskLabelIds",
    //      expression = "java(getJsonNullable().of(model.getLabels().stream()"
    //            + ".map(Label::getId).collect(getCollectors().toSet())))")
    public abstract TaskDTO map(Task model);

    public abstract void update(TaskUpdateDTO dto, @MappingTarget Task model);

    @Mapping(target = "status", source = "taskStatus.slug")
    @Mapping(target = "assigneeId", source = "assignee.id")
    @Mapping(target = "taskLabelIds", source = "labels")

    // @Mapping(target = "taskLabelIds", source = "labels")
    // @Mapping(target = "taskLabelIds", source = "labels")
    //  @Mapping(target = "taskLabelIds", source = "labels")
    //  @Mapping(target = "taskLabelIds",
    //        expression = "java(simpleService.enrichName(source.getName()))")

//    @Mapping(target = "taskLabelIds",
    //          expression = "java(model.getLabels())" )
    //  @Mapping(target = "taskLabelIds",
    //         expression = "java(model.getLabels().stream().map(i -> i.getId()).collect(getCollectors().toSet()))")
    public abstract TaskCreateDTO mapToCreateDTO(Task model);


}
