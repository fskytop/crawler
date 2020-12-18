package top.fsky.crawler.adapter.inbound.rpc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import top.fsky.crawler.application.model.Point;
import top.fsky.crawler.application.model.payloads.ApiResponse;
import top.fsky.crawler.application.model.payloads.PagedResponse;
import top.fsky.crawler.application.model.payloads.PointRequest;
import top.fsky.crawler.application.model.payloads.PointResponse;
import top.fsky.crawler.application.service.PointService;
import top.fsky.crawler.application.utils.AppConstants;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/points")
@Api(value = "point resource", tags = "point resource")
public class PointController {

    private final PointService pointService;
    
    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping
    @ApiOperation("get points")
    public PagedResponse<PointResponse> getPoints(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return pointService.getPoints(page, size);
    }

    @GetMapping("/{pointId}")
    @ApiOperation("get point by id")
    public PointResponse getPointById(@PathVariable Long pointId) {
        return pointService.getPointById(pointId);
    }
    
    @PostMapping
    @ApiOperation("create point")
    public ResponseEntity<?> createPoint(@Valid @RequestBody PointRequest pointRequest) {
        Point point = pointService.createPoint(pointRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pointId}")
                .buildAndExpand(point.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Point Created Successfully"));
    }
    
}
