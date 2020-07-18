package top.fsky.crawler.adapter.inbound.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import top.fsky.crawler.application.model.Detail;
import top.fsky.crawler.application.model.Photo;
import top.fsky.crawler.application.model.payloads.*;
import top.fsky.crawler.application.service.PhotoService;
import top.fsky.crawler.application.utils.AppConstants;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/photos")
@Api(value = "photos resource", tags = "photos resource")
public class PhotoResource {

    private final PhotoService photoService;
    
    @Autowired
    public PhotoResource(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping
    @ApiOperation("get products")
    public PagedResponse<PhotoResponse> getPhotos(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return photoService.getAllPhotos(page, size);
    }

    @GetMapping("/{photoId}")
    @ApiOperation("get photo by id")
    public PhotoResponse getPhotoById(@PathVariable Long photoId) {
        return photoService.getPhotoById(photoId);
    }

    @PostMapping
    @ApiOperation("create product")
    public ResponseEntity<?> createPhoto(@Valid @RequestBody PhotoRequest photoRequest) {
        Photo photo = photoService.createPhoto(photoRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{photoId}")
                .buildAndExpand(photo.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Photo Created Successfully"));
    }

    @PostMapping("/{photoId}/details")
    @ApiOperation("create details")
    public ResponseEntity<?> createPhotoDetails(@Valid @RequestBody PhotoDetailRequest photoDetailRequest, @PathVariable Long photoId) {
        Detail detail = photoService.createPhotoDetail(photoId, photoDetailRequest);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{photoId}/details/{detailId}")
                .buildAndExpand(photoId, detail.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Photo Detail Created Successfully"));
    }
    
}
