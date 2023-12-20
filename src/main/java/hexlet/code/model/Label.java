package hexlet.code.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "labels")
@EntityListeners(AuditingEntityListener.class)
public class Label implements BaseEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    @Size(min = 3, max = 1000)
    private String name;

    @CreatedDate
    private Date createdAt;

    //@ManyToMany(mappedBy = "labels")
    //private List<Task> tasks;

   // public Label() {}
}
