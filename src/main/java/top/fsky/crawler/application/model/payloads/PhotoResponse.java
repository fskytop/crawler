package top.fsky.crawler.application.model.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.fsky.crawler.application.model.Detail;
import top.fsky.crawler.application.model.Tag;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PhotoResponse {
    private Long id;
    private String name;
    private String path;
    private String host;
    private String url;
    private Set<Tag> tags;
    private Detail detail;
}
