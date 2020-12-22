package top.fsky.crawler.application.model.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PointResponse {
    private Long id;
    private int point;
    private String description;
    private Long createdAt;
}
