package top.fsky.crawler.application.model;

import lombok.*;
import top.fsky.crawler.application.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "photos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        })
})
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Photo extends UserDateAudit {
    
    private static final long serialVersionUID = 3345370590219284231L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 120)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String path;

    @NotBlank
    @Size(max = 60)
    private String host;

    @Size(max = 280)
    private String url;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "photo_tags",
            joinColumns = @JoinColumn(name = "photo_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();
}
