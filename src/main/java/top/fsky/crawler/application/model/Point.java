package top.fsky.crawler.application.model;

import lombok.*;
import top.fsky.crawler.application.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "points")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Point extends UserDateAudit {
    private static final long serialVersionUID = 2196639685874180706L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer point;
    
    @NotBlank
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "point_subject",
            joinColumns = @JoinColumn(name = "point_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Subject subject;
}
