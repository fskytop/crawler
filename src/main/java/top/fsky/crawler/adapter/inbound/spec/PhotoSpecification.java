package top.fsky.crawler.adapter.inbound.spec;

import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import top.fsky.crawler.application.model.Photo;
import top.fsky.crawler.application.model.TagName;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class PhotoSpecification implements Specification<Photo> {

    private final SpecSearchCriteria criteria;

    public PhotoSpecification(SpecSearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(final Root<Photo> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        switch (criteria.getOperation()) {
        case EQUALITY: 
            {
                String key = criteria.getKey();
                if (key.equals("tags")){
                    String[] tags = ((String) criteria.getValue()).split(",");
                    List<TagName> tagNames = new ArrayList<>();
                    for (String tag: tags) {
                        tagNames.add(TagName.valueOf(tag));
                    }
                    return root.join("tags").get("name").in(tagNames);
                }
                return builder.equal(root.get(criteria.getKey()), criteria.getValue()); 
            }
        case NEGATION:
            return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
        case GREATER_THAN:
            return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
        case LESS_THAN:
            return builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
        case LIKE:
            return builder.like(root.get(criteria.getKey()), criteria.getValue().toString());
        case STARTS_WITH:
            return builder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
        case ENDS_WITH:
            return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
        case CONTAINS:
            return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
        default:
            return null;
        }
    }
}
