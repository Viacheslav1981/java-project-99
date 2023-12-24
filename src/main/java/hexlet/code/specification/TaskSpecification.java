package hexlet.code.specification;

import hexlet.code.dto.task.TaskParamsDTO;
import hexlet.code.model.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TaskSpecification {
    public Specification<Task> build(TaskParamsDTO params) {
        return titleCont(params.getTitleCont())
                .and(assigneeId(params.getAssigneeId()))
                .and(status(params.getStatus()))
                .and(labelId(params.getLabelId()));
    }

    private Specification<Task> assigneeId(Long assigneeId) {
        return (root, query, cb) -> assigneeId == null ? cb.conjunction()
                : cb.equal(root.get("assignee").get("id"), assigneeId);
    }

    private Specification<Task> status(String status) {
        return (root, query, cb) -> status == null ? cb.conjunction()
                : cb.equal(root.get("taskStatus").get("name"), status);
    }

    private Specification<Task> labelId(Long labelId) {
        return (root, query, cb) -> labelId == null ? cb.conjunction()
                : cb.equal(root.get("labels").get("id"), labelId);
    }

    private Specification<Task> titleCont(String titleCont) {
        return (root, query, cb) -> titleCont == null ? cb.conjunction()
                : cb.like(root.get("name"), "%" + titleCont + "%");
    }


}
