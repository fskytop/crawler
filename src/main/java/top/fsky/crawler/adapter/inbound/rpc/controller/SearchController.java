package top.fsky.crawler.adapter.inbound.rpc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.fsky.crawler.application.model.payloads.PagedResponse;
import top.fsky.crawler.application.model.payloads.PhotoResponse;
import top.fsky.crawler.application.service.PhotoService;
import top.fsky.crawler.application.utils.AppConstants;

@Slf4j
@RestController
@RequestMapping("/api/search")
@Api(value = "search", tags = "search")
public class SearchController {
    private final PhotoService photoService;
    
    @Autowired
    public SearchController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping
    @ApiOperation("search for photos")
    public PagedResponse<PhotoResponse> search(@RequestParam(value = "photos") String photos,
                                               @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return this.photoService.search(photos, page, size);
    }
}
