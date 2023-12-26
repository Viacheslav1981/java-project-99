package hexlet.code.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "tasks")
@EntityListeners(AuditingEntityListener.class)
public class Task implements BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1)
    private String name;

    private Integer index;

    private String description;

    @NotNull
    @ManyToOne
    private TaskStatus taskStatus;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    //  @JoinColumn(name = "assignee_id", nullable = true)
    //@JoinColumn(columnDefinition = "long", name = "assignee_id")
    // @Column(nullable=true)
    // @ManyToOne(fetch = FetchType.LAZY)

    //@Column(nullable = true)
    // @ManyToOne(cascade = CascadeType.MERGE)
    private User assignee;
    //  private JsonNullable<User> assignee;
    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    private Set<Label> labels;

    @CreatedDate
    private Date createdAt;

}


