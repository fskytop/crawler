package top.fsky.crawler.adapter.inbound.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import top.fsky.crawler.adapter.inbound.payloads.ApiResponse;
import top.fsky.crawler.adapter.inbound.payloads.PagedResponse;
import top.fsky.crawler.adapter.inbound.payloads.PhotoRequest;
import top.fsky.crawler.adapter.inbound.payloads.PhotoResponse;
import top.fsky.crawler.application.model.Photo;
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
    public PagedResponse<PhotoResponse> getProductions(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return photoService.getAllProducts(page, size);
    }

    @GetMapping("/{productId}")
    @ApiOperation("get product by id")
    public PhotoResponse getProductById(@PathVariable Long productId) {
        return photoService.getProductById(productId);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ApiOperation("create product")
    public ResponseEntity<?> createProduct(@Valid @RequestBody PhotoRequest productRequest) {
        Photo product = photoService.createProduct(productRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{productId}")
                .buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Product Created Successfully"));
    }
    
}
