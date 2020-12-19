package top.fsky.crawler.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import top.fsky.crawler.application.exception.BadRequestException;
import top.fsky.crawler.application.exception.ConstraintException;
import top.fsky.crawler.application.exception.ResourceNotFoundException;
import top.fsky.crawler.application.model.Point;
import top.fsky.crawler.application.model.Subject;
import top.fsky.crawler.application.model.payloads.PagedResponse;
import top.fsky.crawler.application.model.payloads.PointRequest;
import top.fsky.crawler.application.model.payloads.PointResponse;
import top.fsky.crawler.application.repository.PointRepository;
import top.fsky.crawler.application.repository.SubjectRepository;
import top.fsky.crawler.application.utils.AppConstants;

import java.util.List;

@Slf4j
@Service
public class PointService {

    private final PointRepository pointRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public PointService(
            PointRepository pointRepository,
            SubjectRepository subjectRepository
    ) {
        this.pointRepository = pointRepository;
        this.subjectRepository = subjectRepository;
    }

    public PagedResponse<PointResponse> getPoints(int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Page<Point> points = pointRepository.findAll(pageable);

        List<PointResponse> pointResponses = points.map(
                point -> new PointResponse(
                        point.getId(), 
                        point.getPoint(), 
                        point.getDescription())
        ).getContent();
        
        return new PagedResponse<>(pointResponses, points.getNumber(),
                points.getSize(), points.getTotalElements(),
                points.getTotalPages(), points.isLast()
        );
    }
    
    public Long sumPoints(){
        return pointRepository.sumPoint();
    }
    
    public Point createPoint(PointRequest pointRequest) {
        Point point = new Point();
        point.setPoint(pointRequest.getPoint());
        point.setDescription(pointRequest.getDescription());
        
        Subject subject = this.findSubjectByName(pointRequest.getSubjectName());
        point.setSubject(subject);
        
        try {
            return pointRepository.save(point);
        } catch (DataIntegrityViolationException e){
            throw new ConstraintException(e.getMessage(), e);
        }
    }

    public PointResponse getPointById(Long pointId) {
        Point point = this.findPointById(pointId);

        return this.mappingPointToResponse(point);
    }

    private Point findPointById(Long pointId){
        return pointRepository.findById(pointId).orElseThrow(
                () -> new ResourceNotFoundException("Point", "id", pointId));
    }

    private Subject findSubjectByName(String name){
        return subjectRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Subject", "name", name));
    }
    
    private PointResponse mappingPointToResponse(Point point){
        PointResponse pointResponse = new PointResponse();
        pointResponse.setId(point.getId());
        pointResponse.setPoint(point.getPoint());
        pointResponse.setDescription(point.getDescription());
        
        return pointResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException(AppConstants.PAGE_NUMBER_0_WARNING_MSG);
        }

        if (size < 0 || size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException(AppConstants.PAGE_NUMBER_GREATER_MSG + AppConstants.MAX_PAGE_SIZE);
        }
    }
}
