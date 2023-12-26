package hexlet.code.service;

import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.dto.task.TaskDTO;
import hexlet.code.dto.task.TaskParamsDTO;
import hexlet.code.dto.task.TaskUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.specification.TaskSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskSpecification specBuilder;


    public List<TaskDTO> getAll(TaskParamsDTO taskParamsDTO) {
        var spec = specBuilder.build(taskParamsDTO);
        var tasks = taskRepository.findAll(spec);
        return tasks.stream()
                .map(taskMapper::map)
                .toList();
    }

    public TaskDTO findById(Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Task with id %s not found", id)));
        return taskMapper.map(task);
    }

    /*
    public TaskDTO create(TaskCreateDTO taskData) {

        var task = taskMapper.map(taskData);

        taskRepository.save(task);
        return taskMapper.map(task);
    }

     */

    public TaskDTO create(TaskCreateDTO taskCreateDTO) {

        var task = taskMapper.map(taskCreateDTO);

        var taskStatusSlug = taskCreateDTO.getStatus();
        var taskStatus = taskStatusRepository.findBySlug(taskStatusSlug)
                .orElseThrow();
        task.setTaskStatus(taskStatus);

        var taskDataUserId = taskCreateDTO.getAssigneeId();
        //  if (taskDataUserId != null) {
        var assignee = userRepository.findById(taskDataUserId)
                .orElseThrow();
        task.setAssignee(assignee);

        //    } else {
        //        var assignee = userRepository.findById(1L).get();
        //       task.setAssignee(assignee);
        //   }

        taskRepository.save(task);
        return taskMapper.map(task);
    }

    public TaskDTO update(TaskUpdateDTO taskUpdateDTO, long id) {

        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Task with id %s not found", id)));

        var taskStatusSlug = taskUpdateDTO.getStatus();
        if (taskStatusSlug != null) {
            var taskStatus = taskStatusRepository.findBySlug(taskStatusSlug)
                    .orElseThrow();
            task.setTaskStatus(taskStatus);

        }
        //   var taskStatus = taskStatusRepository.findBySlug(String.valueOf(taskStatusSlug))
        //            .orElseThrow();
        //    task.setTaskStatus(taskStatus);

        var taskDataUserId = taskUpdateDTO.getAssigneeId();
        if (taskDataUserId != null) {
            var assignee = userRepository.findById(taskDataUserId)
                    .orElseThrow();
            task.setAssignee(assignee);
        }


        var taskLabelsIds = taskUpdateDTO.getTaskLabelIds();
        if (taskLabelsIds != null) {
            var labelSet = taskMapper.labelSet(taskLabelsIds);
            task.setLabels(labelSet);

        }


        taskMapper.update(taskUpdateDTO, task);
        taskRepository.save(task);

        return taskMapper.map(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);

    }
}


