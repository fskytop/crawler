package top.fsky.crawler.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import top.fsky.crawler.adapter.inbound.payloads.PagedResponse;
import top.fsky.crawler.adapter.inbound.payloads.PhotoRequest;
import top.fsky.crawler.adapter.inbound.payloads.PhotoResponse;
import top.fsky.crawler.application.exception.BadRequestException;
import top.fsky.crawler.application.exception.ResourceNotFoundException;
import top.fsky.crawler.application.model.Photo;
import top.fsky.crawler.application.repository.PhotoRepository;
import top.fsky.crawler.application.utils.AppConstants;

import java.util.List;

@Slf4j
@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public PagedResponse<PhotoResponse> getAllProducts(int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Page<Photo> products = photoRepository.findAll(pageable);

        List<PhotoResponse> productResponses = products.map(
                product -> new PhotoResponse(product.getId(), product.getName())
        ).getContent();
        
        return new PagedResponse<>(productResponses, products.getNumber(),
                products.getSize(), products.getTotalElements(),
                products.getTotalPages(), products.isLast()
        );
    }

    public Photo createProduct(PhotoRequest productRequest) {
        Photo product = new Photo();
        product.setName(productRequest.getName());

        return photoRepository.save(product);
    }

    public PhotoResponse getProductById(Long productId) {
        Photo product = photoRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id", productId));

        PhotoResponse productResponse = new PhotoResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());

        return productResponse;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

}
