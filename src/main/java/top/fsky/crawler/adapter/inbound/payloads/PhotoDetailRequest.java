package top.fsky.crawler.adapter.inbound.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PhotoDetailRequest {
    @NotBlank
    @Size(max = 120)
    private String title;

    @NotBlank
    private String description;
}
