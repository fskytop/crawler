package top.fsky.crawler.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.fsky.crawler.application.model.Detail;

import java.util.Optional;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Long> {
    @Override
    Optional<Detail> findById(Long detailId);
}
