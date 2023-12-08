package hexlet.code.service;

import hexlet.code.dto.taskStatus.TaskStatusCreateDTO;
import hexlet.code.dto.taskStatus.TaskStatusDTO;
import hexlet.code.dto.taskStatus.TaskStatusUpdateDTO;
import hexlet.code.exception.ConstraintViolationException;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusService {

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskStatusMapper taskStatusMapper;


    public TaskStatusDTO create(TaskStatusCreateDTO taskStatusCreateDTO) {

        var statusSlug = taskStatusCreateDTO.getSlug();
        var findSlug = taskStatusRepository.findByName(statusSlug);
        if (findSlug.isPresent()) {
            throw new ConstraintViolationException(String.format("TaskStatus with name %s already exists", statusSlug));
        }

        var taskStatus = taskStatusMapper.map(taskStatusCreateDTO);
        taskStatusRepository.save(taskStatus);
        return taskStatusMapper.map(taskStatus);

    }

    public List<TaskStatusDTO> getAll() {
        var taskStatuses = taskStatusRepository.findAll();
        return taskStatuses.stream()
                .map(taskStatusMapper::map)
                .toList();
    }

    public TaskStatusDTO findById(Long id) {
        var taskStatus = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("TaskStatus with id %s not found", id)));
        return taskStatusMapper.map(taskStatus);
    }

    public TaskStatusDTO update(TaskStatusUpdateDTO taskStatusData, Long id) {
        var taskStatus = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("TaskStatus with id %s not found", id)));
        var statusSlug = taskStatusData.getSlug();

        if (statusSlug != null) {
            var findSlug = taskStatusRepository.findBySlug(statusSlug);
            if (findSlug.isPresent()) {
                throw new ConstraintViolationException(
                        String.format("TaskStatus with name %s already exists", statusSlug));
            }
        }
        taskStatusMapper.update(taskStatusData, taskStatus);
        taskStatusRepository.save(taskStatus);
        return taskStatusMapper.map(taskStatus);
    }

    public void delete(Long id) {
        taskStatusRepository.deleteById(id);
    }

}
