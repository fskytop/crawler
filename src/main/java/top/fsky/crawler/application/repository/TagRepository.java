package top.fsky.crawler.application.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.fsky.crawler.application.model.Tag;
import top.fsky.crawler.application.model.TagName;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(TagName tagName);
}
