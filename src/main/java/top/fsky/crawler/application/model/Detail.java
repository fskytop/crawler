package top.fsky.crawler.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "details")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 120)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;
}
