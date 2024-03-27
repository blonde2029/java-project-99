package hexlet.code.mapper;

import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.dto.task.TaskDTO;
import hexlet.code.dto.task.TaskUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class TaskMapper {
    @Autowired
    private TaskStatusRepository taskStatusRepository;
    @Autowired
    private LabelRepository labelRepository;
    @Mapping(target = "assignee", source = "assigneeId")
    @Mapping(target = "taskStatus", source = "status", qualifiedByName = "getStatusBySlug")
    @Mapping(target = "labels", source = "taskLabelIds", qualifiedByName = "getLabelsByIds")
    public abstract Task map(TaskCreateDTO dto);

    @Mapping(target = "assigneeId", source = "assignee.id")
    @Mapping(target = "status", source = "taskStatus.slug")
    @Mapping(target = "taskLabelIds", source = "labels", qualifiedByName = "getIdsByLabels")
    public abstract TaskDTO map(Task model);

    @Mapping(target = "assignee", source = "assigneeId")
    @Mapping(target = "taskStatus", source = "status", qualifiedByName = "getStatusBySlug")
    @Mapping(target = "labels", source = "taskLabelIds", qualifiedByName = "getLabelsByIds")
    public abstract void update(TaskUpdateDTO dto, @MappingTarget Task model);

    @Named("getStatusBySlug")
    TaskStatus getStatusBySlug(String status) {
        return taskStatusRepository.findBySlug(status)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Status with slug " + status + " not found"));
    }

    @Named("getLabelsByIds")
    Set<Label> getLabelsByIds(Set<Long> ids) {
        var result = new HashSet<Label>();
        if (ids == null) {
            return result;
        }
        for (var labelId : ids) {
            var label = labelRepository.findById(labelId).get();
            result.add(label);
        }
        return result;
    }

    @Named("getIdsByLabels")
    Set<Long> getIdsByLabels(Set<Label> labels) {
        var result = labels.stream()
                .map(Label::getId)
                .collect(Collectors.toSet());
        return result;
    }
}
