package hexlet.code.service;

import hexlet.code.dto.label.LabelCreateDTO;
import hexlet.code.dto.label.LabelDTO;
import hexlet.code.dto.label.LabelUpdateDTO;
import hexlet.code.dto.task.TaskDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {
    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private LabelMapper labelMapper;


    public List<LabelDTO> getAll() {
        var label = labelRepository.findAll();
        return label.stream()
                .map(labelMapper::map)
                .toList();
    }

    public LabelDTO findById(Long id) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Label with id %s not found", id)));
        return labelMapper.map(label);
    }

    public LabelDTO create(LabelCreateDTO labelCreateDTO){
        var label = labelMapper.map(labelCreateDTO);

        labelRepository.save(label);
        return labelMapper.map(label);

    }

    public LabelDTO update(LabelUpdateDTO labelUpdateDTO, long id){
        var label = labelRepository.findById(id).orElseThrow();

        label.setName(labelUpdateDTO.getName());
        labelRepository.save(label);

        return labelMapper.map(label);
    }

    public void delete(long id){
        labelRepository.deleteById(id);
    }



}
