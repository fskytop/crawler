package top.fsky.crawler.application.model.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PointRequest {
    @NotNull
    @Max(1000)
    @Min(-999)
    private Integer point;
    
    @NotNull
    private String description;
    
    @NotNull
    private String subjectName;
}
