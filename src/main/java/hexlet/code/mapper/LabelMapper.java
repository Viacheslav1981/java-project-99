package hexlet.code.mapper;

import hexlet.code.dto.label.LabelCreateDTO;
import hexlet.code.dto.label.LabelDTO;
import hexlet.code.dto.label.LabelUpdateDTO;
import hexlet.code.model.Label;
import lombok.Getter;
import org.mapstruct.*;

@Getter
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class LabelMapper {

    public abstract Label map(LabelCreateDTO dto);

    public abstract LabelDTO map(Label model);

    public abstract void update(LabelUpdateDTO dto, @MappingTarget Label model);

    public abstract LabelCreateDTO mapToCreateDTO(Label model);
}
