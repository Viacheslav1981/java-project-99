package hexlet.code.controller;


import hexlet.code.dto.*;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.TaskStatusService;
import hexlet.code.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task_statuses")
@AllArgsConstructor
public class TaskStatusController {

    private final TaskStatusRepository taskStatusRepository;
    private final TaskStatusService taskStatusService;

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskStatusDTO create(@Valid @RequestBody TaskStatusCreateDTO taskStatusCreateDTO) {
        return taskStatusService.create(taskStatusCreateDTO);
    }

    @GetMapping(path = "")
    // @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskStatusDTO>> getAll() {
        var taskStatuses = taskStatusService.getAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(taskStatuses.size()))
                .body(taskStatuses);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskStatusDTO getTaskStatus(@PathVariable Long id) {
        return taskStatusService.findById(id);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskStatusDTO update(@PathVariable Long id, @RequestBody TaskStatusUpdateDTO taskStatusUpdateDTO){
        return taskStatusService.update(taskStatusUpdateDTO, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskStatusService.delete(id);
    }


}
