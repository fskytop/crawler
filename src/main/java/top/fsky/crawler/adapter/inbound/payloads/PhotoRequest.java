package top.fsky.crawler.adapter.inbound.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PhotoRequest {
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
    
    private Set<String> tags;
}
