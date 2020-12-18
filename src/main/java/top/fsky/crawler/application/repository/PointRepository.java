package top.fsky.crawler.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.fsky.crawler.application.model.Point;

import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    @Override
    Optional<Point> findById(Long testKitId);
}
