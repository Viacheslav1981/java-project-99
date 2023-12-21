package hexlet.code.service;

import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.dto.task.TaskDTO;
import hexlet.code.dto.task.TaskParamsDTO;
import hexlet.code.dto.task.TaskUpdateDTO;
import hexlet.code.dto.taskStatus.TaskStatusDTO;
import hexlet.code.dto.user.UserDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.specification.TaskSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public TaskDTO create(TaskCreateDTO taskCreateDTO) {
      //  var taskLabelsIds = taskCreateDTO.getTaskLabelIds();

        var task = taskMapper.map(taskCreateDTO);

        var taskStatusSlug = taskCreateDTO.getStatus();
        var taskStatus = taskStatusRepository.findBySlug(taskStatusSlug)
                .orElseThrow();
        task.setTaskStatus(taskStatus);

        var taskDataUserId = taskCreateDTO.getAssigneeId();
        if (taskDataUserId != 0) {
            var assignee = userRepository.findById(taskDataUserId)
                    .orElseThrow();
            task.setAssignee(assignee);
        }

      //  var taskLabelsIds = taskCreateDTO.getTaskLabelIds();

     //   if (taskLabelsIds != null) {
      //      var labelSet = taskMapper.labelSet(taskLabelsIds);
      //      task.setLabels(labelSet);
      //  }

       // var labelSet = taskMapper.labelSet(taskLabelsIds);
      //  task.setLabels(labelSet);


        taskRepository.save(task);
        return taskMapper.map(task);
    }

    public TaskDTO update(TaskUpdateDTO taskUpdateDTO, long id) {
      //  var taskLabelsIds = taskUpdateDTO.getTaskLabelIds();

        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Task with id %s not found", id)));

        var taskStatusSlug = taskUpdateDTO.getStatus();
        var taskStatus = taskStatusRepository.findBySlug(taskStatusSlug)
                .orElseThrow();
        task.setTaskStatus(taskStatus);

        var taskDataUserId = taskUpdateDTO.getAssigneeId();
        if (taskDataUserId != 0) {
            var assignee = userRepository.findById(taskDataUserId)
                    .orElseThrow();
            task.setAssignee(assignee);
        }


        var taskLabelsIds = taskUpdateDTO.getTaskLabelIds();
        var labelSet = taskMapper.labelSet(taskLabelsIds);
        /*
        Set<Label> labelSet = new HashSet<>();
        if (!(taskLabels == null)) {
            for (Long labelId : taskLabels) {
                var label = labelRepository.findById(labelId).orElseThrow();
                labelSet.add(label);
            }
        }

         */
        task.setLabels(labelSet);



        taskMapper.update(taskUpdateDTO, task);
        taskRepository.save(task);

        return taskMapper.map(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);

    }
}
