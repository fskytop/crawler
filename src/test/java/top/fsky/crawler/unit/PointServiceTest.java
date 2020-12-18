package top.fsky.crawler.unit;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import top.fsky.crawler.application.service.PointService;
import top.fsky.crawler.application.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {
    @Mock
    private PointRepository pointRepository;

    @Mock
    private SubjectRepository subjectRepository;

    private PointService pointService;
    private Point pointMoc;
    private PointRequest pointReqMoc;
    private Subject subjectMoc;

    @BeforeEach
    void initService(){
        pointService = new PointService(pointRepository, subjectRepository);
        subjectMoc = Subject.builder().id(1L).name("math").build(); 
        pointMoc = Point.builder().id(1L).point(10).description("math practice").subject(subjectMoc).build();
        pointReqMoc = PointRequest.builder().point(10).description("math practice").subjectName("math").build();
    }

    @Test
    void given_error_paged_info_when_get_pageable_response_then_throw_exception() {
        assertThrows(BadRequestException.class, () -> {
            pointService.getPoints(-1, 5);
        });

        assertThrows(BadRequestException.class, () -> {
            pointService.getPoints(10, -1);
        });

        assertThrows(BadRequestException.class, () -> {
            pointService.getPoints(10, AppConstants.MAX_PAGE_SIZE + 1);
        });
    }
    
    @Test
    void given_correct_paged_info_when_get_pageable_response_then_points_received() {
        List<Point> output = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            Point point = Point.builder().
                    id((long) (i + 1))
                    .point(10)
                    .description("math practice").build();
            output.add(point);
        }
        Page<Point> points = new PageImpl<Point>(output);
        when(pointRepository.findAll(any(Pageable.class))).thenReturn(points);

        PagedResponse<PointResponse> pagedResponse = pointService.getPoints(1, 3);
        assertEquals(1L, pagedResponse.getContent().get(0).getId());
        assertEquals(10, pagedResponse.getContent().get(0).getPoint());
        assertEquals("math practice", pagedResponse.getContent().get(0).getDescription());
        assertFalse(pagedResponse.getContent().isEmpty());
    }
    
    @Test
    void given_api_call_when_create_then_new_point_created(){
        when(subjectRepository.findByName(any(String.class))).thenReturn(Optional.ofNullable(subjectMoc));
        when(pointRepository.save(any(Point.class))).thenReturn(pointMoc);
        
        Point pointExpected = pointService.createPoint(pointReqMoc);

        assertEquals(pointExpected.getPoint(), pointMoc.getPoint());
        assertEquals(pointExpected.getDescription(), pointMoc.getDescription());
    }

    @Test
    void given_error_call_when_create_then_throw_exception() {
        when(subjectRepository.findByName(any(String.class))).thenReturn(Optional.ofNullable(subjectMoc));
        when(pointRepository.save(any(Point.class))).thenThrow(new DataIntegrityViolationException("sql error"));

        assertThrows(ConstraintException.class, () -> {
            pointService.createPoint(pointReqMoc);
        });
    }
    
    @Test
    void given_exist_pointId_when_getById_then_point_found_with_query_id(){
        when(pointRepository.findById(any())).thenReturn(Optional.ofNullable(pointMoc));

        PointResponse pointResponse = pointService.getPointById(1L);
        assertEquals(pointMoc.getId(), pointResponse.getId());
        assertEquals(pointMoc.getPoint(), pointResponse.getPoint());
        assertEquals(pointMoc.getDescription(), pointResponse.getDescription());
    }

    @Test
    void given_non_exist_pointId_when_getById_then_not_found_exception_received(){
        when(pointRepository.findById(any())).thenThrow(new ResourceNotFoundException("Point", "id", 2L));

        assertThrows(ResourceNotFoundException.class, () -> {
            pointService.getPointById(1L);
        });
    }
}
