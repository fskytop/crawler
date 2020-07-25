package top.fsky.crawler.adapter.inbound.rpc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.fsky.crawler.adapter.inbound.spec.CriteriaParser;
import top.fsky.crawler.adapter.inbound.spec.GenericSpecificationsBuilder;
import top.fsky.crawler.adapter.inbound.spec.PhotoSpecification;
import top.fsky.crawler.application.model.Photo;
import top.fsky.crawler.application.repository.PhotoRepository;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/search")
@Api(value = "search", tags = "search")
public class SearchController {
    private final PhotoRepository photoRepository;
    
    @Autowired
    public SearchController(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @GetMapping
    @ApiOperation("search for photos")
    public List<Photo> search(@RequestParam(value = "photos") String photos) {
        Specification<Photo> spec = resolveSpecificationFromInfixExpr(photos);
        return this.photoRepository.findAll(spec);
    }

    protected Specification<Photo> resolveSpecificationFromInfixExpr(String searchParameters) {
        CriteriaParser parser = new CriteriaParser();
        GenericSpecificationsBuilder<Photo> specBuilder = new GenericSpecificationsBuilder<>();
        return specBuilder.build(parser.parse(searchParameters), PhotoSpecification::new);
    }
}
