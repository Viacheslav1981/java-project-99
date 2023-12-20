package hexlet.code.controller;

import hexlet.code.dto.label.LabelCreateDTO;
import hexlet.code.dto.label.LabelDTO;
import hexlet.code.dto.label.LabelUpdateDTO;
import hexlet.code.service.LabelService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/labels")
@AllArgsConstructor
public class LabelController {

    private final LabelService labelService;


    @GetMapping(path = "")
    // @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LabelDTO>> getAll() {
        var labels = labelService.getAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(labels.size()))
                .body(labels);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelDTO getLabel(@PathVariable long id) {
        return labelService.findById(id);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public LabelDTO create(@Valid @RequestBody LabelCreateDTO taskCreateDTO) {
        return labelService.create(taskCreateDTO);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelDTO update(@PathVariable long id, @RequestBody @Valid LabelUpdateDTO labelUpdateDTO) {
        return labelService.update(labelUpdateDTO, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        labelService.delete(id);
    }

}
